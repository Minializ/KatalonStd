import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC07_GetTypeEquipments
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint OData TypeEquipments retorna informacion del tipo de equipo por ID.
 * Precondiciones: Tipo de equipo con ID=2 existente.
 * Datos:         TypeEquipmentId=2.
 * Resultado esperado: HTTP 200, respuesta con id, description.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC07 - GetTypeEquipments ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/equipments/api/TypeEquipments(${API.TYPE_EQUIPMENT_ID})?" +
    "\$select=id,description&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json.id != null          : "TC07: id es null"
assert json.description != null : "TC07: description es null"

println "  -> id: ${json.id}"
println "  -> description: ${json.description}"
println "  TC07 PASSED"
