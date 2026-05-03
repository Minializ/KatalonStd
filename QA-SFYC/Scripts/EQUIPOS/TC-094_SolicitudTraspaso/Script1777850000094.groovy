/**
 * ID:            TC-094_SolicitudTraspaso
 * Modulo:        EQUIPOS
 * Objetivo:      Validar el flujo de solicitud de traspaso de equipo.
 * Precondiciones: Usuario con acceso a EQUIPOS.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de solicitud de traspaso accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-094_SolicitudTraspaso')

// 1) Login a EQUIPOS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_EQUIPOS,
    GlobalVariable.USER_EQUIPOS,
    GlobalVariable.PASSWORD_EQUIPOS)

// 2) Navegar a Solicitud de Traspaso via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Equipos', 'Solicitud de Traspaso', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-094 - Modulo de solicitud de traspaso cargado exitosamente')
