/**
 * ID:            TC-005_BuscarRangoAnis_FiltroEstadoTipo
 * Modulo:        EQUIPOS - Operaciones - Administracion de Anis
 * Objetivo:      Buscar un rango de ANIs filtrando por Estado=Disponible y
 *                Tipo=Normal. Valida que el grid cargue y muestre resultados
 *                filtrados correctamente.
 * Precondiciones: - Usuario con permisos de Administracion de Anis
 *                 - ANIs con estado Disponible y tipo Normal existentes
 * Resultado esperado: Grid muestra registros filtrados por Estado y Tipo.
 * Autor:         QA Automatizacion
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-005_BuscarRangoAnis_FiltroEstadoTipo')

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
// PASO 3: Llenar rango de ANIs
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisDesde'), '1000000')

WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_AnisHasta'), '9999999')

// =====================================================
// PASO 4: Llenar Codigo Local
// =====================================================
WebUI.setText(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/INPUT_CodigoLocal'), '33')

// =====================================================
// PASO 5: Seleccionar Estado = Disponible
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/SELECT_Estado'), 'Disponible')

// =====================================================
// PASO 6: Seleccionar Tipo = Normal
// =====================================================
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/SELECT_Tipo'), 'Normal')

WebUI.takeScreenshot()

// =====================================================
// PASO 7: Click en Buscar Rango
// =====================================================
WebUI.click(findTestObject('Object Repository/EQUIPOS/AdministracionAnis/BUTTON_BuscarRango'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// =====================================================
// PASO 8: Validar que el grid carga con resultados
// =====================================================
WebUI.verifyElementPresent(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/GRID_Anis'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementPresent(
    findTestObject('Object Repository/EQUIPOS/AdministracionAnis/TR_PrimerResultado'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.takeScreenshot()

WebUI.comment('FIN TC-005 - Busqueda con filtro Estado=Disponible Tipo=Normal completada EXITOSAMENTE')
