/**
 * ID:            TC-007_ValidarCampoObligatorio_TipoContrato
 * Modulo:        AGENDA - Quejas Tecnicas
 * Objetivo:      Validar que el boton Aceptar permanece deshabilitado cuando
 *                no se selecciona Tipo de Contrato (campo obligatorio).
 * Precondiciones: Usuario con permisos de AGENDA y suscriptor activo.
 * Resultado esperado: Boton Aceptar deshabilitado sin Tipo de Contrato.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-007_ValidarCampoObligatorio_TipoContrato - Suscriptor: ' + numeroSuscriptor)

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
// PASO 5: Llenar campos EXCEPTO Tipo de Contrato
// =====================================================
CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Trabajos'),
    'FALLA DE SEÑAL')

CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_TipoServicio'),
    'Cable')

CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Servicio'),
    'TV Básico Plus')

// NO seleccionar Tipo de Contrato

WebUI.takeScreenshot()

// =====================================================
// PASO 6: Validar que boton Aceptar esta deshabilitado
// =====================================================
WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/BUTTON_Aceptar'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-007 - Boton Aceptar DESHABILITADO sin Tipo de Contrato: CORRECTO')
