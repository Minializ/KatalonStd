import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC25 - POST GetPreferenceByBranchGroupId - HTTP 204 (services-process)
// Valida que preferencia no configurada retorna 204 No Content

println "\n===== TC25 - GetPreferenceByBranchGroupId (HTTP 204) ====="
def token = API.getOAuthToken(passwordAPI)
def reqTC25 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/services-process/api/GetPreferenceByBranchGroupId",
    """{"KeyPreference":"HabilitarCambioTecnologia","BranchGroupId":${API.BRANCH_ID}}""",
    token
)
ResponseObject respTC25 = WS.sendRequest(reqTC25)

WS.verifyResponseStatusCode(respTC25, 204)
println "  -> HTTP 204 No Content (preferencia no configurada) - comportamiento esperado"
println "  TC25 PASSED"
