import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC13 - GET Works OData por typeWorkId (workorders)
// Valida trabajos de tipo queja (typeWorkId=2)

println "\n===== TC13 - Works OData (tipo queja) ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC13 = API.buildGetRequest(
    "${API.BASE_URL_DATA}/workorders/api/Works?\$top=20&\$select=id,typeWorkId,workId,description,swDirect&\$filter=(typeWorkId%20eq%202)%20and%20((workId%20eq%201)%20or%20(workId%20eq%20115)%20or%20(workId%20eq%20141)%20or%20(workId%20eq%20135))&api-version=1.0",
    token
)
ResponseObject respTC13 = WS.sendRequest(reqTC13)

WS.verifyResponseStatusCode(respTC13, 200)
API.verifyNotNull(respTC13, "value")

def parser = new JsonSlurper()
def bodyTC13 = parser.parseText(respTC13.getResponseBodyContent())
assert bodyTC13.value != null : "TC13: value debe existir"
if (bodyTC13.value.size() > 0) {
    assert bodyTC13.value[0].typeWorkId == 2 : "TC13: typeWorkId debe ser 2 (queja)"
    assert bodyTC13.value[0].id != null      : "TC13: id debe estar presente"
}
println "  -> Trabajos tipo queja: ${bodyTC13.value.size()}"
println "  TC13 PASSED"
