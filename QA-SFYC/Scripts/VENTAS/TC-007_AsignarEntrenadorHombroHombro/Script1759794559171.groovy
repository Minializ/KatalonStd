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

CustomKeywords.'utilidades.Ventas.selectOptionByCode'(findTestObject('Object Repository/OBJETOS_GLOBALES/SELECT_Sucursal'), sucursal)

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Vendedores hombro a hombro', '')

CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/SELECT_EmpresaNomina'), 'CABLE VENTAS CAMBACEO')

WebUI.setText(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/INPUT_NumeroVendedor'), numeroVendedor)

WebUI.delay(1)

WebUI.sendKeys(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/INPUT_NumeroVendedor'), Keys.chord(Keys.ENTER))

if (WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 3, FailureHandling.OPTIONAL)) {
    CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()
}

if(entrenadorNuevo.toUpperCase().contains('SI')) {
	WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/BUTTON_Guardar'))
	
	WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
	
	def msgAlertaAsignacionEntrenador = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))
	
	CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.matches'(msgAlertaAsignacionEntrenador, '^Se certificó a .+? como Entrenador Hombro Hombro\\.$',
		'')
	
	WebUI.takeScreenshot()
	
	CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()
}

WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/TAB_AsignarDescartar'))

WebUI.waitForElementPresent(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_BuscarVendedor'), 10)

WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_BuscarVendedor'))

WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/INPUT_NumeroVendedor'))

WebUI.setText(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/INPUT_NumeroVendedor'), numeroVendedorEntrenamiento)

WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_Enviar'))

//El vendedor tiene más de dos semanas que ingreso.
if (WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 3, FailureHandling.OPTIONAL)) {
    WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_Enviar'))
}

CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/SELECT_Periodo'), 
    '/')

WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_Asingar'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlertaAsignacionVendedor = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.matches'(msgAlerta, '^El vendedor .+? Fue asignado\\.$', 
    msgAlertaAsignacionVendedor, '')