/**
 * ID:            TC-004_ValidarCamposObligatorios
 * Modulo:        EQUIPOS - Operaciones - Administracion de Anis
 * Objetivo:      Validar que los botones Crear Rango y Buscar Rango permanecen
 *                deshabilitados cuando los campos obligatorios (Desde, Hasta,
 *                Codigo Local) no estan llenos. Prueba con formulario vacio
 *                y con llenado parcial.
 * Precondiciones: - Usuario con permisos de Administracion de Anis
 * Resultado esperado: Botones deshabilitados sin campos obligatorios.
 * Autor:         QA Automatizacion
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-004_ValidarCamposObligatorios')

// =====================================================
// PASO 1: Login al modulo EQUIPOS
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_EQUIPOS,
    GlobalVariable.USER_EQUIPOS,
    GlobalVariable.PASSWORD_EQUIPOS)

// =====================================================
// PASO 2: Navegar a Operaciones > Administracion de Anis
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Administración de Anis', '')

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 3: Validar formulario vacio - botones deshabilitados
// =====================================================
WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_CrearRango'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_BuscarRango'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('Formulario vacio: botones deshabilitados - OK')

// =====================================================
// PASO 4: Llenar solo campo Desde - sigue deshabilitado
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisDesde'), '1000000')

WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_CrearRango'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Solo Desde: botones deshabilitados - OK')

// =====================================================
// PASO 5: Llenar Desde + Hasta sin Codigo Local - sigue deshabilitado
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisHasta'), '1000100')

WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_CrearRango'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Desde + Hasta sin Codigo Local: botones deshabilitados - OK')

// =====================================================
// PASO 6: Llenar Codigo Local - ahora si habilitado
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_CodigoLocal'), '33')

WebUI.verifyElementClickable(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_CrearRango'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_BuscarRango'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-004 - Campos obligatorios validados: botones se habilitan solo con todos los campos')
