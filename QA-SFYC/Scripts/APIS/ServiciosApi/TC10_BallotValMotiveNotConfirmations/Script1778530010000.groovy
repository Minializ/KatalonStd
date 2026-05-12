import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC10_BallotValMotiveNotConfirmations
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint OData BallotValMotiveNotConfirmations retorna motivos de no confirmacion.
 * Precondiciones: Catalogo con registros existentes.
 * Datos:         top=10, orderby=id.
 * Resultado esperado: HTTP 200, respuesta con array value no vacio.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC10 - BallotValMotiveNotConfirmations ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/services/api/BallotValMotiveNotConfirmations?" +
    "\$orderby=id&\$top=10&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
API.verifyArrayNotEmpty(json, "value", "TC10")

println "  -> registros: ${json.value.size()}"
if (json.value.size() > 0) {
    println "  -> primer registro id: ${json.value[0].id}"
    println "  -> primer registro description: ${json.value[0].description}"
}
println "  TC10 PASSED"
