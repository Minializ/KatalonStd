import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC05_GetContractBilling
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint GetContractBilling retorna la facturacion del contrato.
 * Precondiciones: Suscriptor 601390 existente en sucursal 44 con cisId=1.
 * Datos:         branchId=44, subscriberNumber=601390, cisId=1.
 * Resultado esperado: HTTP 200, respuesta con datos de facturacion.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC05 - GetContractBilling ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/services-process/api/GetContractBilling"
def body = """
{
  "branchId": ${API.BRANCH_ID},
  "subscriberNumber": ${API.SUBSCRIBER_NUM},
  "cisId": 1,
  "isRfcCurpPassportEdit": false,
  "legalRepresentativeCurp": "",
  "legalRepresentativeName": ""
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json != null : "TC05: respuesta es null"

println "  -> respuesta obtenida correctamente"
if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
println "  TC05 PASSED"
