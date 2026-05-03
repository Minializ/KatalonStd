/**
 * ID:            TC-108_SupervisionCajero
 * Modulo:        SERVICIOS
 * Objetivo:      Validar el acceso al modulo de supervision de cajeros.
 * Precondiciones: Usuario con acceso a SERVICIOS, permisos de supervision.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de supervision accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-108_SupervisionCajero')

// 1) Login a SERVICIOS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_SERVICIOS,
    GlobalVariable.USER_SERVICIOS,
    GlobalVariable.PASSWORD_SERVICIOS)

// 2) Navegar a Supervision de Cajeros
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Servicios', 'Supervisión de Cajeros', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-108 - Modulo de supervision de cajeros cargado exitosamente')
