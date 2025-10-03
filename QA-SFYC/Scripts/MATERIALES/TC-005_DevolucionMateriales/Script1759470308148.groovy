import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement

CustomKeywords.'utilidades.Materiales.ingresarMenu'('Materiales', 'Devoluciones')

WebUI.verifyElementPresent(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_TipoCuadrilla'), 10)

CustomKeywords.'utilidades.Materiales.selectOptionTab'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_TipoCuadrilla'), 
    'Quejas')

CustomKeywords.'utilidades.Materiales.selectOptionTab'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_Cuadrilla'), 
    '32')

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/INPUT_Fecha'))

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SPAN_FechaHabil'))

WebElement table = DriverFactory.getWebDriver().findElement(By.xpath('(//dx-data-grid[@class="material-grid dx-card-grid-catalog dx-widget dx-visibility-change-handler"]//table)[2]'))

// Obtiene todas las filas (excepto cabecera si aplica)
List<WebElement> rows = table.findElements(By.xpath('(//dx-data-grid[@class="material-grid dx-card-grid-catalog dx-widget dx-visibility-change-handler"]//table)[2]//tr'))

// Itera por cada fila
for (WebElement row : rows-1) {
    List<WebElement> cells = row.findElements(By.tagName('td'))

    String tipoMaterial = (cells[2]).getText().trim()

    if (tipoMaterial == '1 - Material de Acometidas') {
        double cantidad = Double.parseDouble((cells[5]).getText().trim())

        double mInicial = Double.parseDouble((cells[6]).getText().trim())

        double mFinal = Double.parseDouble((cells[7]).getText().trim())

        double diferencia = mFinal - mInicial

        //El metraje inicial debe ser mayor al metraje final
        WebUI.verifyGreaterThanOrEqual(mInicial, mFinal, FailureHandling.CONTINUE_ON_FAILURE)

        //La diferencia de los dos metrajes debe ser igual a la cantidad entregada
        WebUI.verifyEqual(diferencia, cantidad, FailureHandling.CONTINUE_ON_FAILURE)
    }
}

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/BUTTON_Guardar'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Folio de devolución creado correctamente:',
	null)

def folio = (msgAlerta.split('Folio de devolución creado correctamente:')[1])

CustomKeywords.'utilidades.FuncionesAuxiliares.writeMultipleToExcel'('MATERIALES/FoliosMateriales.xlsx', 'Devolucion', [
        ('A') : folio])