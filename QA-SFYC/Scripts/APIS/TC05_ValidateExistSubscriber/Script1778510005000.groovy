import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC05 - POST ValidateExistSubscriber (sfyc)
// Valida que el suscriptor existe en el sistema

println "\n===== TC05 - ValidateExistSubscriber ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC05 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/sfyc/api/ValidateExistSubscriber",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM}}""",
    token
)
ResponseObject respTC05 = WS.sendRequest(reqTC05)

WS.verifyResponseStatusCode(respTC05, 200)
API.verifyNotNull(respTC05, "result")

def parser = new JsonSlurper()
def bodyTC05 = parser.parseText(respTC05.getResponseBodyContent())
assert bodyTC05.result != null : "TC05: result no debe ser null"
println "  TC05 PASSED"
