import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC21 - GET GetVwCallLevelFeature nivel 01 (subscribers)
// Valida arbol de motivos de llamada - nivel 1

println "\n===== TC21 - GetVwCallLevelFeature (nivel 01) ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC21 = API.buildGetRequest(
    "${API.BASE_URL_DATA}/subscribers/api/GetVwCallLevelFeature?parentId=00000000-0000-0000-0000-000000000000&level=01&\$Select=id,description",
    token
)
ResponseObject respTC21 = WS.sendRequest(reqTC21)

WS.verifyResponseStatusCode(respTC21, 200)
def parser = new JsonSlurper()
def bodyTC21 = parser.parseText(respTC21.getResponseBodyContent())
assert bodyTC21 instanceof List : "TC21: La respuesta debe ser un array"
assert bodyTC21.size() > 0      : "TC21: El array de nivel 01 no debe estar vacio"
assert bodyTC21[0].Id != null   : "TC21: Id debe estar presente en cada elemento"
assert bodyTC21[0].Description != null : "TC21: Description debe estar presente"
println "  -> Opciones nivel 01: ${bodyTC21.size()}"
println "  -> Primer item: ${bodyTC21[0].Description}"
println "  TC21 PASSED"
