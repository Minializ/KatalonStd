/**
 * ID:            TC-085_AclaracionDeMaterial
 * Modulo:        MATERIALES
 * Objetivo:      Validar el flujo de aclaracion de material.
 * Precondiciones: Usuario con acceso a MATERIALES.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de aclaracion accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-085_AclaracionDeMaterial')

// 1) Login a MATERIALES
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_MATERIALES,
    GlobalVariable.USER_MATERIALES,
    GlobalVariable.PASSWORD_MATERIALES)

// 2) Navegar a Aclaracion de Material via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Aclaración de Material', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-085 - Modulo de aclaracion de material cargado exitosamente')
