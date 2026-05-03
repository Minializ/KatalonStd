/**
 * ID:            TC-101_CajeroPago
 * Modulo:        SERVICIOS
 * Objetivo:      Validar el registro de un pago en el modulo Cajero.
 * Precondiciones: Usuario con acceso a SERVICIOS, suscriptor con saldo pendiente.
 * Datos:         numeroSuscriptor (parametro del TC).
 * Resultado esperado: Pago registrado exitosamente con mensaje de confirmacion.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-101_CajeroPago - Suscriptor: ' + numeroSuscriptor)

// 1) Login a SERVICIOS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_SERVICIOS,
    GlobalVariable.USER_SERVICIOS,
    GlobalVariable.PASSWORD_SERVICIOS)

// 2) Navegar a Cajero
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Servicios', 'Cajero', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Consultar suscriptor
WebUI.waitForElementPresent(
    findTestObject('Object Repository/SERVICIOS/Cajero/INPUT_Suscriptor'),
    GlobalVariable.TIME_OUT)
WebUI.setText(
    findTestObject('Object Repository/SERVICIOS/Cajero/INPUT_Suscriptor'),
    numeroSuscriptor)
WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/BUTTON_Consultar'))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Registrar pago
WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/BUTTON_Pagar'))

// 5) Confirmar pago
WebUI.waitForElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'),
    GlobalVariable.TIME_OUT)
WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'))

// 6) Validar mensaje de exito
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-101 - Pago registrado exitosamente')
