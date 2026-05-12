import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC18_CauseCancellationsWorkOrders
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint OData CauseCancellations de workorders retorna causas de cancelacion.
 * Precondiciones: Catalogo con registros existentes.
 * Datos:         top=20, orderby=id.
 * Resultado esperado: HTTP 200, respuesta con array value no vacio.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC18 - CauseCancellationsWorkOrders ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/workorders/api/CauseCancellations?" +
    "\$orderby=id&\$top=20&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
API.verifyArrayNotEmpty(json, "value", "TC18")

println "  -> registros: ${json.value.size()}"
if (json.value.size() > 0) {
    println "  -> primer registro id: ${json.value[0].id}"
    println "  -> primer registro description: ${json.value[0].description}"
}
println "  TC18 PASSED"
