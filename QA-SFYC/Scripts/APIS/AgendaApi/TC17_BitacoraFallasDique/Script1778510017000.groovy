import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC17 - GET bitacoraFallasDique (MuleSoft API)
// Valida bitacora de fallas en rango de fechas

println "\n===== TC17 - bitacoraFallasDiquePorOrigenRangoFechas (MuleSoft) ====="
def token = API.getOAuthToken(passwordAPI)
def fechaDesde = "2026-04-10"
def fechaHasta = "2026-05-11"
def reqTC17 = API.buildGetRequest(
    "${API.BASE_URL_MULE}/megacable_sfyc/api/cliente360/tableros/bitacoraFallasDiquePorOrigenRangoFechas?sucursal=${API.BRANCH_ID}&suscriptor=${API.SUBSCRIBER_NUM}&fechaDesde=${fechaDesde}&fechaHasta=${fechaHasta}",
    token
)
ResponseObject respTC17 = WS.sendRequest(reqTC17)

WS.verifyResponseStatusCode(respTC17, 200)
WS.verifyElementPropertyValue(respTC17, "status", "OK")
API.verifyNotNull(respTC17, "result")

def parser = new JsonSlurper()
def bodyTC17 = parser.parseText(respTC17.getResponseBodyContent())
assert bodyTC17.status == "OK"   : "TC17: status debe ser OK"
assert bodyTC17.result != null   : "TC17: result no debe ser null"
assert bodyTC17.result instanceof List : "TC17: result debe ser un array"
println "  -> Fallas en el periodo: ${bodyTC17.result.size()}"
println "  TC17 PASSED"
