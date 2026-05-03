/**
 * ID:            TC-071_ConsultarSuscriptorPorNombre
 * Modulo:        AGENDA
 * Objetivo:      Validar la busqueda de suscriptor por nombre y apellidos.
 * Precondiciones: Usuario con acceso a AGENDA, suscriptor existente.
 * Datos:         noSucursal, apellidoPaterno, apellidoMaterno, nombre (parametros).
 * Resultado esperado: Se muestra el suscriptor encontrado en la tabla de resultados.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-071_ConsultarSuscriptorPorNombre')

// 1) Login a AGENDA
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_AGENDA,
    GlobalVariable.USER_AGENDA,
    GlobalVariable.PASSWORD_AGENDA)

// 2) Consultar por nombre usando keyword existente
CustomKeywords.'utilidades.Agenda.consultarSusciptorPorNombre'(noSucursal, apellidoPaterno, apellidoMaterno, nombre)

// 3) Validar que se encontro al menos un resultado
WebUI.verifyElementPresent(
    findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-071 - Busqueda por nombre exitosa')
