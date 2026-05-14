import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC08 - POST GetOrderWorks (workorders-process)
// Valida ordenes de trabajo del suscriptor

println "\n===== TC08 - GetOrderWorks ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC08 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/workorders-process/api/GetOrderWorks",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM}}""",
    token
)
ResponseObject respTC08 = WS.sendRequest(reqTC08)

WS.verifyResponseStatusCode(respTC08, 200)
def parser = new JsonSlurper()
def bodyTC08 = parser.parseText(respTC08.getResponseBodyContent())
assert bodyTC08 instanceof List : "TC08: La respuesta debe ser un array"
println "  -> Ordenes de trabajo: ${bodyTC08.size()}"
println "  TC08 PASSED"
