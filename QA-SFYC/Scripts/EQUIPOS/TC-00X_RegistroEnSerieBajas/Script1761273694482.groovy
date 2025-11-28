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
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable

import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement

String archivoConsulta = RunConfiguration.getProjectDir() + "/Data Files/Equipos/Consulta.csv"

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Registro en Serie', 'Bajas')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/BajasEquipos/SELECT_TipoEquipo'), '04')

WebUI.click(findTestObject('Object Repository/EQUIPOS/BajasEquipos/CHECK_MotivoBaja'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/EQUIPOS/BajasEquipos/SELECT_MotivoBaja'), 'Irreparable')

WebUI.click(findTestObject('Object Repository/EQUIPOS/BajasEquipos/CHECK_CargaArchivoCSV'))

WebUI.uploadFile(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/INPUT_CargaArchivos'), archivoConsulta)

WebUI.click(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/BTN_Aceptar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

// Captura el texto del mensaje de alerta
def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

// Verifica que el mensaje contenga el texto esperado para una devolución exitosa
CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Finalizo proceso con éxito', null)