/**
 * ID:            TC-092_DevolucionDeMaterialCompleta
 * Modulo:        MATERIALES
 * Objetivo:      Validar el flujo completo de devolucion de material: seleccionar cuadrilla,
 *                fecha, validar cantidades vs metrajes en la tabla, guardar y verificar folio.
 * Precondiciones: Usuario con acceso a MATERIALES, material previamente entregado a cuadrilla.
 * Datos:         cuadrillaMateriales (parametro), sucursalesDiaActual (parametro).
 * Resultado esperado: Devolucion registrada exitosamente con folio generado.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement

WebUI.comment('INICIO TC-092_DevolucionDeMaterialCompleta')

try {
    // 1) Navegar a Devoluciones
    CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Devoluciones', '')
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
        cuadrillaMateriales.toString())

    // 5) Seleccionar fecha
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/INPUT_Fecha'))

    if (WebUI.getAttribute(findTestObject('Object Repository/OBJETOS_GLOBALES/SPAN_IdSucursal'), 'value') in sucursalesDiaActual) {
        WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/SPAN_FechaHabil'))
    } else {
        def diaJueves = CustomKeywords.'utilidades.FuncionesAuxiliares.obtenerJeuvesProximoMesActual'()
        WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/SPAN_DiaJueves-Dyn',
            [('diaJueves') : diaJueves]))
    }

    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // 6) Validar cantidades vs metrajes en la tabla de materiales
    WebElement table = DriverFactory.getWebDriver().findElement(
        By.xpath('(//dx-data-grid[@class="material-grid dx-card-grid-catalog dx-widget dx-visibility-change-handler"]//table)[2]'))

    List<WebElement> rows = table.findElements(
        By.xpath('(//dx-data-grid[@class="material-grid dx-card-grid-catalog dx-widget dx-visibility-change-handler"]//table)[2]//tr'))

    for (WebElement row : rows - 1) {
        List<WebElement> cells = row.findElements(By.tagName('td'))
        String tipoMaterial = (cells[2]).getText().trim()

        if (tipoMaterial == '1 - Material de Acometidas') {
            double cantidad = Double.parseDouble((cells[5]).getText().trim())
            double mInicial = Double.parseDouble((cells[6]).getText().trim())
            double mFinal = Double.parseDouble((cells[7]).getText().trim())
            double diferencia = mFinal - mInicial

            if (mInicial != 0) {
                WebUI.verifyGreaterThanOrEqual(mInicial, mFinal, FailureHandling.CONTINUE_ON_FAILURE)
                WebUI.verifyEqual(diferencia, cantidad, FailureHandling.CONTINUE_ON_FAILURE)
            }
        }
    }
    WebUI.comment('Validacion de cantidades vs metrajes completada')

    // 7) Guardar devolucion
    WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_Guardar'))
    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // 8) Verificar mensaje de exito
    WebUI.verifyElementPresent(
        findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'),
        GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

    def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))
    WebUI.comment("Mensaje obtenido: ${msgAlerta}")

    // 9) Validar texto de confirmacion y extraer folio
    CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(
        msgAlerta, 'Folio de devolución creado correctamente:', null)

    def folio = msgAlerta.split('Folio de devolución creado correctamente:')[1]
    WebUI.comment("Folio de devolucion generado: ${folio}")

    // 10) Registrar folio en Excel
    CustomKeywords.'utilidades.FuncionesAuxiliares.writeMultipleToExcel'(
        'MATERIALES/FoliosMateriales.xlsx', 'Devolucion', [('A') : folio])

    WebUI.comment('FIN TC-092 - Devolucion de material registrada exitosamente')

} finally {
    WebUI.click(findTestObject('OBJETOS_GLOBALES/BUTTON_Cerrar'), FailureHandling.OPTIONAL)
}
