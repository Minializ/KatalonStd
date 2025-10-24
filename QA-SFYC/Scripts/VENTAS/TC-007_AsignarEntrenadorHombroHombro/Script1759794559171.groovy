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

String ARCHIVO_DATOS = 'Data Files/VENTAS/VededoresHombroHombro.xlsx'

String HOJA_DATOS = 'in'

def vendedorNoExistente = false

def vendedorPreviamenteAsignado = false

def msgAlertaGlobal = null

// Se selecciona la sucursal que requiere el vendedor
CustomKeywords.'utilidades.Ventas.selectOptionByCode'(findTestObject('Object Repository/OBJETOS_GLOBALES/SELECT_Sucursal'), 
    sucursal)

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Vendedores hombro a hombro', '', '')

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

//Valida que la bandera de vendedorNoExitente este en falso, esto indica que el vendedor si existe y puede continuar con el flujo de asignación
if (!vendedorNoExistente) {
   
	 //Si el vendedor va a ser entrenador nuevo, de debe asignar el valor "SI" en la columna "entrenadorNuevo" del excel "VendedoresQueYaSonHH"
    if (entrenadorNuevo.toUpperCase().contains('SI')) {
        WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/BUTTON_Guardar'))

        WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

        def msgAlertaAsignacionEntrenador = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

        CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.matches'(msgAlertaAsignacionEntrenador, '^Se certificó a .+? como Entrenador Hombro Hombro\\.$')

        WebUI.takeScreenshot()

        CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()
    }
    
    WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/TAB_AsignarDescartar'))

    WebUI.waitForElementPresent(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_BuscarVendedor'), 
        10)

    WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_BuscarVendedor'))

    WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/INPUT_NumeroVendedor'))

    WebUI.setText(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/INPUT_NumeroVendedor'), 
        numeroVendedorEntrenamiento)

    WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_Enviar'))

    //Valida los posibles mensajes que arroja la busqueda del vendedor a asignar
    if (WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 3, FailureHandling.OPTIONAL)) {
        msgAlertaGlobal = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

        //Si el vendedor tiene mpas de dos semanas que ingreso envia la solicitud
        if (msgAlertaGlobal.contains('El vendedor tiene más de dos semanas que ingreso')) {
            WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_Enviar'))
        }
        
        // Si el vendedor ya esta asignado detiene el flujo
        if (msgAlertaGlobal.contains('ya esta asignado')) {
            vendedorPreviamenteAsignado = true

            //Caso contrario, se muestra un mensaje en la columna de estatus del excel
            CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'(ARCHIVO_DATOS, HOJA_DATOS, 
                'VendedorEntrenador', numeroVendedor, msgAlertaGlobal)

            KeywordUtil.markFailed('\n\n El Vendedor ya fue asignado previamente\n\n')
        }
    }
    
    //Si el vendedor a asignar no fue previamente asignado a otro usuario, el flujo continua
    if (!(vendedorPreviamenteAsignado)) {
        CustomKeywords.'utilidades.Ventas.selectOptionByName'(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/SELECT_Periodo'), 
            '/')

        WebUI.click(findTestObject('Object Repository/VENTAS/VendedoresHombro-Capacitador/AsignarDescartar/BUTTON_Asingar'))

        WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

        def msgAlertaAsignacionVendedor = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

        CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'(ARCHIVO_DATOS, HOJA_DATOS, 'VendedorEntrenador', 
            numeroVendedor, msgAlertaAsignacionVendedor)

        CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.matches'(msgAlertaAsignacionVendedor, '^El vendedor .+?\\. Fue asignado a: .+\\.$', 
            null)
    }
} else {
	// Caso contrario de que el vendedor no exista se coloca el mensaje de error en el excel
    CustomKeywords.'utilidades.FuncionesAuxiliares.actualizarEstatusDataSetSuscriptor'(ARCHIVO_DATOS, HOJA_DATOS, 'VendedorEntrenador', 
        numeroVendedor, msgAlertaGlobal)

    KeywordUtil.markFailed('\n\n El Vendedor Entrenador no se encontro con los parametros de busuqeda \n\n')
}