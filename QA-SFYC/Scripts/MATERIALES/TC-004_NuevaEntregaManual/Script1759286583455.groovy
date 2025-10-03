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

CustomKeywords.'utilidades.Materiales.ingresarMenu'('Materiales', 'Entregas')

WebUI.verifyElementPresent(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/BUTTON_NuevaEntregaManual'), GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/BUTTON_NuevaEntregaManual'))

CustomKeywords.'utilidades.Materiales.selectOption'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_TipoCuadrilla'), 'ALM Almacen')

CustomKeywords.'utilidades.Materiales.selectOption'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/SELECT_Cuadrilla'), '0001')

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/BUTTON_AgregarFila'))

WebUI.sendKeys(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/TD_TablaMateriales-Dyn', [('idx') : '2']), Keys.chord(Keys.TAB))

CustomKeywords.'utilidades.Materiales.selectOptionTable'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/TD_TablaMateriales-Dyn', [('idx') : '2']), '4 Cable Coaxial')

CustomKeywords.'utilidades.Materiales.selectOptionTable'(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/TD_TablaMateriales-Dyn', [('idx') : '3']), '3 CABLE COAXIAL 500 PIII')

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/TD_TablaMateriales-Dyn', [('idx') : '5']))

WebUI.sendKeys(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/INPUT_Cantidades-Dyn', [('idx') : '5']), '5')

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/TD_TablaMateriales-Dyn', [('idx') : '6']))

WebUI.setMaskedText(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/INPUT_Cantidades-Dyn', [('idx') : '6']), '100.00')

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/TD_TablaMateriales-Dyn', [('idx') : '7']))

WebUI.setMaskedText(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/INPUT_Cantidades-Dyn', [('idx') : '7']), '80.00')

WebUI.click(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/BUTTON_Guardar'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
	
def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

def folio = msgAlerta.split('Entrega generada con exito. Folio: ')[1].trim()

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Entrega generada con exito.', null)

CustomKeywords.'utilidades.FuncionesAuxiliares.writeMultipleToExcel'('MATERIALES/FoliosMateriales.xlsx', 'Entregas', ["A" : folio])