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


final String ARCHIVO_DATOS = 'Data Files/SERVICIOS/Suscriptores servicio telefonia.xlsx'

final String HOJA_DATOS = 'Hoja1'

WebUI.setMaskedText(findTestObject('Object Repository/SERVICIOS/INPUT_Suscriptor'), '0' + numeroSuscriptor)

WebUI.sendKeys(findTestObject('Object Repository/SERVICIOS/INPUT_Suscriptor'), Keys.chord(Keys.ENTER))

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Contratos', '', '')

WebUI.waitForElementPresent(findTestObject('AGENDA/Contratos/TAB_DYN', [('opcion') : 'Contratos']), GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('AGENDA/Contratos/TAB_DYN', [('opcion') : 'Contratos']))

WebUI.click(findTestObject('AGENDA/Contratos/TAB_DYN', [('opcion') : 'Principales']))

WebUI.click(findTestObject('Object Repository/SERVICIOS/TD_ServicioTelefonia'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/BUTTON_VerOpciones'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/SPAN_Suspender'))

WebUI.verifyElementPresent(findTestObject('Object Repository/SERVICIOS/RADIO_MalServicio'), 5)

WebUI.click(findTestObject('Object Repository/SERVICIOS/CHECK_Telefonia'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/RADIO_MalServicio'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/RADIO_Si'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/SELECT_Alternativa'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/OPTION_ServicioTecnico'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/RADIO_Bueno'))

WebUI.sendKeys(findTestObject('Object Repository/SERVICIOS/TXTAREA_Comentario'), 'Prueba Katalon Studio')

WebUI.click(findTestObject('Object Repository/SERVICIOS/BUTTON_Imprimir'))

WebUI.switchToWindowIndex(1)

WebUI.waitForElementNotPresent(findTestObject('Object Repository/EQUIPOS/VentanaPDF/SPAN_LoadingPDF'), 180)

WebUI.takeScreenshot()

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

CustomKeywords.'utilidades.Ventas.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/SELECT_Cis'), '01')

WebUI.click(findTestObject('Object Repository/SERVICIOS/BUTTON_SuspenderContrato'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

WebUI.verifyElementText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 'Operación ejecutada de forma correcta.', FailureHandling.CONTINUE_ON_FAILURE)

CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'(ARCHIVO_DATOS, HOJA_DATOS, 'numerosuscriptor', numeroSuscriptor, msgAlerta)