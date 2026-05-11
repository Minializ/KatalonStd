package utilidades

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper

import java.security.SecureRandom
import java.security.MessageDigest

class AgendaApiHelper {

	static String BASE_URL_DATA = "https://data.qa-cluster.sfycnextgen.com.mx"
	static String BASE_URL_MULE = "https://mgcl-api-megacable-sfyc.dev.mega-mule.mx"
	static String BASE_URL_INN  = "https://inn-wsdiqueconsumer-sys-api.dev.mega-mule.mx"
	static int    BRANCH_ID      = 41
	static int    SUBSCRIBER_NUM = 1521390
	static String BRANCH_GROUP   = "041"
	static String SUB_CONTRACT   = "0411521390"
	static String MAC_ADDRESS    = "48575443DEFD32AB"

	@Keyword
	static String getOAuthToken(String password) {
		def IDP_BASE       = "https://idp.qa-cluster.sfycnextgen.com.mx/ev2/security"
		def IDP_TOKEN_URL  = "${IDP_BASE}/connect/token"
		def IDP_AUTHORIZE  = "${IDP_BASE}/connect/authorize"
		def IDP_LOGIN_URL  = "${IDP_BASE}/Account/Login"
		def CLIENT_ID      = "sfyc-menu"
		def REDIRECT_URI   = "https://global.qa-cluster.sfycnextgen.com.mx/ui/signin-oidc"
		def API_USER       = GlobalVariable.USER_AGENDA
		def API_PASSWORD   = password
		def DOMAIN         = "sfyc"

		def SCOPES = "openid profile roles equipmentApi subscriberApi saleApi serviceApi " +
			"workOrderApi contractApi chargeApi addressApi branchApi userApi logbookApi " +
			"paymentApi cashierApi debtCalculationApi administrativeComplaintApi materialApi sfycApi"

		// Paso 1: PKCE
		byte[] randomBytes = new byte[32]
		new SecureRandom().nextBytes(randomBytes)
		def codeVerifier = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
		byte[] sha256 = MessageDigest.getInstance("SHA-256").digest(codeVerifier.getBytes("ASCII"))
		def codeChallenge = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(sha256)

		// Paso 2: GET /authorize
		def encRedirectUri = java.net.URLEncoder.encode(REDIRECT_URI, "UTF-8")
		def encScopes = java.net.URLEncoder.encode(SCOPES, "UTF-8")
		def authorizeUrl = "${IDP_AUTHORIZE}?client_id=${CLIENT_ID}" +
			"&redirect_uri=${encRedirectUri}&response_type=code&scope=${encScopes}" +
			"&code_challenge=${codeChallenge}&code_challenge_method=S256" +
			"&state=katalon_api_test&nonce=${System.currentTimeMillis()}"

		def cookieManager = new java.net.CookieManager()
		java.net.CookieHandler.setDefault(cookieManager)

		def conn = new java.net.URL(authorizeUrl).openConnection() as java.net.HttpURLConnection
		conn.setInstanceFollowRedirects(true)
		conn.setRequestMethod("GET")
		conn.connect()

		def loginPageHtml = ""
		if (conn.responseCode == 200) {
			loginPageHtml = conn.inputStream.getText("UTF-8")
		} else if (conn.responseCode == 302) {
			def loc = conn.getHeaderField("Location")
			if (!loc.startsWith("http")) loc = "https://idp.qa-cluster.sfycnextgen.com.mx" + loc
			def conn2 = new java.net.URL(loc).openConnection() as java.net.HttpURLConnection
			conn2.setInstanceFollowRedirects(true)
			conn2.setRequestMethod("GET")
			conn2.connect()
			loginPageHtml = conn2.inputStream.getText("UTF-8")
		}
		assert loginPageHtml.contains("__RequestVerificationToken") : "No se pudo obtener la pagina de login del IDP"

		def csrfMatcher = (loginPageHtml =~ /__RequestVerificationToken.*?value="([^"]+)"/)
		def returnUrlMatcher = (loginPageHtml =~ /id="ReturnUrl".*?value="([^"]+)"/)
		assert csrfMatcher.find() : "No se encontro __RequestVerificationToken"
		assert returnUrlMatcher.find() : "No se encontro ReturnUrl"
		def csrfToken = csrfMatcher.group(1)
		def returnUrl = returnUrlMatcher.group(1).replace("&amp;", "&")

		// Paso 3: POST login
		def loginPostBody = "ReturnUrl=${java.net.URLEncoder.encode(returnUrl, 'UTF-8')}" +
			"&Domain=${DOMAIN}" +
			"&Username=${java.net.URLEncoder.encode(API_USER, 'UTF-8')}" +
			"&Password=${java.net.URLEncoder.encode(API_PASSWORD, 'UTF-8')}" +
			"&button=login" +
			"&__RequestVerificationToken=${java.net.URLEncoder.encode(csrfToken, 'UTF-8')}"

		def loginConn = new java.net.URL(IDP_LOGIN_URL).openConnection() as java.net.HttpURLConnection
		loginConn.setInstanceFollowRedirects(false)
		loginConn.setRequestMethod("POST")
		loginConn.setDoOutput(true)
		loginConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
		loginConn.outputStream.write(loginPostBody.getBytes("UTF-8"))
		loginConn.outputStream.flush()
		assert loginConn.responseCode == 302 : "Login fallo. Status: ${loginConn.responseCode}"
		def loginRedirect = loginConn.getHeaderField("Location")

		// Paso 4: Callback para obtener code
		def callbackUrl = loginRedirect.startsWith("http") ? loginRedirect :
			"https://idp.qa-cluster.sfycnextgen.com.mx" + loginRedirect
		def callbackConn = new java.net.URL(callbackUrl).openConnection() as java.net.HttpURLConnection
		callbackConn.setInstanceFollowRedirects(false)
		callbackConn.setRequestMethod("GET")
		callbackConn.connect()
		assert callbackConn.responseCode == 302 : "Callback no retorno redirect. Status: ${callbackConn.responseCode}"
		def codeRedirect = callbackConn.getHeaderField("Location")
		def codeUri = new java.net.URI(codeRedirect)
		def queryParams = codeUri.query.split("&").collectEntries { param ->
			def parts = param.split("=", 2)
			[(parts[0]): java.net.URLDecoder.decode(parts[1], "UTF-8")]
		}
		def authCode = queryParams["code"]
		assert authCode : "No se obtuvo authorization code"

		// Paso 5: Intercambiar code por token
		def tokenBody = "grant_type=authorization_code" +
			"&client_id=${CLIENT_ID}" +
			"&code=${authCode}" +
			"&redirect_uri=${encRedirectUri}" +
			"&code_verifier=${codeVerifier}"

		RequestObject tokenReq = new RequestObject()
		tokenReq.setRestUrl(IDP_TOKEN_URL)
		tokenReq.setRestRequestMethod("POST")
		tokenReq.setBodyContent(new com.kms.katalon.core.testobject.impl.HttpTextBodyContent(
			tokenBody, "UTF-8", "application/x-www-form-urlencoded"
		))
		tokenReq.setHttpHeaderProperties([
			new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/x-www-form-urlencoded")
		])

		ResponseObject tokenResp = WS.sendRequest(tokenReq)
		assert tokenResp.getStatusCode() == 200 : "No se pudo obtener token. Status: ${tokenResp.getStatusCode()}"

		def tokenJson = new JsonSlurper().parseText(tokenResp.getResponseBodyContent())
		return "Bearer ${tokenJson.access_token}"
	}

	@Keyword
	static RequestObject buildPostRequest(String url, String body, String authToken) {
		RequestObject req = new RequestObject()
		req.setRestUrl(url)
		req.setRestRequestMethod("POST")
		req.setBodyContent(new com.kms.katalon.core.testobject.impl.HttpTextBodyContent(
			body, "UTF-8", "application/json"
		))
		def headers = [
			new TestObjectProperty("Content-Type",  ConditionType.EQUALS, "application/json"),
			new TestObjectProperty("Accept",        ConditionType.EQUALS, "application/json"),
			new TestObjectProperty("Authorization", ConditionType.EQUALS, authToken)
		]
		req.setHttpHeaderProperties(headers)
		return req
	}

	@Keyword
	static RequestObject buildGetRequest(String url, String authToken) {
		RequestObject req = new RequestObject()
		req.setRestUrl(url)
		req.setRestRequestMethod("GET")
		def headers = [
			new TestObjectProperty("Accept",        ConditionType.EQUALS, "application/json"),
			new TestObjectProperty("Authorization", ConditionType.EQUALS, authToken)
		]
		req.setHttpHeaderProperties(headers)
		return req
	}

	@Keyword
	static void verifyNotNull(ResponseObject resp, String fieldPath) {
		def json = new JsonSlurper().parseText(resp.getResponseBodyContent())
		def value = fieldPath.tokenize('.').inject(json) { obj, prop ->
			if (obj == null) return null
			def arrayMatch = (prop =~ /^\[(\d+)\]$/)
			if (arrayMatch.matches()) return obj[arrayMatch.group(1).toInteger()]
			def propArray = (prop =~ /^(\w+)\[(\d+)\]$/)
			if (propArray.matches()) return obj[propArray.group(1)]?.getAt(propArray.group(2).toInteger())
			return obj[prop]
		}
		assert value != null : "Campo '${fieldPath}' es null en la respuesta"
	}
}
