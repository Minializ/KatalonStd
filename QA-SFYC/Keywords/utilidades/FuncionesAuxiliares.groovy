package utilidades
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import org.openqa.selenium.Keys as Keys

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class FuncionesAuxiliares {
	@Keyword
	/*
	 * Login dinamico, el cual detecta si al iniciar un caso de prueba ya esta la sesión iniciada,
	 * el script lo que hace es ingresar a la pagina de inicio del sistema, caso contrario de que el sistema
	 * detecte que no se ha iniciado sesión procede a iniciar sesión.
	 */
	def login(String url, String usuario, String password) {
		// Verificar si el navegador está abierto
		def currentURL = WebUI.getUrl(FailureHandling.OPTIONAL)

		if (currentURL == null) {
			WebUI.openBrowser(url)
			WebUI.maximizeWindow()
			WebUI.waitForElementPresent(findTestObject('Object Repository/LOGIN/SELECT_Dominio'), GlobalVariable.TIME_OUT_CONTENT)
			currentURL = WebUI.getUrl(FailureHandling.OPTIONAL)
		}else {
			WebUI.navigateToUrl(url)
		}

		// Función auxiliar para iniciar sesión
		def iniciarSesion = {
			WebUI.selectOptionByLabel(findTestObject('Object Repository/LOGIN/SELECT_Dominio'), 'SFYC', false)
			WebUI.setText(findTestObject('Object Repository/LOGIN/INPUT_User'), usuario)
			WebUI.setEncryptedText(findTestObject('Object Repository/LOGIN/INPUT_Password'), password)
			WebUI.click(findTestObject('Object Repository/LOGIN/BUTTON_IniciarSesion'))
		}

		// Si estamos en la pantalla de login
		if (currentURL.contains("Login")) {
			iniciarSesion()
		} else if (WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/PaginaInicio/LABEL_NombreUsuario'), 3, FailureHandling.OPTIONAL)) {
			def usuarioActual = WebUI.getText(findTestObject('Object Repository/AGENDA/PaginaInicio/LABEL_NombreUsuario'))

			// Si el usuario actual no coincide, cerrar sesión e iniciar con el nuevo
			if (!usuarioActual.contains(usuario)) {
				WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/LABEL_NombreUsuario'))
				WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/Usuario/BUTTON_CerrarSesion'))
				WebUI.delay(2)
				WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'))

				WebUI.waitForElementPresent(findTestObject('Object Repository/LOGIN/SELECT_Dominio'), GlobalVariable.TIME_OUT)
				iniciarSesion()
			}
		}

		// Validar que la sesión se haya iniciado correctamente
		WebUI.verifyElementNotPresent(findTestObject('Object Repository/LOGIN/SELECT_Dominio'), 2, FailureHandling.STOP_ON_FAILURE)

		//Metodo personalizdo que elimina el chatbot para evitar obstrucciones con otros objetos
		eliminarChatBot()
	}


	@Keyword
	// Método que actualiza el estatus de un suscriptor en un archivo Excel dado.
	// Parámetros:
	//   - filePath: Ruta del archivo Excel
	//   - sheetName: Nombre de la hoja dentro del archivo
	//   - numeroSuscriptorBuscado: Número de suscriptor que se desea actualizar
	//   - estatus: Nuevo valor que se desea asignar en la columna "estatus"
	def actualizarEstatusDataSetSuscriptor(String filePath, String sheetName, String numeroSuscriptorBuscado, String estatus) {

		// Abre el archivo Excel para lectura
		FileInputStream fis = new FileInputStream(filePath)
		Workbook workbook = new XSSFWorkbook(fis)

		// Obtiene la hoja deseada
		Sheet sheet = workbook.getSheet(sheetName)
		if (sheet == null) {
			fis.close()
			throw new Exception("No se encontró la hoja '${sheetName}'")
		}

		// Obtiene la fila de encabezado (primera fila) para identificar las columnas por nombre
		Row headerRow = sheet.getRow(0)
		int colNumeroSuscriptor = -1
		int colEstatus = -1

		// Busca los índices de las columnas "numeroSuscriptor" y "estatus"
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			String colName = headerRow.getCell(i)?.getStringCellValue()?.trim()?.toLowerCase()
			if (colName == "numerosuscriptor") colNumeroSuscriptor = i
			if (colName == "estatus") colEstatus = i
		}

		// Si alguna columna no fue encontrada, lanza una excepción
		if (colNumeroSuscriptor == -1 || colEstatus == -1) {
			fis.close()
			throw new Exception("No se encontró la columna 'numeroSuscriptor' o 'Estatus'")
		}

		// Se prepara para buscar el suscriptor en las filas del archivo
		int filaSuscriptor = -1
		DataFormatter formatter = new DataFormatter()  // Permite obtener valores como texto, sin importar el tipo de celda

		// Recorre cada fila para buscar el número de suscriptor en la columna correspondiente
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			// Empieza desde la fila 1 (fila 0 es encabezado)
			Row row = sheet.getRow(i)
			if (row != null) {
				Cell cell = row.getCell(colNumeroSuscriptor)
				String cellValue = ""
				if (cell != null) {
					cellValue = formatter.formatCellValue(cell).trim()
				}

				// Compara el valor de la celda con el número de suscriptor buscado
				if (cellValue == numeroSuscriptorBuscado) {
					filaSuscriptor = i
					break
				}
			}
		}

		// Si no se encuentra el número de suscriptor, se lanza una excepción
		if (filaSuscriptor == -1) {
			fis.close()
			throw new Exception("No se encontró el número de suscriptor: ${numeroSuscriptorBuscado}")
		}

		// Guarda todos los valores de la fila encontrada en una lista temporal
		Row rowToMove = sheet.getRow(filaSuscriptor)
		List<String> values = []
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			Cell cell = rowToMove.getCell(i)
			String valorCelda = ""
			if (cell != null) {
				valorCelda = formatter.formatCellValue(cell).trim()
			}
			values << valorCelda
		}

		// Actualiza el valor del estatus en la posición correspondiente
		values[colEstatus] = estatus

		// Elimina la fila original donde estaba el suscriptor
		sheet.removeRow(rowToMove)

		// Reorganiza las filas que estaban debajo (las mueve una fila hacia arriba)
		for (int i = filaSuscriptor + 1; i <= sheet.getLastRowNum(); i++) {
			sheet.shiftRows(i, i, -1)
		}

		// Agrega una nueva fila al final con los mismos datos pero con el estatus actualizado
		Row newRow = sheet.createRow(sheet.getLastRowNum() + 1)
		for (int i = 0; i < values.size(); i++) {
			newRow.createCell(i).setCellValue(values[i])
		}

		// Cierra el archivo de entrada
		fis.close()

		// Guarda los cambios en el archivo Excel sobrescribiendo el archivo original
		FileOutputStream fos = new FileOutputStream(filePath)
		workbook.write(fos)
		fos.close()

		// Cierra el workbook
		workbook.close()
	}

	@Keyword
	// Método que limpia la vista actual cerrando todas las ventanas, pestañas o modales emergentes visibles.
	// Se utiliza un bucle `while` que verifica si existe un botón de "Cerrar" visible en la interfaz.
	// Mientras el botón esté presente, se hace clic para cerrarlo.
	// Esto asegura que el entorno de la interfaz quede limpio para continuar con el siguiente paso del test.
	def limpiarVista() {
		while (WebUI.verifyElementPresent(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'), 1, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))
		}
	}

	@Keyword
	//Metodo que elimina el chatbot para evitar obstrucciones con objetos
	def eliminarChatBot() {

		String script = "var el = document.querySelector('app-megan-side-bar-container'); if (el) el.remove();"
		WebUI.executeJavaScript(script, null, FailureHandling.OPTIONAL)
	}


	@Keyword
	def writeMultipleToExcel(String fileName, String sheetName, Map<String, String> columnasYValores) {
		try {
			// Ruta completa al archivo dentro del proyecto
			String filePath = RunConfiguration.getProjectDir() + "/Data Files/" + fileName
			File excelFile = new File(filePath)

			Workbook workbook
			Sheet sheet

			if (excelFile.exists()) {
				FileInputStream fis = new FileInputStream(excelFile)
				workbook = WorkbookFactory.create(fis)
				sheet = workbook.getSheet(sheetName)
				if (sheet == null) {
					sheet = workbook.createSheet(sheetName)
				}
				fis.close()
			} else {
				workbook = new XSSFWorkbook()
				sheet = workbook.createSheet(sheetName)
			}

			// Buscar siguiente fila vacía
			int lastRowNum = sheet.getLastRowNum()
			int nextRowNum = lastRowNum
			while (sheet.getRow(nextRowNum) != null && !isRowEmpty(sheet.getRow(nextRowNum))) {
				nextRowNum++
			}

			Row newRow = sheet.createRow(nextRowNum)

			// Escribir cada columna y su valor
			columnasYValores.each { columnaLetra, valor ->
				int columnIndex = convertColumnLetterToIndex(columnaLetra)
				Cell cell = newRow.createCell(columnIndex)
				cell.setCellValue(valor)
			}

			// Guardar el archivo
			FileOutputStream fos = new FileOutputStream(filePath)
			workbook.write(fos)
			fos.close()
			workbook.close()

			println "✅ Valores guardados en '${fileName}', hoja '${sheetName}', fila ${nextRowNum + 1}"
		} catch (Exception e) {
			println "❌ Error al escribir en Excel: ${e.message}"
			throw e
		}
	}


	private boolean isRowEmpty(Row row) {
		if (row == null) return true
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c)
			if (cell != null && cell.getCellType() != CellType.BLANK) {
				return false
			}
		}
		return true
	}


	private int convertColumnLetterToIndex(String column) {
		column = column.toUpperCase()
		int index = 0
		for (int i = 0; i < column.length(); i++) {
			char c = column.charAt(i)
			index *= 26
			index += ((int)c - (int)'A' + 1)
		}
		return index - 1
	}

	@Keyword
	def obtenerJeuvesProximoMesActual() {		
		LocalDate hoy = LocalDate.now()
		
		int diasHastaJueves = DayOfWeek.THURSDAY.getValue() - hoy.getDayOfWeek().getValue()
		if (diasHastaJueves <= 0) {
			diasHastaJueves += 7
		}
		
		LocalDate proximoJueves = hoy.plusDays(diasHastaJueves)
		
		int diaDelMes = proximoJueves.getDayOfMonth()
		
		return diaDelMes
	}
}