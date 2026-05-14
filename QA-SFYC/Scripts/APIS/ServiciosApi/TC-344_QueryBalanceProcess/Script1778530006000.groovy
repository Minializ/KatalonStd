import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC06_QueryBalanceProcess
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint QueryBalanceProcess retorna el balance por grupo de sucursal.
 * Precondiciones: Sucursal 44 con CIS 24 existente.
 * Datos:         branchGroupId=44, cis=24, dateQueryBalance=2026-03-04.
 * Resultado esperado: HTTP 200, respuesta con datos de balance.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC06 - QueryBalanceProcess ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/services-process/api/QueryBalanceProcess"
def body = """
{
  "branchGroupId": ${API.BRANCH_ID},
  "dateQueryBalance": "2026-03-04T06:00:00.000Z",
  "cis": ${API.CIS_ID}
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json != null : "TC06: respuesta es null"

println "  -> respuesta obtenida correctamente"
if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
println "  TC06 PASSED"
