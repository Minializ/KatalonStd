/**
 * ID:            TC-005_ValidarMinimoMayorQueMaximo
 * Módulo:        MATERIALES - Catálogo de Materiales
 * Objetivo:      Validar la regla de negocio cross-field: el campo Mínimo no puede ser
 *                mayor que el campo Máximo. El formulario debe impedir el envío.
 * Precondiciones: Usuario con acceso a MATERIALES y permiso materialCatalog.
 * Datos:         Mínimo = 500, Máximo = 100 (mínimo > máximo).
 * Resultado esperado: El botón Aceptar permanece deshabilitado o se muestra error de validación.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-07-22
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-005_ValidarMinimoMayorQueMaximo')

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

// 5) Llenar todos los campos obligatorios correctamente primero

// Tipo Material
CustomKeywords.'utilidades.Materiales.selectOption'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_TipoMaterial'))
WebUI.delay(1)

// Código Material
WebUI.setText(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_CodigoMaterial'),
    '8888')

// Descripción
WebUI.setText(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Descripcion'),
    'PRUEBA MIN MAYOR QUE MAX')

// Unidad
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_Unidad'),
    'PIEZA')

// Vigente
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_Vigente'),
    'Si vigente')

// Tipo Construcción
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_TipoConstruccion'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_TipoConstruccion'), '1')

// Plantillas
CustomKeywords.'utilidades.Materiales.selectOption'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_Plantillas'))
WebUI.delay(1)

// Mínimo Plantilla
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MinimoPlantilla'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MinimoPlantilla'), '1')

// Máximo Plantilla
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MaximoPlantilla'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MaximoPlantilla'), '50')

// 6) Establecer Mínimo MAYOR que Máximo (caso inválido)
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Minimo'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Minimo'), '500')

WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Maximo'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Maximo'), '100')

WebUI.comment('Mínimo = 500, Máximo = 100 → Mínimo es mayor que Máximo')

// Click fuera para disparar la validación cross-field
WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Descripcion'))
WebUI.delay(1)

// 7) Verificar que Aceptar está deshabilitado por la validación cross-field
WebUI.verifyElementHasAttribute(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Aceptar'),
    'aria-disabled', GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('PASO 1: Mínimo > Máximo - Botón Aceptar DESHABILITADO - OK')

// 8) Corregir: Mínimo < Máximo
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Minimo'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Minimo'), '10')

WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Maximo'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Maximo'), '500')

WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Descripcion'))
WebUI.delay(1)

// 9) Verificar que Aceptar ahora está habilitado
WebUI.verifyElementNotHasAttribute(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Aceptar'),
    'aria-disabled', GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('PASO 2: Mínimo < Máximo corregido - Botón Aceptar HABILITADO - OK')

// 10) Limpiar vista sin guardar
CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()

WebUI.comment('FIN TC-005 - Validación mínimo mayor que máximo completada exitosamente')
