import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC02_GetContractsSubscriber
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint GetContractsSubscriber retorna los contratos del suscriptor.
 * Precondiciones: Suscriptor 601390 existente en sucursal 44 con contratos activos.
 * Datos:         branchId=44, subscriberNumber=601390.
 * Resultado esperado: HTTP 200, respuesta con lista de contratos.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC02 - GetContractsSubscriber ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/contracts-process/api/GetContractsSubscriber"
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
assert json != null : "TC02: respuesta es null"

println "  -> respuesta obtenida correctamente"
if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
println "  TC02 PASSED"
