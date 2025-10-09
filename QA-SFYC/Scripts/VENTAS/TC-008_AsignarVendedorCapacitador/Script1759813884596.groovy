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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

String ARCHIVO_DATOS = 'Data Files/VENTAS/VendedoresCapacitador.xlsx'

String HOJA_DATOS = 'in'

def msgAlertaGlobal = null

def vendedorNoExistente = false

// Se selecciona la sucursal 41 en base al caso de prueba
CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/OBJETOS_GLOBALES/SELECT_Sucursal'), 
    'Guadalajara')

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Vendedor Capacitador', '')

CustomKeywords.'utilidades.Ventas.selectOptionByCode'(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/SELECT_EmpresaNomina'), 
    empresaNomina)

WebUI.setText(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/INPUT_NumeroVendedor'), numeroVendedor)

WebUI.delay(1)

WebUI.sendKeys(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/INPUT_NumeroVendedor'), Keys.chord(
        Keys.ENTER))

//Valida que si se muestra la alerta de vendedor no encontrado, se habilita una bandera para detener el flujo
if (WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 3, FailureHandling.OPTIONAL)) {
    msgAlertaGlobal = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

    vendedorNoExistente = msgAlertaGlobal.contains('No se encontró el vendedor')

    CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()
}

//Si el vendedor existe procede a convertirlo a capacitador
if (!(vendedorNoExistente)) {
    WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/BUTTON_Guardar'))

    WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

    def msgAlertaAsignacionCapacitador = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

    CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'(ARCHIVO_DATOS, HOJA_DATOS, 'NumVendedor', 
        msgAlertaAsignacionCapacitador, msgAlertaGlobal)

    CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.matches'(msgAlertaAsignacionCapacitador, '^Se certificó a .+? como Entrenador Hombro Hombro\\.$', 
        '')
} else {
	// SI el  vendedor no existe se actualiza el registro en el excel y se marca el test case como fallado
    KeywordUtil.markFailed('\n\n El Vendedor Entrenador no se encontro con los parametros de busuqeda \n\n')

    CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'(ARCHIVO_DATOS, HOJA_DATOS, 'NumVendedor', 
        numeroVendedor, msgAlertaGlobal)
}