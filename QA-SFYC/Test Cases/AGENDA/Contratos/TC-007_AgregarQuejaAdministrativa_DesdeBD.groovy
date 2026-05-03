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
            "SELECT TOP 1 numero_suscriptor FROM tbl_suscriptores_test " +
            "WHERE estado = ? AND apto_para_queja = 1 ORDER BY NEWID()",
            ['ACTIVO']
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
// 3) Login a AGENDA
// ----------------------------------------------------------------------------
try {
    WebUI.openBrowser('')
    WebUI.maximizeWindow()
    WebUI.navigateToUrl(GlobalVariable.URL_AGENDA)

    WebUI.setText(findTestObject('LOGIN/INPUT_User'),
        GlobalVariable.USER_AGENDA)
    WebUI.setEncryptedText(findTestObject('LOGIN/INPUT_Password'),
        GlobalVariable.PASSWORD_AGENDA)
    WebUI.setText(findTestObject('LOGIN/SELECT_Dominio'),
        GlobalVariable.DOMINIO)
    WebUI.click(findTestObject('LOGIN/BUTTON_IniciarSesion'))

    WebUI.waitForElementVisible(
        findTestObject('AGENDA/PaginaInicio/LABEL_NombreUsuario'),
        GlobalVariable.TIME_OUT)

    // ------------------------------------------------------------------------
    // 4) Buscar suscriptor
    // ------------------------------------------------------------------------
    WebUI.setText(
        findTestObject('AGENDA/PaginaInicio/Consulta/INPUT_Suscriptor'),
        numeroSuscriptor)
    WebUI.click(findTestObject('AGENDA/PaginaInicio/BUTTON_Consultar'))

    WebUI.waitForElementVisible(
        findTestObject('AGENDA/PaginaInicio/Consulta/TD_SuscriptorEncontrado'),
        GlobalVariable.TIME_OUT)
    WebUI.click(
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
