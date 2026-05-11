import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC14_GetTypeCrews
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint OData TypeCrews retorna el catalogo de tipos de cuadrilla.
 * Precondiciones: Catalogo de tipos de cuadrilla con datos.
 * Datos:         Paginacion top=10.
 * Resultado esperado: HTTP 200, respuesta con array value de tipos de cuadrilla.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC14 - GetTypeCrews ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/crews/api/TypeCrews?" +
    "\$top=10" +
    "&\$select=id,description,crewSubType" +
    "&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json.containsKey("value") : "TC14: respuesta debe contener 'value'"
assert json.value instanceof List : "TC14: 'value' debe ser un array"
assert json.value.size() > 0      : "TC14: debe haber al menos un tipo de cuadrilla"

def typeCrew = json.value[0]
assert typeCrew.containsKey("id")          : "TC14: tipo cuadrilla debe tener id"
assert typeCrew.containsKey("description") : "TC14: tipo cuadrilla debe tener description"

println "  -> total tipos de cuadrilla: ${json.value.size()}"
json.value.each { tc ->
    println "  -> id: '${tc.id}', description: ${tc.description}, crewSubType: ${tc.crewSubType}"
}
println "  TC14 PASSED"
