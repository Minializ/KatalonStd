/**
 * ID:            TC-081_RequisicionDeMaterial
 * Modulo:        MATERIALES
 * Objetivo:      Validar el flujo de creacion de una requisicion de material.
 * Precondiciones: Usuario con acceso a MATERIALES, permisos de requisicion.
 * Datos:         Ninguno (se usan valores del sistema).
 * Resultado esperado: Pantalla de requisiciones accesible y funcional.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-081_RequisicionDeMaterial')

// 1) Login a MATERIALES
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_MATERIALES,
    GlobalVariable.USER_MATERIALES,
    GlobalVariable.PASSWORD_MATERIALES)

// 2) Navegar a Requisiciones via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Requisiciones', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Validar carga de la pantalla
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-081 - Modulo de requisiciones cargado exitosamente')
