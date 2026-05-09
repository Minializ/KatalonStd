/**
 * ID:            TC-003_EditarMaterialExistente
 * Módulo:        MATERIALES - Catálogo de Materiales
 * Objetivo:      Validar la edición de un material existente: modificar la descripción
 *                y guardar los cambios exitosamente.
 * Precondiciones: Usuario con acceso a MATERIALES, al menos un material en el catálogo.
 * Datos:         Se edita el primer registro visible en la grilla.
 * Resultado esperado: Mensaje TOAST de éxito al actualizar el material.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-07-22
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-003_EditarMaterialExistente')

// 1) Login a MATERIALES
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_MATERIALES,
    GlobalVariable.USER_MATERIALES,
    GlobalVariable.PASSWORD_MATERIALES)

// 2) Navegar al menú: Materiales > Catálogos > Materiales
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Catálogos', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/SPAN_OpcionMenu-Dyn', [('opcionMenu') : 'Materiales']))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 3) Verificar grilla cargada con al menos un registro
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/GRID_CatalogoMateriales'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/TR_PrimerResultado'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// 4) Click en botón Editar del primer registro
WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_EditarFila'))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 5) Verificar que se abrió el popup de edición
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/POPUP_DescripcionMaterial'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// 6) Obtener descripción actual y modificarla
String descripcionOriginal = WebUI.getAttribute(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Descripcion'),
    'value', FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Descripción original: ' + descripcionOriginal)

// Limpiar y escribir nueva descripción
String sufijo = new Date().format('HHmm')
String nuevaDescripcion = descripcionOriginal.length() > 25 ? descripcionOriginal.substring(0, 25) : descripcionOriginal
nuevaDescripcion = nuevaDescripcion.trim() + ' ED' + sufijo

WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Descripcion'))
WebUI.setText(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Descripcion'),
    nuevaDescripcion)

WebUI.comment('Nueva descripción: ' + nuevaDescripcion)

// 7) Click en Aceptar para guardar cambios
WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Aceptar'))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 8) Confirmar en diálogo
WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 9) Validar mensaje TOAST de éxito
String textoToast = WebUI.getText(
    findTestObject('Object Repository/OBJETOS_GLOBALES/TOAST_Mensaje'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Mensaje TOAST: ' + textoToast)

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    textoToast, 'xito', null)

WebUI.comment('FIN TC-003 - Material editado exitosamente')
