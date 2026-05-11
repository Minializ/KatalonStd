import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC22 - POST GetScheduleContracts (contracts-process)
// Valida contratos agendados del suscriptor

println "\n===== TC22 - GetScheduleContracts ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC22 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/contracts-process/api/GetScheduleContracts",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM},"isShowCanceled":false,"contractFilter":5}""",
    token
)
ResponseObject respTC22 = WS.sendRequest(reqTC22)

WS.verifyResponseStatusCode(respTC22, 200)
def parser = new JsonSlurper()
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
