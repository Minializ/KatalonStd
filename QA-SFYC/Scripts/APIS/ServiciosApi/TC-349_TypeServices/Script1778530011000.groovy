import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC11_TypeServices
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint OData TypeServices retorna el catalogo de tipos de servicio con conteo.
 * Precondiciones: Catalogo con registros existentes.
 * Datos:         top=20, orderby=id, count=true.
 * Resultado esperado: HTTP 200, respuesta con array value no vacio y @odata.count.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC11 - TypeServices ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/services/api/TypeServices?" +
    "\$orderby=id&\$top=20&\$count=true&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
API.verifyArrayNotEmpty(json, "value", "TC11")
assert json['@odata.count'] != null : "TC11: @odata.count es null"

println "  -> total registros (@odata.count): ${json['@odata.count']}"
println "  -> registros retornados: ${json.value.size()}"
if (json.value.size() > 0) {
    println "  -> primer registro id: ${json.value[0].id}"
    println "  -> primer registro description: ${json.value[0].description}"
}
println "  TC11 PASSED"
