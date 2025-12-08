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

String archivoCMU = RunConfiguration.getProjectDir() + "/Data Files/Equipos/CMU2.csv"

WebUI.delay(5)

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Cambio Masivo de Ubicación', '')

WebUI.click(findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/CHECK_CambioEstado'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_TipoEquipo'), '19')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_UbicacionOrigen'), 'LOG')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_UbicacionDestino'), 'ALM')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_EstadoOrigen'), 'DÑA')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/CambioMasivoUbicacion/SELECT_EstadoDestino'), 'F04')

WebUI.uploadFile(findTestObject('Object Repository/EQUIPOS//CambioMasivoUbicacion/INPUT_CargaCsv'), archivoCMU)

WebUI.click(findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'), GlobalVariable.TIME_OUT + 10, FailureHandling.STOP_ON_FAILURE)

// Captura el texto del mensaje de alerta
def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

// Verifica que el mensaje contenga el texto esperado para una devolución exitosa
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Finalizo proceso de validaciones con éxito', null)