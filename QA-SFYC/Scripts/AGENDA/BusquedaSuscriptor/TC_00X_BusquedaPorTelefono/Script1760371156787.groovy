import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_telefono/SPAN_OpcionTelefono'))

// Click para abrir el dropdown
WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_telefono/select_sucursal'))

// Esperar un momento a que aparezca el input de filtro
WebUI.delay(1)

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_telefono/INPUT_DescripcionSucursal'))

// Ingresar la sucursal
WebUI.setText(findTestObject('AGENDA/BusquedaContrato/busqueda_por_telefono/INPUT_DescripcionSucursal'), 'Tepic')

// Esperar un momento a que aparezca la fila filtrada
WebUI.delay(1)

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_nombre/td_sucursal'))

WebUI.delay(4)

WebUI.setText(findTestObject('AGENDA/BusquedaContrato/busqueda_por_telefono/input__phone'), '3317091163')

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_nombre/BUTTON_Consultar'))

WebUI.delay(1)

WebUI.doubleClick(findTestObject('AGENDA/BusquedaContrato/busqueda_por_nombre/TD_SuscriptorContrato'))

WebUI.delay(3)

WebUI.closeBrowser()

