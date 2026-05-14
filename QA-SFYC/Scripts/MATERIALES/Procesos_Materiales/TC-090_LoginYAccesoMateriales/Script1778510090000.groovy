/**
 * ID:            TC-090_LoginYAccesoMateriales
 * Modulo:        MATERIALES
 * Objetivo:      Validar login al modulo de Materiales y acceso a cada submodulo del menu.
 * Precondiciones: Usuario con acceso a MATERIALES y permisos en todos los submodulos.
 * Datos:         GlobalVariable.URL_MATERIALES, USER_MATERIALES, PASSWORD_MATERIALES.
 * Resultado esperado: Cada submodulo del menu carga correctamente sin errores.
 * Autor:         Agente QA (generado)
 * Fecha:         2026-05-14
 */
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.comment('INICIO TC-090_LoginYAccesoMateriales')

// 1) Validar que el login fue exitoso (el Listener ya ejecuto login)
WebUI.verifyElementPresent(
    findTestObject('Object Repository/OBJETOS_GLOBALES/IMG_LogoMega'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Login exitoso - Logo Megacable visible')

// 2) Navegar a Entregas
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Entregas', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Submodulo Entregas accesible')

// 3) Navegar a Devoluciones
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Devoluciones', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Submodulo Devoluciones accesible')

// 4) Navegar a Liquidaciones
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Liquidaciones', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Entregas/SELECT_TipoCuadrilla'),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Submodulo Liquidaciones accesible')

// 5) Navegar a Aclaraciones
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Aclaraciones', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'ACLARACIÓN DE MATERIALES']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Submodulo Aclaraciones accesible')

// 6) Navegar a Traspaso de inventarios
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Traspaso de inventarios', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'TRASPASO DE INVENTARIOS']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Submodulo Traspaso de inventarios accesible')

// 7) Navegar a Recuperacion de materiales
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Recuperación materiales', '')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'RECUPERACIÓN DE MATERIALES']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Submodulo Recuperacion de materiales accesible')

// 8) Navegar a Catalogos > Stock de materiales
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Catálogos', 'Stock de materiales')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'STOCK DE MATERIALES']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Submodulo Stock de materiales accesible')

// 9) Navegar a Catalogos > Cuadrillas
CustomKeywords.'utilidades.FuncionesAuxiliares.ingresarMenu'('Materiales', 'Catálogos', 'Cuadrillas')
CustomKeywords.'utilidades.FuncionesAuxiliares.esperarCargaContenido'()
WebUI.verifyElementPresent(
    findTestObject('Object Repository/MATERIALES/Procesos/LABEL_TituloPagina-Dyn',
        [('titulo') : 'CATÁLOGO DE CUADRILLAS']),
    GlobalVariable.TIME_OUT, FailureHandling.STOP_ON_FAILURE)
WebUI.comment('Submodulo Catalogo de Cuadrillas accesible')

WebUI.comment('FIN TC-090 - Todos los submodulos de MATERIALES accesibles correctamente')
