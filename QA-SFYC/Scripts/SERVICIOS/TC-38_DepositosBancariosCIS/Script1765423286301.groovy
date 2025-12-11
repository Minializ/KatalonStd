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

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Supervisión', 'Depósitos Bancarios', '')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/SELECT_Sucursales'), '41')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/SELECT_Cis'), 'CIS INGRESOS NORTE')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectDate'(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/INPUT_FechaInicial'), 'sep', '1')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectDate'(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/INPUT_FechaFinal'), '', '13')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/BUTTON_Aceptar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.click(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/BUTTON_Agregar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/FormularioDeposito/SELECT_Cuenta'), 'Banamex')

WebUI.setMaskedText(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/FormularioDeposito/INPUT_Depositos'), '300')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/FormularioDeposito/SELECT_Forma'), 'Efectivo')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/FormularioDeposito/BUTTON_Enviar'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

WebUI.takeScreenshot('AplicarPago.png')

WebUI.verifyElementText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 'Depósito realizado correctamente.',
	FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/TD_SeleccionarDeposito'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Supervision/DepositosBancarios/BUTTON_Eliminar'))

WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Si'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlertaEliminacion = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

WebUI.verifyElementText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 'Depósito eliminado correctamente',
	FailureHandling.CONTINUE_ON_FAILURE)