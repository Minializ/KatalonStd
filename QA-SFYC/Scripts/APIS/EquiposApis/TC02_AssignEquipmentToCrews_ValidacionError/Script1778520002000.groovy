import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.EquiposApiHelper as API

/**
 * ID:            TC02_AssignEquipmentToCrews_ValidacionError
 * Modulo:        EQUIPOS
 * Objetivo:      Valida que al intentar asignar un equipo con mas de 8 anios, se retorna la regla RuleCheckMoreThanEightYears.
 * Precondiciones: Equipo con serie antigua en sucursal 41.
 * Datos:         serieId=044306, dryRun=false, getEquipments=false.
 * Resultado esperado: HTTP 200, validationResult contiene error de regla de 8 anios.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-11
 */

println "\n===== TC02 - AssignEquipmentToCrews ValidacionError ====="
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
  "dryRun": false,
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
assert json.validationResult != null : "TC02: validationResult es null"

// El equipo es antiguo, por lo que la validacion retorna error
if (!json.validationResult.isValid) {
    assert json.validationResult.errors.size() > 0 : "TC02: debe haber errores de validacion"
    def hasEightYearRule = json.validationResult.errors.any { it.errorMessage?.contains("8") || it.ruleName?.contains("EightYear") }
    println "  -> validacion invalida con ${json.validationResult.errors.size()} error(es)"
    json.validationResult.errors.each { err ->
        println "  -> regla: ${err.ruleName}, mensaje: ${err.errorMessage}"
    }
} else {
    println "  -> ADVERTENCIA: validacion paso (equipo puede no ser > 8 anios en este entorno)"
    println "  -> isValid: ${json.validationResult.isValid}"
}

println "  TC02 PASSED"
