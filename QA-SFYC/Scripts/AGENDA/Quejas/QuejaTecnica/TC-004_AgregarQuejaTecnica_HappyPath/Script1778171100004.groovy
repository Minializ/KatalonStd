/**
 * ID:            TC-004_AgregarQuejaTecnica_HappyPath
 * Modulo:        AGENDA - Quejas Tecnicas
 * Objetivo:      Flujo completo de alta de queja tecnica: login, consultar
 *                suscriptor, navegar a Trabajos > Quejas, abrir formulario
 *                via Ver opciones > Agregar, llenar todos los campos del
 *                formulario (tipo servicio, horario, telefono, sucursal, CIS,
 *                codificacion cascada, reporte) y enviar.
 * Precondiciones: Usuario con permisos de AGENDA y suscriptor activo.
 * Datos:         numeroSuscriptor (variable del TC).
 * Resultado esperado: Queja tecnica registrada exitosamente, visible en tab Generales.
 * Autor:         Agente QA (generado desde grabacion)
 * Fecha:         2026-05-09
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-004_AgregarQuejaTecnica_HappyPath - Suscriptor: ' + numeroSuscriptor)

try {
    // =====================================================
    // Steps 1-4: Login a AGENDA (Iniciar sesion)
    // =====================================================
    CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
        GlobalVariable.URL_AGENDA,
        GlobalVariable.USER_AGENDA,
        GlobalVariable.PASSWORD_AGENDA)

    // =====================================================
    // Steps 5-6: Consultar suscriptor
    // =====================================================
    CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'(numeroSuscriptor)

    // Steps 7-9: Cerrar popups/alertas que aparezcan tras la consulta
    CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()

    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // =====================================================
    // Step 10: Click en tab "Trabajos"
    // =====================================================
    WebUI.waitForElementPresent(
        findTestObject('AGENDA/QuejasTecnicas/TAB_Trabajos'),
        GlobalVariable.TIME_OUT)

    WebUI.click(findTestObject('AGENDA/QuejasTecnicas/TAB_Trabajos'))

    // Step 11: Click en sub-tab "Quejas"
    WebUI.waitForElementPresent(
        findTestObject('AGENDA/QuejasTecnicas/TAB_Quejas'),
        GlobalVariable.TIME_OUT)

    WebUI.click(findTestObject('AGENDA/QuejasTecnicas/TAB_Quejas'))

    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // =====================================================
    // Step 12: Click en "Ver opciones"
    // =====================================================
    WebUI.waitForElementClickable(
        findTestObject('AGENDA/Quejas/BUTTON_VerOpciones'),
        GlobalVariable.TIME_OUT)

    WebUI.click(findTestObject('AGENDA/Quejas/BUTTON_VerOpciones'))

    // Step 13: Click en "Agregar"
    WebUI.waitForElementClickable(
        findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/BUTTON_Agregar'),
        GlobalVariable.TIME_OUT)

    WebUI.click(findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/BUTTON_Agregar'))

    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // =====================================================
    // Steps 14-15: Seleccionar Tipo de Servicio = "Internet"
    // =====================================================
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_TipoServicioDD'),
        'Internet')

    // =====================================================
    // Steps 16-17: Seleccionar Horario Instalacion = "Todo el dia"
    // =====================================================
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_HorarioInstalacion'),
        'Todo el dia')

    // =====================================================
    // Steps 18-19: Seleccionar Telefono del suscriptor
    // =====================================================
    WebUI.waitForElementPresent(
        findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Telefono'),
        GlobalVariable.TIME_OUT_CONTENT)

    WebUI.click(findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/SELECT_Telefono'))

    WebUI.delay(1)

    WebUI.click(findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/DIV_Opcion-Dyn',
        [('opcion') : 'Web celular']))

    // =====================================================
    // Steps 20-21: Seleccionar Sucursal = "Guadalajara"
    // =====================================================
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Sucursal'),
        'Guadalajara')

    // =====================================================
    // Steps 22-23: Seleccionar CIS = "CIS DEPTO INGRESOS"
    // =====================================================
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_CIS'),
        'CIS DEPTO INGRESOS')

    // =====================================================
    // Steps 24-35: Codificacion en cascada (callDropdown x6)
    // =====================================================

    // Step 24-25: Nivel 1 - "CCN"
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo2'),
        'CCN')

    // Step 26-27: Nivel 2 - "CODIFICACION UNIFICADA"
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo2'),
        'CODIFICACION UNIFICADA')

    // Step 28-29: Nivel 3 - "ANALOGO"
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo2'),
        'ANALOGO')

    // Step 30-31: Nivel 4 - "NO TENGO SEÑAL DE CABLE"
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo2'),
        'NO TENGO SEÑAL DE CABLE')

    // Step 32-33: Nivel 5 - "SIN SEÑAL EN NINGUN CANAL"
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo2'),
        'SIN SEÑAL EN NINGUN CANAL')

    // Step 34-35: Nivel 6 - "QUEJA"
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo2'),
        'QUEJA')

    // =====================================================
    // Step 36: Ingresar texto en campo Reporte
    // =====================================================
    WebUI.click(findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/TXTAREA_Reporte'))

    WebUI.setText(findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/TXTAREA_Reporte'),
        'Prueba automatizada - Alta de queja tecnica TC-004')

    WebUI.takeScreenshot()

    // =====================================================
    // Step 37: Click en "Enviar"
    // =====================================================
    WebUI.click(findTestObject('AGENDA/Quejas/BUTTON_Enviar'))

    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // =====================================================
    // Validar mensaje de exito
    // =====================================================
    WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'),
        GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

    WebUI.takeScreenshot()

    // =====================================================
    // Step 38: Click en tab "Generales" para verificar
    // =====================================================
    WebUI.click(findTestObject('AGENDA/QuejasTecnicas/FormQuejaTecnica/TAB_Generales'))

    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // Step 39: Verificar que aparece "CODIFICACION UNIFICADA" en la grid
    WebUI.verifyTextPresent('CODIFICACION UNIFICADA', false, FailureHandling.STOP_ON_FAILURE)

    WebUI.takeScreenshot()

    WebUI.comment('FIN TC-004_AgregarQuejaTecnica_HappyPath - Queja registrada EXITOSAMENTE')
} finally {
    WebUI.closeBrowser()
}
