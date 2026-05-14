import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC09_BallotTypeStates
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint OData BallotTypeStates retorna el catalogo de estados de boletas.
 * Precondiciones: Catalogo con registros existentes.
 * Datos:         top=10.
 * Resultado esperado: HTTP 200, respuesta con array value no vacio.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC09 - BallotTypeStates ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/services/api/BallotTypeStates?" +
    "\$top=10&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
API.verifyArrayNotEmpty(json, "value", "TC09")

println "  -> registros: ${json.value.size()}"
if (json.value.size() > 0) {
    println "  -> primer registro id: ${json.value[0].id}"
    println "  -> primer registro description: ${json.value[0].description}"
}
println "  TC09 PASSED"
