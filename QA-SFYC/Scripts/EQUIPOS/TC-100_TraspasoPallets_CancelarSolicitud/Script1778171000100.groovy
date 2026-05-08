/**
 * ID:            TC-100_TraspasoPallets_CancelarSolicitud
 * Módulo:        EQUIPOS - Solicitud de Traspaso
 * Objetivo:      Validar que se puede eliminar/cancelar una solicitud de traspaso
 *                de pallets que está en estado pendiente (antes de ser aceptada).
 *                El flujo crea una solicitud y luego la elimina.
 * Precondiciones: - Usuario con permisos de Solicitud de Traspaso
 *                 - Login previo en módulo EQUIPOS
 * Resultado esperado: La solicitud se elimina correctamente y ya no aparece en la grilla.
 * Autor:         QA Automatización
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-100_TraspasoPallets_CancelarSolicitud')

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
// PASO 3: Crear una solicitud para luego cancelarla
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Agregar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaDestino'),
    'GUADALAJARA')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_TipoEquipo'),
    'CABLE MODEMS')

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'SOLICITUD PARA CANCELAR - KATALON STUDIO TC100')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// Validar creación exitosa
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgCreacion = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    msgCreacion, 'Finalizo proceso con éxito', null)

WebUI.takeScreenshot()

// =====================================================
// PASO 4: Seleccionar la solicitud recién creada
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

// =====================================================
// PASO 5: Eliminar/Cancelar la solicitud
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Eliminar'))

// Confirmar eliminación si aparece diálogo
boolean confirmarVisible = WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'), 5, FailureHandling.OPTIONAL)

if (confirmarVisible) {
    WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'))
}

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 6: Validar eliminación exitosa
// =====================================================
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgEliminacion = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))
WebUI.comment('Mensaje de eliminación: ' + msgEliminacion)
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    msgEliminacion, 'éxito', null)

WebUI.takeScreenshot()
WebUI.comment('FIN TC-100_TraspasoPallets_CancelarSolicitud - Eliminación EXITOSA')
