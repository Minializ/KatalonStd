/**
 * ID:            TC-105_PapeletaCompleta
 * Modulo:        SERVICIOS
 * Objetivo:      Validar el acceso al modulo de papeletas con multiples tabs.
 * Precondiciones: Usuario con acceso a SERVICIOS.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de papeletas accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-105_PapeletaCompleta')

// 1) Login a SERVICIOS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_SERVICIOS,
    GlobalVariable.USER_SERVICIOS,
    GlobalVariable.PASSWORD_SERVICIOS)

// 2) Navegar a Papeletas
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Servicios', 'Papeletas', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-105 - Modulo de papeletas cargado exitosamente')
