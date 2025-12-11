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

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Operaciones', 'Papeletas', '')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/BUTTON_Nuevo'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Papeletas/SELECT_Origen'), 
    'DIS')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Papeletas/SELECT_NombreOperador'), 
    '108525')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Papeletas/SELECT_Poblacion'), 
    '41')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/CHECK_AsignacionAutomatica'))

WebUI.setMaskedText(findTestObject('Object Repository/SERVICIOS/Papeletas/INPUT_Suscriptor'), '0000413')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/BUTTON_TomarDatosSuscriptor'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/TAB_Domicilio'))

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Papeletas/INPUT_TelefonoCasa'), '3334569887')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Papeletas/INPUT_TelefonoMovil'), '3321301566')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Papeletas/INPUT_TelefonoOficina'), '3334567766')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/TAB_Informacion'))

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Papeletas/SELECT_Medio'), 
    '1')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/SELECT_VentaPor'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/OPTION_VendidoPor', [('VendidoPor') : 'Por Promotor']))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/CHECK_UsuarioMega'))

WebUI.sendKeys(findTestObject('Object Repository/SERVICIOS/Papeletas/INPUT_Descripcion'), 'Test')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/TAB_Agenda'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/CHECK_Horario'))

WebUI.sendKeys(findTestObject('Object Repository/SERVICIOS/Papeletas/INPUT_ReferenciaUbicacion'), 'Test')

WebUI.sendKeys(findTestObject('Object Repository/SERVICIOS/Papeletas/INPUT_Pago'), 'Efectivo')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Papeletas/BUTTON_Guardar'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

def msgAlerta = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

WebUI.verifyElementText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 'Proceso finalizado', 
    FailureHandling.CONTINUE_ON_FAILURE)

//Almacenar Id