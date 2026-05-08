/**
 * ID:            TC-001_BuscarRangoAnis_HappyPath
 * Modulo:        EQUIPOS - Operaciones - Administracion de Anis
 * Objetivo:      Buscar un rango de ANIs con filtros validos (Desde, Hasta,
 *                Codigo Local) y validar que el grid muestre resultados.
 * Precondiciones: - Usuario con permisos de Administracion de Anis
 *                 - Sucursal con ANIs existentes
 * Resultado esperado: Grid muestra registros de ANIs en el rango buscado.
 * Autor:         QA Automatizacion
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-001_BuscarRangoAnis_HappyPath')

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
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisDesde'), '1000000')

// =====================================================
// PASO 4: Llenar campo Hasta
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisHasta'), '1000100')

// =====================================================
// PASO 5: Llenar Codigo Local
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_CodigoLocal'), '33')

WebUI.takeScreenshot()

// =====================================================
// PASO 6: Click en Buscar Rango
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_BuscarRango'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 7: Validar que el grid tiene resultados
// =====================================================
WebUI.verifyElementPresent(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/TR_PrimerResultado'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-001 - Busqueda de rango de ANIs completada EXITOSAMENTE con resultados en grid')
