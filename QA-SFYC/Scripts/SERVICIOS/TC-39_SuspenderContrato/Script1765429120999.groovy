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

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Contratos', '', '')

WebUI.setMaskedText(findTestObject('Object Repository/SERVICIOS/Cajero/INPUT_NumeroSuscriptor'), '0' + numeroSuscriptor)

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/TAB_Contratos'))

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/TD_Telefonia'))

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/BUTTON_VerOpciones'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/BUTTON_Suspender'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/Suspender/RADIO_Si'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/Suspender/SELECT_Alternativa'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/Suspender/OPT_SelectAlternativa', ['opcion' : 'Promoción de retención']))

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/Suspender/INPUT_Comentario'), 'Prueba Suspensión')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/Suspender/CHECK_Telefonia'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/Suspender/BUTTON_Imprimir'))

WebUI.switchToWindowIndex(1)

WebUI.waitForElementNotPresent(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/VentanaPDF/SPAN_LoadingPDF'), 180)

WebUI.takeScreenshot('CuestionarioSuspension.jpg')

WebUI.verifyElementNotPresent(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/VentanaPDF/SPAN_ErrorPDF'), 3, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByIndex'(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/Suspender/SELECT_Cis'), '1')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByIndex'(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/Suspender/SELECT_SubCausa'), '1')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/Suspender/BUTTO_SuspenderContrato'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlertaEliminacion = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

WebUI.verifyElementText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 'Operación ejecutada de forma correcta.',
	FailureHandling.CONTINUE_ON_FAILURE)