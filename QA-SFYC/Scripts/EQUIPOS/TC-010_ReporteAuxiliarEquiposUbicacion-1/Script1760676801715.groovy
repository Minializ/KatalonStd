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

// Se selecciona la sucursal que requiere el vendedor
CustomKeywords.'utilidades.Ventas.selectOptionByCode'(findTestObject('Object Repository/OBJETOS_GLOBALES/SELECT_Sucursal'), 
    sucursal)

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Reportes', '', '')

CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/EQUIPOS/SELECT_Reporte'), 'Auxiliar de equipos por ubicación')

WebUI.click(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Clasificacion/TAB_Clasificacion'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Clasificacion/RADIO_Identificador'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Seleccion/TAB_Seleccion'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Seleccion/CHECK_TiposEquipo'))

CustomKeywords.'utilidades.Ventas.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Seleccion/SELECT_TiposEquipo'), 
    '002')

WebUI.click(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Seleccion/CHECK_ModelosEquipo'))

WebUI.delay(1)

CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Seleccion/SELECT_ModelosEquipo'), 
    'T504')

WebUI.click(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Seleccion/CHECK_UbicacionDesde'))

CustomKeywords.'utilidades.Ventas.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Seleccion/SELECT_UbicacionDesde'), 
    'A01')

CustomKeywords.'utilidades.Ventas.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/Seleccion/SELECT_UbicacionHasta'), 
    'CTL')

WebUI.click(findTestObject('Object Repository/EQUIPOS/BUTTON_Aceptar'))

WebUI.switchToWindowIndex(1)

WebUI.verifyElementNotPresent(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/VentanaPDF/SPAN_ErrorPDF'), 3, FailureHandling.STOP_ON_FAILURE)

WebUI.waitForElementNotPresent(findTestObject('Object Repository/EQUIPOS/ReporteAuxiliarEquipos/VentanaPDF/SPAN_LoadingPDF'), 180)