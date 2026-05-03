/**
 * ID:            TC-072_ModificarQueja
 * Modulo:        AGENDA
 * Objetivo:      Validar que se puede editar una queja administrativa existente
 *                y guardar los cambios correctamente.
 * Precondiciones: Usuario con acceso a AGENDA, suscriptor con queja existente.
 * Datos:         numeroSuscriptor (parametro del TC).
 * Resultado esperado: Mensaje de exito al guardar la modificacion de la queja.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-072_ModificarQueja - Suscriptor: ' + numeroSuscriptor)

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

// 4) Seleccionar primera queja existente
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasAdm/TR_QuejaASI'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/TR_QuejaASI'))

// 5) Click en Editar
WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/BUTTON_Editar'))

// 6) Modificar campo Motivo
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Motivo'),
    GlobalVariable.TIME_OUT)

WebUI.clearText(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Motivo'))

WebUI.setText(
    findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Motivo'),
    'Modificacion de queja - Agente QA ' + new Date().format('yyyy-MM-dd HH:mm'))

// 7) Enviar y validar
WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/BUTTON_Enviar'))

WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-072 - Queja modificada exitosamente')
