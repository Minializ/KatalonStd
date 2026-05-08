/**
 * ID:            TC-011_ValidarFormularioVacio
 * Modulo:        AGENDA - Quejas Tecnicas
 * Objetivo:      Validar que al abrir el formulario de queja tecnica sin llenar
 *                ningun campo, el boton Aceptar permanece deshabilitado.
 *                Verifica que los validators required funcionan correctamente.
 * Precondiciones: Usuario con permisos de AGENDA y suscriptor activo.
 * Resultado esperado: Boton Aceptar deshabilitado con formulario vacio.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-011_ValidarFormularioVacio - Suscriptor: ' + numeroSuscriptor)

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
// PASO 4: Abrir formulario sin llenar ningun campo
// =====================================================
WebUI.click(findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/BUTTON_NuevoRegistro'))

WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/POPUP_Trabajos'),
    GlobalVariable.TIME_OUT)

WebUI.takeScreenshot()

// =====================================================
// PASO 5: Validar que los campos obligatorios estan vacios
// =====================================================

// Verificar que el popup se abrio correctamente
WebUI.verifyElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Trabajos'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_TipoServicio'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Servicio'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_TipoContrato'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// =====================================================
// PASO 6: Validar boton Aceptar deshabilitado
// =====================================================
WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/BUTTON_Aceptar'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-011 - Formulario vacio: Boton Aceptar DESHABILITADO correctamente')
