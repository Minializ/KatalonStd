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


final String ARCHIVO_DATOS = 'Data Files/AGENDA/Suscriptores.xlsx'
final String HOJA_DATOS = 'Hoja1'

boolean excepcionSIM = false

// Login
CustomKeywords.'utilidades.FuncionesAuxiliares.login'("APSANTILLAV", "xmwPVkGXOzg/mjMgod9mdJXPoCY3ZmJvZjpNUH1MJBC5xTLD1ro+jQ==")

// Consulta del suscriptor
WebUI.callTestCase(findTestCase('AGENDA/ConsultarSuscriptor'), [('numeroSuscriptor') : numeroSuscriptor], FailureHandling.STOP_ON_FAILURE)

// Navegación hacia opción de asignar SIM
WebUI.click(findTestObject('AGENDA/Contratos/TAB_DYN', [('opcion') : 'Contratos']))

WebUI.click(findTestObject('AGENDA/Contratos/TAB_DYN', [('opcion') : 'Otros']))

WebUI.click(findTestObject('AGENDA/Contratos/TR_TipoServicio'))

WebUI.mouseOver(findTestObject('AGENDA/Contratos/BUTTON_VerOpciones'))

boolean asignarSerieDisponible = WebUI.verifyElementClickable(findTestObject('AGENDA/Contratos/SPAN_AsignarSerieSIM'), FailureHandling.OPTIONAL)

if (!asignarSerieDisponible) {
	CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'(ARCHIVO_DATOS, HOJA_DATOS, numeroSuscriptor, 'Sin Permisos para Asignar SIM')
	KeywordUtil.markFailed('\n\n El usuario no tiene permisos para asignar serie SIM \n\n')
	return
}

// Proceso de asignación de SIM
WebUI.click(findTestObject('AGENDA/Contratos/SPAN_AsignarSerieSIM'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionWithFilter'(findTestObject('AGENDA/Contratos/FormAsignarSerieSIM/SELECT_Cis'), cis)

WebUI.setText(findTestObject('AGENDA/Contratos/FormAsignarSerieSIM/INPUT_SerieSIM'), serieSIM)

WebUI.click(findTestObject('AGENDA/Contratos/FormAsignarSerieSIM/BUTTON_Confirmar'))

// Validar mensaje de alerta
WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 30, FailureHandling.STOP_ON_FAILURE)

def mensajeAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

if (mensajeAlerta.contains('Proceso finalizado con éxito')) {
	CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'(ARCHIVO_DATOS, HOJA_DATOS, numeroSuscriptor, 'Asignado Con Exito')
	excepcionSIM = true
} else {
	CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'(ARCHIVO_DATOS, HOJA_DATOS, numeroSuscriptor, 'SIM no Registrada en BD')
	excepcionSIM = true
	KeywordUtil.markFailed("\n\nErrores o Problemas con la asignación de la serie SIM: ${mensajeAlerta}\n\n")
}

// Validar orden de activación si no hubo excepción
if (!excepcionSIM) {
	CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()

	WebUI.click(findTestObject('AGENDA/Contratos/TAB_DYN', [('opcion') : 'Contratos']))
	
	WebUI.click(findTestObject('AGENDA/Contratos/TAB_DYN', [('opcion') : 'Aprovisionamiento MVNO']))

	WebUI.verifyElementPresent(findTestObject('AGENDA/Contratos/TR_ActivacionSIM'), 1)
}