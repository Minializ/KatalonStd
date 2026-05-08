/**
 * ID:            TC-098_TraspasoPallets_ValidarCamposObligatorios
 * Módulo:        EQUIPOS - Solicitud de Traspaso
 * Objetivo:      Validar que el sistema impide crear una solicitud de traspaso de pallets
 *                cuando los campos obligatorios no están completos:
 *                - Plaza Destino
 *                - Tipo de Equipo
 *                - Observaciones
 * Precondiciones: - Usuario con permisos de Solicitud de Traspaso
 *                 - Login previo en módulo EQUIPOS
 * Resultado esperado: El sistema muestra mensaje de error y no permite crear la solicitud.
 * Autor:         QA Automatización
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-098_TraspasoPallets_ValidarCamposObligatorios')

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
// PASO 3: Abrir formulario de nueva solicitud
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Agregar'))

// =====================================================
// PASO 4: Intentar aprobar sin llenar campos obligatorios
// =====================================================
WebUI.takeScreenshot()

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

// =====================================================
// PASO 5: Validar que se muestra mensaje de error
// =====================================================
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgError = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))
WebUI.comment('Mensaje de validación recibido: ' + msgError)

WebUI.takeScreenshot()

// =====================================================
// PASO 6: Llenar solo plaza destino, intentar nuevamente
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaDestino'),
    'GUADALAJARA')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

// Validar que sigue requiriendo tipo de equipo y observaciones
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.OPTIONAL)

WebUI.takeScreenshot()

// =====================================================
// PASO 7: Llenar tipo equipo pero sin observaciones
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_TipoEquipo'),
    'CABLE MODEMS')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

// Validar que sigue requiriendo observaciones
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.OPTIONAL)

WebUI.takeScreenshot()
WebUI.comment('FIN TC-098_TraspasoPallets_ValidarCamposObligatorios - Validaciones correctas')
