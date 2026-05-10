/**
 * ID:            TC-001_ConsultarCatalogoMateriales_HappyPath
 * Módulo:        MATERIALES - Catálogo de Materiales
 * Objetivo:      Validar que el catálogo de descripción de materiales carga correctamente,
 *                muestra la grilla con datos y permite refrescar.
 * Precondiciones: Usuario con acceso a MATERIALES y permiso materialCatalog.
 * Datos:         Ninguno.
 * Resultado esperado: La grilla muestra al menos un registro de materiales.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-07-22
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-001_ConsultarCatalogoMateriales_HappyPath')

// 1) Login a MATERIALES
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_MATERIALES,
    GlobalVariable.USER_MATERIALES,
    GlobalVariable.PASSWORD_MATERIALES)

// 2) Navegar al menú: Materiales > Catálogos > Materiales
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Catálogos', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// Click en sub-opción "Materiales" dentro de Catálogos
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/SPAN_OpcionMenu-Dyn', [('opcionMenu') : 'Materiales']))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Verificar que la grilla de catálogo se cargó
WebUI.verifyElementPresent(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/GRID_CatalogoMateriales'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/GRID_CatalogoMateriales'))
// 4) Verificar que hay al menos un registro en la grilla
WebUI.verifyElementPresent(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/TR_PrimerResultado'),GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('La grilla de catálogo de materiales se cargó con datos correctamente')

// 5) Click en botón Refrescar y verificar que la grilla sigue cargada
WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Refrescar'))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/TR_PrimerResultado'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('FIN TC-001 - Catálogo de materiales consultado exitosamente')
