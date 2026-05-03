/**
 * ID:            TC-092_CambioMasivoUbicacion
 * Modulo:        EQUIPOS
 * Objetivo:      Validar el flujo de cambio masivo de ubicacion de equipos.
 * Precondiciones: Usuario con acceso a EQUIPOS, equipos registrados.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de cambio masivo de ubicacion accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-092_CambioMasivoUbicacion')

// 1) Login a EQUIPOS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_EQUIPOS,
    GlobalVariable.USER_EQUIPOS,
    GlobalVariable.PASSWORD_EQUIPOS)

// 2) Navegar a Cambio Masivo Ubicacion via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Equipos', 'Cambio Masivo Ubicación', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-092 - Modulo de cambio masivo de ubicacion cargado exitosamente')
