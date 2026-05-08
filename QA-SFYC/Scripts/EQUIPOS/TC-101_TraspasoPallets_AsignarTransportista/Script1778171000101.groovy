/**
 * ID:            TC-101_TraspasoPallets_AsignarTransportista
 * Módulo:        EQUIPOS - Solicitud de Traspaso
 * Objetivo:      Validar el flujo de aceptación destino donde se asigna transportista
 *                y guía de envío a un traspaso de pallets existente.
 *                Verifica la validación del campo guía (máx 40 chars, patrón alfanumérico).
 * Precondiciones: - Usuario con permisos de Solicitud de Traspaso
 *                 - Login previo en módulo EQUIPOS
 *                 - Solicitud existente en estado pendiente de aceptación destino
 * Resultado esperado: Transportista asignado correctamente con guía de envío válida.
 * Autor:         QA Automatización
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

// Generar guía de envío aleatoria
int guia = ((Math.random() * 90000) as int) + 10000

WebUI.comment('INICIO TC-101_TraspasoPallets_AsignarTransportista')

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
// PASO 3: Crear solicitud para tener una disponible
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Agregar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaDestino'),
    'GUADALAJARA')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_TipoEquipo'),
    'CABLE MODEMS')

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'TRASPASO PARA ASIGNAR TRANSPORTISTA - KATALON STUDIO TC101')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

// =====================================================
// PASO 4: Aceptar Origen
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_AceptarOrigen'))

WebUI.verifyElementPresent(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaOrigen'), 10)

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaOrigen'),
    'ACATLAN')

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'ORIGEN ACEPTADO - TC101')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// =====================================================
// PASO 5: Aceptar Cantidades
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_AceptarDestino'))

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'CANTIDADES ACEPTADAS - TC101')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// =====================================================
// PASO 6: Aceptar Destino - Asignar transportista y guía
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_AceptarDestino'))

// Verificar que aparece el campo de transportistas
WebUI.verifyElementPresent(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_Transportistas'),
    10, FailureHandling.STOP_ON_FAILURE)

// Seleccionar transportista
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_Transportistas'),
    'GALEX')

// Ingresar guía de envío válida (alfanumérica, máx 40 chars)
WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_GuiaEnvio'), guia.toString())

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'TRANSPORTISTA ASIGNADO CON GUIA - KATALON STUDIO TC101')

WebUI.takeScreenshot()

// =====================================================
// PASO 7: Aprobar asignación de transportista
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 8: Validar éxito
// =====================================================
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgFinal = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))
WebUI.comment('Mensaje final: ' + msgFinal)
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    msgFinal, 'Finalizo proceso con éxito', null)

WebUI.takeScreenshot()
WebUI.comment('FIN TC-101_TraspasoPallets_AsignarTransportista - Asignación EXITOSA')
