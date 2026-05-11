import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC20 - POST MasterCardData (subscribers-process)
// Valida datos de domiciliacion del suscriptor

println "\n===== TC20 - MasterCardData ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC20 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/subscribers-process/api/MasterCardData/",
    """{"branchId":${API.BRANCH_ID},"subscriber":${API.SUBSCRIBER_NUM}}""",
    token
)
ResponseObject respTC20 = WS.sendRequest(reqTC20)

WS.verifyResponseStatusCode(respTC20, 200)
API.verifyNotNull(respTC20, "result")

def parser = new JsonSlurper()
def bodyTC20 = parser.parseText(respTC20.getResponseBodyContent())
assert bodyTC20.result != null : "TC20: result no debe ser null"
println "  -> inProcess: ${bodyTC20.result.inProcess}"
println "  TC20 PASSED"
