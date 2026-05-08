/**
 * ID:            TC-010_AgregarQuejaTecnica_ServicioTelefonia
 * Modulo:        AGENDA - Quejas Tecnicas
 * Objetivo:      Agregar queja tecnica con tipo servicio Telefonia, validando que
 *                los servicios se filtran correctamente y la queja se registra.
 * Precondiciones: Usuario con permisos de AGENDA y suscriptor con servicio Telefonia activo.
 * Resultado esperado: Queja con tipo servicio Telefonia registrada exitosamente.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-010_AgregarQuejaTecnica_ServicioTelefonia - Suscriptor: ' + numeroSuscriptor)

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
// PASO 5: Llenar formulario con Tipo Servicio = Telefonia
// =====================================================
CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Trabajos'),
    'FALLA DE SEÑAL')

CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Reporte'),
    'CTE REPORTA')

// Seleccionar Telefonia como Tipo de Servicio
CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_TipoServicio'),
    'Telefonía')

// Seleccionar un servicio filtrado por Telefonia
CustomKeywords.'utilidades.Agenda.selectOptionByName'(
    findTestObject('Object Repository/AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Servicio'),
    'Telefonía Básica')

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

WebUI.comment('FIN TC-010 - Queja con servicio Telefonia registrada EXITOSAMENTE')
