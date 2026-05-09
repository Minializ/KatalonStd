/**
 * ID:            TC-002_CrearNuevoMaterial_HappyPath
 * Módulo:        MATERIALES - Catálogo de Materiales
 * Objetivo:      Validar la creación de un nuevo material llenando todos los campos
 *                obligatorios y enviando el formulario.
 * Precondiciones: Usuario con acceso a MATERIALES y permiso materialCatalog.
 * Datos:         Código material generado aleatoriamente (4 dígitos), descripción de prueba.
 * Resultado esperado: Mensaje TOAST de éxito al crear el material.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-07-22
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-002_CrearNuevoMaterial_HappyPath')

// Generar código de material aleatorio de 4 dígitos
int codigoRandom = new Random().nextInt(9000) + 1000
String codigoMaterial = codigoRandom.toString()

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

// 3) Verificar grilla cargada
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/GRID_CatalogoMateriales'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// 4) Click en botón Nuevo Registro (+)
WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_NuevoRegistro'))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 5) Verificar que se abrió el popup de formulario
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/POPUP_DescripcionMaterial'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// 6) Llenar campos obligatorios

// Tipo Material (primer elemento disponible)
CustomKeywords.'utilidades.Materiales.selectOption'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_TipoMaterial'))

WebUI.delay(1)

// Código Material
WebUI.setText(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_CodigoMaterial'),
    codigoMaterial)

// Descripción
WebUI.setText(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Descripcion'),
    'MATERIAL PRUEBA AUTOMATIZADA ' + codigoMaterial)

// Unidad
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_Unidad'),
    'PIEZA')

// Vigente
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_Vigente'),
    'Si vigente')

// Mínimo
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Minimo'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Minimo'), '0')

// Máximo
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Maximo'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_Maximo'), '100')

// Tipo Construcción
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_TipoConstruccion'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_TipoConstruccion'), '1')

// Plantillas (seleccionar primera opción disponible)
CustomKeywords.'utilidades.Materiales.selectOption'(
    findTestObject('Object Repository/MATERIALES/CatalogoMateriales/SELECT_Plantillas'))

WebUI.delay(1)

// Mínimo Plantilla
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MinimoPlantilla'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MinimoPlantilla'), '1')

// Máximo Plantilla
WebUI.clearText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MaximoPlantilla'))
WebUI.setText(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/INPUT_MaximoPlantilla'), '50')

// 7) Click en Aceptar para enviar el formulario
WebUI.click(findTestObject('Object Repository/MATERIALES/CatalogoMateriales/BUTTON_Aceptar'))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 8) Confirmar en diálogo de confirmación
WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'))
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 9) Validar mensaje TOAST de éxito
String textoToast = WebUI.getText(
    findTestObject('Object Repository/OBJETOS_GLOBALES/TOAST_Mensaje'),
    FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Mensaje TOAST: ' + textoToast)

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
    textoToast, 'xito', null)

WebUI.comment('FIN TC-002 - Nuevo material creado exitosamente con código: ' + codigoMaterial)
