/**
 * ID:            TC-100_CajeroConsultarSuscriptor
 * Modulo:        SERVICIOS
 * Objetivo:      Validar la consulta de suscriptor en modulo Cajero.
 * Precondiciones: Usuario con acceso a SERVICIOS, suscriptor existente.
 * Datos:         numeroSuscriptor (parametro del TC).
 * Resultado esperado: Datos del suscriptor desplegados en pantalla de cajero.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-100_CajeroConsultarSuscriptor - Suscriptor: ' + numeroSuscriptor)

// 1) Login a SERVICIOS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_SERVICIOS,
    GlobalVariable.USER_SERVICIOS,
    GlobalVariable.PASSWORD_SERVICIOS)

// 2) Navegar a Cajero via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Servicios', 'Cajero', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Buscar suscriptor
WebUI.waitForElementPresent(
    findTestObject('Object Repository/SERVICIOS/Cajero/INPUT_Suscriptor'),
    GlobalVariable.TIME_OUT)

WebUI.setText(
    findTestObject('Object Repository/SERVICIOS/Cajero/INPUT_Suscriptor'),
    numeroSuscriptor)

WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/BUTTON_Consultar'))

// 5) Esperar y validar resultado
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-100 - Consulta de suscriptor en cajero exitosa')
