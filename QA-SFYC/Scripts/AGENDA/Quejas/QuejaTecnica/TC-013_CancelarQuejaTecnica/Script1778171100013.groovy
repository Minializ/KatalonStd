/**
 * ID:            TC-013_CancelarQuejaTecnica
 * Modulo:        AGENDA - Quejas Tecnicas
 * Objetivo:      Cancelar una queja tecnica existente. Se navega a la sub-tab
 *                Quejas, se selecciona una queja activa (ASI), se abre el menu
 *                de opciones, se selecciona Cancelar, se elige causa de cancelacion
 *                y se valida que el estatus cambie a CAN.
 * Precondiciones: Usuario con permisos de AGENDA, suscriptor con queja activa.
 * Resultado esperado: Queja cancelada con estatus CAN.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-013_CancelarQuejaTecnica - Suscriptor: ' + numeroSuscriptor)

// =====================================================
// PASO 1: Login a AGENDA
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_AGENDA,
    GlobalVariable.USER_AGENDA,
    GlobalVariable.PASSWORD_AGENDA)

// =====================================================
// PASO 2: Consultar suscriptor y abrir detalle
// =====================================================
CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'(numeroSuscriptor)

CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()

WebUI.waitForElementVisible(
    findTestObject('AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'),
    GlobalVariable.TIME_OUT)

WebUI.doubleClick(
    findTestObject('AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'))

// =====================================================
// PASO 3: Navegar a Trabajos > Quejas
// =====================================================
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Trabajos']),
    GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Trabajos']))

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Quejas']))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 4: Seleccionar queja activa (ASI) y obtener nombre
// =====================================================
def nombreTrabajo = WebUI.getText(
    findTestObject('Object Repository/AGENDA/Quejas/TR_QuejaASI', [('idxCol') : '2']))

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/TR_QuejaASI', [('idxCol') : '2']))

// =====================================================
// PASO 5: Abrir menu de opciones
// =====================================================
WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/BUTTON_VerOpciones'))

// =====================================================
// PASO 6: Click en Cancelar
// =====================================================
WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/BUTTON_Cancelar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.eliminarChatBot'()

// =====================================================
// PASO 7: Seleccionar causa de cancelacion
// =====================================================
WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/SELECT_CausaCancelacion'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/OPT_Cancelacion',
    [('opcion') : 'Cte. informa serv reestablecido']))

// =====================================================
// PASO 8: Ingresar observaciones y enviar
// =====================================================
WebUI.setText(findTestObject('Object Repository/AGENDA/Quejas/INPUT_Observaciones'),
    'CANCELACION QUEJA TECNICA - KATALON TC-013')

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/BUTTON_Enviar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.takeScreenshot()

// =====================================================
// PASO 9: Validar estatus CAN
// =====================================================
def estatusQueja = WebUI.getText(
    findTestObject('Object Repository/AGENDA/Quejas/TD_Queja',
        [('nombreTrabajo') : nombreTrabajo]))

WebUI.verifyMatch(estatusQueja, 'CAN', false)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-013 - Queja cancelada: estatus = CAN CORRECTO')
