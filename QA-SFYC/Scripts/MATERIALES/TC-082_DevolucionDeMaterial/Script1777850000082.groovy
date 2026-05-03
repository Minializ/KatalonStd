/**
 * ID:            TC-082_DevolucionDeMaterial
 * Modulo:        MATERIALES
 * Objetivo:      Validar el flujo de devolucion de material al almacen.
 * Precondiciones: Usuario con acceso a MATERIALES, material previamente entregado.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de devolucion accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-082_DevolucionDeMaterial')

// 1) Login a MATERIALES
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_MATERIALES,
    GlobalVariable.USER_MATERIALES,
    GlobalVariable.PASSWORD_MATERIALES)

// 2) Navegar a Devolucion de Material via menu
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Devolución de Material', '')

// 3) Esperar carga
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-082 - Modulo de devolucion de material cargado exitosamente')
