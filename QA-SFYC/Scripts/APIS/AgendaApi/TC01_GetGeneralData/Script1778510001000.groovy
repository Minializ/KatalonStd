import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC01 - POST GetGeneralData (subscribers-process)
// Valida datos generales del suscriptor y correlaciona IDs

println "\n===== TC01 - GetGeneralData ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC01 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/subscribers-process/api/GetGeneralData",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM}}""",
    token
)
ResponseObject respTC01 = WS.sendRequest(reqTC01)

WS.verifyResponseStatusCode(respTC01, 200)
WS.verifyElementPropertyValue(respTC01, "sucursal",    API.BRANCH_ID)
WS.verifyElementPropertyValue(respTC01, "suscriptor",  API.SUBSCRIBER_NUM)
API.verifyNotNull(respTC01, "id_suscriptor")
API.verifyNotNull(respTC01, "id_domicilio")
API.verifyNotNull(respTC01, "id_colonia")
API.verifyNotNull(respTC01, "sucursal_desc")

def parser = new JsonSlurper()
def bodyTC01 = parser.parseText(respTC01.getResponseBodyContent())
println "  -> id_suscriptor: ${bodyTC01.id_suscriptor}"
println "  -> id_colonia:    ${bodyTC01.id_colonia}"
println "  TC01 PASSED"
