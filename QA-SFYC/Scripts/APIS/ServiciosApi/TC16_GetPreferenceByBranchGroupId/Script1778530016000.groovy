import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC16_GetPreferenceByBranchGroupId
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint GetPreferenceByBranchGroupId retorna preferencias de configuracion.
 * Precondiciones: Preferencia BASICO_CODIFICADO existente para grupo 41.
 * Datos:         KeyPreference=BASICO_CODIFICADO, BranchGroupId=41.
 * Resultado esperado: HTTP 200, respuesta con datos de preferencia.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC16 - GetPreferenceByBranchGroupId ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/services-process/api/GetPreferenceByBranchGroupId"
def body = """
{
  "KeyPreference": "BASICO_CODIFICADO",
  "BranchGroupId": ${API.BRANCH_GROUP_ID}
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json != null : "TC16: respuesta es null"

println "  -> respuesta obtenida correctamente"
if (json instanceof Map) {
    if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
} else {
    println "  -> valor: ${json}"
}
println "  TC16 PASSED"
