import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC03 - POST GetContractsSubscriber (contracts-process)
// Valida contratos activos y correlaciona contractId

println "\n===== TC03 - GetContractsSubscriber ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC03 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/contracts-process/api/GetContractsSubscriber",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM}}""",
    token
)
ResponseObject respTC03 = WS.sendRequest(reqTC03)

WS.verifyResponseStatusCode(respTC03, 200)
API.verifyNotNull(respTC03, "result.subscriberStatus")
API.verifyNotNull(respTC03, "result.activeContracts")

def parser = new JsonSlurper()
def bodyTC03  = parser.parseText(respTC03.getResponseBodyContent())
def statusSub = bodyTC03.result.subscriberStatus
assert statusSub != null && statusSub != "" : "TC03: subscriberStatus no debe estar vacio"

def activeContracts = bodyTC03.result.activeContracts
assert activeContracts != null : "TC03: activeContracts debe existir"
if (activeContracts.size() > 0) {
    println "  -> subscriberStatus: ${statusSub}"
    println "  -> Contratos activos: ${activeContracts.size()}"
    println "  -> contractId: ${activeContracts[0].contractId}"
}
println "  TC03 PASSED"
