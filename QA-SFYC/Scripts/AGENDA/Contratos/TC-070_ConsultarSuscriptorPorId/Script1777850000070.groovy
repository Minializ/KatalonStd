/**
 * ID:            TC-070_ConsultarSuscriptorPorId
 * Modulo:        AGENDA
 * Objetivo:      Validar que al consultar un suscriptor por su numero de ID
 *                se muestren correctamente sus datos generales.
 * Precondiciones: Usuario con acceso a AGENDA, suscriptor existente.
 * Datos:         numeroSuscriptor (parametro del TC).
 * Resultado esperado: Se muestra el dashboard del suscriptor con su numero visible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-070_ConsultarSuscriptorPorId - Suscriptor: ' + numeroSuscriptor)

// 1) Login a AGENDA
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_AGENDA,
    GlobalVariable.USER_AGENDA,
    GlobalVariable.PASSWORD_AGENDA)

// 2) Consultar suscriptor por ID
CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'(numeroSuscriptor)

// 3) Esperar a que aparezca el suscriptor en resultados y hacer doble click
WebUI.waitForElementVisible(
    findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'),
    GlobalVariable.TIME_OUT)

WebUI.doubleClick(
    findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'))

// 4) Validar que se cargo el dashboard del suscriptor
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/PaginaInicio/LABEL_NumeroSuscriptor'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

String suscriptorMostrado = WebUI.getText(
    findTestObject('Object Repository/AGENDA/PaginaInicio/LABEL_NumeroSuscriptor'))

WebUI.verifyMatch(suscriptorMostrado, '.*' + numeroSuscriptor.takeLast(7) + '.*', true,
    FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-070 - Suscriptor consultado exitosamente: ' + suscriptorMostrado)
