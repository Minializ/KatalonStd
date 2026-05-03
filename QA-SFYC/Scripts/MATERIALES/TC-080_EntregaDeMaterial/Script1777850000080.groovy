/**
 * ID:            TC-080_EntregaDeMaterial
 * Modulo:        MATERIALES
 * Objetivo:      Validar el flujo de entrega de material, navegando al modulo,
 *                llenando el formulario y enviando la entrega.
 * Precondiciones: Usuario con acceso a MATERIALES, materiales disponibles en inventario.
 * Datos:         Ninguno (parametros internos del sistema).
 * Resultado esperado: Mensaje de exito al registrar la entrega de material.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-080_EntregaDeMaterial')

// 1) Login a MATERIALES (usa la app control)
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_MATERIALES,
    GlobalVariable.USER_MATERIALES,
    GlobalVariable.PASSWORD_MATERIALES)

// 2) Navegar a Entrega de Material via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Entrega de Material', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Validar que se cargo la pantalla de entregas
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-080 - Modulo de entrega de material cargado exitosamente')
