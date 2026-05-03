/**
 * ID:            TC-111_BusquedaAvanzadaVentas
 * Modulo:        VENTAS
 * Objetivo:      Validar la busqueda avanzada de solicitudes de venta.
 * Precondiciones: Usuario con acceso a VENTAS.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de busqueda avanzada accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-111_BusquedaAvanzadaVentas')

// 1) Login a VENTAS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_VENTAS,
    GlobalVariable.USER_VENTAS,
    GlobalVariable.PASSWORD_VENTAS)

// 2) Navegar a Busqueda Avanzada
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Ventas', 'Búsqueda Avanzada', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-111 - Busqueda avanzada de ventas cargada exitosamente')
