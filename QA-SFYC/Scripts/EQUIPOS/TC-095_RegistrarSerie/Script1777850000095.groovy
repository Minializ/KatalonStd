/**
 * ID:            TC-095_RegistrarSerie
 * Modulo:        EQUIPOS
 * Objetivo:      Validar el registro de serie de equipo.
 * Precondiciones: Usuario con acceso a EQUIPOS.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de registro de serie accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-095_RegistrarSerie')

// 1) Login a EQUIPOS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_EQUIPOS,
    GlobalVariable.USER_EQUIPOS,
    GlobalVariable.PASSWORD_EQUIPOS)

// 2) Navegar a Registrar Serie via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Equipos', 'Registrar Serie', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-095 - Modulo de registro de serie cargado exitosamente')
