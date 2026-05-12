import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC15_ValidatePromotionByBranchId
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint ValidatePromotionByBranchId retorna la validacion de una promocion.
 * Precondiciones: Promocion 3998 existente en sucursal 41.
 * Datos:         branchId=41, promotionId=3998.
 * Resultado esperado: HTTP 200, respuesta con datos de validacion.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC15 - ValidatePromotionByBranchId ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/services-process/api/ValidatePromotionByBranchId?" +
    "branchId=${API.BRANCH_GROUP_ID}&promotionId=${API.PROMOTION_ID}"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json != null : "TC15: respuesta es null"

println "  -> respuesta obtenida correctamente"
if (json instanceof Map) {
    if (json instanceof List) { println "  -> registros: ${json.size()}" } else { println "  -> keys: ${json.keySet()}" }
} else {
    println "  -> valor: ${json}"
}
println "  TC15 PASSED"
