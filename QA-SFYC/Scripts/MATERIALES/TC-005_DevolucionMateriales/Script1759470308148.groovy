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

// Ingresa al menú "Materiales" y luego a la opción "Devoluciones"
CustomKeywords.'utilidades.Materiales.ingresarMenu'('Materiales', 'Devoluciones')

// Verifica que el combo "Tipo de Cuadrilla" esté presente antes de continuar
WebUI.verifyElementPresent(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_TipoCuadrilla'), 10)

// Selecciona la opción "Quejas" en el selector de Tipo de Cuadrilla
CustomKeywords.'utilidades.Materiales.selectOptionTab'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_TipoCuadrilla'), 
    'Quejas')

// Selecciona la cuadrilla con identificador "32"
CustomKeywords.'utilidades.Materiales.selectOptionTab'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_Cuadrilla'), 
    cuadrillaMateriales)

// Hace clic en el campo de fecha para desplegar el calendario
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/INPUT_Fecha'))

if( WebUI.getAttribute(findTestObject('Object Repository/OBJETOS_GLOBALES/SELECT_Sucursal'), 'value') in sucursalesDiaActual) {
	// Selecciona una fecha válida en el calendario (fecha hábil)
	WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SPAN_FechaHabil'))
}else {
	// Selecciona como fecha un día habil de Jueves
	WebUI.takeScreenshot()
	def diaJueves = CustomKeywords.'utilidades.FuncionesAuxiliares.obtenerJeuvesProximoMesActual'()
	WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SPAN_DiaJueves-Dyn', [('diaJueves') : diaJueves]))
}

// Obtiene la tabla de materiales desde el DOM (segunda instancia de la tabla dentro del grid)
WebElement table = DriverFactory.getWebDriver().findElement(By.xpath('(//dx-data-grid[@class="material-grid dx-card-grid-catalog dx-widget dx-visibility-change-handler"]//table)[2]'))

// Obtiene todas las filas de la tabla (incluyendo encabezados)
List<WebElement> rows = table.findElements(By.xpath('(//dx-data-grid[@class="material-grid dx-card-grid-catalog dx-widget dx-visibility-change-handler"]//table)[2]//tr'))

// Itera sobre todas las filas excepto la última (posiblemente vacía o resumen)
for (WebElement row : rows - 1) {
    // Extrae todas las celdas de la fila actual
    List<WebElement> cells = row.findElements(By.tagName('td'))

    // Obtiene el texto del tipo de material en la columna 3
    String tipoMaterial = (cells[2]).getText().trim()

	// Solo aplica validaciones para el tipo "1 - Material de Acometidas"
	if (tipoMaterial == '1 - Material de Acometidas') {
		// Obtiene e interpreta la cantidad entregada
		double cantidad = Double.parseDouble((cells[5]).getText().trim())

		// Obtiene metraje inicial (columna 6)
		double mInicial = Double.parseDouble((cells[6]).getText().trim())

		// Obtiene metraje final (columna 7)
		double mFinal = Double.parseDouble((cells[7]).getText().trim())

		// Calcula la diferencia entre metraje final e inicial
		double diferencia = mFinal - mInicial

		//Valida que se hayan usado los materiales para hacer la validacion, caso contrario no valida campos
		if(mInicial != 0) {
			// Verifica que el metraje inicial sea mayor o igual al final (lógica esperada en devoluciones)
			WebUI.verifyGreaterThanOrEqual(mInicial, mFinal, FailureHandling.CONTINUE_ON_FAILURE)
	
			// Verifica que la diferencia entre metrajes coincida con la cantidad entregada
			WebUI.verifyEqual(diferencia, cantidad, FailureHandling.CONTINUE_ON_FAILURE)
		}
	}
}

// Hace clic en el botón "Guardar" para confirmar la devolución
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/BUTTON_Guardar'))

// Verifica que se muestre un mensaje de alerta (confirmación de éxito)
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// Captura el texto del mensaje de alerta
def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

// Verifica que el mensaje contenga el texto esperado para una devolución exitosa
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Folio de devolución creado correctamente:', 
    null)

// Extrae el número de folio de devolución desde el mensaje
def folio = msgAlerta.split('Folio de devolución creado correctamente:')[1]

// Escribe el folio obtenido en el archivo Excel correspondiente a devoluciones
CustomKeywords.'utilidades.FuncionesAuxiliares.writeMultipleToExcel'('MATERIALES/FoliosMateriales.xlsx', 'Devolucion', [
        ('A') : folio])