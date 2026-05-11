import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC06 - GET GetSkillWarrantyService (subscribers-process)
// Valida garantia y habilidades de servicio del suscriptor

println "\n===== TC06 - GetSkillWarrantyService ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC06 = API.buildGetRequest(
    "${API.BASE_URL_DATA}/subscribers-process/api/GetSkillWarrantyService/${API.SUBSCRIBER_NUM}/${API.BRANCH_GROUP}",
    token
)
ResponseObject respTC06 = WS.sendRequest(reqTC06)

WS.verifyResponseStatusCode(respTC06, 200)
WS.verifyElementPropertyValue(respTC06, "value.validationResult.isValid", true)
WS.verifyElementPropertyValue(respTC06, "statusCode", 200)

def parser = new JsonSlurper()
def bodyTC06 = parser.parseText(respTC06.getResponseBodyContent())
assert bodyTC06.value.validationResult.isValid == true : "TC06: validationResult.isValid debe ser true"
println "  -> Warranty result: ${bodyTC06.value.result}"
println "  TC06 PASSED"
