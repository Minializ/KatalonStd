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

def randomInterior = new Random().nextInt(1000) + 1

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Cajero', '', '')

def getSucursal = WebUI.getAttribute(findTestObject('Object Repository/SERVICIOS/Cajero/IngresarCajero/SELECT_Sucursal'), 
    'ng-reflect-value')

WebUI.setMaskedText(findTestObject('Object Repository/SERVICIOS/Cajero/INPUT_NumeroSuscriptor'), '0' + numeroSuscriptor)

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Cajero/IngresarCajero/SELECT_Usuario'), 
    'IASALAZARM')

//Habilitar solo si se necesita usar sucursal diferente a 41
//CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Cajero/IngresarCajero/SELECT_Sucursal'), '01')
CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Cajero/IngresarCajero/SELECT_Cis'), 
    '001')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/SERVICIOS/Cajero/IngresarCajero/SELECT_Cajero'), 
    '3323')

WebUI.setEncryptedText(findTestObject('Object Repository/SERVICIOS/Cajero/IngresarCajero/INPUT_Contrasenia'), 'PYE7Gvk29zc=')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/IngresarCajero/BUTTON_Aceptar'))

for (int x = 1; x <= 2; x++) {
    if (WebUI.waitForElementPresent(findTestObject('Object Repository/SERVICIOS/Cajero/IngresarCajero/SPAN_MensajeSuscriptor'), 
        5, FailureHandling.OPTIONAL)) {
        WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))
    }
}

WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/BUTTON_Calcular'))

def totalPagar = WebUI.getText(findTestObject('Object Repository/SERVICIOS/Cajero/LABEL_TotalPagar'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.contains'(totalPagar, '0.00', null)

WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/TAB_CambioDomicilio'))

if (WebUI.verifyTextPresent('El suscriptor no tiene adeudo pendiente. Puede continuar con el cambio de domicilio.', false, 
    FailureHandling.OPTIONAL)) {
    WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/BUTTON_Siguiente'))
}

def colonia = (WebUI.getText(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/SPAN_Colonia')).split(':')[
1]).trim()

def domicilio = WebUI.getText(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/SPAN_Domicilio'))

// Se usa una expresion regular en donde '\s' ignora espacios despues del texto -> (.*?) captura todo lo que esta despues y \s+# limita la busqueda.
def matcher = domicilio =~ 'Domicilio:\\s+(.*?)\\s+#'

def calle = matcher ? (matcher[0])[1] : null

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/SELECT_Colonia'), 
    colonia.toUpperCase())

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByIndex'(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/SELECT_Calle'), 
    '1')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByIndex'(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/SELECT_EntreCalle'), 
    '1')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByIndex'(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/SELECT_EntreCalle2'), 
    '1')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/INPUT_NumExterior'), randomInterior.toString())

WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/RADIO_Sur'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/BUTTON_Siguiente'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.waitForElementPresent(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 60)

def msjAlerta = WebUI.getText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

WebUI.takeScreenshot('AlertaCambioDomicilio.jpg')

WebUI.verifyMatch(msjAlerta, '.*Datos de domicilio guardado.*', true, FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))

if (WebUI.verifyElementPresent(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/BUTTON_AplicarPromocion'), 
    10, FailureHandling.OPTIONAL)) {
    WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/BUTTON_AplicarPromocion'))
}

WebUI.verifyElementPresent(findTestObject('Object Repository/SERVICIOS/Cajero/AplicarPago/BUTTON_Efectivo'), 30)

WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/AplicarPago/BUTTON_Efectivo'))

WebUI.setMaskedText(findTestObject('Object Repository/SERVICIOS/Cajero/AplicarPago/INPUT_Efectivo'), '300.00')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/AplicarPago/BUTTON_Imprimir'))

WebUI.waitForElementPresent(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 240)

WebUI.switchToWindowIndex(1)

WebUI.takeScreenshot('ImprimirPago.jpg')

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

String script = 'document.querySelector(".dx-overlay-content.dx-loadpanel-content")?.remove();'

WebUI.executeJavaScript(script, null, FailureHandling.OPTIONAL)

def msjAlertaPago = WebUI.getText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

WebUI.takeScreenshot('AlertaPago.jpg')

WebUI.verifyMatch(msjAlertaPago, '.*Pago agregado de forma correcta.*', true, FailureHandling.STOP_ON_FAILURE)

WebUI.enhancedClick(findTestObject('Object Repository/SERVICIOS/Cajero/AplicarPago/BUTTON_Aplicar'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.waitForElementPresent(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 60)

def msjAlertaAplicarPago = WebUI.getText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

WebUI.takeScreenshot('AlertaAplicarPago.jpg')

WebUI.verifyMatch(msjAlertaAplicarPago, '.*ha sido aplicado correctamente.*', true, FailureHandling.STOP_ON_FAILURE)