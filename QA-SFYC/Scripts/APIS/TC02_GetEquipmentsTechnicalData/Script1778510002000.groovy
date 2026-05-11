import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC02 - POST GetEquipmentsTechnicalData (services-process)
// Valida equipos tecnicos y extrae MAC del primer equipo

println "\n===== TC02 - GetEquipmentsTechnicalData ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC02 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/services-process/api/GetEquipmentsTechnicalData",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM}}""",
    token
)
ResponseObject respTC02 = WS.sendRequest(reqTC02)

WS.verifyResponseStatusCode(respTC02, 200)
API.verifyNotNull(respTC02, "[0].serie")
API.verifyNotNull(respTC02, "[0].modelo")
API.verifyNotNull(respTC02, "[0].estado")
WS.verifyElementPropertyValue(respTC02, "[0].suscriptor", API.SUBSCRIBER_NUM)
WS.verifyElementPropertyValue(respTC02, "[0].sucursal",   API.BRANCH_ID)

def parser = new JsonSlurper()
def bodyTC02 = parser.parseText(respTC02.getResponseBodyContent())
assert bodyTC02 instanceof List : "TC02: La respuesta debe ser un array de equipos"
assert bodyTC02.size() > 0      : "TC02: El array de equipos no debe estar vacio"
println "  -> Equipos encontrados: ${bodyTC02.size()}"
println "  -> MAC primer equipo: ${bodyTC02[0].serie}"
println "  TC02 PASSED"
