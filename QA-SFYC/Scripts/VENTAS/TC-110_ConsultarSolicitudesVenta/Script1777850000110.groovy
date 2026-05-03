/**
 * ID:            TC-110_ConsultarSolicitudesVenta
 * Modulo:        VENTAS
 * Objetivo:      Validar la consulta de solicitudes de venta.
 * Precondiciones: Usuario con acceso a VENTAS.
 * Datos:         Ninguno.
 * Resultado esperado: Listado de solicitudes de venta desplegado.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-110_ConsultarSolicitudesVenta')

// 1) Login a VENTAS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_VENTAS,
    GlobalVariable.USER_VENTAS,
    GlobalVariable.PASSWORD_VENTAS)

// 2) Navegar a Solicitudes de Venta
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Ventas', 'Solicitudes de Venta', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-110 - Modulo de solicitudes de venta cargado exitosamente')
