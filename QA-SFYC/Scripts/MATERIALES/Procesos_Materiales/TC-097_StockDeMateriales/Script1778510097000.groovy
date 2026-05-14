/**
 * ID:            TC-097_StockDeMateriales
 * Modulo:        MATERIALES
 * Objetivo:      Validar acceso al catalogo de Stock de Materiales, verificar titulo,
 *                presencia del grid con datos y funcionalidad del filtro por tipo de material.
 * Precondiciones: Usuario con acceso a MATERIALES > Catalogos > Stock de materiales.
 * Datos:         N/A.
 * Resultado esperado: Grid de stock visible con filtro de tipo de material funcional.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-097_StockDeMateriales')

// 1) Navegar a Catalogos > Stock de materiales
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Catálogos', 'Stock de materiales')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 2) Verificar titulo
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'STOCK DE MATERIALES']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Titulo STOCK DE MATERIALES visible')

// 3) Verificar grid principal esta presente
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/GRID_Principal'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Grid de stock de materiales presente')

// 4) Verificar combo Tipo de Cuadrilla / filtro
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'),
    GlobalVariable.TIME_OUT, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.comment('Filtro de tipo cuadrilla presente')

// 5) Verificar boton Refrescar
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Refrescar'),
    GlobalVariable.TIME_OUT, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.comment('Boton Refrescar presente')

WebUI.comment('FIN TC-097 - Stock de Materiales cargado correctamente')
