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

try {
    // 1) Login a SERVICIOS
    CustomKeywords.'utilidades.FuncionesAuxiliares.login'(GlobalVariable.URL_SERVICIOS, GlobalVariable.USER_SERVICIOS, GlobalVariable.PASSWORD_SERVICIOS)

    // 2) Navegar a Cajero via menu
    CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Cajero', '', '')

    // 3) Ingresar numero de suscriptor
    WebUI.setMaskedText(findTestObject('SERVICIOS/Cajero/INPUT_NumeroSuscriptor'), '0' + numeroSuscriptor)
    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

	/*
    // 4) Seleccionar usuario IYMUNGUIA
    CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Usuario'), 'IYMUNGUIA')

    // 5) Seleccionar CIS 001
    CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Cis'), '001')

    // 6) Seleccionar Cajero 3343
    CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Cajero'), '3343')

    // 7) Ingresar contrasenia del cajero
    WebUI.waitForElementVisible(findTestObject('SERVICIOS/Cajero/IngresarCajero/INPUT_Contrasenia'), 20)
    WebUI.setEncryptedText(findTestObject('SERVICIOS/Cajero/IngresarCajero/INPUT_Contrasenia'), PasswordCajero)
    WebUI.delay(2)

    // 8) Click Aceptar para ingresar al cajero
    WebUI.click(findTestObject('SERVICIOS/Cajero/IngresarCajero/BUTTON_Aceptar'))*/

    // 9) Limpiar popup si aparece
    CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()

    // 10) Esperar y validar que se muestran datos del suscriptor
    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    WebUI.verifyElementPresent(findTestObject('SERVICIOS/Cajero/BUTTON_Calcular'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

    WebUI.comment('FIN TC-100 - Consulta de suscriptor en cajero exitosa')
} finally {
    WebUI.closeBrowser()
}
