/**
 * ID:            TC-004_ValidarCamposObligatorios
 * Módulo:        MATERIALES - Catálogo de Materiales
 * Objetivo:      Validar que el botón Aceptar permanece deshabilitado cuando los campos
 *                obligatorios del formulario de nuevo material están vacíos, y se habilita
 *                progresivamente al llenarlos.
 * Precondiciones: Usuario con acceso a MATERIALES y permiso materialCatalog.
 * Datos:         Ninguno.
 * Resultado esperado: Botón Aceptar deshabilitado con campos vacíos, habilitado al completar.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-07-22
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-004_ValidarCamposObligatorios')

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

// 3) Click en Nuevo Registro
WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_NuevoRegistro'))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 4) Verificar popup abierto
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/POPUP_DescripcionMaterial'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// 5) Verificar que Aceptar está deshabilitado con formulario vacío
WebUI.verifyElementHasAttribute(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Aceptar'),
    'aria-disabled', GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('PASO 1: Botón Aceptar está deshabilitado con formulario vacío - OK')

// 6) Llenar solo Tipo Material → Aceptar sigue deshabilitado
CustomKeywords.'utilidades.Materiales.selectOption'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_TipoMaterial'))
WebUI.delay(1)

WebUI.verifyElementHasAttribute(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Aceptar'),
    'aria-disabled', GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('PASO 2: Solo Tipo Material lleno - Aceptar sigue deshabilitado - OK')

// 7) Llenar Código Material → sigue deshabilitado
WebUI.setText(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_CodigoMaterial'),
    '9999')

WebUI.verifyElementHasAttribute(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Aceptar'),
    'aria-disabled', GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('PASO 3: Tipo Material + Código llenos - Aceptar sigue deshabilitado - OK')

// 8) Llenar Descripción → sigue deshabilitado (faltan unidad, vigente, plantillas)
WebUI.setText(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Descripcion'),
    'PRUEBA CAMPOS OBLIGATORIOS')

WebUI.verifyElementHasAttribute(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Aceptar'),
    'aria-disabled', GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('PASO 4: + Descripción llena - Aceptar sigue deshabilitado - OK')

// 9) Llenar Unidad
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_Unidad'),
    'PIEZA')

// 10) Llenar Vigente
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_Vigente'),
    'Si vigente')

// 11) Llenar Plantillas
CustomKeywords.'utilidades.Materiales.selectOption'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_Plantillas'))
WebUI.delay(1)

// 12) Llenar Mínimo Plantilla y Máximo Plantilla
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MinimoPlantilla'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MinimoPlantilla'), '1')

WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MaximoPlantilla'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MaximoPlantilla'), '50')

WebUI.delay(1)

// 13) Verificar que Aceptar ahora está habilitado (no tiene aria-disabled)
WebUI.verifyElementNotHasAttribute(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Aceptar'),
    'aria-disabled', GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('PASO 5: Todos los campos obligatorios llenos - Aceptar HABILITADO - OK')

// 14) Limpiar vista sin guardar
CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()

WebUI.comment('FIN TC-004 - Validación de campos obligatorios completada exitosamente')
