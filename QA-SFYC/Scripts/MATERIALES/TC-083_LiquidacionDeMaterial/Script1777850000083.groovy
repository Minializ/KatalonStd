/**
 * ID:            TC-083_LiquidacionDeMaterial
 * Modulo:        MATERIALES
 * Objetivo:      Validar el flujo de liquidacion de material entregado.
 * Precondiciones: Usuario con acceso a MATERIALES, materiales pendientes de liquidar.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de liquidacion accesible y funcional.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-083_LiquidacionDeMaterial')

// 1) Login a MATERIALES
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_MATERIALES,
    GlobalVariable.USER_MATERIALES,
    GlobalVariable.PASSWORD_MATERIALES)

// 2) Navegar a Liquidacion de Material via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Liquidación de Material', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-083 - Modulo de liquidacion de material cargado exitosamente')
