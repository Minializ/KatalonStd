package utilidades

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.Keys

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

public class Materiales {


	@Keyword
	def ingresarMenu(String menu, String subMenu) {

		if(!WebUI.verifyElementPresent(findTestObject('Object Repository/MATERIALES/Entregas/Dashboard/BUTTON_NuevaEntregaManual'), 3, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/SPAN_OpcionMenu-Dyn', [('opcionMenu') : menu]))

			if(!subMenu.blank) {
				WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/SPAN_OpcionMenu-Dyn', [('opcionMenu') : subMenu]))
			}
		}
	}

	@Keyword
	//Metodo que nos ayuda a seleccionar opciones de los componentes select web que contienen tablas y filtros
	def selectOption(TestObject objetcSelect, String opcion) {

		WebUI.waitForElementPresent(objetcSelect, GlobalVariable.TIME_OUT_CONTENT)

		WebUI.click(objetcSelect)

		WebUI.delay(1)

		WebUI.sendKeys(findTestObject('Object Repository/OBJETOS_GLOBALES/INPUT_Seleccione'), opcion)

		WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/SPAN_OpcionMenu-Dyn', [('opcionMenu') : opcion]))
	}


	@Keyword
	//Metodo que nos ayuda a seleccionar opciones de los componentes select web que contienen tablas y filtros
	def selectOptionTab(TestObject objetcSelect, String opcion) {

		WebUI.waitForElementPresent(objetcSelect, GlobalVariable.TIME_OUT_CONTENT)

		WebUI.click(objetcSelect)

		WebUI.delay(1)

		WebUI.sendKeys(findTestObject('Object Repository/OBJETOS_GLOBALES/INPUT_Seleccione'), opcion)

		WebUI.delay(2)
		
		WebUI.sendKeys(findTestObject('Object Repository/OBJETOS_GLOBALES/INPUT_Seleccione'), Keys.chord(Keys.TAB))
	}
		@Keyword
	//Metodo que nos ayuda a seleccionar opciones de los componentes select web que contienen tablas y filtros
	def selectOptionTable(TestObject objetcSelect, String opcion) {

		WebUI.waitForElementPresent(objetcSelect, GlobalVariable.TIME_OUT_CONTENT)

		WebUI.click(objetcSelect)

		WebUI.delay(1)

		WebUI.sendKeys(findTestObject('Object Repository/OBJETOS_GLOBALES/INPUT_DatosFila-Dyn'), opcion)

		WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/SPAN_OpcionMenu-Dyn', [('opcionMenu') : opcion]))
	}

	@Keyword
	def consultarEntrega() {
	}
}
