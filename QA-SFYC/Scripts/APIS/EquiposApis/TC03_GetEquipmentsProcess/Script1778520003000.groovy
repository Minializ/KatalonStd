import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC03_GetEquipmentsProcess
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint GetEquipmentsProcess retorna equipos asignados a una cuadrilla.
 * Precondiciones: Cuadrilla con equipos asignados en sucursal 41.
 * Datos:         typeCrewId='E', crewId=4, branchId=41.
 * Resultado esperado: HTTP 200, respuesta con result.equipments array.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC03 - GetEquipmentsProcess ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/equipments-process/api/GetEquipmentsProcess?api-version=1.0"
def body = """
{
  "crewId": 4,
  "typeCrewId": "E  ",
  "typeEquipmentId": ${API.TYPE_EQUIPMENT_ID},
  "branchId": ${API.BRANCH_ID},
  "branchGroupId": ${API.BRANCH_ID},
  "locationEquipmentId": null,
  "zoneTime": "${API.ZONE_TIME}",
  "dryRun": false,
  "getEquipments": true,
  "byLocation": false,
  "serieId": "",
  "typeUser": ${API.TYPE_USER},
  "isInventory": false
}
"""

def req = API.buildPostRequest(url, body, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json.result != null : "TC03: result es null"
assert json.result.containsKey("equipments") : "TC03: result no contiene 'equipments'"
assert json.result.equipments instanceof List : "TC03: equipments no es un array"

println "  -> total equipos en cuadrilla: ${json.result.equipments.size()}"
if (json.result.equipments.size() > 0) {
    def first = json.result.equipments[0]
    println "  -> primer equipo - serieId: ${first.serieId}, modelId: ${first.modelId}, stateId: ${first.stateId}"
}
println "  -> limitedExceeded: ${json.result.limitedExceeded}"
println "  TC03 PASSED"
