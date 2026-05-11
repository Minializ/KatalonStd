import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC07 - GET GetSubscriberAgreement (services-process)
// Valida acuerdo de servicio del suscriptor

println "\n===== TC07 - GetSubscriberAgreement ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC07 = API.buildGetRequest(
    "${API.BASE_URL_DATA}/services-process/api/GetSubscriberAgreement/${API.BRANCH_ID}/${API.SUBSCRIBER_NUM}",
    token
)
ResponseObject respTC07 = WS.sendRequest(reqTC07)

WS.verifyResponseStatusCode(respTC07, 200)
WS.verifyElementPropertyValue(respTC07, "validationResult.isValid", true)

def parser = new JsonSlurper()
def bodyTC07 = parser.parseText(respTC07.getResponseBodyContent())
assert bodyTC07.validationResult.isValid == true : "TC07: validationResult.isValid debe ser true"
println "  TC07 PASSED"
