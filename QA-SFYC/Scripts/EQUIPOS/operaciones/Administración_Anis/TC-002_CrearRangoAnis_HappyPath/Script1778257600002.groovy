/**
 * ID:            TC-002_CrearRangoAnis_HappyPath
 * Modulo:        EQUIPOS - Operaciones - Administracion de Anis
 * Objetivo:      Crear un rango de ANIs con todos los campos validos.
 *                Desde, Hasta, Codigo Local, Estado, Tipo. Valida mensaje de exito.
 * Precondiciones: - Usuario con permisos de Administracion de Anis
 *                 - Rango de ANIs no existente previamente
 * Resultado esperado: Rango de ANIs creado exitosamente con mensaje de confirmacion.
 * Autor:         QA Automatizacion
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

// Generar rango aleatorio para evitar duplicados
int rangoBase = ((Math.random() * 8000000) as int) + 1000000

WebUI.comment('INICIO TC-002_CrearRangoAnis_HappyPath - Rango desde: ' + rangoBase)

// =====================================================
// PASO 1: Login al modulo EQUIPOS
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
    GlobalVariable.URL_EQUIPOS,
    GlobalVariable.USER_EQUIPOS,
    GlobalVariable.PASSWORD_EQUIPOS)

// =====================================================
// PASO 2: Navegar a Operaciones > Administracion de Anis
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Administración de Anis', '')

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 3: Llenar campo Desde
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisDesde'),
    String.valueOf(rangoBase))

// =====================================================
// PASO 4: Llenar campo Hasta
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisHasta'),
    String.valueOf(rangoBase + 10))

// =====================================================
// PASO 5: Llenar Codigo Local
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_CodigoLocal'), '33')

// =====================================================
// PASO 6: Seleccionar Estado = Disponible
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/SELECT_Estado'), 'Disponible')

// =====================================================
// PASO 7: Seleccionar Tipo = Normal
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/SELECT_Tipo'), 'Normal')

WebUI.takeScreenshot()

// =====================================================
// PASO 8: Click en Crear Rango
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_CrearRango'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 9: Confirmar creacion (Si)
// =====================================================
WebUI.waitForElementPresent(
    findTestObject('OBJETOS_GLOBALES/BUTTON_Si'), GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('OBJETOS_GLOBALES/BUTTON_Si'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 10: Validar mensaje de exito
// =====================================================
WebUI.verifyElementPresent(
    findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'),
    GlobalVariable.TIME_OUT + 10, FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-002 - Rango de ANIs creado EXITOSAMENTE')
