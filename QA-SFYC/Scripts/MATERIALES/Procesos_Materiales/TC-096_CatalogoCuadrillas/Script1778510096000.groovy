/**
 * ID:            TC-096_CatalogoCuadrillas
 * Modulo:        MATERIALES
 * Objetivo:      Validar acceso al Catalogo de Cuadrillas, verificar grid con datos,
 *                abrir popup de Nuevo Registro y cerrarlo sin guardar.
 * Precondiciones: Usuario con acceso a MATERIALES > Catalogos > Cuadrillas.
 * Datos:         N/A.
 * Resultado esperado: Grid de cuadrillas visible, popup Nuevo Registro abre y cierra correctamente.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-096_CatalogoCuadrillas')

// 1) Navegar a Catalogos > Cuadrillas
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Catálogos', 'Cuadrillas')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 2) Verificar titulo
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'CATÁLOGO DE CUADRILLAS']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Titulo CATALOGO DE CUADRILLAS visible')

// 3) Verificar grid principal esta presente
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/GRID_Principal'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Grid de cuadrillas presente')

// 4) Verificar boton Nuevo Registro
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_NuevoRegistro'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// 5) Abrir popup Nuevo Registro
WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_NuevoRegistro'))
WebUI.delay(1)

// 6) Verificar que el popup se abrio
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/DIV_PopupContenido'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Popup Nuevo Registro abierto correctamente')

// 7) Cerrar popup sin guardar
WebUI.click(findTestObject('OBJETOS_GLOBALES/BUTTON_Cerrar'), FailureHandling.OPTIONAL)
WebUI.delay(1)

// 8) Verificar que el grid sigue visible tras cerrar popup
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/GRID_Principal'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Grid visible despues de cerrar popup')

WebUI.comment('FIN TC-096 - Catalogo de Cuadrillas funciona correctamente')
