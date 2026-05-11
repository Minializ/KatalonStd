import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC09_GetModelEquipments
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint OData ModelEquipments retorna informacion del modelo de equipo.
 * Precondiciones: Modelo de equipo existente (ej: 2|HG8245W5).
 * Datos:         Modelo HG8245W5, TypeEquipmentId=2.
 * Resultado esperado: HTTP 200, respuesta con id, modelId, description, typeEquipmentId.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC09 - GetModelEquipments ====="
def token = API.getOAuthToken(passwordAPI)

def modelKey = "2|HG8245W5  "
def modelEncoded = java.net.URLEncoder.encode(modelKey, "UTF-8").replace('+', '%20')
def url = "${API.BASE_URL_DATA}/equipments/api/ModelEquipments('${modelEncoded}')?" +
    "\$select=id,modelId,description,typeEquipmentId,typeAddressable,supportNgv,supportVoice,supportIptv" +
    "&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json.id != null              : "TC09: id no debe ser null"
assert json.modelId != null         : "TC09: modelId no debe ser null"
assert json.description != null     : "TC09: description no debe ser null"
assert json.typeEquipmentId != null : "TC09: typeEquipmentId no debe ser null"

println "  -> id: ${json.id}"
println "  -> modelId: ${json.modelId}"
println "  -> description: ${json.description}"
println "  -> typeEquipmentId: ${json.typeEquipmentId}"
println "  -> supportNgv: ${json.supportNgv}"
println "  -> supportVoice: ${json.supportVoice}"
println "  -> supportIptv: ${json.supportIptv}"
println "  TC09 PASSED"
