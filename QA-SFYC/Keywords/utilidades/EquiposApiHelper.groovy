package utilidades

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper

/**
 * Helper reutilizable para los Test Cases de APIs del modulo EQUIPOS.
 * Delega autenticacion OAuth y construccion de requests a AgendaApiHelper.
 */
class EquiposApiHelper {

	// ── Constantes de entorno ──
	static String BASE_URL_DATA = AgendaApiHelper.BASE_URL_DATA
	static int    BRANCH_ID     = AgendaApiHelper.BRANCH_ID

	// ── Constantes especificas de Equipos ──
	static int    TYPE_EQUIPMENT_ID = 2
	static String SERIE_ID          = "044306"
	static String SERIE_ID_MOV      = "48575443012992A4"
	static String LOCATION_EQUIP_ID = "41|0|002"
	static String ZONE_TIME         = "C"
	static int    TYPE_USER         = 88
	static String USER_ID           = "MAGONZALEZ"

	// ── Delegados a AgendaApiHelper ──
	@Keyword
	static String getOAuthToken(String password) {
		return AgendaApiHelper.getOAuthToken(password)
	}

	@Keyword
	static RequestObject buildPostRequest(String url, String body, String authToken) {
		return AgendaApiHelper.buildPostRequest(url, body, authToken)
	}

	@Keyword
	static RequestObject buildGetRequest(String url, String authToken) {
		return AgendaApiHelper.buildGetRequest(url, authToken)
	}

	// ── Metodos auxiliares de verificacion ──
	@Keyword
	static void verifyFieldEquals(def json, String field, def expected, String tcId) {
		assert json[field] == expected : "${tcId}: se esperaba ${field}=${expected} pero fue ${json[field]}"
	}

	@Keyword
	static void verifyArrayNotEmpty(def json, String arrayField, String tcId) {
		assert json.containsKey(arrayField) : "${tcId}: respuesta no contiene '${arrayField}'"
		assert json[arrayField] instanceof List : "${tcId}: '${arrayField}' no es un array"
		assert json[arrayField].size() > 0 : "${tcId}: '${arrayField}' esta vacio"
	}

	@Keyword
	static void verifyIsArray(def json, String arrayField, String tcId) {
		assert json.containsKey(arrayField) : "${tcId}: respuesta no contiene '${arrayField}'"
		assert json[arrayField] instanceof List : "${tcId}: '${arrayField}' no es un array"
	}

	@Keyword
	static def parseResponse(ResponseObject resp) {
		return new JsonSlurper().parseText(resp.getResponseBodyContent())
	}
}
