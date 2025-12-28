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

Random rand = new Random()

int folios = rand.nextInt(6001) + 3000

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/OBJETOS_GLOBALES/SELECT_Sucursal'), 
    '41')

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Solicitudes', '', '')

WebUI.click(findTestObject('Object Repository/VENTAS/Solicitudes/BUTTON_AsignacionSolicitudes'))

CustomKeywords.'utilidades.Agenda.selectOptionById'(findTestObject('Object Repository/VENTAS/Solicitudes/SELECT_Vendedor'), 
    '328811')

WebUI.click(findTestObject('Object Repository/VENTAS/Solicitudes/CHECK_AsignacionManual'))

WebUI.setText(findTestObject('Object Repository/VENTAS/Solicitudes/INPUT_DelFolio'), folios.toString())

WebUI.setText(findTestObject('Object Repository/VENTAS/Solicitudes/INPUT_AlFolio'), (folios + 5).toString())

WebUI.click(findTestObject('Object Repository/VENTAS/Solicitudes/BUTTON_Aceptar'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.matches'(msgAlerta, '^Se asignaron \\d+ solicitud\\(es\\) al vendedor\\.?$', 
    'Validar mensaje de asignación', FailureHandling.STOP_ON_FAILURE)