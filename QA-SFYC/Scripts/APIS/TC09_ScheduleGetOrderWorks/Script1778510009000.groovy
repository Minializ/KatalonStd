import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC09 - POST ScheduleGetOrderWorks (workorders-process)
// Valida ordenes de trabajo agendadas

println "\n===== TC09 - ScheduleGetOrderWorks ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC09 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/workorders-process/api/ScheduleGetOrderWorks",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM},"orderWorkFilter":0,"isAll":false}""",
    token
)
ResponseObject respTC09 = WS.sendRequest(reqTC09)

WS.verifyResponseStatusCode(respTC09, 200)
def parser = new JsonSlurper()
def bodyTC09 = parser.parseText(respTC09.getResponseBodyContent())
assert bodyTC09 instanceof List : "TC09: La respuesta debe ser un array"
if (bodyTC09.size() > 0) {
    assert bodyTC09[0].sucursal != null  : "TC09: sucursal debe estar presente"
    assert bodyTC09[0].suscriptor != null: "TC09: suscriptor debe estar presente"
    println "  -> OTs agendadas: ${bodyTC09.size()}"
    println "  -> Primer folio: ${bodyTC09[0].folio_trabajo}"
}
println "  TC09 PASSED"
