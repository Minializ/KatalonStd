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

String archivoConsulta = RunConfiguration.getProjectDir() + "/Data Files/Equipos/Consulta.csv"

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Consulta Ubicación de Equipos', '')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/SELECT_CampoFiltrar'), 'Serie')

WebUI.uploadFile(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/INPUT_CargaArchivos'), archivoConsulta)

WebUI.click(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/BTN_Aceptar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebElement table = DriverFactory.getWebDriver().findElement(By.xpath('//table[@class="dx-datagrid-table dx-pointer-events-none dx-datagrid-table-fixed"]'))

List<WebElement> rows = table.findElements(By.xpath('//table[@class="dx-datagrid-table dx-pointer-events-none dx-datagrid-table-fixed"]//tr'))

def totalRegistros = rows.size()

//Valida que el filtrado tenga datos, en caso de que sea "0" falla, tiene que ser mayor a "0"
WebUI.verifyGreaterThan(totalRegistros, 0,  FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/CHECK_Agrupar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

def textoAgrupacionActual = WebUI.getText(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/TD_Agrupado'))

int totalEsperado = totalRegistros - 1

//Valida que los registros se hayan agrupado por medio del texto que contiene la fila
WebUI.verifyMatch(textoAgrupacionActual, ".*Guadalajara\\s*\\(${totalEsperado}\\s+equipos\\).*", true, FailureHandling.CONTINUE_ON_FAILURE)

//Descarga la tabla en excel y espera 3 segundos
WebUI.click(findTestObject('Object Repository/EQUIPOS/ConsultaUbicacionEquipos/BTN_ExportarExcel'))

WebUI.delay(3)