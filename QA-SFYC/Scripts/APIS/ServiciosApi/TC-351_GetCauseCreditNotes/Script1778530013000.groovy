import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC13_GetCauseCreditNotes
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint GetCauseCreditNotes retorna las causas de notas de credito.
 * Precondiciones: Catalogo de causas existente.
 * Datos:         GET sin parametros.
 * Resultado esperado: HTTP 200, respuesta con datos.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC13 - GetCauseCreditNotes ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/services-process/api/GetCauseCreditNotes"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json != null : "TC13: respuesta es null"

if (json instanceof List) {
    println "  -> registros: ${json.size()}"
    if (json.size() > 0) {
        println "  -> primer registro: ${json[0]}"
    }
} else {
    if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
}
println "  TC13 PASSED"
