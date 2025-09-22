/**
 * AUTOR: Ian Romero
 * FECHA CREACIÓN: 22/09/2024
 * DESCRIPCIÓN GENERAL:
 * Este caso de prueba automatiza el proceso de asignación de una serie SIM a un suscriptor en el sistema de contratos.
 * El flujo cubre: navegación al servicio de telefonía móvil, apertura del formulario de asignación de SIM, ingreso de datos,
 * validación de mensaje de éxito y verificación de orden de activación. Además, actualiza el estado del suscriptor en el
 * dataset según el resultado del proceso (éxito, error, o sin permisos).
 * REQUISITOS: Depurar y actualizar el excel "Suscriptores.xlsx" y validar que las series y suscriptores puedan ser usados por el
 * usuario APSANTILLAV
 */

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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

//Se manda a llamar el caso de prueba de consulta de suscriptor, el cual necesita el parametro de Id para realizar la consulta
WebUI.callTestCase(findTestCase('AGENDA/ConsultarSuscriptor'), [('numeroSuscriptor') : numeroSuscriptor], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_Contratos'))

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_OtrosContratos'))

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TR_TipoServicio'))

WebUI.mouseOver(findTestObject('Object Repository/AGENDA/Contratos/BUTTON_VerOpciones'))

//Se valida que la opción e asignar SIM este disponible
boolean asignarSerieDisponible = WebUI.verifyElementClickable(findTestObject('Object Repository/AGENDA/Contratos/SPAN_AsignarSerieSIM'), FailureHandling.OPTIONAL)

//Si esta disponible, procede a realizar el proceso de asignación de SIM
if (asignarSerieDisponible) {
	WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/SPAN_AsignarSerieSIM'))

	//Manda a llamar una funcion personalizada para seleccionar el CIS
	CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionWithFilter'(findTestObject('AGENDA/Contratos/FormAsignarSerieSIM/SELECT_Cis'),
			cis)

	WebUI.setText(findTestObject('Object Repository/AGENDA/Contratos/FormAsignarSerieSIM/INPUT_SerieSIM'), serieSIM)

	WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/FormAsignarSerieSIM/BUTTON_Confirmar'))

	//Valida que se muestre una alerta al realizar la asignacion de SIM
	WebUI.verifyElementPresent(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 30, FailureHandling.STOP_ON_FAILURE)

	//Guarda el mensaje que muestra la alerta
	def mensajeAlerta = WebUI.getText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

	//Si el mensaje de alerta es exitoso procede a actualziar el estatus del regsitro en el Data File
	if (mensajeAlerta.contains('Proceso finalizado con éxito')) {
		CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'('Data Files/AGENDA/Suscriptores.xlsx',
				'Hoja1', numeroSuscriptor, 'Asignado Con Exito')
	} else {
		//Caso contrario que el mensaje sea de error o warning, actualiza el estatus del testcase a Warning y actualiza el registro en el DataSet
		CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'('Data Files/AGENDA/Suscriptores.xlsx',
				'Hoja1', numeroSuscriptor, 'SIM no Registrada en BD')

		KeywordUtil.markWarning(('\n\nErrores o Problemas con la asignación de la serie SIM: ' + mensajeAlerta) + '\n\n')
	}

	//Metodo personalizado que cierra ventanas o alertas
	CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()

	WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_Contratos'))

	WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_AprovisionamientoMVNO'))

	//Valida la presencia del registro "Activacion de SIM" en estado "POR"
	WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/Contratos/TR_ActivacionSIM'), 1)
} else {
	//Si no esta disponible, manda un mensaje de alerta al reporte indicando que no se tienen permisos para asignar la SIM y actualiza el estatus del registro del Data File
	CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'('Data Files/AGENDA/Suscriptores.xlsx',
			'Hoja1', numeroSuscriptor, 'Sin Permisos para Asignar SIM')

	KeywordUtil.markWarning('El usuario no tiene permisos para asignar serie SIM')
}
