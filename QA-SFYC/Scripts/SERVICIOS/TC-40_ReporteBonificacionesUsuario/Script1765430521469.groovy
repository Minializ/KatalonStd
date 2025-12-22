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

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Reportes', '', '')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/SELECT_Reporte'), 'Bonificaciones por Usuario')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectDate'(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/INPUT_Desde'), 'sep', '1')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectDate'(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/INPUT_Hasta'), '', '13')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/CHECK_CisTodos'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/SELECT_Cis'), '1')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/TAB_MasOpciones'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/CHECK_Conceptos'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/SELECT_Conceptos'), '1')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/CHECK_TiposServicios'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/SELECT_TiposServicios'), '4')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/TAB_MasOpciones2'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/CHECK_Promociones'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Reportes/BonificacionesUsuario/SELECT_Promociones'), '1051')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Reportes/BUTTON_Aceptar'))

WebUI.switchToWindowIndex(1)

WebUI.waitForElementNotPresent(findTestObject('Object Repository/EQUIPOS/Reportes/ReporteAuxiliarEquipos/VentanaPDF/SPAN_LoadingPDF'), 180)

WebUI.verifyElementNotPresent(findTestObject('Object Repository/EQUIPOS/Reportes/ReporteAuxiliarEquipos/VentanaPDF/SPAN_ErrorPDF'), 3, FailureHandling.STOP_ON_FAILURE)