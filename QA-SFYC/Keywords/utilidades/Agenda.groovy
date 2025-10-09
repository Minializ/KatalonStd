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

public class Agenda {
	@Keyword
	def consultarSusciptorPorId(String numeroSuscriptor) {

		FuncionesAuxiliares funcAux = new FuncionesAuxiliares()

		// Verifica si la pestaña de "Contratos" está abierta y la cierra para evitar llegar al limite de solicitudes abiertas.
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/Contratos/BUTTON_CerrarTabContratos'), 1, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/BUTTON_CerrarTabContratos'))
		}

		// Se valida que si estamos en el Dashboard de una consulta de cliente, se hace clic en "+ Consultar"
		// y se limpia el campo de suscriptor para preparar una nueva consulta.
		if (WebUI.verifyTextPresent('Atención al cliente', false, FailureHandling.OPTIONAL)) {

			def suscriptorActual = WebUI.getText(findTestObject('Object Repository/AGENDA/PaginaInicio/LABEL_NumeroSuscriptor'))

			if(!suscriptorActual.equals(numeroSuscriptor)) {
				WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/BUTTON_Consultar'))

				WebUI.clearText(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_Suscriptor'))

				WebUI.setText(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_Suscriptor'), numeroSuscriptor)

				WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/BUTTON_Consultar'))
			}
		}else if(WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_Suscriptor'), 1, FailureHandling.OPTIONAL)){
			WebUI.setText(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_Suscriptor'), numeroSuscriptor)

			WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/BUTTON_Consultar'))
		}


		// Si aparece un mensaje de alerta relacionado con el suscriptor al momento de consultar, se cierra el mensaje para poder continuar.
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/AlertaSuscriptor/DIV_MensajeTituloSuscriptor'),
				GlobalVariable.TIME_OUT_CONTENT, FailureHandling.OPTIONAL)) {
			//Metodo personalizado que cierra ventanas o alertas
			funcAux.limpiarVista()
		}

		WebUI.takeScreenshot()
	}

	@Keyword
	def consultarSusciptorPorNombre(String noSucursal, String apellidoPaterno, String apellidoMaterno, String nombre) {

		FuncionesAuxiliares funcAux = new FuncionesAuxiliares()

		// Verifica si la pestaña de "Contratos" está abierta y la cierra para evitar llegar al limite de solicitudes abiertas.
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/Contratos/BUTTON_CerrarTabContratos'), 1, FailureHandling.OPTIONAL)) {
			WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/BUTTON_CerrarTabContratos'))
		}

		// Se valida que si estamos en el Dashboard de una consulta de cliente, se hace clic en "+ Consultar"
		// y se limpia el campo de suscriptor para preparar una nueva consulta.
		if (WebUI.verifyTextPresent('Atención al cliente', false, FailureHandling.OPTIONAL)) {

			WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/BUTTON_Consultar'))

			WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/SPAN_NombreApellidos'))

			selectOptionById(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/SELECT_Sucursal'), noSucursal)

			WebUI.setText(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_ApellidoPaterno'), apellidoPaterno)

			WebUI.setText(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_ApellidoMaterno'), apellidoMaterno)

			WebUI.setText(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_Nombre'), nombre)

			WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/BUTTON_Consultar'))

			WebUI.doubleClick(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'))
			
		}else if(WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_Suscriptor'), 1, FailureHandling.OPTIONAL)){
			WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/SPAN_NombreApellidos'))

			selectOptionById(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/SELECT_Sucursal'), noSucursal)

			WebUI.setText(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_ApellidoPaterno'), apellidoPaterno)

			WebUI.setText(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_ApellidoMaterno'), apellidoMaterno)

			WebUI.setText(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/INPUT_Nombre'), nombre)

			WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/BUTTON_Consultar'))

			WebUI.click(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'))

			WebUI.doubleClick(findTestObject('Object Repository/AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'))
		}

		// Si aparece un mensaje de alerta relacionado con el suscriptor al momento de consultar, se cierra el mensaje para poder continuar.
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/AlertaSuscriptor/DIV_MensajeTituloSuscriptor'),
				GlobalVariable.TIME_OUT_CONTENT, FailureHandling.OPTIONAL)) {
			//Metodo personalizado que cierra ventanas o alertas
			funcAux.limpiarVista()
		}

		WebUI.takeScreenshot()
	}

	@Keyword
	//Metodo que nos ayuda a seleccionar opciones de los componentes select web que contienen tablas y filtros
	def selectOptionByName(TestObject objetcSelect, String opcion) {

		WebUI.waitForElementPresent(objetcSelect, GlobalVariable.TIME_OUT_CONTENT)

		WebUI.click(objetcSelect)

		WebUI.setText(findTestObject('Object Repository/OBJETOS_GLOBALES/INPUT_DescripcionFiltro'), opcion)

		WebUI.delay(1)

		WebUI.enhancedClick(findTestObject('Object Repository/MATERIALES/Entregas/TD_Registro-Dyn', [('opcion') : opcion]))
	}

	@Keyword
	//Metodo que nos ayuda a seleccionar opciones de los componentes select web que contienen tablas y filtros
	def selectOptionById(TestObject objetcSelect, String id) {

		WebUI.waitForElementPresent(objetcSelect, GlobalVariable.TIME_OUT_CONTENT)

		WebUI.click(objetcSelect)

		WebUI.setText(findTestObject('Object Repository/OBJETOS_GLOBALES/INPUT_IdFiltro'), id)

		WebUI.delay(1)

		WebUI.enhancedClick(findTestObject('Object Repository/MATERIALES/Entregas/TD_Registro-Dyn', [('opcion') : id]))
	}
}
