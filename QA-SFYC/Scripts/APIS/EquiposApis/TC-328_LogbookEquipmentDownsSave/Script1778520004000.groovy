import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC04_LogbookEquipmentDownsSave
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint LogbookEquipmentDownsSave registra una baja de equipo en bitacora.
 * Precondiciones: Equipo existente, usuario con permisos.
 * Datos:         serieId=044306, typeEquipmentId=2, branchId=41.
 * Resultado esperado: HTTP 200, respuesta exitosa.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC04 - LogbookEquipmentDownsSave ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/equipments-process/api/LogbookEquipmentDownsSave?api-version=1.0"
def body = """
{
  "typeEquipmentId": ${API.TYPE_EQUIPMENT_ID},
  "branchId": ${API.BRANCH_ID},
  "serieId": "${API.SERIE_ID}",
  "userId": "${API.USER_ID}",
  "zoneTime": "${API.ZONE_TIME}"
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
println "  -> respuesta: ${resp.getResponseBodyContent().take(500)}"
println "  TC04 PASSED"
