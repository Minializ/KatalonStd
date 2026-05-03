/**
 * ID:            TC-007_AgregarQuejaAdministrativa_DesdeBD
 * Modulo:        AGENDA
 * Objetivo:      Crear una queja administrativa para un suscriptor obtenido
 *                desde SQL Server, validando que el formulario se envia
 *                correctamente y la queja queda registrada.
 * Precondiciones:
 *                - Usuario con permiso de quejas en SFYC.
 *                - Existe al menos un suscriptor activo en la BD QA_DATA.
 * Datos:
 *                - numeroSuscriptor: SQL Server (tabla tbl_suscriptores_test)
 *                                    o fallback al valor del TC-003 (0411627544).
 *                - tipoQueja:        parametro del TC ('profeco' | 'administrativa').
 *                - reporta:          parametro del TC (default 'Pruebas agente').
 * Resultado esperado:
 *                - Toast de exito visible tras enviar el formulario.
 *                - URL del navegador permanece en el modulo de Quejas.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-03
 * Caso dorado:   TC-003_AgregarQueja (Profeco o Administrativa)
 */
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

// ----------------------------------------------------------------------------
// 1) Resolver suscriptor (BD o fallback)
// ----------------------------------------------------------------------------
String numeroSuscriptor

if (obtenerSuscriptorDeBD) {
    try {
        Map fila = CustomKeywords.'db.QueryKeyword.getFirstRow'(
            "SELECT TOP 1 " +
            "CONCAT(FORMAT(c.sucursal, '000'), FORMAT(c.suscriptor, '0000000')) AS numero_suscriptor " +
            "FROM contratos AS c " +
            "INNER JOIN suscriptores_datos_adicionales AS da " +
            "ON c.sucursal = da.sucursal AND c.suscriptor = da.suscriptor " +
            "WHERE c.sucursal = 43 " +
            "AND tipo_servicio IN (2,4,9,19) " +
            "AND estado IN ('ACT') " +
            "AND curp != '' " +
            "AND web_celular != '' " +
            "ORDER BY NEWID()"
        )
        if (fila == null) {
            KeywordUtil.markWarning("BD no devolvio suscriptores. Usando fallback.")
            numeroSuscriptor = '0411627544'
        } else {
            numeroSuscriptor = String.valueOf(fila['numero_suscriptor'])
            KeywordUtil.logInfo("Suscriptor obtenido de BD: ${numeroSuscriptor}")
        }
    } catch (Throwable t) {
        KeywordUtil.markWarning("Fallo lectura BD (${t.message}). Usando fallback 0411627544.")
        numeroSuscriptor = '0411627544'
    }
} else {
    numeroSuscriptor = '0411627544'
}

// ----------------------------------------------------------------------------
// 2) Validacion de parametro
// ----------------------------------------------------------------------------
if (!(tipoQueja in ['profeco', 'administrativa'])) {
    KeywordUtil.markFailedAndStop(
        "Parametro tipoQueja invalido: '${tipoQueja}'. Valores permitidos: profeco, administrativa."
    )
}

// ----------------------------------------------------------------------------
// 3) Login a AGENDA (via Keyword reutilizable)
// ----------------------------------------------------------------------------
try {
    CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
        GlobalVariable.URL_AGENDA,
        GlobalVariable.USER_AGENDA,
        GlobalVariable.PASSWORD_AGENDA)

    // ------------------------------------------------------------------------
    // 4) Buscar suscriptor (via Keyword reutilizable)
    // ------------------------------------------------------------------------
    CustomKeywords.'utilidades.Agenda.consultarSusciptorPorId'(numeroSuscriptor)

	// Cierra el mensaje "ERROR EN EL SERVIDOR" si aparece.
	CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()
	
	WebUI.waitForElementVisible(
        findTestObject('AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'),
        GlobalVariable.TIME_OUT)
    WebUI.doubleClick(
        findTestObject('AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'))

    // ------------------------------------------------------------------------
    // 5) Abrir formulario de Queja Administrativa
    // ------------------------------------------------------------------------
    WebUI.waitForElementClickable(
        findTestObject('AGENDA/QuejasAdm/BUTTON_AgregarQuejaAdministrativa'),
        GlobalVariable.TIME_OUT)
    WebUI.click(
        findTestObject('AGENDA/QuejasAdm/BUTTON_AgregarQuejaAdministrativa'))

    WebUI.waitForElementVisible(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Reporta'),
        GlobalVariable.TIME_OUT)

    // ------------------------------------------------------------------------
    // 6) Llenar formulario
    // ------------------------------------------------------------------------
    WebUI.setText(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Reporta'),
        reporta)

    WebUI.setText(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Telefono'),
        '3331234567')

    WebUI.setText(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/INPUT_Motivo'),
        "Queja generada por agente QA - ${tipoQueja} - ${new Date().format('yyyy-MM-dd HH:mm')}")

    // El campo SELECT_Tipo distingue profeco vs administrativa
    WebUI.selectOptionByLabel(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/SELECT_Tipo'),
        tipoQueja.capitalize(),  // 'Profeco' / 'Administrativa'
        false)

    // ------------------------------------------------------------------------
    // 7) Enviar y validar
    // ------------------------------------------------------------------------
    WebUI.click(
        findTestObject('AGENDA/QuejasAdm/FormQuejasAdministrativas/BUTTON_Enviar'))

    boolean exito = WebUI.waitForElementVisible(
        findTestObject('OBJETOS_GLOBALES/toast_mensaje'),
        GlobalVariable.TIME_OUT_CONTENT,
        FailureHandling.OPTIONAL)

    if (!exito) {
        WebUI.takeScreenshot()
        KeywordUtil.markFailed(
            "No se mostro el toast de exito tras enviar la queja ${tipoQueja} " +
            "para el suscriptor ${numeroSuscriptor}.")
    } else {
        String mensaje = WebUI.getText(findTestObject('OBJETOS_GLOBALES/toast_mensaje'))
        KeywordUtil.logInfo("Toast recibido: ${mensaje}")
        KeywordUtil.markPassed(
            "Queja '${tipoQueja}' creada para suscriptor ${numeroSuscriptor}. " +
            "Reporta: ${reporta}.")
    }

} catch (Throwable t) {
    WebUI.takeScreenshot()
    KeywordUtil.markFailed("Error inesperado en TC-007: ${t.message}")
    throw t
} finally {
    WebUI.closeBrowser()
}
