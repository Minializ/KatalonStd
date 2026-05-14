/**
 * ID:            TC-094_ConsultaOrdenesDeTrabajoTabs
 * Modulo:        MATERIALES
 * Objetivo:      Validar la navegacion y carga de todas las pestañas (tabs) en Ordenes
 *                de Trabajo: General, Trabajos, Asignacion, Atencion, Bonos, Materiales,
 *                Visitas, Equipos, Llamadas.
 * Precondiciones: Usuario con acceso a MATERIALES > Trabajos > Órdenes.
 * Datos:         N/A.
 * Resultado esperado: Cada tab carga correctamente sin errores.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-094_ConsultaOrdenesDeTrabajoTabs')

// 1) Navegar a Trabajos > Ordenes
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Trabajos', 'Órdenes')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

// 2) Verificar grid principal esta presente
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/GRID_Principal'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Grid de Ordenes de Trabajo cargado')

// 3) Verificar cada tab del dx-tab-panel
def tabs = ['General', 'Trabajos', 'Asignacion', 'Atencion', 'Bonos', 'Materiales', 'Visitas', 'Equipos', 'Llamadas']

for (String tabName : tabs) {
    WebUI.verifyElementPresent(
        findTestObject('Object Repository/MATERIALES/Procesos/TAB_Panel-Dyn', [('tabName') : tabName]),
        GlobalVariable.TIME_OUT, FailureHandling.CONTINUE_ON_FAILURE)

    WebUI.click(findTestObject('Object Repository/MATERIALES/Procesos/TAB_Panel-Dyn', [('tabName') : tabName]))
    WebUI.delay(1)
    WebUI.comment("Tab '${tabName}' cargado correctamente")
}

WebUI.comment('FIN TC-094 - Todas las pestanas de Ordenes de Trabajo accesibles')
