import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC24 - GET GetBranch OData (branchs)
// Valida catalogo de sucursales

println "\n===== TC24 - GetBranch OData ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC24 = API.buildGetRequest(
    "${API.BASE_URL_DATA}/branchs/api/GetBranch?\$top=10&\$select=id,description,branchGroupId,address,phone,manager,zoneTime,active,warehouse,regionId&api-version=1.0",
    token
)
ResponseObject respTC24 = WS.sendRequest(reqTC24)

WS.verifyResponseStatusCode(respTC24, 200)
API.verifyNotNull(respTC24, "value")

def parser = new JsonSlurper()
def bodyTC24 = parser.parseText(respTC24.getResponseBodyContent())
assert bodyTC24.value != null         : "TC24: value debe existir"
assert bodyTC24.value.size() > 0      : "TC24: El catalogo de sucursales no debe estar vacio"
assert bodyTC24.value[0].id != null   : "TC24: id de sucursal debe estar presente"
assert bodyTC24.value[0].description != null : "TC24: description debe estar presente"
println "  -> Sucursales: ${bodyTC24.value.size()}"
println "  TC24 PASSED"
