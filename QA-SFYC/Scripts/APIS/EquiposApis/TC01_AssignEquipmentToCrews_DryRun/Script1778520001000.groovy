import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC01_AssignEquipmentToCrews_DryRun
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint AssignEquipmentToCrews con dryRun=true retorna informacion del equipo sin asignarlo.
 * Precondiciones: Equipo con serie existente en sucursal 41.
 * Datos:         serieId=044306, typeEquipmentId=2, branchId=41, dryRun=true.
 * Resultado esperado: HTTP 200, validationResult.isValid=true, equipment no null.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC01 - AssignEquipmentToCrews DryRun ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/equipments-process/api/AssignEquipmentToCrews?api-version=1.0"
def body = """
{
  "crewId": null,
  "typeCrewId": null,
  "typeEquipmentId": ${API.TYPE_EQUIPMENT_ID},
  "branchId": ${API.BRANCH_ID},
  "branchGroupId": ${API.BRANCH_ID},
  "locationEquipmentId": "${API.LOCATION_EQUIP_ID}",
  "zoneTime": "${API.ZONE_TIME}",
  "dryRun": true,
  "getEquipments": false,
  "byLocation": true,
  "serieId": "${API.SERIE_ID}",
  "typeUser": ${API.TYPE_USER},
  "isInventory": false
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json.validationResult != null          : "TC01: validationResult es null"
assert json.validationResult.isValid == true  : "TC01: validationResult.isValid debe ser true"
assert json.result != null                    : "TC01: result es null"
assert json.result.equipment != null          : "TC01: result.equipment es null"

println "  -> equipment id: ${json.result.equipment.id}"
println "  -> serieId: ${json.result.equipment.serieId}"
println "  -> modelId: ${json.result.equipment.modelId}"
println "  -> stateId: ${json.result.equipment.stateId}"
println "  -> isValid: ${json.validationResult.isValid}"
println "  TC01 PASSED"
