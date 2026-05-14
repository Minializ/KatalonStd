import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC14 - POST ValidateTechnicalComplaint (services-process)
// Valida reglas de negocio antes de registrar queja tecnica
// Prerequisito: obtiene idColonia de GetGeneralData

println "\n===== TC14 - ValidateTechnicalComplaint ====="
def token = API.getOAuthToken(passwordAPI)
def parser = new JsonSlurper()

// Prerequisito: obtener idColonia via GetGeneralData
def reqPre = API.buildPostRequest(
    "${API.BASE_URL_DATA}/subscribers-process/api/GetGeneralData",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM}}""",
    token
)
ResponseObject respPre = WS.sendRequest(reqPre)
WS.verifyResponseStatusCode(respPre, 200)
def bodyPre = parser.parseText(respPre.getResponseBodyContent())
def idColonia = bodyPre.id_colonia
println "  -> idColonia (prerequisito): ${idColonia}"

// TC14 principal
def reqTC14 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/services-process/api/ValidateTechnicalComplaint",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM},"neighborhoodId":"${idColonia}","userType":79}""",
    token
)
ResponseObject respTC14 = WS.sendRequest(reqTC14)

WS.verifyResponseStatusCode(respTC14, 200)
API.verifyNotNull(respTC14, "result.tipoQueja")

def bodyTC14 = parser.parseText(respTC14.getResponseBodyContent())
assert bodyTC14.result != null            : "TC14: result no debe ser null"
assert bodyTC14.result.tipoQueja != null  : "TC14: tipoQueja debe estar presente"
println "  -> tipoQueja: ${bodyTC14.result.tipoQueja}"
println "  -> repetitiva: ${bodyTC14.result.repetitiva}"
println "  TC14 PASSED"
