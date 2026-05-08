/**
 * ID:            TC-004_AgregarQuejaTecnica_HappyPath
 * Modulo:        AGENDA - Quejas Tecnicas
 * Objetivo:      Flujo completo de alta de queja tecnica: login, consultar
 *                suscriptor, navegar a Trabajos > Quejas, abrir formulario,
 *                llenar todos los campos y enviar. Validar mensaje de exito.
 * Precondiciones: Usuario con permisos de AGENDA y suscriptor activo.
 * Resultado esperado: Queja tecnica registrada exitosamente.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-004_AgregarQuejaTecnica_HappyPath - Suscriptor: ' + numeroSuscriptor)

// =====================================================
// PASO 1: Login a AGENDA
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_AGENDA,
    GlobalVariable.USER_AGENDA,
    GlobalVariable.PASSWORD_AGENDA)

// =====================================================
// PASO 2: Consultar suscriptor
// =====================================================
CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'(numeroSuscriptor)

CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()

WebUI.waitForElementVisible(
    findTestObject('AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'),
    GlobalVariable.TIME_OUT)

WebUI.doubleClick(
    findTestObject('AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'))

// =====================================================
// PASO 3: Navegar a Tab Trabajos > Sub-tab Quejas
// =====================================================
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Trabajos']),
    GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Trabajos']))

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Quejas']))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 4: Click en boton + (Nuevo Registro) para abrir formulario
// =====================================================
WebUI.click(findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/BUTTON_NuevoRegistro'))

WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/POPUP_Trabajos'),
    GlobalVariable.TIME_OUT)

// =====================================================
// PASO 5: Llenar formulario de queja tecnica
// =====================================================

// Seleccionar Trabajo
CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Trabajos'),
    'FALLA DE SEÑAL')

// Seleccionar Reporte (solo visible cuando tipo trabajo = Quejas)
CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Reporte'),
    'CTE REPORTA')

// Seleccionar Tipo Servicio
CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_TipoServicio'),
    'Cable')

// Seleccionar Servicio
CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Servicio'),
    'TV Básico Plus')

// Seleccionar Tipo de Contrato
CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_TipoContrato'),
    'Principal')

WebUI.takeScreenshot()

// =====================================================
// PASO 6: Enviar formulario
// =====================================================
WebUI.click(findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/BUTTON_Aceptar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 7: Validar mensaje de exito
// =====================================================
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-004_AgregarQuejaTecnica_HappyPath - Queja registrada EXITOSAMENTE')
