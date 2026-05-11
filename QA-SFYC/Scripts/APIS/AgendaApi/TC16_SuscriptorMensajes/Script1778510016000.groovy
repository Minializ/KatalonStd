import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC16 - GET suscriptorMensajes (MuleSoft API)
// Valida mensajes configurados para el suscriptor

println "\n===== TC16 - suscriptorMensajes (MuleSoft) ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC16 = API.buildGetRequest(
    "${API.BASE_URL_MULE}/megacable_sfyc/api/cliente360/catalogos/suscriptorMensajes?sucursal=${API.BRANCH_ID}&suscriptor=${API.SUBSCRIBER_NUM}",
    token
)
ResponseObject respTC16 = WS.sendRequest(reqTC16)

WS.verifyResponseStatusCode(respTC16, 200)
WS.verifyElementPropertyValue(respTC16, "status", "OK")
API.verifyNotNull(respTC16, "result")

def parser = new JsonSlurper()
def bodyTC16 = parser.parseText(respTC16.getResponseBodyContent())
assert bodyTC16.status == "OK" : "TC16: status debe ser OK"
assert bodyTC16.result != null : "TC16: result no debe ser null"
println "  -> Mensajes del suscriptor: ${bodyTC16.result.size()}"
println "  TC16 PASSED"
