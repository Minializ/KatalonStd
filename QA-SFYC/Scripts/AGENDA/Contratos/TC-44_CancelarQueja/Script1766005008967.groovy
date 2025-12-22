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

CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'(numeroSuscriptor)

WebUI.waitForElementPresent(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Trabajos']),
	GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Trabajos']))

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Quejas']))

def nombreTrabajo = WebUI.getText(findTestObject('Object Repository/AGENDA/Quejas/TR_QuejaASI', ['idxCol' : '2']))

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/TR_QuejaASI', ['idxCol' : '2']))

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/BUTTON_VerOpciones'))

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/BUTTON_Cancelar'))

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/SELECT_CausaCancelacion'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/OPT_Cancelacion', ['opcion' : 'Cte. informa serv reestablecido']))

WebUI.setText(findTestObject('Object Repository/AGENDA/Quejas/INPUT_Observaciones'), 'CANCELACION QUEJA KATALON')

WebUI.click(findTestObject('Object Repository/AGENDA/Quejas/BUTTON_Enviar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

def estatusQueja = WebUI.getText(findTestObject('Object Repository/AGENDA/Quejas/TD_Queja', ['nombreTrabajo' : nombreTrabajo]))

WebUI.verifyMatch(estatusQueja, 'CAN', false)