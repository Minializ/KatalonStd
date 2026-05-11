import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC10 - GET GetTypeOrderWorkDescriptions (workorders-process)
// Valida las descripciones de tipos de OT disponibles

println "\n===== TC10 - GetTypeOrderWorkDescriptions ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC10 = API.buildGetRequest(
    "${API.BASE_URL_DATA}/workorders-process/api/GetTypeOrderWorkDescriptions/${API.BRANCH_ID}/${API.SUBSCRIBER_NUM}",
    token
)
ResponseObject respTC10 = WS.sendRequest(reqTC10)

WS.verifyResponseStatusCode(respTC10, 200)
def parser = new JsonSlurper()
def bodyTC10 = parser.parseText(respTC10.getResponseBodyContent())
assert bodyTC10 instanceof List : "TC10: La respuesta debe ser un array de strings"
assert bodyTC10.size() > 0     : "TC10: El array no debe estar vacio"
assert bodyTC10.contains("Instalaciones") : "TC10: Debe contener tipo Instalaciones"
println "  -> Tipos de OT: ${bodyTC10}"
println "  TC10 PASSED"
