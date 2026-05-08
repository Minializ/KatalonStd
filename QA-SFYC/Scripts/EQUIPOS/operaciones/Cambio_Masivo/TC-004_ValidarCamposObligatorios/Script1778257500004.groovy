/**
 * ID:            TC-004_ValidarCamposObligatorios
 * Modulo:        EQUIPOS - Operaciones - Cambio Masivo de Ubicacion
 * Objetivo:      Validar que el boton Aceptar permanece deshabilitado
 *                cuando no se llenan los campos obligatorios del formulario.
 *                Se verifica con formulario vacio y con llenado parcial.
 *                Sucursal 41.
 * Precondiciones: - Usuario con permisos de Cambio Masivo de Ubicacion
 * Resultado esperado: Boton Aceptar deshabilitado sin campos obligatorios.
 * Autor:         QA Automatizacion
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-004_ValidarCamposObligatorios - Sucursal 41')

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
// PASO 4: Verificar formulario vacio - boton deshabilitado
// =====================================================
WebUI.verifyElementPresent(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_TipoEquipo'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('Formulario vacio: boton Aceptar deshabilitado - OK')

// =====================================================
// PASO 5: Llenar solo Tipo Equipo - boton sigue deshabilitado
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_TipoEquipo'), '19')

WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Solo Tipo Equipo: boton Aceptar deshabilitado - OK')

// =====================================================
// PASO 6: Agregar Ubicacion Origen - boton sigue deshabilitado
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_UbicacionOrigen'), 'LOG')

WebUI.verifyElementNotClickable(
    findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Tipo Equipo + Ubicacion Origen: boton Aceptar deshabilitado - OK')

WebUI.takeScreenshot()

WebUI.comment('FIN TC-004 - Campos obligatorios validados: Boton DESHABILITADO correctamente')
