/**
 * ID:            TC-104_NuevoContrato
 * Modulo:        SERVICIOS
 * Objetivo:      Validar el acceso a la pantalla de creacion de nuevo contrato.
 * Precondiciones: Usuario con acceso a SERVICIOS, permisos de creacion.
 * Datos:         Ninguno.
 * Resultado esperado: Formulario de nuevo contrato accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-104_NuevoContrato')

// 1) Login a SERVICIOS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_SERVICIOS,
    GlobalVariable.USER_SERVICIOS,
    GlobalVariable.PASSWORD_SERVICIOS)

// 2) Navegar a Contratos > Nuevo
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Servicios', 'Contratos', 'Nuevo')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Validar carga del formulario
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-104 - Formulario de nuevo contrato cargado exitosamente')
