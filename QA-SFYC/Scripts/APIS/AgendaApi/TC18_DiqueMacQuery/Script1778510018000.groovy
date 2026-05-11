import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC18 - GET Consulta Dique por MAC (INN API)
// Valida estado del dispositivo en el sistema DIQUE

println "\n===== TC18 - INN DIQUE Consumer (MAC query) ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC18 = API.buildGetRequest(
    "${API.BASE_URL_INN}/?query=3&mac=${API.MAC_ADDRESS}&tipoEquipo=O",
    token
)
ResponseObject respTC18 = WS.sendRequest(reqTC18)

WS.verifyResponseStatusCode(respTC18, 200)
API.verifyNotNull(respTC18, "total_quejas")
API.verifyNotNull(respTC18, "dique.estatus")
API.verifyNotNull(respTC18, "dique.datos")

def parser = new JsonSlurper()
def bodyTC18 = parser.parseText(respTC18.getResponseBodyContent())
assert bodyTC18.total_quejas != null  : "TC18: total_quejas debe estar presente"
assert bodyTC18.dique != null         : "TC18: dique debe estar presente"
assert bodyTC18.dique.estatus != null : "TC18: dique.estatus debe estar presente"
println "  -> Total quejas: ${bodyTC18.total_quejas}"
println "  -> Estatus DIQUE: ${bodyTC18.dique.estatus}"
println "  TC18 PASSED"
