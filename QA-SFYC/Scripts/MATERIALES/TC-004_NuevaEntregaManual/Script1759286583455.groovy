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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// Ingresa al menú "Materiales" y luego a la opción "Entregas"
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Entregas', '')

// Verifica que el botón "Nueva Entrega Manual" esté presente en el DOM antes de continuar
WebUI.verifyElementPresent(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_NuevaEntregaManual'), 
    GlobalVariable.TIME_OUT)

// Hace clic en el botón para iniciar una nueva entrega manual
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_NuevaEntregaManual'))

// Selecciona la opción "ALM Almacen" en el combo de Tipo de Cuadrilla
CustomKeywords.'utilidades.Materiales.selectOption'(findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'), 
    'ALM Almacen')

// Selecciona la cuadrilla "0001" en el combo correspondiente
CustomKeywords.'utilidades.Materiales.selectOption'(findTestObject('Object Repository/MATERIALES/Entregas/SELECT_Cuadrilla'), 
    '0001')

// Agrega una nueva fila a la tabla de materiales
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_AgregarFila'))

// Envía un TAB en la celda dinámica índice 2 para activar la edición/autocompletado
WebUI.sendKeys(findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '2']), 
    Keys.chord(Keys.TAB))

// Selecciona el tipo de material "4 Cable Coaxial"
CustomKeywords.'utilidades.Materiales.selectOptionTable'(findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', 
        [('idx') : '2']), 'Cable Coaxial')

// Selecciona el material "3 CABLE COAXIAL 500 PIII" (índice 3)
CustomKeywords.'utilidades.Materiales.selectOptionTable'(findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', 
        [('idx') : '3']), 'CABLE COAXIAL 500 PIII')

// Hace clic en la celda de la columna cantidad (índice 5)
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '5']))

// Ingresa el valor "5" en la celda de cantidad
WebUI.sendKeys(findTestObject('Object Repository/MATERIALES/Entregas/INPUT_Cantidades-Dyn', [('idx') : '5']), 
    '5')

// Hace clic en la celda de la columna M Inicial (índice 6)
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '6']))

// Ingresa el valor "100.00" en la columna M Inicial
WebUI.setMaskedText(findTestObject('Object Repository/MATERIALES/Entregas/INPUT_Cantidades-Dyn', [('idx') : '6']), 
    '100.00')

// Hace clic en la celda de la columna M Final
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/TD_TablaMateriales-Dyn', [('idx') : '7']))

// Ingresa el valor "80.00" en la columna M Final
WebUI.setMaskedText(findTestObject('Object Repository/MATERIALES/Entregas/INPUT_Cantidades-Dyn', [('idx') : '7']), 
    '80.00')

// Hace clic en el botón "Guardar" para registrar la entrega
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_Guardar'))

// Verifica que aparezca un mensaje de alerta indicando el resultado de la operación
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// Obtiene el texto del mensaje de alerta mostrado en pantalla
def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

// Extrae el número de folio de la entrega desde el mensaje
def folio = (msgAlerta.split('Entrega generada con exito. Folio: ')[1]).trim()

// Valida que el mensaje contenga el texto esperado de confirmación
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Entrega generada con exito.', null)

// Escribe el número de folio generado en el archivo Excel correspondiente
CustomKeywords.'utilidades.FuncionesAuxiliares.writeMultipleToExcel'('MATERIALES/FoliosMateriales.xlsx', 'Entregas', [('A') : folio])