import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC23 - POST ScheduleSubscriberCalls (subscribers-process)
// Valida llamadas agendadas del suscriptor

println "\n===== TC23 - ScheduleSubscriberCalls ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC23 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/subscribers-process/api/ScheduleSubscriberCalls",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM}}""",
    token
)
ResponseObject respTC23 = WS.sendRequest(reqTC23)

WS.verifyResponseStatusCode(respTC23, 200)
def parser = new JsonSlurper()
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
