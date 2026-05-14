/**
 * ID:            TC-099_RecuperacionDeMateriales
 * Modulo:        MATERIALES
 * Objetivo:      Validar acceso al submodulo Recuperacion de Materiales, verificar titulo,
 *                presencia del grupo de botones (automatica/manual/busqueda) y navegacion
 *                entre modos.
 * Precondiciones: Usuario con acceso a MATERIALES > Recuperacion materiales.
 * Datos:         N/A.
 * Resultado esperado: Pagina carga con titulo y button-group funcional.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-099_RecuperacionDeMateriales')

// 1) Navegar a Recuperacion materiales
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Recuperación materiales', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 2) Verificar titulo
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'RECUPERACIÓN DE MATERIALES']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Titulo RECUPERACION DE MATERIALES visible')

// 3) Verificar boton grupo: Automatica
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/BUTTON_GroupItem-Dyn',
        [('itemText') : 'Automatica']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Boton Automatica presente')

// 4) Verificar boton grupo: Manual
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/BUTTON_GroupItem-Dyn',
        [('itemText') : 'Manual']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Boton Manual presente')

// 5) Verificar boton grupo: Busqueda
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/BUTTON_GroupItem-Dyn',
        [('itemText') : 'Busqueda']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Boton Busqueda presente')

// 6) Click en modo Manual
WebUI.click(findTestObject('Object Repository/MATERIALES/Procesos/BUTTON_GroupItem-Dyn',
    [('itemText') : 'Manual']))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.comment('Modo Manual seleccionado')

// 7) Click en modo Busqueda
WebUI.click(findTestObject('Object Repository/MATERIALES/Procesos/BUTTON_GroupItem-Dyn',
    [('itemText') : 'Busqueda']))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.comment('Modo Busqueda seleccionado')

// 8) Regresar a modo Automatica
WebUI.click(findTestObject('Object Repository/MATERIALES/Procesos/BUTTON_GroupItem-Dyn',
    [('itemText') : 'Automatica']))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.comment('Modo Automatica seleccionado')

WebUI.comment('FIN TC-099 - Recuperacion de Materiales funciona correctamente')
