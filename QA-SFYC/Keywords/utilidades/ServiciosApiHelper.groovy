package utilidades

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper

/**
 * Helper reutilizable para los Test Cases de APIs del modulo SERVICIOS.
 * Delega autenticacion OAuth y construccion de requests a AgendaApiHelper.
 */
class ServiciosApiHelper {

	// ── Constantes de entorno ──
	static String BASE_URL_DATA   = AgendaApiHelper.BASE_URL_DATA
	static int    BRANCH_ID       = 44
	static int    BRANCH_GROUP_ID = 41

	// ── Constantes especificas de Servicios ──
	static int    SUBSCRIBER_NUM     = 601390
	static String SUBSCRIBER_ID      = "b6e229ce-ebd9-4c7a-b283-be54ba75087c"
	static int    CIS_ID             = 24
	static String CIS_COMPOUND_ID    = "44|24"
	static String STREET_ID          = "d1510dbb-7eca-40c4-9ac9-144695368ead"
	static String PERSON_TYPE        = "PF"
	static String MODULE_SER         = "SER"
	static int    PROMOTION_ID       = 3998
	static String USERNAME           = "APSANTILLAV"
	static int    PROFILE_ID         = 79
	static int    MODULE_ID          = 3
	static String MODULE_DESCRIPTION = "ServiceWeb"

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
