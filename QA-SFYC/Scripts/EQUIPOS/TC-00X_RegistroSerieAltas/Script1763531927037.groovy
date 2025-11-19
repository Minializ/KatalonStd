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

String archivoAltas = RunConfiguration.getProjectDir() + "/Data Files/Equipos/SerieAltas.csv"

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Registro en Serie', 'Altas')

WebUI.click(findTestObject('Object Repository/EQUIPOS/AltasEquipos/BUTTON_Nuevo'))

CustomKeywords.'utilidades.Ventas.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/BajasEquipos/SELECT_TipoEquipo'), '02')

WebUI.click(findTestObject('Object Repository/EQUIPOS/AltasEquipos/CHECK_Precarga'))

WebUI.uploadFile(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/INPUT_CargaArchivos'), archivoAltas)

WebUI.click(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/BTN_Aceptar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlertaCargaDoc = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

WebUI.takeScreenshot()

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlertaCargaDoc, 'Finalizo proceso con éxito', null)

WebUI.click(findTestObject('Object Repository/EQUIPOS/AltasEquipos/BUTTON_Cancelar'))

CustomKeywords.'utilidades.Ventas.selectOptionByIndex'(findTestObject('Object Repository/EQUIPOS/AltasEquipos/SELECT_Orden'), '1')

CustomKeywords.'utilidades.Ventas.selectOptionByIndex'(findTestObject('Object Repository/EQUIPOS/AltasEquipos/SELECT_Factura'), '1')

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

def totalEquipos = WebUI.getText(findTestObject('Object Repository/EQUIPOS/AltasEquipos/SPAN_TotalEquipos'))

WebUI.verifyMatch(totalEquipos, 'Total: 2', false)

WebUI.takeScreenshot()

WebUI.click(findTestObject('Object Repository/EQUIPOS/AltasEquipos/BUTTON_AceptarEquipos'))

def msgAlertaAceptarEquipos = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

WebUI.takeScreenshot()

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlertaAceptarEquipos, 'Finalizo proceso con éxito', null)