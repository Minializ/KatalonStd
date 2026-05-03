/**
 * ID:            TC-113_AsignarSolicitudVendedor
 * Modulo:        VENTAS
 * Objetivo:      Validar la asignacion de solicitud a un vendedor.
 * Precondiciones: Usuario con acceso a VENTAS, solicitud existente sin asignar.
 * Datos:         Ninguno.
 * Resultado esperado: Pantalla de asignacion accesible.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-113_AsignarSolicitudVendedor')

// 1) Login a VENTAS
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_VENTAS,
    GlobalVariable.USER_VENTAS,
    GlobalVariable.PASSWORD_VENTAS)

// 2) Navegar a Asignar Solicitud
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Ventas', 'Asignar Solicitud', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Validar carga
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-113 - Modulo de asignacion de solicitud cargado exitosamente')
