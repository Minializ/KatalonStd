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

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/EQUIPOS/SELECT_Reporte'), 'Listado de equipos')

WebUI.click(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/TAB_Clasificacion'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/CHECK_Identificador'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/TAB_Seleccion'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/CHECK_TipoEquipo'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/SELECT_TipoEquipo'), '02')

WebUI.click(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/CHECK_Ubicacion'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/SELECT_UbicacionDesde'), 'ALM')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/SELECT_UbicacionHasta'), 'ALM')

WebUI.click(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/TAB_MasOpciones'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/CHECK_Serie'))

WebUI.setText(findTestObject('Object Repository/EQUIPOS/Reportes/ListadoEquipos/INPUT_Serie'), '331922122296102866')

WebUI.click(findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'))

WebUI.switchToWindowIndex(1)

WebUI.verifyElementNotPresent(findTestObject('Object Repository/EQUIPOS/Reportes/ReporteAuxiliarEquipos/VentanaPDF/SPAN_ErrorPDF'), 3, FailureHandling.STOP_ON_FAILURE)

WebUI.waitForElementNotPresent(findTestObject('Object Repository/EQUIPOS/Reportes/ReporteAuxiliarEquipos/VentanaPDF/SPAN_LoadingPDF'), 180)