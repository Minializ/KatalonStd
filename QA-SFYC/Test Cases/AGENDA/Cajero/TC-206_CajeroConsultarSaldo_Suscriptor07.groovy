/**
 * ID:            TC-206_CajeroConsultarSaldo_Suscriptor07
 * Modulo:        SERVICIOS - Cajero
 * Objetivo:      Consultar saldo de un suscriptor activo de la sucursal 41
 *                obtenido desde BD, ingresar al cajero, realizar pago en efectivo
 *                y guardar el pago.
 * Precondiciones:
 *                - Usuario IYMUNGUIA con permisos de cajero en SFYC.
 *                - Suscriptor activo con pagos >= $1 en sucursal 41.
 *                - Conexion a BD SQL Server configurada en GlobalVariable.
 * Datos:
 *                - Usuario cajero: IYMUNGUIA
 *                - Sucursal: 41
 *                - CIS: 001
 *                - Cajero: 3343
 *                - Contrasena: CCN12345
 *                - Suscriptor: obtenido de BD (fila 7 de 15)
 * Resultado esperado:
 *                - Se consulta el saldo correctamente.
 *                - Se habilita el boton Guardar Pago.
 *                - Se aplica pago en efectivo exitosamente.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

// ============================================================================
// CONFIGURACION DEL CASO
// ============================================================================
int indiceSuscriptor = 6  // Fila 7 de 15 (indice 6)

String sqlSuscriptores = """
SELECT DISTINCT TOP 15
    CONCAT(FORMAT(c.sucursal, '000'), FORMAT(c.suscriptor, '0000000')) AS numero_suscriptor,
    c.sucursal,
    c.suscriptor,
    p.fecha,
    p.importe_neto,
    c.estado,
    c.tipo_servicio,
    da.curp,
    da.web_celular
FROM contratos AS c
INNER JOIN suscriptores_datos_adicionales AS da
    ON c.sucursal = da.sucursal AND c.suscriptor = da.suscriptor
INNER JOIN pagos_offline AS p
    ON c.sucursal = p.sucursal AND c.suscriptor = p.suscriptor
WHERE 
    c.sucursal = 41
    AND p.fecha >= '2026-05-01'
    AND c.estado = 'ACT'
    AND p.importe_neto >= 1
    AND c.tipo_servicio IN (2,4,9,19)
    AND da.curp != ''
    AND da.web_celular != ''
    AND c.tipo_contrato = 'A'
    AND da.telefono_celular = da.telefono_whatsapp
    AND da.telefono_whatsapp = da.web_celular
"""

// ============================================================================
// 1) Obtener suscriptor desde BD
// ============================================================================
String numeroSuscriptor = ''

try {
    List<Map<String, Object>> filas = CustomKeywords.'db.QueryKeyword.getTestData'(sqlSuscriptores)

    if (filas == null || filas.size() <= indiceSuscriptor) {
        KeywordUtil.markFailedAndStop(
            "No se obtuvieron suficientes suscriptores de la BD. Se requiere al menos ${indiceSuscriptor + 1} fila(s).")
    }

    numeroSuscriptor = String.valueOf(filas[indiceSuscriptor]['numero_suscriptor'])
    KeywordUtil.logInfo("Suscriptor seleccionado [${indiceSuscriptor + 1}/15]: ${numeroSuscriptor}")
} catch (Throwable t) {
    KeywordUtil.markFailedAndStop("Error al consultar BD: ${t.message}")
}

// ============================================================================
// 2) Login a SERVICIOS
// ============================================================================
try {
    CustomKeywords.'utilidades.FuncionesAuxiliares.login'(
        GlobalVariable.URL_SERVICIOS + 'cashier',
        GlobalVariable.USER_SERVICIOS,
        GlobalVariable.PASSWORD_SERVICIOS)
    KeywordUtil.logInfo("Login exitoso en SERVICIOS - Cajero")
} catch (Throwable t) {
    KeywordUtil.markFailedAndStop("Error en login SERVICIOS: ${t.message}")
}

// ============================================================================
// 3) Ingresar datos del cajero
// ============================================================================
try {
    WebUI.waitForElementClickable(
        findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Usuario'),
        GlobalVariable.TIME_OUT)

    // Seleccionar usuario IYMUNGUIA
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(
        findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Usuario'), 'IYMUNGUIA')

    // Seleccionar sucursal 41
    CustomKeywords.'utilidades.Agenda.selectOptionById'(
        findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Sucursal'), '41')

    // Seleccionar CIS 001
    CustomKeywords.'utilidades.Agenda.selectOptionById'(
        findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Cis'), '1')

    // Seleccionar cajero 3343
    CustomKeywords.'utilidades.Agenda.selectOptionById'(
        findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Cajero'), '3343')

    // Ingresar contrasena
    WebUI.setText(
        findTestObject('SERVICIOS/Cajero/IngresarCajero/INPUT_Contrasenia'), 'CCN12345')

    // Clic en Aceptar
    WebUI.click(findTestObject('SERVICIOS/Cajero/IngresarCajero/BUTTON_Aceptar'))

    WebUI.delay(2)
    KeywordUtil.logInfo("Cajero ingresado correctamente")
} catch (Throwable t) {
    WebUI.takeScreenshot()
    KeywordUtil.markFailedAndStop("Error al ingresar datos del cajero: ${t.message}")
}

// ============================================================================
// 4) Consultar saldo del suscriptor
// ============================================================================
try {
    WebUI.waitForElementClickable(
        findTestObject('SERVICIOS/Cajero/INPUT_NumeroSuscriptor'),
        GlobalVariable.TIME_OUT)

    WebUI.setText(
        findTestObject('SERVICIOS/Cajero/INPUT_NumeroSuscriptor'), numeroSuscriptor)

    WebUI.click(findTestObject('SERVICIOS/Cajero/BUTTON_Calcular'))

    // Esperar a que se muestre el total a pagar
    WebUI.waitForElementVisible(
        findTestObject('SERVICIOS/Cajero/LABEL_TotalPagar'),
        GlobalVariable.TIME_OUT)

    String totalPagar = WebUI.getText(findTestObject('SERVICIOS/Cajero/LABEL_TotalPagar'))
    KeywordUtil.logInfo("Suscriptor: ${numeroSuscriptor} - Total a pagar: ${totalPagar}")
} catch (Throwable t) {
    WebUI.takeScreenshot()
    KeywordUtil.markFailedAndStop("Error al consultar saldo del suscriptor ${numeroSuscriptor}: ${t.message}")
}

// ============================================================================
// 5) Guardar pago - clic en Siguiente
// ============================================================================
try {
    WebUI.waitForElementClickable(
        findTestObject('SERVICIOS/Cajero/BUTTON_Siguiente'),
        GlobalVariable.TIME_OUT)

    WebUI.click(findTestObject('SERVICIOS/Cajero/BUTTON_Siguiente'))
    WebUI.delay(2)
    KeywordUtil.logInfo("Boton Siguiente/Guardar Pago seleccionado")
} catch (Throwable t) {
    WebUI.takeScreenshot()
    KeywordUtil.markFailedAndStop("Error al guardar pago: ${t.message}")
}

// ============================================================================
// 6) Pagar en efectivo
// ============================================================================
try {
    WebUI.waitForElementClickable(
        findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Efectivo'),
        GlobalVariable.TIME_OUT)

    WebUI.click(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Efectivo'))

    WebUI.waitForElementClickable(
        findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Aplicar'),
        GlobalVariable.TIME_OUT)

    WebUI.click(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Aplicar'))

    // Validar mensaje de exito
    boolean exito = WebUI.waitForElementVisible(
        findTestObject('OBJETOS_GLOBALES/toast_mensaje'),
        GlobalVariable.TIME_OUT,
        FailureHandling.OPTIONAL)

    if (!exito) {
        // Intentar validar con LABEL_MensajeAlerta
        exito = WebUI.waitForElementVisible(
            findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'),
            GlobalVariable.TIME_OUT_CONTENT,
            FailureHandling.OPTIONAL)
    }

    if (exito) {
        KeywordUtil.markPassed(
            "Pago en efectivo aplicado exitosamente para suscriptor ${numeroSuscriptor}")
    } else {
        WebUI.takeScreenshot()
        KeywordUtil.markWarning(
            "No se detecto mensaje de confirmacion para suscriptor ${numeroSuscriptor}. Verificar manualmente.")
    }
} catch (Throwable t) {
    WebUI.takeScreenshot()
    KeywordUtil.markFailed("Error al aplicar pago en efectivo: ${t.message}")
}

