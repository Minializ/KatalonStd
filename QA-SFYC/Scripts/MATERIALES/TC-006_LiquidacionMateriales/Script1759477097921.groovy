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

CustomKeywords.'utilidades.Materiales.ingresarMenu'('Materiales', 'Liquidaciones')

WebUI.verifyElementPresent(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_TipoCuadrilla'), 10)

CustomKeywords.'utilidades.Materiales.selectOptionTab'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_TipoCuadrilla'), 
    'Quejas')

CustomKeywords.'utilidades.Materiales.selectOptionTab'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_Cuadrilla'), 
    '32')

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/INPUT_Fecha'))

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SPAN_FechaHabil'))

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/BUTTON_Aceptar'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Proceso de liquidación de materiales realizado con éxito con el folio', 
    null)

def folio = msgAlerta.split('Proceso de liquidación de materiales realizado con éxito con el folio:')[1]

CustomKeywords.'utilidades.FuncionesAuxiliares.writeMultipleToExcel'('MATERIALES/FoliosMateriales.xlsx', 'Liquidacion', 
    [('A') : folio])