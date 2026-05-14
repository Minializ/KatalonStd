/**
 * ID:            TC-093_LiquidacionDeMaterialCompleta
 * Modulo:        MATERIALES
 * Objetivo:      Validar el flujo completo de liquidacion de material: seleccionar cuadrilla,
 *                fecha habil, ejecutar proceso y verificar folio generado.
 * Precondiciones: Usuario con acceso a MATERIALES, entregas y devoluciones pendientes de liquidar.
 * Datos:         cuadrillaMateriales (parametro).
 * Resultado esperado: Liquidacion ejecutada exitosamente con folio generado.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-093_LiquidacionDeMaterialCompleta')

try {
    // 1) Navegar a Liquidaciones
    CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Liquidaciones', '')
    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // 2) Verificar que el combo Tipo de Cuadrilla este presente
    WebUI.verifyElementPresent(
        findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'),
        GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

    // 3) Seleccionar Tipo de Cuadrilla: Quejas
    CustomKeywords.'utilidades.Materiales.selectOptionTab'(
        findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'),
        'Quejas')

    // 4) Seleccionar Cuadrilla
    CustomKeywords.'utilidades.Materiales.selectOptionTab'(
        findTestObject('Object Repository/MATERIALES/Entregas/SELECT_Cuadrilla'),
        cuadrillaMateriales)

    // 5) Seleccionar fecha habil
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/INPUT_Fecha'))
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/SPAN_FechaHabil'))

    // 6) Ejecutar proceso de liquidacion
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_Aceptar'))
    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // 7) Verificar mensaje de exito
    WebUI.verifyElementPresent(
        findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'),
        GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

    def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))
    WebUI.comment("Mensaje obtenido: ${msgAlerta}")

    // 8) Validar texto de confirmacion y extraer folio
    CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
        msgAlerta, 'Proceso de liquidación de materiales realizado con éxito con el folio', null)

    def folio = msgAlerta.split('Proceso de liquidación de materiales realizado con éxito con el folio:')[1]
    WebUI.comment("Folio de liquidacion generado: ${folio}")

    // 9) Registrar folio en Excel
    CustomKeywords.'utilidades.FuncionesAuxiliares.writeMultipleToExcel'(
        'MATERIALES/FoliosMateriales.xlsx', 'Liquidacion', [('A') : folio])

    WebUI.comment('FIN TC-093 - Liquidacion de material ejecutada exitosamente')

} finally {
    WebUI.click(findTestObject('OBJETOS_GLOBALES/BUTTON_Cerrar'), FailureHandling.OPTIONAL)
}
