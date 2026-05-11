import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC05_InsertLogbookModuleEvent_Equipos
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que InsertLogbookModuleEvent registra un evento de bitacora para el modulo Equipos.
 * Precondiciones: Usuario con permisos de bitacora.
 * Datos:         module=EquipmentsWeb, process=AssignedEquipmentToCrews.
 * Resultado esperado: HTTP 200, evento registrado.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC05 - InsertLogbookModuleEvent Equipos ====="
def token = API.getOAuthToken(passwordAPI)

def now = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())
def url = "${API.BASE_URL_DATA}/logbooks-process/api/InsertLogbookModuleEvent"
def body = """
{
  "dateEnd": "${now}",
  "dateStart": "${now}",
  "messageValidity": "Proceso ejecutado con exito",
  "module": "EquipmentsWeb",
  "process": "AssignedEquipmentToCrews",
  "status": "Success",
  "parameter": "{\\"serieId\\":\\"${API.SERIE_ID}\\",\\"typeEquipmentId\\":${API.TYPE_EQUIPMENT_ID},\\"branchId\\":${API.BRANCH_ID}}",
  "response": "{}",
  "processCorrect": true,
  "serie": "${API.SERIE_ID}",
  "isProcessMassive": false,
  "locationRegisterMovement": "",
  "typeEquipment": ${API.TYPE_EQUIPMENT_ID},
  "typeCrew": "",
  "crew": "",
  "mainTransactionId": "",
  "profileId": ${API.TYPE_USER},
  "profileDescription": "Supervisor Equipos Corp",
  "branchId": ${API.BRANCH_ID},
  "branchDescription": "Guadalajara",
  "regionId": 2,
  "regionDescription": "OCCIDENTE",
  "subscriberBranchId": 0,
  "subscriberBranchDescription": "",
  "subscriberNumber": 0,
  "subscriberName": "",
  "subscriberRegionId": 0,
  "subscriberRegionDescription": "",
  "userId": "${API.USER_ID}",
  "userName": "MARIO ALBERTO GONZALEZ CISNEROS"
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

println "  -> respuesta: ${resp.getResponseBodyContent().take(500)}"
println "  TC05 PASSED"
