/**
 * ID:            TC-099_TraspasoPallets_PlazaOrigenIgualDestino
 * Módulo:        EQUIPOS - Solicitud de Traspaso
 * Objetivo:      Validar que el sistema impide crear un traspaso de pallets
 *                cuando la plaza origen es igual a la plaza destino.
 *                El validador noEqualValueValidator debe rechazar la operación.
 * Precondiciones: - Usuario con permisos de Solicitud de Traspaso
 *                 - Login previo en módulo EQUIPOS
 * Resultado esperado: El sistema muestra error indicando que origen y destino no pueden ser iguales.
 * Autor:         QA Automatización
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-099_TraspasoPallets_PlazaOrigenIgualDestino')

// =====================================================
// PASO 1: Login al módulo EQUIPOS
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_EQUIPOS,
    GlobalVariable.USER_EQUIPOS,
    GlobalVariable.PASSWORD_EQUIPOS)

// =====================================================
// PASO 2: Navegar a Solicitud de Traspaso
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Solicitud de Traspaso', '')

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 3: Crear solicitud con plaza destino específica
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Agregar'))

// Seleccionar la misma plaza que el usuario tiene asignada como origen
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaDestino'),
    'ACATLAN')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_TipoEquipo'),
    'CABLE MODEMS')

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'PRUEBA PLAZA ORIGEN IGUAL DESTINO - TC099')

WebUI.takeScreenshot()

// =====================================================
// PASO 4: Intentar aprobar - debe fallar por plazas iguales
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 5: Validar mensaje de error por plazas iguales
// =====================================================
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgError = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))
WebUI.comment('Mensaje de error recibido: ' + msgError)

// El mensaje debe indicar que las plazas no pueden ser iguales
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    msgError, 'igual', null)

WebUI.takeScreenshot()
WebUI.comment('FIN TC-099_TraspasoPallets_PlazaOrigenIgualDestino - Validación correcta')
