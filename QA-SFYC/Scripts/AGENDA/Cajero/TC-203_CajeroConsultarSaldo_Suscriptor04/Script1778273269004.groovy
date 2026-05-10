/**
 * ID:            TC-203_CajeroConsultarSaldo_Suscriptor04
 * Modulo:        SERVICIOS - Cajero
 * Objetivo:      Consultar saldo de un suscriptor activo de la sucursal 41,
 *                ingresar al cajero, realizar pago en efectivo y guardarlo.
 * Datos:         Usuario cajero: IYMUNGUIA | Sucursal: 41 | CIS: 001 | Cajero: 3343
 *                Suscriptor: obtenido de BD (fila 4 de 15)
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-08
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

int indiceSuscriptor = 3  // Fila 4 de 15 (indice 3)

String sqlSuscriptores = """
SELECT DISTINCT TOP 15
    CONCAT(FORMAT(c.sucursal, '000'), FORMAT(c.suscriptor, '0000000')) AS numero_suscriptor,
    c.sucursal, c.suscriptor, p.fecha, p.importe_neto, c.estado, c.tipo_servicio, da.curp, da.web_celular
FROM contratos AS c
INNER JOIN suscriptores_datos_adicionales AS da ON c.sucursal = da.sucursal AND c.suscriptor = da.suscriptor
INNER JOIN pagos_offline AS p ON c.sucursal = p.sucursal AND c.suscriptor = p.suscriptor
WHERE c.sucursal = 41 AND p.fecha >= '2026-05-01' AND c.estado = 'ACT' AND p.importe_neto >= 1
    AND c.tipo_servicio IN (2,4,9,19) AND da.curp != '' AND da.web_celular != ''
    AND c.tipo_contrato = 'A' AND da.telefono_celular = da.telefono_whatsapp AND da.telefono_whatsapp = da.web_celular
"""


String numeroSuscriptor = ''
try {
    List<Map<String, Object>> filas = CustomKeywords.'db.QueryKeyword.getTestData'(sqlSuscriptores)
    if (filas == null || filas.size() <= indiceSuscriptor) {
        KeywordUtil.markFailedAndStop("No se obtuvieron suficientes suscriptores de la BD. Se requiere al menos ${indiceSuscriptor + 1} fila(s).")
    }
    numeroSuscriptor = String.valueOf(filas[indiceSuscriptor]['numero_suscriptor'])
    KeywordUtil.logInfo("Suscriptor seleccionado [${indiceSuscriptor + 1}/15]: ${numeroSuscriptor}")
} catch (Throwable t) {
    KeywordUtil.markFailedAndStop("Error al consultar BD: ${t.message}")
}

try {
    CustomKeywords.'utilidades.FuncionesAuxiliares.login'(GlobalVariable.URL_SERVICIOS + 'cashier', GlobalVariable.USER_SERVICIOS, GlobalVariable.PASSWORD_SERVICIOS)
} catch (Throwable t) { KeywordUtil.markFailedAndStop("Error en login SERVICIOS: ${t.message}") }

try {
    WebUI.waitForElementClickable(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Usuario'), GlobalVariable.TIME_OUT)
    CustomKeywords.'utilidades.Agenda.selectOptionByName'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Usuario'), 'IYMUNGUIA')
    CustomKeywords.'utilidades.Agenda.selectOptionById'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Sucursal'), '41')
    CustomKeywords.'utilidades.Agenda.selectOptionById'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Cis'), '1')
    CustomKeywords.'utilidades.Agenda.selectOptionById'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Cajero'), '3343')
    WebUI.setText(findTestObject('SERVICIOS/Cajero/IngresarCajero/INPUT_Contrasenia'), 'CCN12345')
    WebUI.click(findTestObject('SERVICIOS/Cajero/IngresarCajero/BUTTON_Aceptar'))
    WebUI.delay(2)
} catch (Throwable t) { WebUI.takeScreenshot(); KeywordUtil.markFailedAndStop("Error al ingresar cajero: ${t.message}") }

try {
    WebUI.waitForElementClickable(findTestObject('SERVICIOS/Cajero/INPUT_NumeroSuscriptor'), GlobalVariable.TIME_OUT)
    WebUI.setText(findTestObject('SERVICIOS/Cajero/INPUT_NumeroSuscriptor'), numeroSuscriptor)
    WebUI.click(findTestObject('SERVICIOS/Cajero/BUTTON_Calcular'))
    WebUI.waitForElementVisible(findTestObject('SERVICIOS/Cajero/LABEL_TotalPagar'), GlobalVariable.TIME_OUT)
    String totalPagar = WebUI.getText(findTestObject('SERVICIOS/Cajero/LABEL_TotalPagar'))
    KeywordUtil.logInfo("Suscriptor: ${numeroSuscriptor} - Total a pagar: ${totalPagar}")
} catch (Throwable t) { WebUI.takeScreenshot(); KeywordUtil.markFailedAndStop("Error al consultar saldo: ${t.message}") }

try {
    WebUI.waitForElementClickable(findTestObject('SERVICIOS/Cajero/BUTTON_Siguiente'), GlobalVariable.TIME_OUT)
    WebUI.click(findTestObject('SERVICIOS/Cajero/BUTTON_Siguiente'))
    WebUI.delay(2)
} catch (Throwable t) { WebUI.takeScreenshot(); KeywordUtil.markFailedAndStop("Error al guardar pago: ${t.message}") }

try {
    WebUI.waitForElementClickable(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Efectivo'), GlobalVariable.TIME_OUT)
    WebUI.click(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Efectivo'))
    WebUI.waitForElementClickable(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Aplicar'), GlobalVariable.TIME_OUT)
    WebUI.click(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Aplicar'))
    boolean exito = WebUI.waitForElementVisible(findTestObject('OBJETOS_GLOBALES/toast_mensaje'), GlobalVariable.TIME_OUT, FailureHandling.OPTIONAL)
    if (!exito) { exito = WebUI.waitForElementVisible(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT_CONTENT, FailureHandling.OPTIONAL) }
    if (exito) { KeywordUtil.markPassed("Pago en efectivo aplicado para suscriptor ${numeroSuscriptor}") }
    else { WebUI.takeScreenshot(); KeywordUtil.markWarning("No se detecto mensaje de confirmacion para suscriptor ${numeroSuscriptor}") }
} catch (Throwable t) { WebUI.takeScreenshot(); KeywordUtil.markFailed("Error al aplicar pago: ${t.message}") }
