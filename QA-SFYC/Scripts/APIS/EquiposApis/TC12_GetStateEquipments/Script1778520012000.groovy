import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC12_GetStateEquipments
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint OData StateEquipments retorna informacion del estado por ID.
 * Precondiciones: Estado 'OK ' existente en catalogo.
 * Datos:         StateId='OK '.
 * Resultado esperado: HTTP 200, respuesta con id, description.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC12 - GetStateEquipments ====="
def token = API.getOAuthToken(passwordAPI)

def stateId = "OK "
def stateEncoded = java.net.URLEncoder.encode(stateId, "UTF-8").replace('+', '%20')
def url = "${API.BASE_URL_DATA}/equipments/api/StateEquipments('${stateEncoded}')?" +
    "\$select=id,description&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json.id != null          : "TC12: id es null"
assert json.description != null : "TC12: description es null"

println "  -> id: '${json.id}'"
println "  -> description: ${json.description}"
println "  TC12 PASSED"
