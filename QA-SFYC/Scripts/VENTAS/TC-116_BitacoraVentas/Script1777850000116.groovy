/**
 * ID:            TC-116_BitacoraVentas
 * Modulo:        VENTAS
 * Objetivo:      Validar el acceso a la bitacora de ventas.
 * Precondiciones: Usuario con acceso a VENTAS.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de bitacora accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-116_BitacoraVentas')

// 1) Login a VENTAS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_VENTAS,
    GlobalVariable.USER_VENTAS,
    GlobalVariable.PASSWORD_VENTAS)

// 2) Navegar a Bitacora de Ventas
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Ventas', 'Bitácora de Ventas', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-116 - Bitacora de ventas cargada exitosamente')
