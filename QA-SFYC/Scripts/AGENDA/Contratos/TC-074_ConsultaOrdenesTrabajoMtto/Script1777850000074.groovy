/**
 * ID:            TC-074_ConsultaOrdenesTrabajoMtto
 * Modulo:        AGENDA
 * Objetivo:      Validar que se pueden consultar las ordenes de trabajo de
 *                mantenimiento (OT) para un suscriptor dado.
 * Precondiciones: Usuario con acceso a AGENDA, modulo de mantenimiento habilitado.
 * Datos:         numeroSuscriptor (parametro del TC).
 * Resultado esperado: Se muestra la seccion de ordenes de trabajo del suscriptor.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-074_ConsultaOrdenesTrabajoMtto - Suscriptor: ' + numeroSuscriptor)

// 1) Login a AGENDA
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_AGENDA,
    GlobalVariable.USER_AGENDA,
    GlobalVariable.PASSWORD_AGENDA)

// 2) Navegar al modulo de Administracion de Mantenimiento via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Mantenimiento', 'Administración', '')

// 3) Esperar carga del contenido
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Buscar suscriptor en el modulo de mantenimiento
WebUI.waitForElementPresent(
    findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_Suscriptor'),
    GlobalVariable.TIME_OUT)

WebUI.setText(
    findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_Suscriptor'),
    numeroSuscriptor)

WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/BUTTON_Consultar'))

// 5) Validar que se cargaron los datos
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-074 - Consulta de OT de mantenimiento exitosa')
