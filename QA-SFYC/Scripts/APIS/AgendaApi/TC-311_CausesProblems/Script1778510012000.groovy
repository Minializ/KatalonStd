import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC12 - GET CausesProblems OData (materials)
// Valida causas de problemas disponibles

println "\n===== TC12 - CausesProblems OData ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC12 = API.buildGetRequest(
    "${API.BASE_URL_DATA}/materials/api/CausesProblems?\$top=20&\$select=id,description&\$filter=available%20eq%20%27S%27&api-version=1.0",
    token
)
ResponseObject respTC12 = WS.sendRequest(reqTC12)

WS.verifyResponseStatusCode(respTC12, 200)
API.verifyNotNull(respTC12, "value[0].id")
API.verifyNotNull(respTC12, "value[0].description")

def parser = new JsonSlurper()
def bodyTC12 = parser.parseText(respTC12.getResponseBodyContent())
assert bodyTC12.value.size() > 0 : "TC12: CausesProblems no debe estar vacio"
println "  -> Causas problemas: ${bodyTC12.value.size()}"
println "  TC12 PASSED"
