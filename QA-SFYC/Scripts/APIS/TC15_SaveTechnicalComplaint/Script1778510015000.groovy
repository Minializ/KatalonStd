import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC15 - POST SaveTechnicalComplaint (services-process)
// Registra queja tecnica y correlaciona folio generado
// Prerequisito: obtiene contractId de GetContractsSubscriber

println "\n===== TC15 - SaveTechnicalComplaint ====="
def token = API.getOAuthToken(passwordAPI)
def parser = new JsonSlurper()

// Prerequisito: obtener contractId
def reqPre = API.buildPostRequest(
    "${API.BASE_URL_DATA}/contracts-process/api/GetContractsSubscriber",
    """{"branchId":${API.BRANCH_ID},"subscriberNumber":${API.SUBSCRIBER_NUM}}""",
    token
)
ResponseObject respPre = WS.sendRequest(reqPre)
WS.verifyResponseStatusCode(respPre, 200)
def bodyPre = parser.parseText(respPre.getResponseBodyContent())
def contrato = bodyPre.result?.activeContracts?.size() > 0 ?
    bodyPre.result.activeContracts[0].contractId :
    "1791d126-bc98-4083-a904-b82807154060"
println "  -> contractId (prerequisito): ${contrato}"

// TC15 principal
def reqTC15 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/services-process/api/SaveTechnicalComplaint",
    """{"sucursal":${API.BRANCH_ID},"suscriptor":${API.SUBSCRIBER_NUM},"tipo_trabajo":2,"trabajo":1,"id_trabajo":"2|1","tipo_servicio":1,"servicio":7,"tipo_contrato":"P","id_contrato":"${contrato}","reporto":"AUTOMATIZACION","reporte":"QUEJA TECNICA TEST AUTOMATIZADO KATALON","profileId":79}""",
    token
)
ResponseObject respTC15 = WS.sendRequest(reqTC15)

WS.verifyResponseStatusCode(respTC15, 200)
WS.verifyElementPropertyValue(respTC15, "validationResult.isValid", true)
API.verifyNotNull(respTC15, "result")

def bodyTC15 = parser.parseText(respTC15.getResponseBodyContent())
assert bodyTC15.validationResult.isValid == true : "TC15: validationResult.isValid debe ser true"
assert bodyTC15.result != null && bodyTC15.result != "" : "TC15: result (folio) no debe estar vacio"
assert bodyTC15.result =~ /^[0-9]+$/ : "TC15: El folio debe ser numerico"
println "  -> Folio queja generado: ${bodyTC15.result}"
println "  TC15 PASSED"
