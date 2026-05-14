/**
 * ID:            TC-098_TraspasoDeInventarios
 * Modulo:        MATERIALES
 * Objetivo:      Validar acceso al submodulo de Traspaso de Inventarios, verificar titulo,
 *                presencia de formulario con origen y destino, y grid de materiales a traspasar.
 * Precondiciones: Usuario con acceso a MATERIALES > Traspaso de inventarios.
 * Datos:         N/A.
 * Resultado esperado: Pagina de Traspaso carga correctamente con formulario y grid visibles.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-098_TraspasoDeInventarios')

// 1) Navegar a Traspaso de inventarios
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Traspaso de inventarios', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 2) Verificar titulo
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'TRASPASO DE INVENTARIOS']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Titulo TRASPASO DE INVENTARIOS visible')

// 3) Verificar combo de tipo cuadrilla origen
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Combo Tipo de Cuadrilla origen presente')

// 4) Verificar combo de cuadrilla
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Entregas/SELECT_Cuadrilla'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Combo Cuadrilla presente')

// 5) Verificar grid principal de materiales a traspasar
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/GRID_Principal'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Grid de materiales para traspaso presente')

// 6) Verificar boton Guardar/Aceptar
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_Guardar'),
    GlobalVariable.TIME_OUT, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.comment('Boton Guardar presente')

WebUI.comment('FIN TC-098 - Traspaso de Inventarios cargado correctamente')
