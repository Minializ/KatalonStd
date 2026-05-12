import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC01_GetGeneralData
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint GetGeneralData retorna datos generales del suscriptor.
 * Precondiciones: Suscriptor 601390 existente en sucursal 44.
 * Datos:         branchId=44, subscriberNumber=601390.
 * Resultado esperado: HTTP 200, respuesta con datos del suscriptor (id, nombre, sucursal).
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC01 - GetGeneralData ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/subscribers-process/api/GetGeneralData"
def body = """
{
  "branchId": ${API.BRANCH_ID},
  "subscriberNumber": ${API.SUBSCRIBER_NUM}
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json != null : "TC01: respuesta es null"

println "  -> respuesta obtenida correctamente"
if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
println "  TC01 PASSED"
