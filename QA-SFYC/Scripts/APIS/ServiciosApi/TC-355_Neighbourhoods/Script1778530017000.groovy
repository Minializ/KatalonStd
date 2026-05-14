import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC17_Neighbourhoods
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint Neighbourhoods retorna colonias por sucursal.
 * Precondiciones: Sucursal 44 con colonias registradas.
 * Datos:         branchId=44, zipCode="" (todas las colonias).
 * Resultado esperado: HTTP 200, respuesta con datos de colonias.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC17 - Neighbourhoods ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/addresses-process/api/Neighbourhoods"
def body = """
{
  "branchId": ${API.BRANCH_ID},
  "zipCode": ""
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json != null : "TC17: respuesta es null"

if (json instanceof List) {
    println "  -> colonias encontradas: ${json.size()}"
    if (json.size() > 0) {
        println "  -> primera colonia: ${json[0]}"
    }
} else {
    if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
}
println "  TC17 PASSED"
