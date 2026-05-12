import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC03_GetScheduleContracts
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint GetScheduleContracts retorna la programacion de contratos.
 * Precondiciones: Suscriptor 601390 existente en sucursal 44.
 * Datos:         branchId=44, subscriberNumber=601390, isShowCanceled=false, contractFilter=0.
 * Resultado esperado: HTTP 200, respuesta con datos de programacion.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC03 - GetScheduleContracts ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/contracts-process/api/GetScheduleContracts"
def body = """
{
  "branchId": ${API.BRANCH_ID},
  "subscriberNumber": ${API.SUBSCRIBER_NUM},
  "isShowCanceled": false,
  "contractFilter": 0
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json != null : "TC03: respuesta es null"

println "  -> respuesta obtenida correctamente"
if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
println "  TC03 PASSED"
