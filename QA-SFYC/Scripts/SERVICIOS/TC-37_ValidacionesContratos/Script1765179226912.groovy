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
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory

WebDriver driver = DriverFactory.getWebDriver()

def randomInterior = new Random().nextInt(1000) + 1

CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Contratos', '', '')

WebUI.setMaskedText(findTestObject('Object Repository/SERVICIOS/Cajero/INPUT_NumeroSuscriptor'), '0' + numeroSuscriptor)

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

//Generales

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Generales/INPUT_Curp'), 'AAAA980627HJCMRN00')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Generales/INPUT_IdSct'), '1234')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/Generales/BUTTON_Aceptar'))

def flagAlertGenerales = WebUI.waitForElementPresent(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 15, FailureHandling.CONTINUE_ON_FAILURE)

if(flagAlertGenerales) {
	def msgActualizacionGenerales = WebUI.getText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'))
	
	WebUI.takeScreenshot('GuardarGenerales.jpg')
	
	WebUI.verifyMatch(msgActualizacionGenerales, '.*Datos de cliente actualizados correctamente*', true, FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))
}

//Domicilio

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/Domicilio/TAB_Domicilio'))

String calle = WebUI.getAttribute(findTestObject('Object Repository/SERVICIOS/Contratos/Domicilio/SELECT_Calle'), 'ng-reflect-value')

assert calle != null && calle.trim() != '' : "La calle esta vacia"

String EntreCalle = WebUI.getAttribute(findTestObject('Object Repository/SERVICIOS/Contratos/Domicilio/SELECT_EntreCalle'), 'ng-reflect-value')

assert EntreCalle != null && EntreCalle.trim() != '' : "La EntreCalle esta vacia"

String EntreCalle2 = WebUI.getAttribute(findTestObject('Object Repository/SERVICIOS/Contratos/Domicilio/SELECT_EntreCalle2'), 'ng-reflect-value')

assert EntreCalle2 != null && EntreCalle2.trim() != '' : "La EntreCalle2 esta vacia"

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/INPUT_NumExterior'), randomInterior.toString())

WebUI.sendKeys(findTestObject('Object Repository/SERVICIOS/Cajero/CambioDomicilio/INPUT_NumExterior'), Keys.chord(Keys.TAB))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/Generales/BUTTON_Aceptar'))

/*
def flagAlertDom = WebUI.waitForElementPresent(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 15, FailureHandling.CONTINUE_ON_FAILURE)

if(flagAlertDom) {
	def msgActualizacionDom = WebUI.getText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'))
	
	WebUI.takeScreenshot('GuardarGenerales.jpg')
	
	WebUI.verifyMatch(msgActualizacionGenerales, '.*Datos de cliente actualizados correctamente*', true, FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))
}
}*/

//Facturacion

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/TAB_Facturacion'))

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/INPUT_RazonSocial'), 'KATALON AUTOMATION SA DE CV')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/INPUT_CalleNumero'), 'COVADONGA #1543')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/INPUT_Colonia'), 'LOMAS DE ZAPOPAN')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/INPUT_Ciudad'), 'ZAPOPAN')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/INPUT_Estado'), 'JALISCO')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/INPUT_Cp'), '45130')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/INPUT_RFC'), 'AAA010101AAA')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/INPUT_CuentaDeudor'), '951')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/INPUT_CentCost'), '1')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/SELECT_UsoCfdi'), 'G03')

CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/SELECT_Regimen'), '601')

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/Facturacion/BUTTON_Guardar'))

def flagAlertFact = WebUI.waitForElementPresent(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 15, FailureHandling.CONTINUE_ON_FAILURE)

if(flagAlertFact) {
	def msgActualizacionFact = WebUI.getText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'))
	
	WebUI.takeScreenshot('GuardarGenerales.jpg')
	
	WebUI.verifyMatch(msgActualizacionFact, '.*Datos de cliente actualizados correctamente*', true, FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.click(findTestObject('Object Repository/OBJETOS_GLOBALES/BUTTON_Cerrar'))
}

// Otros Datos

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/OtrosDatos/TAB_OtrosDatos'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/OtrosDatos/BUTTON_Bloquear'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/OtrosDatos/BUTTON_Guardar'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.takeScreenshot('OtrosDatos.jpg')

//Orden Trabajo

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/OrdenTrabajo/TAB_OrdenTrabajo'))

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/OrdenTrabajo/CHECK_Am'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/OrdenTrabajo/CHECK_Confirmar'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/OrdenTrabajo/CHECK_Perro'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/OrdenTrabajo/CHECK_Pm'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/OrdenTrabajo/CHECK_Timbre'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/OrdenTrabajo/CHECK_TodoDia'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.takeScreenshot('OrdenTrabajo.jpg')

//Clientes

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/Clientes/BUTTON_Bulk'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/Clientes/BUTTON_Recomendaciones'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.takeScreenshot('Clientes.jpg')

//Contactos

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/Contactos/TAB_Contactos'))

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Contactos/INPUT_NombreTecnico'), 'HERNAN VILLALPANDO')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Contactos/INPUT_TelefonoTecnico'), '3334567788')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Contactos/INPUT_NombreAdmin'), 'MARIANA TORRES')

WebUI.setText(findTestObject('Object Repository/SERVICIOS/Contratos/Contactos/INPUT_TelefonoAdmin'), '3534567788')

WebUI.takeScreenshot('Contactos.jpg')

//Contratos

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/TAB_Contratos'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/TD_Internet'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/BUTTON_VerOpciones'))

WebUI.verifyElementClickable(findTestObject('Object Repository/SERVICIOS/Contratos/PContratos/BUTTON_Nuevo'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.takeScreenshot('Contratos.jpg')

//Migrar Servicios

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/MigrarServicios/TAB_MigrarServicios'))

List<WebElement> filasIniciales = driver.findElements(
	By.xpath('((//div[@class="migrate-service-container-columns"])[1]//table)[4]//tr')
)

def sizeInicialTablaDestino = filasIniciales.size()

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/MigrarServicios/CHECK_MoverPrimerRegistro'))

WebUI.click(findTestObject('Object Repository/SERVICIOS/Contratos/MigrarServicios/BUTTON_MoverDestino'))

List<WebElement> filasActuales = driver.findElements(
	By.xpath('((//div[@class="migrate-service-container-columns"])[1]//table)[4]//tr')
)

def sizeActualTablaDestino = filasActuales.size()

WebUI.verifyNotEqual(sizeInicialTablaDestino, sizeActualTablaDestino, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.takeScreenshot('Migracion.jpg')