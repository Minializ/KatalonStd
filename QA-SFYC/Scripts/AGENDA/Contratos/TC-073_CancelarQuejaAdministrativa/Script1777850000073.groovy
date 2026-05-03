/**
 * ID:            TC-073_CancelarQuejaAdministrativa
 * Modulo:        AGENDA
 * Objetivo:      Validar el flujo de cancelacion de una queja administrativa,
 *                seleccionando una causa de cancelacion y confirmando.
 * Precondiciones: Usuario con acceso a AGENDA, suscriptor con queja activa.
 * Datos:         numeroSuscriptor (parametro del TC).
 * Resultado esperado: Mensaje de confirmacion de queja cancelada.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-073_CancelarQuejaAdministrativa - Suscriptor: ' + numeroSuscriptor)

// 1) Login a AGENDA
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_AGENDA,
    GlobalVariable.USER_AGENDA,
    GlobalVariable.PASSWORD_AGENDA)

// 2) Consultar suscriptor
CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'(numeroSuscriptor)

WebUI.waitForElementVisible(
    findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'),
    GlobalVariable.TIME_OUT)

WebUI.doubleClick(
    findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'))

// 3) Navegar a tab Quejas Adm.
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Quejas Adm.']),
    GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Quejas Adm.']))

// 4) Seleccionar queja y abrir opciones
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasAdm/TR_QuejaASI'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/TR_QuejaASI'))

WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/BUTTON_AccionesQuejaASI'))

// 5) Seleccionar opcion Cancelar
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/Quejas/OPT_Cancelacion'),
    GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/OPT_Cancelacion'))

// 6) Seleccionar causa de cancelacion
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/Quejas/SELECT_CausaCancelacion'),
    GlobalVariable.TIME_OUT)

CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/Quejas/SELECT_CausaCancelacion'),
    'Duplicada')

// 7) Ingresar observaciones
WebUI.setText(
    findTestObject('Object Repository/AGENDA/Quejas/INPUT_Observaciones'),
    'Cancelacion por prueba automatizada - Agente QA')

// 8) Confirmar cancelacion
WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/BUTTON_Cancelar'))

// 9) Confirmar dialogo
WebUI.waitForElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'),
    GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'))

// 10) Validar mensaje de exito
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-073 - Queja cancelada exitosamente')
