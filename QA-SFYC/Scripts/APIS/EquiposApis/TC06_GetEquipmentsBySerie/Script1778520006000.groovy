import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC06_GetEquipmentsBySerie
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint OData Equipments retorna equipo filtrado por serieId y branchGroupId.
 * Precondiciones: Equipo con serie 48575443012992A4 en sucursal 41.
 * Datos:         serieId=48575443012992A4, branchGroupId=41.
 * Resultado esperado: HTTP 200, respuesta con array value con al menos un equipo.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC06 - GetEquipmentsBySerie ====="
def token = API.getOAuthToken(passwordAPI)

def seriePadded = API.SERIE_ID_MOV.padRight(27)
def serieEncoded = java.net.URLEncoder.encode("'${seriePadded}'", "UTF-8").replace('+', '%20')
def url = "${API.BASE_URL_DATA}/equipments/api/Equipments?" +
    "\$filter=(serieId%20eq%20${serieEncoded})%20and%20(branchGroupId%20eq%20${API.BRANCH_ID})" +
    "&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
API.verifyArrayNotEmpty(json, "value", "TC06")

def equip = json.value[0]
assert equip.serieId != null       : "TC06: serieId es null"
assert equip.branchGroupId != null : "TC06: branchGroupId es null"

println "  -> total equipos: ${json.value.size()}"
println "  -> equipo - id: ${equip.id}, serieId: ${equip.serieId?.trim()}, modelId: ${equip.modelId}"
println "  -> stateId: ${equip.stateId}, locationId: ${equip.locationId}"
println "  TC06 PASSED"
