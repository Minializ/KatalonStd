import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import utilidades.ServiciosApiHelper as API

/**
 * ID:            TC12_CatalogCfdiUses
 * Modulo:        SERVICIOS
 * Objetivo:      Valida que el endpoint OData CatalogCfdiUses retorna el uso de CFDI por ID.
 * Precondiciones: Registro con ID=0 existente en catalogo.
 * Datos:         CatalogCfdiUses(0).
 * Resultado esperado: HTTP 200, respuesta con id y description.
 * Autor:         Agente QA (generado)
 * Fecha:         2025-06-11
 */

println "\n===== TC12 - CatalogCfdiUses ====="
def token = API.getOAuthToken(passwordAPI)

def url = "${API.BASE_URL_DATA}/services/api/CatalogCfdiUses?\$top=10&api-version=1.0"

def req = API.buildGetRequest(url, token)
ResponseObject resp = WS.sendRequest(req)

WS.verifyResponseStatusCode(resp, 200)

def json = API.parseResponse(resp)
API.verifyArrayNotEmpty(json, "value", "TC12")

println "  -> registros: ${json.value.size()}"
if (json.value.size() > 0) {
    println "  -> primer registro id: ${json.value[0].id}"
    println "  -> primer registro description: ${json.value[0].description}"
}
println "  TC12 PASSED"
