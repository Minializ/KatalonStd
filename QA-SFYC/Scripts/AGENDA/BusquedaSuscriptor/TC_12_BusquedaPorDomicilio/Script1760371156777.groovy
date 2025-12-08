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

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_domicilio/SPAN_Domicilio'))

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_domicilio/SELECT_SucursalDomicilio'))

WebUI.delay(0.5)

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_domicilio/INPUT_DescripcinSucDomicilio'))

WebUI.delay(0.5)

WebUI.setText(findTestObject('AGENDA/BusquedaContrato/busqueda_por_domicilio/INPUT_DescripcinSucDomicilio'), 'Tepic')

WebUI.delay(1)

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_nombre/td_sucursal'))

WebUI.delay(2)

WebUI.setText(findTestObject('AGENDA/BusquedaContrato/busqueda_por_domicilio/input__street'), 'lopez mateos')

WebUI.setText(findTestObject('AGENDA/BusquedaContrato/busqueda_por_domicilio/INPUT__UutdoorNumber'), '208')

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_domicilio/BUTTON_Consultar'))

WebUI.delay(1)

WebUI.doubleClick(findTestObject('AGENDA/BusquedaContrato/busqueda_por_domicilio/td_suscriptor_contrato'))

WebUI.delay(5)

WebUI.closeBrowser()

