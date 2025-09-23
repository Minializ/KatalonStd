import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class Preconfiguraciones {

	/**
	 * Método ejecutado automáticamente después de cada caso de prueba.
	 * Su objetivo es capturar una evidencia visual del estado final del test y limpiar la interfaz.
	 *
	 * Acciones realizadas:
	 * - Captura una captura de pantalla con el nombre del ID del caso de prueba (para trazabilidad).
	 * - Llama al método personalizado 'limpiarVista' para cerrar ventanas emergentes o pestañas abiertas
	 *   y así dejar el entorno limpio para la siguiente ejecución.
	 */
	@AfterTestCase
	def capturarEvidencias(TestCaseContext testCaseContext) {
		WebUI.takeScreenshot(testCaseContext.getTestCaseId() + ".jpg")
		CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()
	}
}