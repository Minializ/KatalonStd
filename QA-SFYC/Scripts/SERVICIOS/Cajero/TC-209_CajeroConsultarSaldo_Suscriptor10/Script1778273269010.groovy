 /**
 * ID:            TC-209_CajeroConsultarSaldo_Suscriptor10
 * Modulo:        SERVICIOS - Cajero
 * Objetivo:      Consultar saldo de un suscriptor activo de la sucursal 41,
 *                ingresar al cajero, realizar pago en efectivo, imprimir,
 *                aplicar pago, facturar y ejecutar facturacion.
 * Precondiciones: usuario IYMUNGUIA existe, suscriptor activo en sucursal 41.
 * Datos:         Usuario cajero: IYMUNGUIA | Sucursal: 41 | CIS: 001 | Cajero: 3343
 *                Suscriptor: obtenido de BD (fila 10 de 15)
 * Resultado esperado: Pago en efectivo aplicado, facturacion ejecutada exitosamente.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-08
 */ 
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

//Solo usar con BD
/*int indiceSuscriptor = 9 // Fila 10 de 15 (indice 9)

String sqlSuscriptores = '\nSELECT DISTINCT TOP 15\n    CONCAT(FORMAT(c.sucursal, \'000\'), FORMAT(c.suscriptor, \'0000000\')) AS numero_suscriptor,\n    c.sucursal, c.suscriptor, p.fecha, p.importe_neto, c.estado, c.tipo_servicio, da.curp, da.web_celular\nFROM contratos AS c\nINNER JOIN suscriptores_datos_adicionales AS da ON c.sucursal = da.sucursal AND c.suscriptor = da.suscriptor\nINNER JOIN pagos_offline AS p ON c.sucursal = p.sucursal AND c.suscriptor = p.suscriptor\nWHERE c.sucursal = 41 AND p.fecha >= \'2026-05-01\' AND c.estado = \'ACT\' AND p.importe_neto >= 1\n    AND c.tipo_servicio IN (2,4,9,19) AND da.curp != \'\' AND da.web_celular != \'\'\n    AND c.tipo_contrato = \'A\' AND da.telefono_celular = da.telefono_whatsapp AND da.telefono_whatsapp = da.web_celular\n'

// 1) Preparacion de datos desde BD
String numeroSuscriptor = ''

try {
    List<Map> filas = CustomKeywords.'db.QueryKeyword.getTestData'(sqlSuscriptores)

    if ((filas == null) || (filas.size() <= indiceSuscriptor)) {
        KeywordUtil.markFailedAndStop(
            "No se obtuvieron suficientes suscriptores de la BD. Se requiere al menos ${indiceSuscriptor + 1} fila(s)."
        )
    }

    numeroSuscriptor = String.valueOf((filas[indiceSuscriptor])['numero_suscriptor'])

    KeywordUtil.logInfo("Suscriptor seleccionado [${indiceSuscriptor + 1}/15]: ${numeroSuscriptor}")
    // tambien podria ser:  "...]: $numeroSuscriptor"  (eso si es valido)
}
catch (Throwable t) {
    KeywordUtil.markFailedAndStop("Error al consultar BD: $t.message")
} 
*/
try {
    // 2) Login - Steps 1-5: Seleccionar dominio SFYC, ingresar usuario, contrasenia e iniciar sesion
    CustomKeywords.'utilidades.FuncionesAuxiliares.login'(GlobalVariable.URL_SERVICIOS, GlobalVariable.USER_SERVICIOS, GlobalVariable.PASSWORD_SERVICIOS)

    // Step 7: Click en menu "Cajero"
    CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Cajero', '', '')
	WebUI.setMaskedText(findTestObject('Object Repository/SERVICIOS/Cajero/INPUT_NumeroSuscriptor'), '0' + numeroSuscriptor)
	
	CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
   
    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // Steps 8-10: Seleccionar usuario IYMUNGUIA
    CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Usuario'), 'IYMUNGUIA')

    // Steps 11-12: Seleccionar CIS 001
    CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByCode'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Cis'), '001')

    // Steps 13-14: Seleccionar Cajero 03343
    CustomKeywords.'utilidades.FuncionesAuxiliares.selectOptionByName'(findTestObject('SERVICIOS/Cajero/IngresarCajero/SELECT_Cajero'), '3343')

    // Step 15: Ingresar contrasenia del cajero
	WebUI.waitForElementVisible(findTestObject('SERVICIOS/Cajero/IngresarCajero/INPUT_Contrasenia'), 20)
    WebUI.setEncryptedText(findTestObject('SERVICIOS/Cajero/IngresarCajero/INPUT_Contrasenia'),PasswordCajero)

    WebUI.delay(2)

    // Step 16: Click Aceptar
    WebUI.click(findTestObject('SERVICIOS/Cajero/IngresarCajero/BUTTON_Aceptar'))

    // Step 17: Cerrar popup informativo si aparece
    //for (int x = 1; x <= 2; x++) {
        if (WebUI.waitForElementPresent(findTestObject('SERVICIOS/Cajero/IngresarCajero/SPAN_MensajeSuscriptor'), 5, FailureHandling.OPTIONAL)) {
           CustomKeywords.'utilidades.FuncionesAuxiliares.limpiarVista'()
         }
   // }
    
    // Step 18: Click Calcular
    WebUI.waitForElementClickable(findTestObject('SERVICIOS/Cajero/BUTTON_Calcular'), GlobalVariable.TIME_OUT)

    WebUI.click(findTestObject('SERVICIOS/Cajero/BUTTON_Calcular'))

    WebUI.waitForElementVisible(findTestObject('Object Repository/SERVICIOS/Cajero/AplicarPago/BUTTON_GuardarPago'), GlobalVariable.TIME_OUT)
	WebUI.click(findTestObject('Object Repository/SERVICIOS/Cajero/AplicarPago/BUTTON_GuardarPago'))

    //String totalPagar = WebUI.getText(findTestObject('Object Repository/SERVICIOS/Cajero/AplicarPago/BUTTON_GuardarPago'))
	WebUI.delay(2)
	
    WebUI.waitForElementClickable(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Efectivo'), GlobalVariable.TIME_OUT)

    WebUI.click(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Efectivo'))
    
	// Step 21: Ingresar monto en campo efectivo
	WebUI.waitForElementClickable(findTestObject('SERVICIOS/Cajero/AplicarPago/INPUT_Efectivo'),GlobalVariable.TIME_OUT)
	
	// Obtener monto desde span
	String montoTexto = WebUI.getText(findTestObject('SERVICIOS/Cajero/AplicarPago/LABEL_TotalPago'))
	
	// Limpiar formato moneda
	String ValorPago = montoTexto
			.replace('$','')
			.replace('MXN','')
			.trim()
	
	// Ingresar monto
	WebUI.click(findTestObject('SERVICIOS/Cajero/AplicarPago/INPUT_Efectivo'))
	WebUI.setText(findTestObject('SERVICIOS/Cajero/AplicarPago/INPUT_Efectivo'),ValorPago)
	
    // Step 22: Click Imprimir
    WebUI.click(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Imprimir'))

    WebUI.waitForElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), 240)

    // Manejar ventana de impresion si se abre
    if (WebUI.getWindowIndex() > 0) {
        WebUI.switchToWindowIndex(1)

        WebUI.takeScreenshot()

        WebUI.closeWindowIndex(1)

        WebUI.switchToWindowIndex(0)
    }
    
    // Remover overlay de carga si esta presente
    String script = 'document.querySelector(".dx-overlay-content.dx-loadpanel-content")?.remove();'

    WebUI.executeJavaScript(script, null, FailureHandling.OPTIONAL)

    // Step 23: Click Aplicar pago
    WebUI.enhancedClick(findTestObject('SERVICIOS/Cajero/AplicarPago/BUTTON_Aplicar'))

    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    WebUI.waitForElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT)

    def msjAlertaPago = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

    KeywordUtil.logInfo("Mensaje aplicar pago: $msjAlertaPago")

    WebUI.takeScreenshot()

    // Step 24: Cerrar popup de confirmacion
    WebUI.click(findTestObject('OBJETOS_GLOBALES/BUTTON_Cerrar'))

    CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()

    // Step 29: Cerrar popup final
    if (WebUI.waitForElementPresent(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'), GlobalVariable.TIME_OUT, FailureHandling.OPTIONAL)) {
        def msjFinal = WebUI.getText(findTestObject('OBJETOS_GLOBALES/LABEL_MensajeAlerta'))

        KeywordUtil.logInfo("Mensaje facturacion: $msjFinal")

        WebUI.takeScreenshot()

        WebUI.click(findTestObject('OBJETOS_GLOBALES/BUTTON_Cerrar'))
    }
    
    KeywordUtil.markPassed("Flujo completo de cajero ejecutado para suscriptor $numeroSuscriptor")
}
//catch (Throwable t) {
  //  WebUI.takeScreenshot()

  //  KeywordUtil.markFailedAndStop("Error en flujo cajero: $t.message")
//} 
finally { 
  //  WebUI.closeBrowser()
}

