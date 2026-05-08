/**
 * ID:            TC-003_ValidarUbicacionOrigenIgualDestino
 * Modulo:        EQUIPOS - Operaciones - Cambio Masivo de Ubicacion
 * Objetivo:      Validar que al seleccionar la misma ubicacion como origen
 *                y destino, el formulario no permite enviar (validacion cruzada
 *                noEqualValueValidator). Sucursal 41.
 * Precondiciones: - Usuario con permisos de Cambio Masivo de Ubicacion
 * Resultado esperado: Boton Aceptar deshabilitado cuando origen == destino.
 * Autor:         QA Automatizacion
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-003_ValidarUbicacionOrigenIgualDestino - Sucursal 41')

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
// PASO 4: Seleccionar Tipo Equipo
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_TipoEquipo'), '19')

// =====================================================
// PASO 5: Seleccionar MISMA ubicacion en Origen y Destino (LOG)
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_UbicacionOrigen'), 'LOG')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_UbicacionDestino'), 'LOG')

WebUI.takeScreenshot()

// =====================================================
// PASO 6: Validar que el boton Aceptar esta deshabilitado
// =====================================================
WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-003 - Ubicacion origen == destino: Boton Aceptar DESHABILITADO correctamente')
