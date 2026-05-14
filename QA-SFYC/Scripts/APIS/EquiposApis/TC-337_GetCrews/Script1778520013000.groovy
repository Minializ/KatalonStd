import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC13_GetCrews
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que el endpoint OData Crews retorna cuadrillas filtradas por tipo y sucursal activas.
 * Precondiciones: Cuadrillas tipo 'E' activas en sucursal 41.
 * Datos:         TypeCrewId='E', BranchId=41.
 * Resultado esperado: HTTP 200, respuesta con array value de cuadrillas.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC13 - GetCrews ====="
def token = API.getOAuthToken(passwordAPI)

def typeCrewEncoded = java.net.URLEncoder.encode("'E  '", "UTF-8").replace('+', '%20')
def url = "${API.BASE_URL_DATA}/crews/api/Crews?" +
    "\$orderby=crewId" +
    "&\$top=10" +
    "&\$select=id,crewId,mobility,active,technicianChief,supervisor,technicianAuxuliary" +
    "&\$filter=(typeCrewId%20eq%20${typeCrewEncoded})%20and%20(branchId%20eq%20${API.BRANCH_ID})%20and%20(active%20eq%20true)" +
    "&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
assert json.containsKey("value") : "TC13: respuesta debe contener 'value'"
assert json.value instanceof List : "TC13: 'value' debe ser un array"
assert json.value.size() > 0      : "TC13: debe haber al menos una cuadrilla activa"

def crew = json.value[0]
assert crew.containsKey("crewId") : "TC13: cuadrilla debe tener crewId"
assert crew.containsKey("active") : "TC13: cuadrilla debe tener active"
assert crew.active == true         : "TC13: cuadrilla debe estar activa"

println "  -> total cuadrillas: ${json.value.size()}"
println "  -> primera cuadrilla - crewId: ${crew.crewId}, mobility: ${crew.mobility}"
println "  TC13 PASSED"
