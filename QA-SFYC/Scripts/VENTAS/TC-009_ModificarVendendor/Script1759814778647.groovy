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

int randomNumberCuadrilla = (int)(Math.random() * 6)

String formattedNumberCuadrilla = String.format('%04d', randomNumberCuadrilla)

// Se selecciona la sucursal 41 en base al caso de prueba
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/OBJETOS_GLOBALES/SELECT_Sucursal'), '41')

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Catálogos', 'Vendedores', '')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/VENTAS/Vendedores/SELECT_CompaniaNomina'), 'SAOSA')

WebUI.setText(findTestObject('Object Repository/VENTAS/Vendedores/INPUT_NumeroEmpleado'), numeroEmpleado)

WebUI.delay(1)

WebUI.sendKeys(findTestObject('Object Repository/VENTAS/Vendedores/INPUT_NumeroEmpleado'), Keys.chord(Keys.ENTER))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/VENTAS/Vendedores/SELECT_TipoCuadrilla'), 'Punto de Venta')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/VENTAS/Vendedores/SELECT_Cuadrilla'), formattedNumberCuadrilla)

WebUI.click(findTestObject('Object Repository/VENTAS/Vendedores/BUTTON_Aceptar'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Proceso finalizó correctamente', '')