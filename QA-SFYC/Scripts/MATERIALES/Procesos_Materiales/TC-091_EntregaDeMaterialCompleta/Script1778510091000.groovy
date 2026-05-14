/**
 * ID:            TC-091_EntregaDeMaterialCompleta
 * Modulo:        MATERIALES
 * Objetivo:      Validar el flujo completo de entrega manual de material: seleccionar cuadrilla,
 *                agregar material Cable Coaxial, capturar cantidades y metrajes, guardar y
 *                verificar folio generado.
 * Precondiciones: Usuario con acceso a MATERIALES, materiales disponibles en inventario.
 * Datos:         TipoCuadrilla='ALM Almacen', Cuadrilla='0001', Material='Cable Coaxial'.
 * Resultado esperado: Entrega registrada exitosamente con folio generado.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.comment('INICIO TC-091_EntregaDeMaterialCompleta')

try {
    // 1) Navegar a Entregas
    CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Entregas', '')
    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // 2) Verificar que el boton Nueva Entrega Manual este presente
    WebUI.verifyElementPresent(
        findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_NuevaEntregaManual'),
        GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

    // 3) Click en Nueva Entrega Manual
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_NuevaEntregaManual'))
    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // 4) Seleccionar Tipo de Cuadrilla: ALM Almacen
    CustomKeywords.'utilidades.Materiales.selectOption'(
        findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'),
        'ALM Almacen')

    // 5) Seleccionar Cuadrilla: 0001
    CustomKeywords.'utilidades.Materiales.selectOption'(
        findTestObject('Object Repository/MATERIALES/Entregas/SELECT_Cuadrilla'),
        '0001')

    // 6) Agregar fila de material
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_AgregarFila'))
    WebUI.delay(1)

    // 7) Seleccionar tipo de material: Cable Coaxial
    WebUI.sendKeys(
        findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '2']),
        Keys.chord(Keys.TAB))
    CustomKeywords.'utilidades.Materiales.selectOptionTable'(
        findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '2']),
        'Cable Coaxial')

    // 8) Seleccionar material: CABLE COAXIAL 500 PIII
    CustomKeywords.'utilidades.Materiales.selectOptionTable'(
        findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '3']),
        'CABLE COAXIAL 500 PIII')

    // 9) Capturar cantidad: 5
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '5']))
    WebUI.sendKeys(
        findTestObject('Object Repository/MATERIALES/Entregas/INPUT_Cantidades-Dyn', [('idx') : '5']),
        '5')

    // 10) Capturar M Inicial: 100.00
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '6']))
    WebUI.setMaskedText(
        findTestObject('Object Repository/MATERIALES/Entregas/INPUT_Cantidades-Dyn', [('idx') : '6']),
        '100.00')

    // 11) Capturar M Final: 80.00
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '7']))
    WebUI.setMaskedText(
        findTestObject('Object Repository/MATERIALES/Entregas/INPUT_Cantidades-Dyn', [('idx') : '7']),
        '80.00')

    // 12) Guardar entrega
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_Guardar'))
    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // 13) Verificar mensaje de exito
    WebUI.verifyElementPresent(
        findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'),
        GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

    def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))
    WebUI.comment("Mensaje obtenido: ${msgAlerta}")

    // 14) Validar texto de confirmacion y extraer folio
    CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
        msgAlerta, 'Entrega generada con exito.', null)

    def folio = (msgAlerta.split('Entrega generada con exito. Folio: ')[1]).trim()
    WebUI.comment("Folio de entrega generado: ${folio}")

    // 15) Registrar folio en Excel
    CustomKeywords.'utilidades.FuncionesAuxiliares.writeMultipleToExcel'(
        'MATERIALES/FoliosMateriales.xlsx', 'Entregas', [('A') : folio])

    WebUI.comment('FIN TC-091 - Entrega de material registrada exitosamente')

} finally {
    // Cerrar alertas pendientes si existen
    WebUI.click(findTestObject('OBJETOS_GLOBALES/BUTTON_Cerrar'), FailureHandling.OPTIONAL)
}
