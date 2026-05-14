import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC11_GetMovStateEquipments
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint OData MovStateEquipments retorna historial de cambios de estado.
 * Precondiciones: Equipo con cambios de estado registrados.
 * Datos:         serieId=48575443012992A4, typeEquipmentId=2, branchId=41.
 * Resultado esperado: HTTP 200, respuesta con array value de movimientos de estado.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC11 - GetMovStateEquipments ====="
def token = API.getOAuthToken(passwordAPI)

def seriePadded = API.SERIE_ID_MOV.padRight(27)
def serieEncoded = java.net.URLEncoder.encode("'${seriePadded}'", "UTF-8").replace('+', '%20')
def url = "${API.BASE_URL_DATA}/equipments/api/MovStateEquipments?" +
    "\$select=id,branchId,typeEquipmentId,serieId,date,state,folioId" +
    "&\$filter=(serieId%20eq%20${serieEncoded})%20and%20(typeEquipmentId%20eq%20${API.TYPE_EQUIPMENT_ID})%20and%20(branchId%20eq%20${API.BRANCH_ID})" +
    "&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
API.verifyIsArray(json, "value", "TC11")

println "  -> total movimientos de estado: ${json.value.size()}"
if (json.value.size() > 0) {
    def first = json.value[0]
    println "  -> movimiento - date: ${first.date}, state: ${first.state}, folioId: ${first.folioId}"
}
println "  TC11 PASSED"
