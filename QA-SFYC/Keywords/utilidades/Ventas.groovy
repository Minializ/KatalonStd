package utilidades

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class Ventas {
	@Keyword
	//Metodo que nos ayuda a seleccionar opciones de los componentes select web que contienen tablas y filtros
	def selectOptionByName(TestObject objetcSelect, String opcion) {

		WebUI.waitForElementPresent(objetcSelect, GlobalVariable.TIME_OUT_CONTENT)

		WebUI.click(objetcSelect)

		WebUI.delay(1)

		WebUI.sendKeys(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/INPUT_SelectFiltroDescripcion'), opcion)

		WebUI.delay(1)

		WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/SPAN_OpcionFiltro', [('opcionMenu') : opcion]))
	}
	
	@Keyword
	//Metodo que nos ayuda a seleccionar opciones de los componentes select web que contienen tablas y filtros
	def selectOptionByIndex(TestObject objetcSelect, String index) {

		WebUI.waitForElementPresent(objetcSelect, GlobalVariable.TIME_OUT_CONTENT)

		WebUI.click(objetcSelect)

		WebUI.delay(1)

		WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/SPAN_Option', ['idx' : index]))
	}

	@Keyword
	//Metodo que nos ayuda a seleccionar opciones de los componentes select web que contienen tablas y filtros
	def selectOptionByCode(TestObject objetcSelect, String codigo) {

		WebUI.waitForElementPresent(objetcSelect, GlobalVariable.TIME_OUT_CONTENT)

		WebUI.click(objetcSelect)

		WebUI.delay(1)

		WebUI.sendKeys(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/INPUT_SelectFiltroCode'), codigo)

		WebUI.delay(2)

		WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/SPAN_OpcionFiltro', [('opcionMenu') : codigo]))
	}
}