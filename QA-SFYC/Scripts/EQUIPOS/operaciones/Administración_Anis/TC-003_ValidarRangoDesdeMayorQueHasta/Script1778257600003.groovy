/**
 * ID:            TC-003_ValidarRangoDesdeMayorQueHasta
 * Modulo:        EQUIPOS - Operaciones - Administracion de Anis
 * Objetivo:      Validar que cuando el campo Desde es mayor que Hasta,
 *                los botones Crear Rango y Buscar Rango permanecen
 *                deshabilitados (validacion cruzada numberLessThatNumberValidator).
 * Precondiciones: - Usuario con permisos de Administracion de Anis
 * Resultado esperado: Botones deshabilitados cuando Desde > Hasta.
 * Autor:         QA Automatizacion
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-003_ValidarRangoDesdeMayorQueHasta')

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
// PASO 3: Llenar campo Desde con valor MAYOR
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisDesde'), '9000000')

// =====================================================
// PASO 4: Llenar campo Hasta con valor MENOR
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisHasta'), '1000000')

// =====================================================
// PASO 5: Llenar Codigo Local (campo obligatorio)
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_CodigoLocal'), '33')

WebUI.takeScreenshot()

// =====================================================
// PASO 6: Validar que Crear Rango esta deshabilitado
// =====================================================
WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_CrearRango'),
    FailureHandling.STOP_ON_FAILURE)

// =====================================================
// PASO 7: Validar que Buscar Rango esta deshabilitado
// =====================================================
WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_BuscarRango'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-003 - Rango Desde > Hasta: Botones DESHABILITADOS correctamente')
