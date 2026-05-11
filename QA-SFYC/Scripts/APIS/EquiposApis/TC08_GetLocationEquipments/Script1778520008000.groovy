import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC08_GetLocationEquipments
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint OData LocationEquipments retorna informacion de ubicacion por ID compuesto.
 * Precondiciones: Ubicacion 41|0|002 existente.
 * Datos:         LocationEquipmentId='41|0|002'.
 * Resultado esperado: HTTP 200, respuesta con id, description, locationId.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC08 - GetLocationEquipments ====="
def token = API.getOAuthToken(passwordAPI)

def locationEncoded = java.net.URLEncoder.encode(API.LOCATION_EQUIP_ID, "UTF-8").replace('+', '%20')
def url = "${API.BASE_URL_DATA}/equipments/api/LocationEquipments('${locationEncoded}')?" +
    "\$select=id,description,locationId,branchId,warehouseId&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json.id != null          : "TC08: id es null"
assert json.locationId != null  : "TC08: locationId es null"

println "  -> id: ${json.id}"
println "  -> description: ${json.description}"
println "  -> locationId: ${json.locationId}"
println "  -> branchId: ${json.branchId}"
println "  TC08 PASSED"
