import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
//import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
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

// Modelo de equipo a reemplazar; si no se define en el Test Case, se usa valor por defecto.
//def modeloEquipoActual = (binding.hasVariable('modeloEquipo') && modeloEquipo) ? modeloEquipo : 'HG8145V5'


// Consulta del suscriptor (keyword reutilizable del proyecto SFYC).
CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'('0430181005')

// Cierra el mensaje "ERROR EN EL SERVIDOR" si aparece.
CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()
CustomKeywords.'utilidades.FuncionesAuxiliares.eliminarChatBot'()

// Navegación a Contratos.
WebUI.waitForElementPresent(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/TAB_Contratos'), GlobalVariable.TIME_OUT)
WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/TAB_Contratos',))

// Navegación a Equipos dentro de Contratos.
WebUI.waitForElementPresent(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/TAB_Equipos'), GlobalVariable.TIME_OUT)
WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/TAB_Equipos'),)


CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.delay(5)

// Selección del equipo por modelo (ej. HG8145V5).
WebUI.waitForElementPresent(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/TD_ModeloEquipo'), 0)
WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/TD_ModeloEquipo'))//, [('opcion') : modeloEquipoActual]))

// timeout en segundos (usa la variable global definida en tu proyecto)
int timeout = GlobalVariable.TIME_OUT ?: 5



// Abrir opciones del equipo y elegir Reemplazar equipo.
WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/BUTTON_VerOpciones'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

//Seleccionar la opcion de Procesos de equipos
WebUI.delay(5)
WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/SPAN_ProcesosEquipos'))

CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

//Seleccionar la opcion de Reemplazar equipos
WebUI.waitForElementPresent(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/SPAN_ReemplazarEquipo'), 5)
WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/SPAN_ReemplazarEquipo'))

// Completar aquí los pasos del formulario de reemplazo (serie nuevo equipo, CIS, etc.)
// y la verificación del mensaje de éxito según objetos del Object Repository cuando existan.
WebUI.delay(5)
CustomKeywords.'utilidades.Agenda.selectOptionById'(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/Dropdown_Cis'), '001')


//ingresar serie // Obtener texto de la columna 4
String valorColumna4 = WebUI.getText(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/TR_Serie')).trim()

// Ingresar valor en el input serie
WebUI.setText(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/INPUT_Serie'),valorColumna4)

// Seleccionar boton OK
WebUI.waitForElementPresent(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/BUTTON_OK'), 5)
WebUI.click(findTestObject('Object Repository/AGENDA/Contratos/reemplazar_equipos/BUTTON_OK'))