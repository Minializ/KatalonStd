/**
 * ID:            TC-095_AclaracionDeMaterial
 * Modulo:        MATERIALES
 * Objetivo:      Validar acceso al submodulo de Aclaraciones, verificar titulo de pagina
 *                y presencia de elementos del formulario principal.
 * Precondiciones: Usuario con acceso a MATERIALES > Aclaraciones.
 * Datos:         N/A.
 * Resultado esperado: Pagina de Aclaraciones carga con titulo y formulario visibles.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-095_AclaracionDeMaterial')

// 1) Navegar a Aclaraciones
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Aclaraciones', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 2) Verificar titulo de la pagina
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'ACLARACIÓN DE MATERIALES']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Titulo ACLARACION DE MATERIALES visible')

// 3) Verificar que el combo Tipo de Cuadrilla este presente
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Combo Tipo de Cuadrilla presente')

// 4) Verificar que el combo Cuadrilla este presente
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Entregas/SELECT_Cuadrilla'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Combo Cuadrilla presente')

// 5) Verificar que el grid de datos este presente
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/GRID_Principal'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Grid de aclaraciones presente')

WebUI.comment('FIN TC-095 - Pagina de Aclaraciones cargada correctamente')
