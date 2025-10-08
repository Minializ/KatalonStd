/**
 * AUTOR: Ian Romero
 * FECHA CREACIÓN: 22/09/2024
 * DESCRIPCIÓN GENERAL:
 * Este caso de prueba automatiza el proceso de reasignación de quejas en estado ASI, 
 * de igual manera si el suscriptor no cuenta con quejas en ese estado el caso de prueba falla
 * REQUISITOS: 
 * Depurar y actualizar el excel "Suscriptores.xlsx" la hoja "SuscriptoresQueja"
 * Los suscriptores deben contar con quejas en estado SIM
 */
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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys


//Se manda a llamar el caso de prueba de consulta de suscriptor, el cual necesita el parametro de Id para realizar la consulta
CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'(numeroSuscriptor)

WebUI.waitForElementPresent(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Quejas Adm.']), GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Quejas Adm.']))

//Se verifica que existan quejas con estado ASI, Caso contrario falla el caso de prueba
if(WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/QuejasAdm/TR_QuejaASI'), 1, FailureHandling.OPTIONAL)) {
	
	//Se obtiene la posicion de la queja, para posteriormente dar click en el botón de opciones del registro
	def positionRow = WebUI.getAttribute(findTestObject('Object Repository/AGENDA/QuejasAdm/TR_QuejaASI'), 'aria-rowindex')
	
	WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/BUTTON_AccionesQuejaASI', [('positionRow') : positionRow]))
	
	WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/BUTTON_Editar'))
	
	CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject("Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Reasignacion"), 'MAGALI ESTEFANIA GARCIA LLAMAS')
	
	WebUI.setText(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Motivo'), 'Apoyo con esta QA')
	
	WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/BUTTON_Reasingar'))
	
	WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
	
	WebUI.verifyElementText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), '¡Queja administrativa reasignada con éxito!')
}else {
	KeywordUtil.markFailed('\n\n No existen quejas disponibles para reasingar \n\n')
}