/**
 * ID:            TC-091_AsignarEquipoCuadrilla
 * Modulo:        EQUIPOS
 * Objetivo:      Validar la asignacion de equipo a una cuadrilla.
 * Precondiciones: Usuario con acceso a EQUIPOS, cuadrillas configuradas.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de asignacion de equipos a cuadrilla accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-091_AsignarEquipoCuadrilla')

// 1) Login a EQUIPOS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_EQUIPOS,
    GlobalVariable.USER_EQUIPOS,
    GlobalVariable.PASSWORD_EQUIPOS)

// 2) Navegar a Asignar Equipos Cuadrillas via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Equipos', 'Asignar Equipos Cuadrillas', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-091 - Modulo de asignacion equipos cuadrilla cargado exitosamente')
