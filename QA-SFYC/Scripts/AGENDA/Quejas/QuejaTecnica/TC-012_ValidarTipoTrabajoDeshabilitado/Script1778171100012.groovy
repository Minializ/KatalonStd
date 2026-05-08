/**
 * ID:            TC-012_ValidarTipoTrabajoDeshabilitado
 * Modulo:        AGENDA - Quejas Tecnicas
 * Objetivo:      Validar que el campo Tipo Trabajo esta deshabilitado (no editable)
 *                y muestra el valor correcto 'Quejas' al abrir el formulario
 *                desde la sub-tab Quejas.
 * Precondiciones: Usuario con permisos de AGENDA y suscriptor activo.
 * Resultado esperado: Campo Tipo Trabajo deshabilitado con valor 'Quejas'.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-012_ValidarTipoTrabajoDeshabilitado - Suscriptor: ' + numeroSuscriptor)

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
// PASO 4: Abrir formulario de agregar queja
// =====================================================
WebUI.click(findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/BUTTON_NuevoRegistro'))

WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/POPUP_Trabajos'),
    GlobalVariable.TIME_OUT)

// =====================================================
// PASO 5: Validar que Tipo Trabajo esta deshabilitado
// =====================================================
WebUI.verifyElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_TipoTrabajo'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// El campo Tipo Trabajo debe estar deshabilitado (pre-seteado a Quejas = 2)
WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_TipoTrabajo'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

// =====================================================
// PASO 6: Validar que el campo Reporte es visible
//         (solo aparece cuando tipo trabajo = Quejas)
// =====================================================
WebUI.verifyElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Reporte'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-012 - Tipo Trabajo DESHABILITADO y Reporte VISIBLE: CORRECTO')
