import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC04_GetSubscriberLinesData
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint GetSubscriberLinesData retorna las lineas de datos por GUID de suscriptor.
 * Precondiciones: Suscriptor con GUID b6e229ce-ebd9-4c7a-b283-be54ba75087c existente.
 * Datos:         subscriberId=b6e229ce-ebd9-4c7a-b283-be54ba75087c.
 * Resultado esperado: HTTP 200, respuesta con lineas de datos del suscriptor.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC04 - GetSubscriberLinesData ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/contracts-process/api/GetSubscriberLinesData/${API.SUBSCRIBER_ID}"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json != null : "TC04: respuesta es null"

println "  -> respuesta obtenida correctamente"
if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
println "  TC04 PASSED"
