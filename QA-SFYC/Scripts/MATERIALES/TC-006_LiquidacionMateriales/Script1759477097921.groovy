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

// Ingresa al menú "Materiales" y luego a la opción "Liquidaciones"
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Liquidaciones', '')

// Verifica que el combo "Tipo de Cuadrilla" esté presente antes de continuar
WebUI.verifyElementPresent(findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'), 10)

// Selecciona la opción "Quejas" en el selector de Tipo de Cuadrilla
CustomKeywords.'utilidades.Materiales.selectOptionTab'(findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'), 
    'Quejas')

// Selecciona la cuadrilla con identificador "32"
CustomKeywords.'utilidades.Materiales.selectOptionTab'(findTestObject('Object Repository/MATERIALES/Entregas/SELECT_Cuadrilla'), cuadrillaMateriales)

// Hace clic en el campo de fecha para desplegar el calendario
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/INPUT_Fecha'))

// Selecciona una fecha válida en el calendario (fecha hábil)
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/SPAN_FechaHabil'))

// Hace clic en el botón "Aceptar" para procesar la liquidación
WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/BUTTON_Aceptar'))

// Verifica que se muestre el mensaje de alerta indicando el resultado del proceso
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// Captura el texto del mensaje de alerta
def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

// Verifica que el mensaje contenga el texto esperado para una liquidación exitosa
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Proceso de liquidación de materiales realizado con éxito con el folio', 
    null)

// Extrae el número de folio desde el mensaje de confirmación
def folio = msgAlerta.split('Proceso de liquidación de materiales realizado con éxito con el folio:')[1]

// Escribe el número de folio en el archivo Excel correspondiente a liquidaciones
CustomKeywords.'utilidades.FuncionesAuxiliares.writeMultipleToExcel'('MATERIALES/FoliosMateriales.xlsx', 'Liquidacion', 
    [('A') : folio])
