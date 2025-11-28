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

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_folio_llamada/SPAN_OpcionLlamada'))

// Click para abrir el dropdown
WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_folio_llamada/input__sucursal'))

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_folio_llamada/TD_DescripcinSucursal'))

// Ingresar la sucursal
WebUI.setText(findTestObject('AGENDA/BusquedaContrato/busqueda_folio_llamada/TD_DescripcinSucursal'), 
    'Tepic')

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_folio_llamada/INPUT_TipoFolio'))

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_folio_llamada/ITEM_FolioLlamada'))

WebUI.setText(findTestObject('AGENDA/BusquedaContrato/busqueda_folio_llamada/input__folio'), '175094408')

WebUI.click(findTestObject('AGENDA/BusquedaContrato/busqueda_por_nombre/BUTTON_Consultar'))

WebUI.delay(1)

WebUI.doubleClick(findTestObject('AGENDA/BusquedaContrato/busqueda_por_nombre/TD_SuscriptorContrato'))

WebUI.delay(3)

WebUI.closeBrowser()

