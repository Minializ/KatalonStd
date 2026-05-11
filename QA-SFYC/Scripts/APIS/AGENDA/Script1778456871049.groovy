import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.configuration.RunConfiguration
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

// =============================================================
// SFYC NextGen Agenda QA - Casos de Prueba de APIs
// Sistema   : SFyC NextGen QA Cluster
// Generado  : 2026-05-10
// Endpoints : data.qa-cluster.sfycnextgen.com.mx
//             mgcl-api-megacable-sfyc.dev.mega-mule.mx
//             inn-wsdiqueconsumer-sys-api.dev.mega-mule.mx
// =============================================================

// ---- Variables globales ----
def BASE_URL_DATA  = "https://data.qa-cluster.sfycnextgen.com.mx"
def BASE_URL_MULE  = "https://mgcl-api-megacable-sfyc.dev.mega-mule.mx"
def BASE_URL_INN   = "https://inn-wsdiqueconsumer-sys-api.dev.mega-mule.mx"
def BRANCH_ID      = 41
def SUBSCRIBER_NUM = 1521390
def BRANCH_GROUP   = "041"
def SUB_CONTRACT   = "0411521390"
def MAC_ADDRESS    = "48575443DEFD32AB"

// ---- Autenticacion OAuth automatica via Authorization Code + PKCE ----
// El IDP (IdentityServer4) NO soporta grant_type=password.
// Este flujo automatiza el login del navegador usando HttpURLConnection.
def IDP_BASE       = "https://idp.qa-cluster.sfycnextgen.com.mx/ev2/security"
def IDP_TOKEN_URL  = "${IDP_BASE}/connect/token"
def IDP_AUTHORIZE  = "${IDP_BASE}/connect/authorize"
def IDP_LOGIN_URL  = "${IDP_BASE}/Account/Login"
def CLIENT_ID      = "sfyc-menu"
def REDIRECT_URI   = "https://global.qa-cluster.sfycnextgen.com.mx/ui/signin-oidc"
def API_USER       = GlobalVariable.USER_AGENDA
def API_PASSWORD   = passwordAPI
def DOMAIN         = "sfyc"

def SCOPES = "openid profile roles equipmentApi subscriberApi saleApi serviceApi " +
    "workOrderApi contractApi chargeApi addressApi branchApi userApi logbookApi " +
    "paymentApi cashierApi debtCalculationApi administrativeComplaintApi materialApi sfycApi"

println "\n===== Obteniendo token OAuth via Authorization Code + PKCE ====="

// --- Paso 1: Generar PKCE code_verifier y code_challenge ---
import java.security.SecureRandom
import java.security.MessageDigest

byte[] randomBytes = new byte[32]
new SecureRandom().nextBytes(randomBytes)
def codeVerifier = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
byte[] sha256 = MessageDigest.getInstance("SHA-256").digest(codeVerifier.getBytes("ASCII"))
def codeChallenge = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(sha256)
println "  -> PKCE code_verifier generado (${codeVerifier.length()} chars)"

// --- Paso 2: GET /authorize -> redirige a login page, capturar cookies + CSRF ---
def encRedirectUri = java.net.URLEncoder.encode(REDIRECT_URI, "UTF-8")
def encScopes = java.net.URLEncoder.encode(SCOPES, "UTF-8")
def authorizeUrl = "${IDP_AUTHORIZE}?client_id=${CLIENT_ID}" +
    "&redirect_uri=${encRedirectUri}&response_type=code&scope=${encScopes}" +
    "&code_challenge=${codeChallenge}&code_challenge_method=S256" +
    "&state=katalon_api_test&nonce=${System.currentTimeMillis()}"

def cookieManager = new java.net.CookieManager()
java.net.CookieHandler.setDefault(cookieManager)

// Funcion helper para HTTP requests con cookies y sin seguir redirects automaticos
def httpGet = { String urlStr, boolean followRedirects = true ->
    def conn = new java.net.URL(urlStr).openConnection() as java.net.HttpURLConnection
    conn.setInstanceFollowRedirects(followRedirects)
    conn.setRequestMethod("GET")
    conn.connect()
    return conn
}

// GET authorize - seguir redirects hasta llegar al login page
def conn = httpGet(authorizeUrl)
def loginPageHtml = ""
if (conn.responseCode == 200) {
    loginPageHtml = conn.inputStream.getText("UTF-8")
} else if (conn.responseCode == 302) {
    // Seguir el redirect manualmente
    def loc = conn.getHeaderField("Location")
    if (!loc.startsWith("http")) loc = "https://idp.qa-cluster.sfycnextgen.com.mx" + loc
    def conn2 = httpGet(loc)
    loginPageHtml = conn2.inputStream.getText("UTF-8")
}
assert loginPageHtml.contains("__RequestVerificationToken") : \
    "ERROR: No se pudo obtener la pagina de login del IDP"
println "  -> Pagina de login obtenida"

// Extraer __RequestVerificationToken y ReturnUrl del HTML
def csrfMatcher = (loginPageHtml =~ /__RequestVerificationToken.*?value="([^"]+)"/)
def returnUrlMatcher = (loginPageHtml =~ /id="ReturnUrl".*?value="([^"]+)"/)
assert csrfMatcher.find() : "ERROR: No se encontro __RequestVerificationToken"
assert returnUrlMatcher.find() : "ERROR: No se encontro ReturnUrl"
def csrfToken = csrfMatcher.group(1)
// Decodificar entidades HTML en ReturnUrl (&amp; -> &)
def returnUrl = returnUrlMatcher.group(1).replace("&amp;", "&")
println "  -> CSRF token y ReturnUrl extraidos"

// --- Paso 3: POST login con credenciales ---
def loginPostBody = "ReturnUrl=${java.net.URLEncoder.encode(returnUrl, 'UTF-8')}" +
    "&Domain=${DOMAIN}" +
    "&Username=${java.net.URLEncoder.encode(API_USER, 'UTF-8')}" +
    "&Password=${java.net.URLEncoder.encode(API_PASSWORD, 'UTF-8')}" +
    "&button=login" +
    "&__RequestVerificationToken=${java.net.URLEncoder.encode(csrfToken, 'UTF-8')}"

def loginConn = new java.net.URL(IDP_LOGIN_URL).openConnection() as java.net.HttpURLConnection
loginConn.setInstanceFollowRedirects(false)
loginConn.setRequestMethod("POST")
loginConn.setDoOutput(true)
loginConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
loginConn.outputStream.write(loginPostBody.getBytes("UTF-8"))
loginConn.outputStream.flush()
assert loginConn.responseCode == 302 : \
    "ERROR: Login fallo. Status: ${loginConn.responseCode}"
def loginRedirect = loginConn.getHeaderField("Location")
println "  -> Login exitoso, redireccion obtenida"

// --- Paso 4: Seguir redirect al authorize/callback para obtener el code ---
def callbackUrl = loginRedirect.startsWith("http") ? loginRedirect : \
    "https://idp.qa-cluster.sfycnextgen.com.mx" + loginRedirect
def callbackConn = new java.net.URL(callbackUrl).openConnection() as java.net.HttpURLConnection
callbackConn.setInstanceFollowRedirects(false)
callbackConn.setRequestMethod("GET")
callbackConn.connect()
assert callbackConn.responseCode == 302 : \
    "ERROR: Callback no retorno redirect. Status: ${callbackConn.responseCode}"
def codeRedirect = callbackConn.getHeaderField("Location")
// Extraer el code del query string
def codeUri = new java.net.URI(codeRedirect)
def queryParams = codeUri.query.split("&").collectEntries { param ->
    def parts = param.split("=", 2)
    [(parts[0]): java.net.URLDecoder.decode(parts[1], "UTF-8")]
}
def authCode = queryParams["code"]
assert authCode : "ERROR: No se obtuvo authorization code de la respuesta"
println "  -> Authorization code obtenido: ${authCode.substring(0, 20)}..."

// --- Paso 5: Intercambiar code por access_token ---
def tokenBody = "grant_type=authorization_code" +
    "&client_id=${CLIENT_ID}" +
    "&code=${authCode}" +
    "&redirect_uri=${encRedirectUri}" +
    "&code_verifier=${codeVerifier}"

RequestObject tokenReq = new RequestObject()
tokenReq.setRestUrl(IDP_TOKEN_URL)
tokenReq.setRestRequestMethod("POST")
tokenReq.setBodyContent(new com.kms.katalon.core.testobject.impl.HttpTextBodyContent(
    tokenBody, "UTF-8", "application/x-www-form-urlencoded"
))
tokenReq.setHttpHeaderProperties([
    new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/x-www-form-urlencoded")
])

ResponseObject tokenResp = WS.sendRequest(tokenReq)
assert tokenResp.getStatusCode() == 200 : \
    "ERROR: No se pudo obtener token OAuth. Status: ${tokenResp.getStatusCode()}\n" +
    "Body: ${tokenResp.getResponseBodyContent()}"

def tokenJson = new JsonSlurper().parseText(tokenResp.getResponseBodyContent())
AUTH_TOKEN = "Bearer ${tokenJson.access_token}"
println "  -> Token obtenido exitosamente (expira en ${tokenJson.expires_in}s)"
println "  -> Usuario: ${API_USER}"

// Variables extraidas por correlacion (sin 'def' para que sean accesibles en closures)
idSuscriptor   = ""
idDomicilio    = ""
idColonia      = ""
contractId     = ""
folioQueja     = ""

parser = new JsonSlurper()

// Helper para crear request POST con JSON body
def buildPostRequest(String url, String body) {
    RequestObject req = new RequestObject()
    req.setRestUrl(url)
    req.setRestRequestMethod("POST")
    req.setBodyContent(new com.kms.katalon.core.testobject.impl.HttpTextBodyContent(
        body, "UTF-8", "application/json"
    ))
    def headers = [
        new TestObjectProperty("Content-Type",  ConditionType.EQUALS, "application/json"),
        new TestObjectProperty("Accept",        ConditionType.EQUALS, "application/json"),
        new TestObjectProperty("Authorization", ConditionType.EQUALS, AUTH_TOKEN)
    ]
    req.setHttpHeaderProperties(headers)
    return req
}

// Helper para crear request GET
def buildGetRequest(String url) {
    RequestObject req = new RequestObject()
    req.setRestUrl(url)
    req.setRestRequestMethod("GET")
    def headers = [
        new TestObjectProperty("Accept",        ConditionType.EQUALS, "application/json"),
        new TestObjectProperty("Authorization", ConditionType.EQUALS, AUTH_TOKEN)
    ]
    req.setHttpHeaderProperties(headers)
    return req
}

// Helper para verificar que un campo del JSON no sea null
def verifyNotNull(ResponseObject resp, String fieldPath) {
    def json = new JsonSlurper().parseText(resp.getResponseBodyContent())
    def value = fieldPath.tokenize('.').inject(json) { obj, prop ->
        if (obj == null) return null
        // Soportar arrays como "[0].campo"
        def arrayMatch = (prop =~ /^\[(\d+)\]$/)
        if (arrayMatch.matches()) return obj[arrayMatch.group(1).toInteger()]
        // Soportar "campo[0]"
        def propArray = (prop =~ /^(\w+)\[(\d+)\]$/)
        if (propArray.matches()) return obj[propArray.group(1)]?.getAt(propArray.group(2).toInteger())
        return obj[prop]
    }
    assert value != null : "Campo '${fieldPath}' es null en la respuesta"
}

// ================================================================
// TC01 - POST GetGeneralData (subscribers-process)
// Valida datos generales del suscriptor y correlaciona IDs
// ================================================================
println "\n===== TC01 - GetGeneralData ====="
def reqTC01 = buildPostRequest(
    "${BASE_URL_DATA}/subscribers-process/api/GetGeneralData",
    """{"branchId":${BRANCH_ID},"subscriberNumber":${SUBSCRIBER_NUM}}"""
)
ResponseObject respTC01 = WS.sendRequest(reqTC01)

WS.verifyResponseStatusCode(respTC01, 200)
WS.verifyElementPropertyValue(respTC01, "sucursal",    BRANCH_ID)
WS.verifyElementPropertyValue(respTC01, "suscriptor",  SUBSCRIBER_NUM)
verifyNotNull(respTC01, "id_suscriptor")
verifyNotNull(respTC01, "id_domicilio")
verifyNotNull(respTC01, "id_colonia")
verifyNotNull(respTC01, "sucursal_desc")

// Correlacion de IDs para TCs posteriores
def bodyTC01 = parser.parseText(respTC01.getResponseBodyContent())
idSuscriptor = bodyTC01.id_suscriptor
idDomicilio  = bodyTC01.id_domicilio
idColonia    = bodyTC01.id_colonia
println "  -> id_suscriptor: ${idSuscriptor}"
println "  -> id_colonia:    ${idColonia}"
println "  TC01 PASSED"

// ================================================================
// TC02 - POST GetEquipmentsTechnicalData (services-process)
// Valida equipos tecnicos y extrae MAC del primer equipo
// ================================================================
println "\n===== TC02 - GetEquipmentsTechnicalData ====="
def reqTC02 = buildPostRequest(
    "${BASE_URL_DATA}/services-process/api/GetEquipmentsTechnicalData",
    """{"branchId":${BRANCH_ID},"subscriberNumber":${SUBSCRIBER_NUM}}"""
)
ResponseObject respTC02 = WS.sendRequest(reqTC02)

WS.verifyResponseStatusCode(respTC02, 200)
verifyNotNull(respTC02, "[0].serie")
verifyNotNull(respTC02, "[0].modelo")
verifyNotNull(respTC02, "[0].estado")
WS.verifyElementPropertyValue(respTC02, "[0].suscriptor", SUBSCRIBER_NUM)
WS.verifyElementPropertyValue(respTC02, "[0].sucursal",   BRANCH_ID)

def bodyTC02 = parser.parseText(respTC02.getResponseBodyContent())
assert bodyTC02 instanceof List : "TC02: La respuesta debe ser un array de equipos"
assert bodyTC02.size() > 0      : "TC02: El array de equipos no debe estar vacio"
println "  -> Equipos encontrados: ${bodyTC02.size()}"
println "  -> MAC primer equipo: ${bodyTC02[0].serie}"
println "  TC02 PASSED"

// ================================================================
// TC03 - POST GetContractsSubscriber (contracts-process)
// Valida contratos activos y correlaciona contractId
// ================================================================
println "\n===== TC03 - GetContractsSubscriber ====="
def reqTC03 = buildPostRequest(
    "${BASE_URL_DATA}/contracts-process/api/GetContractsSubscriber",
    """{"branchId":${BRANCH_ID},"subscriberNumber":${SUBSCRIBER_NUM}}"""
)
ResponseObject respTC03 = WS.sendRequest(reqTC03)

WS.verifyResponseStatusCode(respTC03, 200)
verifyNotNull(respTC03, "result.subscriberStatus")
verifyNotNull(respTC03, "result.activeContracts")

def bodyTC03  = parser.parseText(respTC03.getResponseBodyContent())
def statusSub = bodyTC03.result.subscriberStatus
assert statusSub != null && statusSub != "" : "TC03: subscriberStatus no debe estar vacio"

def activeContracts = bodyTC03.result.activeContracts
assert activeContracts != null : "TC03: activeContracts debe existir"
if (activeContracts.size() > 0) {
    contractId = activeContracts[0].contractId
    println "  -> subscriberStatus: ${statusSub}"
    println "  -> Contratos activos: ${activeContracts.size()}"
    println "  -> contractId: ${contractId}"
}
println "  TC03 PASSED"

// ================================================================
// TC04 - POST GetSubscriberPackage (debt-calculation-process)
// Valida descripcion del paquete del suscriptor
// ================================================================
println "\n===== TC04 - GetSubscriberPackage ====="
def reqTC04 = buildPostRequest(
    "${BASE_URL_DATA}/debt-calculation-process/api/GetSubscriberPackage",
    """{"subscriberContract":"${SUB_CONTRACT}","branchGroupId":"${BRANCH_GROUP}"}"""
)
ResponseObject respTC04 = WS.sendRequest(reqTC04)

WS.verifyResponseStatusCode(respTC04, 200)
WS.verifyElementPropertyValue(respTC04, "codigo", 0)
verifyNotNull(respTC04, "mensajes[0]")

def bodyTC04 = parser.parseText(respTC04.getResponseBodyContent())
assert bodyTC04.codigo == 0            : "TC04: codigo debe ser 0 (exito)"
assert bodyTC04.mensajes.size() > 0    : "TC04: mensajes no debe estar vacio"
println "  -> Paquete: ${bodyTC04.mensajes[0]}"
println "  TC04 PASSED"

// ================================================================
// TC05 - POST ValidateExistSubscriber (sfyc)
// Valida que el suscriptor existe en el sistema
// ================================================================
println "\n===== TC05 - ValidateExistSubscriber ====="
def reqTC05 = buildPostRequest(
    "${BASE_URL_DATA}/sfyc/api/ValidateExistSubscriber",
    """{"branchId":${BRANCH_ID},"subscriberNumber":${SUBSCRIBER_NUM}}"""
)
ResponseObject respTC05 = WS.sendRequest(reqTC05)

WS.verifyResponseStatusCode(respTC05, 200)
verifyNotNull(respTC05, "result")

def bodyTC05 = parser.parseText(respTC05.getResponseBodyContent())
assert bodyTC05.result != null : "TC05: result no debe ser null"
println "  TC05 PASSED"

// ================================================================
// TC06 - GET GetSkillWarrantyService (subscribers-process)
// Valida garantia y habilidades de servicio del suscriptor
// ================================================================
println "\n===== TC06 - GetSkillWarrantyService ====="
def reqTC06 = buildGetRequest(
    "${BASE_URL_DATA}/subscribers-process/api/GetSkillWarrantyService/${SUBSCRIBER_NUM}/${BRANCH_GROUP}"
)
ResponseObject respTC06 = WS.sendRequest(reqTC06)

WS.verifyResponseStatusCode(respTC06, 200)
WS.verifyElementPropertyValue(respTC06, "value.validationResult.isValid", true)
WS.verifyElementPropertyValue(respTC06, "statusCode", 200)

def bodyTC06 = parser.parseText(respTC06.getResponseBodyContent())
assert bodyTC06.value.validationResult.isValid == true : "TC06: validationResult.isValid debe ser true"
println "  -> Warranty result: ${bodyTC06.value.result}"
println "  TC06 PASSED"

// ================================================================
// TC07 - GET GetSubscriberAgreement (services-process)
// Valida acuerdo de servicio del suscriptor
// ================================================================
println "\n===== TC07 - GetSubscriberAgreement ====="
def reqTC07 = buildGetRequest(
    "${BASE_URL_DATA}/services-process/api/GetSubscriberAgreement/${BRANCH_ID}/${SUBSCRIBER_NUM}"
)
ResponseObject respTC07 = WS.sendRequest(reqTC07)

WS.verifyResponseStatusCode(respTC07, 200)
WS.verifyElementPropertyValue(respTC07, "validationResult.isValid", true)

def bodyTC07 = parser.parseText(respTC07.getResponseBodyContent())
assert bodyTC07.validationResult.isValid == true : "TC07: validationResult.isValid debe ser true"
println "  TC07 PASSED"

// ================================================================
// TC08 - POST GetOrderWorks (workorders-process)
// Valida ordenes de trabajo del suscriptor
// ================================================================
println "\n===== TC08 - GetOrderWorks ====="
def reqTC08 = buildPostRequest(
    "${BASE_URL_DATA}/workorders-process/api/GetOrderWorks",
    """{"branchId":${BRANCH_ID},"subscriberNumber":${SUBSCRIBER_NUM}}"""
)
ResponseObject respTC08 = WS.sendRequest(reqTC08)

WS.verifyResponseStatusCode(respTC08, 200)
def bodyTC08 = parser.parseText(respTC08.getResponseBodyContent())
assert bodyTC08 instanceof List : "TC08: La respuesta debe ser un array"
println "  -> Ordenes de trabajo: ${bodyTC08.size()}"
println "  TC08 PASSED"

// ================================================================
// TC09 - POST ScheduleGetOrderWorks (workorders-process)
// Valida ordenes de trabajo agendadas
// ================================================================
println "\n===== TC09 - ScheduleGetOrderWorks ====="
def reqTC09 = buildPostRequest(
    "${BASE_URL_DATA}/workorders-process/api/ScheduleGetOrderWorks",
    """{"branchId":${BRANCH_ID},"subscriberNumber":${SUBSCRIBER_NUM},"orderWorkFilter":0,"isAll":false}"""
)
ResponseObject respTC09 = WS.sendRequest(reqTC09)

WS.verifyResponseStatusCode(respTC09, 200)
def bodyTC09 = parser.parseText(respTC09.getResponseBodyContent())
assert bodyTC09 instanceof List : "TC09: La respuesta debe ser un array"
if (bodyTC09.size() > 0) {
    assert bodyTC09[0].sucursal != null  : "TC09: sucursal debe estar presente"
    assert bodyTC09[0].suscriptor != null: "TC09: suscriptor debe estar presente"
    println "  -> OTs agendadas: ${bodyTC09.size()}"
    println "  -> Primer folio: ${bodyTC09[0].folio_trabajo}"
}
println "  TC09 PASSED"

// ================================================================
// TC10 - GET GetTypeOrderWorkDescriptions (workorders-process)
// Valida las descripciones de tipos de OT disponibles
// ================================================================
println "\n===== TC10 - GetTypeOrderWorkDescriptions ====="
def reqTC10 = buildGetRequest(
    "${BASE_URL_DATA}/workorders-process/api/GetTypeOrderWorkDescriptions/${BRANCH_ID}/${SUBSCRIBER_NUM}"
)
ResponseObject respTC10 = WS.sendRequest(reqTC10)

WS.verifyResponseStatusCode(respTC10, 200)
def bodyTC10 = parser.parseText(respTC10.getResponseBodyContent())
assert bodyTC10 instanceof List : "TC10: La respuesta debe ser un array de strings"
assert bodyTC10.size() > 0     : "TC10: El array no debe estar vacio"
assert bodyTC10.contains("Instalaciones") : "TC10: Debe contener tipo Instalaciones"
println "  -> Tipos de OT: ${bodyTC10}"
println "  TC10 PASSED"

// ================================================================
// TC11 - GET CauseCancellationOrders OData (workorders)
// Valida causas de cancelacion disponibles
// ================================================================
println "\n===== TC11 - CauseCancellationOrders OData ====="
def reqTC11 = buildGetRequest(
    "${BASE_URL_DATA}/workorders/api/CauseCancellationOrders?\$orderby=id&\$top=20&\$select=id,description&\$filter=(available%20eq%20%27S%27)%20and%20(availableService%20eq%20true)&api-version=1.0"
)
ResponseObject respTC11 = WS.sendRequest(reqTC11)

WS.verifyResponseStatusCode(respTC11, 200)
verifyNotNull(respTC11, "value")

def bodyTC11 = parser.parseText(respTC11.getResponseBodyContent())
assert bodyTC11.value != null             : "TC11: OData value debe existir"
assert bodyTC11."@odata.context" != null  : "TC11: OData context debe existir"
println "  -> Causas cancelacion: ${bodyTC11.value.size()}"
println "  TC11 PASSED"

// ================================================================
// TC12 - GET CausesProblems OData (materials)
// Valida causas de problemas disponibles
// ================================================================
println "\n===== TC12 - CausesProblems OData ====="
def reqTC12 = buildGetRequest(
    "${BASE_URL_DATA}/materials/api/CausesProblems?\$top=20&\$select=id,description&\$filter=available%20eq%20%27S%27&api-version=1.0"
)
ResponseObject respTC12 = WS.sendRequest(reqTC12)

WS.verifyResponseStatusCode(respTC12, 200)
verifyNotNull(respTC12, "value[0].id")
verifyNotNull(respTC12, "value[0].description")

def bodyTC12 = parser.parseText(respTC12.getResponseBodyContent())
assert bodyTC12.value.size() > 0 : "TC12: CausesProblems no debe estar vacio"
println "  -> Causas problemas: ${bodyTC12.value.size()}"
println "  TC12 PASSED"

// ================================================================
// TC13 - GET Works OData por typeWorkId (workorders)
// Valida trabajos de tipo queja (typeWorkId=2)
// ================================================================
println "\n===== TC13 - Works OData (tipo queja) ====="
def reqTC13 = buildGetRequest(
    "${BASE_URL_DATA}/workorders/api/Works?\$top=20&\$select=id,typeWorkId,workId,description,swDirect&\$filter=(typeWorkId%20eq%202)%20and%20((workId%20eq%201)%20or%20(workId%20eq%20115)%20or%20(workId%20eq%20141)%20or%20(workId%20eq%20135))&api-version=1.0"
)
ResponseObject respTC13 = WS.sendRequest(reqTC13)

WS.verifyResponseStatusCode(respTC13, 200)
verifyNotNull(respTC13, "value")

def bodyTC13 = parser.parseText(respTC13.getResponseBodyContent())
assert bodyTC13.value != null           : "TC13: value debe existir"
if (bodyTC13.value.size() > 0) {
    assert bodyTC13.value[0].typeWorkId == 2 : "TC13: typeWorkId debe ser 2 (queja)"
    assert bodyTC13.value[0].id != null      : "TC13: id debe estar presente"
}
println "  -> Trabajos tipo queja: ${bodyTC13.value.size()}"
println "  TC13 PASSED"

// ================================================================
// TC14 - POST ValidateTechnicalComplaint (services-process)
// Valida reglas de negocio antes de registrar queja tecnica
// ================================================================
println "\n===== TC14 - ValidateTechnicalComplaint ====="
def reqTC14 = buildPostRequest(
    "${BASE_URL_DATA}/services-process/api/ValidateTechnicalComplaint",
    """{"branchId":${BRANCH_ID},"subscriberNumber":${SUBSCRIBER_NUM},"neighborhoodId":"${idColonia}","userType":79}"""
)
ResponseObject respTC14 = WS.sendRequest(reqTC14)

WS.verifyResponseStatusCode(respTC14, 200)
verifyNotNull(respTC14, "result.tipoQueja")

def bodyTC14 = parser.parseText(respTC14.getResponseBodyContent())
assert bodyTC14.result != null            : "TC14: result no debe ser null"
assert bodyTC14.result.tipoQueja != null  : "TC14: tipoQueja debe estar presente"
println "  -> tipoQueja: ${bodyTC14.result.tipoQueja}"
println "  -> repetitiva: ${bodyTC14.result.repetitiva}"
println "  TC14 PASSED"

// ================================================================
// TC15 - POST SaveTechnicalComplaint (services-process)
// Registra queja tecnica y correlaciona folio generado
// ================================================================
println "\n===== TC15 - SaveTechnicalComplaint ====="
def contrato = contractId ?: "1791d126-bc98-4083-a904-b82807154060"
def reqTC15 = buildPostRequest(
    "${BASE_URL_DATA}/services-process/api/SaveTechnicalComplaint",
    """{"sucursal":${BRANCH_ID},"suscriptor":${SUBSCRIBER_NUM},"tipo_trabajo":2,"trabajo":1,"id_trabajo":"2|1","tipo_servicio":1,"servicio":7,"tipo_contrato":"P","id_contrato":"${contrato}","reporto":"AUTOMATIZACION","reporte":"QUEJA TECNICA TEST AUTOMATIZADO KATALON","profileId":79}"""
)
ResponseObject respTC15 = WS.sendRequest(reqTC15)

WS.verifyResponseStatusCode(respTC15, 200)
WS.verifyElementPropertyValue(respTC15, "validationResult.isValid", true)
verifyNotNull(respTC15, "result")

def bodyTC15 = parser.parseText(respTC15.getResponseBodyContent())
assert bodyTC15.validationResult.isValid == true : "TC15: validationResult.isValid debe ser true"
assert bodyTC15.result != null && bodyTC15.result != "" : "TC15: result (folio) no debe estar vacio"
assert bodyTC15.result =~ /^[0-9]+$/ : "TC15: El folio debe ser numerico"
folioQueja = bodyTC15.result
println "  -> Folio queja generado: ${folioQueja}"
println "  TC15 PASSED"

// ================================================================
// TC16 - GET suscriptorMensajes (MuleSoft API)
// Valida mensajes configurados para el suscriptor
// ================================================================
println "\n===== TC16 - suscriptorMensajes (MuleSoft) ====="
def reqTC16 = buildGetRequest(
    "${BASE_URL_MULE}/megacable_sfyc/api/cliente360/catalogos/suscriptorMensajes?sucursal=${BRANCH_ID}&suscriptor=${SUBSCRIBER_NUM}"
)
ResponseObject respTC16 = WS.sendRequest(reqTC16)

WS.verifyResponseStatusCode(respTC16, 200)
WS.verifyElementPropertyValue(respTC16, "status", "OK")
verifyNotNull(respTC16, "result")

def bodyTC16 = parser.parseText(respTC16.getResponseBodyContent())
assert bodyTC16.status == "OK" : "TC16: status debe ser OK"
assert bodyTC16.result != null : "TC16: result no debe ser null"
println "  -> Mensajes del suscriptor: ${bodyTC16.result.size()}"
println "  TC16 PASSED"

// ================================================================
// TC17 - GET bitacoraFallasDique (MuleSoft API)
// Valida bitacora de fallas en rango de fechas
// ================================================================
println "\n===== TC17 - bitacoraFallasDiquePorOrigenRangoFechas (MuleSoft) ====="
def fechaDesde = "2026-04-10"
def fechaHasta = "2026-05-11"
def reqTC17 = buildGetRequest(
    "${BASE_URL_MULE}/megacable_sfyc/api/cliente360/tableros/bitacoraFallasDiquePorOrigenRangoFechas?sucursal=${BRANCH_ID}&suscriptor=${SUBSCRIBER_NUM}&fechaDesde=${fechaDesde}&fechaHasta=${fechaHasta}"
)
ResponseObject respTC17 = WS.sendRequest(reqTC17)

WS.verifyResponseStatusCode(respTC17, 200)
WS.verifyElementPropertyValue(respTC17, "status", "OK")
verifyNotNull(respTC17, "result")

def bodyTC17 = parser.parseText(respTC17.getResponseBodyContent())
assert bodyTC17.status == "OK"   : "TC17: status debe ser OK"
assert bodyTC17.result != null   : "TC17: result no debe ser null"
assert bodyTC17.result instanceof List : "TC17: result debe ser un array"
println "  -> Fallas en el periodo: ${bodyTC17.result.size()}"
println "  TC17 PASSED"

// ================================================================
// TC18 - GET Consulta Dique por MAC (INN API)
// Valida estado del dispositivo en el sistema DIQUE
// ================================================================
println "\n===== TC18 - INN DIQUE Consumer (MAC query) ====="
def reqTC18 = buildGetRequest(
    "${BASE_URL_INN}/?query=3&mac=${MAC_ADDRESS}&tipoEquipo=O"
)
ResponseObject respTC18 = WS.sendRequest(reqTC18)

WS.verifyResponseStatusCode(respTC18, 200)
verifyNotNull(respTC18, "total_quejas")
verifyNotNull(respTC18, "dique.estatus")
verifyNotNull(respTC18, "dique.datos")

def bodyTC18 = parser.parseText(respTC18.getResponseBodyContent())
assert bodyTC18.total_quejas != null  : "TC18: total_quejas debe estar presente"
assert bodyTC18.dique != null         : "TC18: dique debe estar presente"
assert bodyTC18.dique.estatus != null : "TC18: dique.estatus debe estar presente"
println "  -> Total quejas: ${bodyTC18.total_quejas}"
println "  -> Estatus DIQUE: ${bodyTC18.dique.estatus}"
println "  TC18 PASSED"

// ================================================================
// TC19 - POST InsertLogbookModuleEvent (logbooks-process)
// Registra evento en bitacora del modulo
// ================================================================
println "\n===== TC19 - InsertLogbookModuleEvent ====="
def now = new Date().format("yyyy-MM-dd'T'HH:mm:ssXXX")
def reqTC19 = buildPostRequest(
    "${BASE_URL_DATA}/logbooks-process/api/InsertLogbookModuleEvent",
    """{"dateEnd":"${now}","dateStart":"${now}","messageValidity":"Success","module":"CustomerServiceWeb","process":"QuerySubscriber","status":"Success","profileId":79,"crew":"","isProcessMassive":false,"locationRegisterMovement":"","serie":"","typeCrew":"","typeEquipment":""}"""
)
ResponseObject respTC19 = WS.sendRequest(reqTC19)

WS.verifyResponseStatusCode(respTC19, 200)
WS.verifyElementPropertyValue(respTC19, "result", true)
WS.verifyElementPropertyValue(respTC19, "validationResult.isValid", true)

def bodyTC19 = parser.parseText(respTC19.getResponseBodyContent())
assert bodyTC19.result == true                       : "TC19: result debe ser true"
assert bodyTC19.validationResult.isValid == true     : "TC19: validationResult.isValid debe ser true"
assert bodyTC19.validationResult.errors.size() == 0  : "TC19: errors debe estar vacio"
println "  TC19 PASSED"

// ================================================================
// TC20 - POST MasterCardData (subscribers-process)
// Valida datos de domiciliacion del suscriptor
// ================================================================
println "\n===== TC20 - MasterCardData ====="
def reqTC20 = buildPostRequest(
    "${BASE_URL_DATA}/subscribers-process/api/MasterCardData/",
    """{"branchId":${BRANCH_ID},"subscriber":${SUBSCRIBER_NUM}}"""
)
ResponseObject respTC20 = WS.sendRequest(reqTC20)

WS.verifyResponseStatusCode(respTC20, 200)
verifyNotNull(respTC20, "result")

def bodyTC20 = parser.parseText(respTC20.getResponseBodyContent())
assert bodyTC20.result != null : "TC20: result no debe ser null"
println "  -> inProcess: ${bodyTC20.result.inProcess}"
println "  TC20 PASSED"

// ================================================================
// TC21 - GET GetVwCallLevelFeature nivel 01 (subscribers)
// Valida arbol de motivos de llamada - nivel 1
// ================================================================
println "\n===== TC21 - GetVwCallLevelFeature (nivel 01) ====="
def reqTC21 = buildGetRequest(
    "${BASE_URL_DATA}/subscribers/api/GetVwCallLevelFeature?parentId=00000000-0000-0000-0000-000000000000&level=01&\$Select=id,description"
)
ResponseObject respTC21 = WS.sendRequest(reqTC21)

WS.verifyResponseStatusCode(respTC21, 200)
def bodyTC21 = parser.parseText(respTC21.getResponseBodyContent())
assert bodyTC21 instanceof List : "TC21: La respuesta debe ser un array"
assert bodyTC21.size() > 0      : "TC21: El array de nivel 01 no debe estar vacio"
assert bodyTC21[0].Id != null   : "TC21: Id debe estar presente en cada elemento"
assert bodyTC21[0].Description != null : "TC21: Description debe estar presente"
println "  -> Opciones nivel 01: ${bodyTC21.size()}"
println "  -> Primer item: ${bodyTC21[0].Description}"
println "  TC21 PASSED"

// ================================================================
// TC22 - POST GetScheduleContracts (contracts-process)
// Valida contratos agendados del suscriptor
// ================================================================
println "\n===== TC22 - GetScheduleContracts ====="
def reqTC22 = buildPostRequest(
    "${BASE_URL_DATA}/contracts-process/api/GetScheduleContracts",
    """{"branchId":${BRANCH_ID},"subscriberNumber":${SUBSCRIBER_NUM},"isShowCanceled":false,"contractFilter":5}"""
)
ResponseObject respTC22 = WS.sendRequest(reqTC22)

WS.verifyResponseStatusCode(respTC22, 200)
def bodyTC22 = parser.parseText(respTC22.getResponseBodyContent())
assert bodyTC22 instanceof List : "TC22: La respuesta debe ser un array"
if (bodyTC22.size() > 0) {
    assert bodyTC22[0].sucursal != null   : "TC22: sucursal debe estar presente"
    assert bodyTC22[0].suscriptor != null : "TC22: suscriptor debe estar presente"
    assert bodyTC22[0].estado != null     : "TC22: estado del contrato debe estar presente"
    println "  -> Contratos agendados: ${bodyTC22.size()}"
    println "  -> Estado primer contrato: ${bodyTC22[0].estado}"
}
println "  TC22 PASSED"

// ================================================================
// TC23 - POST ScheduleSubscriberCalls (subscribers-process)
// Valida llamadas agendadas del suscriptor
// ================================================================
println "\n===== TC23 - ScheduleSubscriberCalls ====="
def reqTC23 = buildPostRequest(
    "${BASE_URL_DATA}/subscribers-process/api/ScheduleSubscriberCalls",
    """{"branchId":${BRANCH_ID},"subscriberNumber":${SUBSCRIBER_NUM}}"""
)
ResponseObject respTC23 = WS.sendRequest(reqTC23)

WS.verifyResponseStatusCode(respTC23, 200)
def bodyTC23 = parser.parseText(respTC23.getResponseBodyContent())
assert bodyTC23 instanceof List : "TC23: La respuesta debe ser un array"
if (bodyTC23.size() > 0) {
    assert bodyTC23[0].sucursal != null    : "TC23: sucursal debe estar presente"
    assert bodyTC23[0].suscriptor != null  : "TC23: suscriptor debe estar presente"
    assert bodyTC23[0].tipo != null        : "TC23: tipo de llamada debe estar presente"
    println "  -> Llamadas agendadas: ${bodyTC23.size()}"
    println "  -> Tipo primer registro: ${bodyTC23[0].tipo}"
}
println "  TC23 PASSED"

// ================================================================
// TC24 - GET GetBranch OData (branchs)
// Valida catalogo de sucursales
// ================================================================
println "\n===== TC24 - GetBranch OData ====="
def reqTC24 = buildGetRequest(
    "${BASE_URL_DATA}/branchs/api/GetBranch?\$top=10&\$select=id,description,branchGroupId,address,phone,manager,zoneTime,active,warehouse,regionId&api-version=1.0"
)
ResponseObject respTC24 = WS.sendRequest(reqTC24)

WS.verifyResponseStatusCode(respTC24, 200)
verifyNotNull(respTC24, "value")

def bodyTC24 = parser.parseText(respTC24.getResponseBodyContent())
assert bodyTC24.value != null         : "TC24: value debe existir"
assert bodyTC24.value.size() > 0      : "TC24: El catalogo de sucursales no debe estar vacio"
assert bodyTC24.value[0].id != null   : "TC24: id de sucursal debe estar presente"
assert bodyTC24.value[0].description != null : "TC24: description debe estar presente"
println "  -> Sucursales: ${bodyTC24.value.size()}"
println "  TC24 PASSED"

// ================================================================
// TC25 - POST GetPreferenceByBranchGroupId - HTTP 204 (services-process)
// Valida que preferencia no configurada retorna 204 No Content
// ================================================================
println "\n===== TC25 - GetPreferenceByBranchGroupId (HTTP 204) ====="
def reqTC25 = buildPostRequest(
    "${BASE_URL_DATA}/services-process/api/GetPreferenceByBranchGroupId",
    """{"KeyPreference":"HabilitarCambioTecnologia","BranchGroupId":${BRANCH_ID}}"""
)
ResponseObject respTC25 = WS.sendRequest(reqTC25)

WS.verifyResponseStatusCode(respTC25, 204)
println "  -> HTTP 204 No Content (preferencia no configurada) - comportamiento esperado"
println "  TC25 PASSED"

// ================================================================
// RESUMEN FINAL
// ================================================================
println "\n========================================="
println "  RESUMEN: 25/25 casos de prueba PASSED"
println "  Sistema: SFyC NextGen QA Cluster"
println "  Sucursal: ${BRANCH_ID} | Suscriptor: ${SUBSCRIBER_NUM}"
println "  Folio queja generado: ${folioQueja}"
println "========================================="

