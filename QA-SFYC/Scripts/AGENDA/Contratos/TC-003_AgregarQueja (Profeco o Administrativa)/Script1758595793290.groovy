 /**
 * AUTOR: Ian Romero
 * FECHA CREACIÓN: 23/09/2024
 * DESCRIPCIÓN GENERAL:
 * Este caso de prueba automatiza el flujo de alta de una queja administrativa tipo PROFECO en el sistema de gestión de suscriptores.
 * El flujo inicia con el inicio de sesión del usuario mediante una función personalizada que permite la autenticación con credenciales cifradas.
 * Posteriormente, se realiza la consulta de un suscriptor específico para obtener su información contractual, y se navega dinámicamente a la pestaña de "Quejas Adm.".
 * Una vez allí, se da de alta una nueva queja administrativa completando todos los campos obligatorios del formulario, seleccionando filtros personalizados
 * en listas desplegables, y proporcionando datos relevantes como el nombre y teléfono del reportante, síntomas, diagnóstico y resolución esperada.
 * Finalmente, el formulario se envía y se valida que el mensaje de confirmación aparezca correctamente, lo cual garantiza que la queja fue procesada exitosamente.
 * REQUISITOS:
 * - El usuario debe contar con permisos para dar de alta quejas administrativas de tipo PROFECO..
 * - Los valores usados en los combos deben estar disponibles y activos en el catálogo correspondiente del sistema.
 */ import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
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

//Se escribe en el reporte el tipo de queja a registrar
WebUI.comment('INICIO TC-003_AgregarQuea - : ' + tipoQueja.toUpperCase())

//Se manda a llamar el caso de prueba de consulta de suscriptor, el cual necesita el parametro de Id para realizar la consulta
CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'(numeroSuscriptor)

WebUI.waitForElementPresent(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Quejas Adm.']), 
    GlobalVariable.TIME_OUT)

WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/TAB_DYN', [('opcion') : 'Quejas Adm.']))

WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/BUTTON_AgregarQuejaAdministrativa'))

//Valida que se haya abierto el formulario de registro de queja, por medio de la presencia del input de motivo
WebUI.verifyElementPresent(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Motivo'), 
    3)

//Valida que si la queja es de Profeco, se llenan los campos habilitado para esta opción
if (tipoQueja.toUpperCase().contains('PROFECO')) {
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo'), 
        'Profeco')

    CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_SubTipo'), 
        'Aclaración de venta')

    CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Motivo'), 
        'Promocion mal Configurada')

    CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_TipoServicio'), 
        'Cable')

    CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Servicio'), 
        'TV Básico Plus')

    CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Canal'), 
        '800') //Caso contrario se interpreta que es una queja administrativa y se llena la información correspondiente
} else {
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo'), 
        'Quejas de Personal')

    CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_SubTipo'), 
        'Mal servicio en CIS')
}

//Se llenan datos de contacto
WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Reporta'))

WebUI.setText(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Reporta'), 'Ian Romero')

WebUI.setText(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Telefono'), '3321301567')

CustomKeywords.'utilidades.Agenda.selectOptionById'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Sucursal'), 
    '41')

CustomKeywords.'utilidades.Agenda.selectOptionById'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_CIS'), 
    '2')

//Se llena sección comun para queja administrativa o de profeco
CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo2'), 
    'CCN')

CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tecnologia'), 
    'PROFECO')

CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_TipoLlamada'), 
    'COBRO EXCEDENTE')

CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Sintoma'), 
    'CARGO AUTOMATICO')

CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Diagnostico'), 
    'NO SE CANCELO CARGO AUTOMATICO')

CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Definitivo'), 
    'SOLUCION TELEFONICA SE GENERA SOLICITUD DE QA')

//Se ingresa el motivo/seguimiento
WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Motivo'))

WebUI.setText(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Motivo'), 'Apoyo con esta QA')

//Se envia el registro de la queja
WebUI.click(findTestObject('Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/BUTTON_Enviar'))

WebUI.verifyElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementText(findTestObject('Object Repository/OBJETOS_GLOBALES/LABEL_MensajeAlerta'), '¡Queja administrativa insertada con éxito!')

