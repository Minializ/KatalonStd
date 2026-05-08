/**
 * ID:            TC-002_CambioMasivoConCambioEstado
 * Modulo:        EQUIPOS - Operaciones - Cambio Masivo de Ubicacion
 * Objetivo:      Validar el flujo completo de cambio masivo con la opcion
 *                "Considerar Cambio de Estado" habilitada. Sucursal 41.
 *                Tipo equipo 19, Origen LOG, Destino ALM,
 *                Estado Origen DÑA, Estado Destino F04, carga CSV.
 * Precondiciones: - Usuario con permisos de Cambio Masivo de Ubicacion
 *                 - Archivo CSV con series validas en Data Files/Equipos/
 * Resultado esperado: Proceso de cambio masivo con cambio de estado exitoso.
 * Autor:         QA Automatizacion
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

String archivoCMU = RunConfiguration.getProjectDir() + '/Data Files/Equipos/CMU2.csv'

WebUI.comment('INICIO TC-002_CambioMasivoConCambioEstado - Sucursal 41')

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
// PASO 5: Seleccionar Tipo Equipo (DECODIFICADOR = 19)
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_TipoEquipo'), '19')

// =====================================================
// PASO 6: Seleccionar Ubicacion Origen (LOG) y Destino (ALM)
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_UbicacionOrigen'), 'LOG')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_UbicacionDestino'), 'ALM')

// =====================================================
// PASO 7: Seleccionar Estado Origen (DÑA) y Estado Destino (F04)
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_EstadoOrigen'), 'DÑA')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_EstadoDestino'), 'F04')

// =====================================================
// PASO 8: Cargar archivo CSV
// =====================================================
WebUI.uploadFile(
    findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/INPUT_CargaCsv'), archivoCMU)

WebUI.takeScreenshot()

// =====================================================
// PASO 9: Enviar formulario
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 10: Confirmar dry-run (Si)
// =====================================================
WebUI.waitForElementPresent(
    findTestObject('OBJETOS_GLOBALES/BUTTON_Si'), GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('OBJETOS_GLOBALES/BUTTON_Si'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 11: Validar mensaje de exito
// =====================================================
WebUI.verifyElementPresent(
    findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT + 10, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    msgAlerta, 'Finalizo proceso de validaciones con éxito', null)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-002 - Cambio masivo CON cambio de estado completado EXITOSAMENTE')
