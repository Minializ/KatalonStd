import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC10_GetMovLocationEquipments
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint OData MovLocationEquipments retorna historial de movimientos de ubicacion.
 * Precondiciones: Equipo con movimientos de ubicacion registrados.
 * Datos:         serieId=48575443012992A4, typeEquipmentId=2, branchId=41.
 * Resultado esperado: HTTP 200, respuesta con array value de movimientos.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC10 - GetMovLocationEquipments ====="
def token = API.getOAuthToken(passwordAPI)

// La serie en OData tiene padding de espacios
def seriePadded = API.SERIE_ID_MOV.padRight(27)
def serieEncoded = java.net.URLEncoder.encode("'${seriePadded}'", "UTF-8").replace('+', '%20')
def url = "${API.BASE_URL_DATA}/equipments/api/MovLocationEquipments?" +
    "\$orderby=folioId%20desc" +
    "&\$select=id,date,locationId,subscriber,userId,typeCrewId,crewId,branchId,folioId" +
    "&\$filter=(serieId%20eq%20${serieEncoded})%20and%20(typeEquipmentId%20eq%20${API.TYPE_EQUIPMENT_ID})%20and%20(branchId%20eq%20${API.BRANCH_ID})" +
    "&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
API.verifyIsArray(json, "value", "TC10")

println "  -> total movimientos de ubicacion: ${json.value.size()}"
if (json.value.size() > 0) {
    def first = json.value[0]
    println "  -> ultimo movimiento - date: ${first.date}, locationId: ${first.locationId}, userId: ${first.userId}"
}
println "  TC10 PASSED"
