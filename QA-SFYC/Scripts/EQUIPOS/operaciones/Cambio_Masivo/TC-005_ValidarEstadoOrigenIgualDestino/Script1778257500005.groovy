/**
 * ID:            TC-005_ValidarEstadoOrigenIgualDestino
 * Modulo:        EQUIPOS - Operaciones - Cambio Masivo de Ubicacion
 * Objetivo:      Validar que al habilitar "Considerar Cambio de Estado" y
 *                seleccionar el mismo estado como origen y destino, el
 *                formulario no permite enviar (validacion cruzada
 *                noEqualValueValidator en estados). Sucursal 41.
 * Precondiciones: - Usuario con permisos de Cambio Masivo de Ubicacion
 * Resultado esperado: Boton Aceptar deshabilitado cuando estado origen == destino.
 * Autor:         QA Automatizacion
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-005_ValidarEstadoOrigenIgualDestino - Sucursal 41')

// =====================================================
// PASO 1: Login al modulo EQUIPOS
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_EQUIPOS,
    GlobalVariable.USER_EQUIPOS,
    GlobalVariable.PASSWORD_EQUIPOS)

// =====================================================
// PASO 2: Seleccionar Sucursal 41
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('OBJETOS_GLOBALES/SELECT_Sucursal'), '41')

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 3: Navegar a Operaciones > Cambio Masivo de Ubicacion
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Cambio Masivo de Ubicación', '')

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 4: Habilitar Considerar Cambio de Estado
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/CHECK_CambioEstado'))

// =====================================================
// PASO 5: Llenar campos obligatorios de ubicacion
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_TipoEquipo'), '19')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_UbicacionOrigen'), 'LOG')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_UbicacionDestino'), 'ALM')

// =====================================================
// PASO 6: Seleccionar MISMO estado en Origen y Destino (DÑA)
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_EstadoOrigen'), 'DÑA')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_EstadoDestino'), 'DÑA')

WebUI.takeScreenshot()

// =====================================================
// PASO 7: Validar que el boton Aceptar esta deshabilitado
// =====================================================
WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-005 - Estado origen == destino: Boton Aceptar DESHABILITADO correctamente')
