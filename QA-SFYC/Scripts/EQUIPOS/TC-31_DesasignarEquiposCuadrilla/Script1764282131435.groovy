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

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Asignar Equipos a Cuadrilla', '')

WebUI.verifyElementPresent(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/CHECK_Ubicacion'), 10, FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/CHECK_Ubicacion'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/CHECK_ValidarEstado'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/CHECK_Inventario'))

WebUI.verifyElementPresent(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/TR_Equipo'), 60)

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/SELECT_TipoEquipo'), 
    '4')

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/TR_Equipo'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/TR_Registro'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/BUTTON_Desasignar'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/BUTTON_Enviar'))

WebUI.verifyElementPresent(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 10)

def mensajeAlerta = WebUI.getText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(mensajeAlerta, 'Equipo desasignado correctamente, ubicación nueva:', 
    null)

WebUI.takeScreenshot()

WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/CHECK_Exportar'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/BUTTON_Imprimir'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/AsignarEquiposCuadrilla/BUTTON_ImprimirReporte'))

WebUI.switchToWindowIndex(1)

WebUI.verifyElementNotPresent(findTestObject('Object Repository/EQUIPOS/Reportes/ReporteAuxiliarEquipos/VentanaPDF/SPAN_ErrorPDF'), 
    3, FailureHandling.STOP_ON_FAILURE)