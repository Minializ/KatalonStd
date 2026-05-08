/**
 * ID:            TC-097_TraspasoPallets_HappyPath
 * Módulo:        EQUIPOS - Solicitud de Traspaso
 * Objetivo:      Validar el flujo completo de traspaso de pallets:
 *                1) Crear solicitud con plaza destino, tipo equipo y observaciones
 *                2) Aceptar origen asignando plaza
 *                3) Aceptar cantidades
 *                4) Aceptar destino con transportista y guía de envío
 * Precondiciones: - Usuario con permisos de Solicitud de Traspaso
 *                 - Login previo en módulo EQUIPOS
 * Resultado esperado: Traspaso de pallets completado en todos sus pasos con mensajes de éxito.
 * Autor:         QA Automatización
 * Fecha:         2026-05-07
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

// Generar guía de envío aleatoria para evitar duplicados
int guia = ((Math.random() * 90000) as int) + 10000

WebUI.comment('INICIO TC-097_TraspasoPallets_HappyPath')

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
// PASO 3: Crear nueva solicitud de traspaso
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Agregar'))

// Seleccionar plaza destino
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaDestino'),
    'GUADALAJARA')

// Seleccionar tipo de equipo
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_TipoEquipo'),
    'CABLE MODEMS')

// Ingresar observaciones
WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'TRASPASO PALLETS - PRUEBA AUTOMATIZADA TC097')

// Aprobar solicitud
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// Validar mensaje de éxito en creación
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgCreacion = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    msgCreacion, 'Finalizo proceso con éxito', null)

WebUI.takeScreenshot()

// =====================================================
// PASO 4: Aceptar Origen - Asignar plaza origen
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_AceptarOrigen'))

WebUI.verifyElementPresent(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaOrigen'), 10)

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaOrigen'),
    'ACATLAN')

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'ASIGNACIÓN ORIGEN PALLETS - KATALON STUDIO TC097')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgOrigen = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    msgOrigen, 'Finalizo proceso con éxito', null)

WebUI.takeScreenshot()

// =====================================================
// PASO 5: Aceptar Cantidades
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_AceptarDestino'))

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'ACEPTAR CANTIDADES PALLETS - KATALON STUDIO TC097')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgCantidades = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    msgCantidades, 'Finalizo proceso con éxito', null)

WebUI.takeScreenshot()

// =====================================================
// PASO 6: Aceptar Destino - Asignar transportista y guía
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_AceptarDestino'))

WebUI.verifyElementPresent(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_Transportistas'), 10)

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_Transportistas'),
    'GALEX')

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_GuiaEnvio'), guia.toString())

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'),
    'ASIGNACIÓN TRANSPORTISTA PALLETS - KATALON STUDIO TC097')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgTransportista = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    msgTransportista, 'Finalizo proceso con éxito', null)

WebUI.takeScreenshot()
WebUI.comment('FIN TC-097_TraspasoPallets_HappyPath - Flujo completo EXITOSO')
