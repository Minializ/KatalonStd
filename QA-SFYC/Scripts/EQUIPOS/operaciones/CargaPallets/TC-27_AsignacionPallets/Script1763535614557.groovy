import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
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

String archivoPallets = RunConfiguration.getProjectDir() + "/Data Files/Equipos/Pallets.txt"

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Carga de Pallets', '')

WebUI.waitForElementClickable(findTestObject('Object Repository/EQUIPOS/CargaPallets/SELECT_TipoEquipo'), 30)

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/CargaPallets/SELECT_TipoEquipo'), '02')

WebUI.click(findTestObject('Object Repository/EQUIPOS/CargaPallets/BUTTON_IniciarPallet'))

def msgAlertaIniciarPallet = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlertaIniciarPallet, 'Proceso terminado con éxito', null)

WebUI.takeScreenshot()

WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/CargaPallets/BUTTON_IniciarCaja'))

def msgAlertaIniciarCaja = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlertaIniciarCaja, 'Proceso terminado con éxito', null)

WebUI.takeScreenshot()

WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/CargaPallets/CHECK_ArchivoCsv'))

WebUI.uploadFile(findTestObject('Object Repository/EQUIPOS//CambioMasivoUbicacion/INPUT_CargaCsv'), archivoPallets)

WebUI.click(findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'), GlobalVariable.TIME_OUT + 20, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Finalizo proceso de validaciones con éxito', null)

WebUI.click(findTestObject('Object Repository/EQUIPOS/CargaPallets/BUTTON_FinalizarCaja'))

def msgAlertaCierreCaja = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlertaCierreCaja, 'esta abierto, se continuará trabajando en el actual', null)

WebUI.takeScreenshot()

WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/CargaPallets/BUTTON_CerrarCaja'))

//Colocar Pasos Confirmación

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlertaIniciarCaja, 'Proceso terminado con éxito', null)

WebUI.takeScreenshot()