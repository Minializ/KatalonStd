import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.AgendaApiHelper as API

// TC19 - POST InsertLogbookModuleEvent (logbooks-process)
// Registra evento en bitacora del modulo

println "\n===== TC19 - InsertLogbookModuleEvent ====="
def token = API.getOAuthToken(passwordAPI)
def now = new Date().format("yyyy-MM-dd'T'HH:mm:ssXXX")
def reqTC19 = API.buildPostRequest(
    "${API.BASE_URL_DATA}/logbooks-process/api/InsertLogbookModuleEvent",
    """{"dateEnd":"${now}","dateStart":"${now}","messageValidity":"Success","module":"CustomerServiceWeb","process":"QuerySubscriber","status":"Success","profileId":79,"crew":"","isProcessMassive":false,"locationRegisterMovement":"","serie":"","typeCrew":"","typeEquipment":""}""",
    token
)
ResponseObject respTC19 = WS.sendRequest(reqTC19)

WS.verifyResponseStatusCode(respTC19, 200)
WS.verifyElementPropertyValue(respTC19, "result", true)
WS.verifyElementPropertyValue(respTC19, "validationResult.isValid", true)

def parser = new JsonSlurper()
def bodyTC19 = parser.parseText(respTC19.getResponseBodyContent())
assert bodyTC19.result == true                       : "TC19: result debe ser true"
assert bodyTC19.validationResult.isValid == true     : "TC19: validationResult.isValid debe ser true"
assert bodyTC19.validationResult.errors.size() == 0  : "TC19: errors debe estar vacio"
println "  TC19 PASSED"
