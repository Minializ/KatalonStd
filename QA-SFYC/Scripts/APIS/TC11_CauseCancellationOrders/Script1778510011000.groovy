import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC11 - GET CauseCancellationOrders OData (workorders)
// Valida causas de cancelacion disponibles

println "\n===== TC11 - CauseCancellationOrders OData ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC11 = API.buildGetRequest(
    "${API.BASE_URL_DATA}/workorders/api/CauseCancellationOrders?\$orderby=id&\$top=20&\$select=id,description&\$filter=(available%20eq%20%27S%27)%20and%20(availableService%20eq%20true)&api-version=1.0",
    token
)
ResponseObject respTC11 = WS.sendRequest(reqTC11)

WS.verifyResponseStatusCode(respTC11, 200)
API.verifyNotNull(respTC11, "value")

def parser = new JsonSlurper()
def bodyTC11 = parser.parseText(respTC11.getResponseBodyContent())
assert bodyTC11.value != null             : "TC11: OData value debe existir"
assert bodyTC11."@odata.context" != null  : "TC11: OData context debe existir"
println "  -> Causas cancelacion: ${bodyTC11.value.size()}"
println "  TC11 PASSED"
