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

int guia = ((Math.random() * 90000) as int) + 10000

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Solicitud de Traspaso', '')

/* Registro Traspaso */
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Agregar'))

CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaDestino'), 
    'GUADALAJARA')

CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_TipoEquipo'), 
    'CABLE MODEMS')

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'), 'CREACIÓN TRASPASO KATALON STUDIO')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlerta, 'Finalizo proceso con éxito', null)

/* Aceptar Origen */
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_AceptarOrigen'))

WebUI.verifyElementPresent(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaOrigen'), 10)

CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_PlazaOrigen'), 
    'ACATLAN')

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'), 'ASIGNACIÓN ORIGEN KATALON STUDIO')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlertaAsgnOrigen = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlertaAsgnOrigen, 'Finalizo proceso con éxito', 
    null)

/* Aceptar Cantidades */
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_AceptarDestino'))

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'), 'ACEPTAR MEDIDAS KATALON STUDIO')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlertaConfMedidas = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlertaConfMedidas, 'Finalizo proceso con éxito', 
    null)

/* Aceptar Destino */
WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/TR_Solicitud'))

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_AceptarDestino'))

WebUI.verifyElementPresent(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_Transportistas'), 10)

CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/SELECT_Transportistas'), 
    'GALEX')

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_GuiaEnvio'), guia.toString())

WebUI.setText(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/INPUT_Observaciones'), 'ASIGNACIÓN TRANSPORTISTAS KATALON STUDIO')

WebUI.click(findTestObject('Object Repository/EQUIPOS/SolicitudTraspaso/BTN_Aprobar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlertaAsgnTransp = WebUI.getText(findTestObject('OBJETOS_GLOBALES/TOAST_Mensaje'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(msgAlertaAsgnTransp, 'Finalizo proceso con éxito', 
    null)