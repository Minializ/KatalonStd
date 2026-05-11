import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC04 - POST GetSubscriberPackage (debt-calculation-process)
// Valida descripcion del paquete del suscriptor

println "\n===== TC04 - GetSubscriberPackage ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC04 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/debt-calculation-process/api/GetSubscriberPackage",
    """{"subscriberContract":"${API.SUB_CONTRACT}","branchGroupId":"${API.BRANCH_GROUP}"}""",
    token
)
ResponseObject respTC04 = WS.sendRequest(reqTC04)

WS.verifyResponseStatusCode(respTC04, 200)
WS.verifyElementPropertyValue(respTC04, "codigo", 0)
API.verifyNotNull(respTC04, "mensajes[0]")

def parser = new JsonSlurper()
def bodyTC04 = parser.parseText(respTC04.getResponseBodyContent())
assert bodyTC04.codigo == 0            : "TC04: codigo debe ser 0 (exito)"
assert bodyTC04.mensajes.size() > 0    : "TC04: mensajes no debe estar vacio"
println "  -> Paquete: ${bodyTC04.mensajes[0]}"
println "  TC04 PASSED"
