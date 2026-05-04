# TC-007 - Caso completo de ejemplo

Este paquete contiene un Test Case end-to-end generado siguiendo las reglas del
`CLAUDE.md`. Es lo que el agente produciria automaticamente cuando le pidas:

> "Genera una variante del TC-003 que tome el suscriptor desde SQL Server."

## Que contiene

```
caso-completo/
├── Test Cases/AGENDA/Contratos/
│   └── TC-007_AgregarQuejaAdministrativa_DesdeBD.tc        <- metadata + variables
├── Scripts/groovy/AGENDA/Contratos/
│   └── TC-007_AgregarQuejaAdministrativa_DesdeBD.groovy    <- logica del caso
├── Object Repository/AGENDA/QuejasAdm/FormQuejasAdministrativas/
│   └── SPAN_QuejaCreadaExito.rs                            <- objeto nuevo
├── Test Suites/
│   └── Regresion_Agenda_Quejas.ts                          <- agrupa TC-003 y TC-007
├── Data Files/AGENDA/
│   └── SuscriptoresQueja_TC007.csv                         <- fallback si SQL no esta
└── sql/
    └── setup_tbl_suscriptores_test.sql                     <- crea tabla + seed
```

## Como instalarlo en tu proyecto

1. **Copia los archivos respetando la estructura** dentro de tu proyecto Katalon.
2. **Ejecuta el SQL** una sola vez en tu BD `QA_DATA`:
   ```sql
   :r sql/setup_tbl_suscriptores_test.sql
   ```
   (o pegalo en SSMS / Azure Data Studio).
3. **Refresca Katalon Studio** (F5 sobre el proyecto).
4. **Ejecuta** el TC-007 desde Katalon o por linea de comandos:
   ```
   katalonc -noSplash -runMode=console ^
     -projectPath="Web_KS_NombreProyecto.prj" ^
     -executionProfile="default" ^
     -testSuitePath="Test Suites/Regresion_Agenda_Quejas" ^
     -browserType="Chrome"
   ```

## Que hace el TC-007 (paso a paso)

1. **Resuelve suscriptor**: consulta SQL Server con `db.QueryKeyword.getFirstRow`.
   Si la BD falla o devuelve vacio, hace fallback al `0411627544` del TC-003.
2. **Valida parametro**: solo acepta `tipoQueja in ['profeco','administrativa']`.
3. **Login**: usa `GlobalVariable.URL_AGENDA`, `USER_AGENDA`, `PASSWORD_AGENDA`.
4. **Busca suscriptor**: en la pagina de inicio.
5. **Abre formulario**: clic en `BUTTON_AgregarQuejaAdministrativa`.
6. **Llena formulario**: reporta, telefono, motivo (con timestamp para trazabilidad)
   y selecciona el tipo correcto.
7. **Envia y valida**: espera el toast de exito; si no aparece, screenshot + fail.
8. **Cleanup**: `closeBrowser()` siempre, en `finally`.

## Variables del TC

| Variable | Default | Tipo | Para que sirve |
|---|---|---|---|
| `tipoQueja` | `'administrativa'` | String | Cambia entre 'administrativa' y 'profeco' |
| `reporta` | `'Pruebas agente'` | String | Nombre que se llena en el form |
| `obtenerSuscriptorDeBD` | `true` | Boolean | Si false, usa fallback hardcodeado |

## Patrones que muestra este caso (clave para el agente)

1. **Try/finally** para garantizar cierre del navegador incluso ante error.
2. **Fallback graceful**: si SQL falla, no se cae el caso, sigue con datos default.
3. **Validacion de inputs**: `markFailedAndStop` antes de hacer trabajo si los datos
   son invalidos.
4. **Mensajes de log descriptivos**: `markPassed` / `markFailed` con contexto util
   (suscriptor, tipo de queja, etc.).
5. **Screenshot solo en fallo**: no satura el reporte.
6. **Timestamps en datos generados**: facilita trazabilidad cruzada con la BD real
   ("la queja con motivo '... 2026-05-03 14:32' es la que genero este run").
7. **Uso de FailureHandling.OPTIONAL**: para validaciones que no deben detener el
   flujo abruptamente (el toast).

## Que faltaria para hacerlo "produccion"

- Crear los Test Objects de LOGIN si aun no existen exactamente con esos nombres
  (vi que tienes `LOGIN/INPUT_User`, etc., asi que deberia funcionar tal cual).
- Verificar el selector real del toast contra el HTML de la app. El XPath de
  `SPAN_QuejaCreadaExito.rs` es una propuesta; si la app usa otra clase CSS,
  ajustar el selector.
- Confirmar que el `SELECT_Tipo` acepta los textos `'Profeco'` / `'Administrativa'`
  con esa capitalizacion. Si no, ajustar la linea de `selectOptionByLabel`.

Estas cosas son las que el agente, en una sesion real con Claude Code + Playwright
MCP, descubriria abriendo la URL y leyendo el DOM. En este ejemplo las dejo como
"hipotesis razonable" basada en lo que vi en tu Object Repository.

---

# Test Cases Generados por Modulo (TC-070 a TC-116)

Batch de **34 Test Cases** generados automaticamente a partir del analisis del
repositorio de desarrollo (`sfyc2/frontend`). Cada TC incluye:

- Archivo `.tc` (metadata XML para Katalon)
- Script `.groovy` (logica de prueba)
- Test Suite de regresion por modulo

## AGENDA (TC-070 a TC-074)

| TC | Nombre | Descripcion |
|---|---|---|
| TC-070 | ConsultarSuscriptorPorId | Consulta de suscriptor por numero de ID |
| TC-071 | ConsultarSuscriptorPorNombre | Consulta de suscriptor por nombre completo |
| TC-072 | ModificarQueja | Modificar datos de una queja administrativa existente |
| TC-073 | CancelarQuejaAdministrativa | Cancelar queja seleccionando causa y confirmando |
| TC-074 | ConsultaOrdenesTrabajoMtto | Consultar ordenes de trabajo de mantenimiento |

**Ubicacion:** `Test Cases/AGENDA/Contratos/`
**Suite:** `Test Suites/Regresion_Agenda_Generados.ts`

## MATERIALES (TC-080 a TC-085)

| TC | Nombre | Descripcion |
|---|---|---|
| TC-080 | EntregaDeMaterial | Navegacion y validacion del modulo de entrega |
| TC-081 | RequisicionDeMaterial | Acceso al modulo de requisiciones |
| TC-082 | DevolucionDeMaterial | Flujo de devolucion de material al almacen |
| TC-083 | LiquidacionDeMaterial | Liquidacion de material entregado |
| TC-084 | OrdenesDeTrabajoMaterial | Consulta de ordenes de trabajo en materiales |
| TC-085 | AclaracionDeMaterial | Flujo de aclaracion de material |

**Ubicacion:** `Test Cases/MATERIALES/`
**Suite:** `Test Suites/Regresion_Materiales_Generados.ts`

## EQUIPOS (TC-090 a TC-096)

| TC | Nombre | Descripcion |
|---|---|---|
| TC-090 | RegistrarMovimiento | Registro de movimiento de equipo |
| TC-091 | AsignarEquipoCuadrilla | Asignacion de equipo a cuadrilla |
| TC-092 | CambioMasivoUbicacion | Cambio masivo de ubicacion de equipos |
| TC-093 | ConsultarUbicacionEquipo | Consulta de ubicacion de un equipo |
| TC-094 | SolicitudTraspaso | Solicitud de traspaso de equipo |
| TC-095 | RegistrarSerie | Registro de serie de equipo |
| TC-096 | PalletId | Gestion de Pallet ID |

**Ubicacion:** `Test Cases/EQUIPOS/`
**Suite:** `Test Suites/Regresion_Equipos_Generados.ts`

## SERVICIOS (TC-100 a TC-108)

| TC | Nombre | Descripcion |
|---|---|---|
| TC-100 | CajeroConsultarSuscriptor | Consulta de suscriptor en modulo Cajero |
| TC-101 | CajeroPago | Registro de pago en Cajero |
| TC-102 | CajeroCancelarPago | Cancelacion de pago en Cajero |
| TC-103 | BuscarContrato | Busqueda de contrato existente |
| TC-104 | NuevoContrato | Acceso al formulario de nuevo contrato |
| TC-105 | PapeletaCompleta | Modulo de papeletas con multiples tabs |
| TC-106 | DepositoBancario | Registro de deposito bancario |
| TC-107 | CorteDeCaja | Consulta de corte de caja |
| TC-108 | SupervisionCajero | Supervision de cajeros |

**Ubicacion:** `Test Cases/SERVICIOS/`
**Suite:** `Test Suites/Regresion_Servicios_Generados.ts`

## VENTAS (TC-110 a TC-116)

| TC | Nombre | Descripcion |
|---|---|---|
| TC-110 | ConsultarSolicitudesVenta | Consulta de solicitudes de venta |
| TC-111 | BusquedaAvanzadaVentas | Busqueda avanzada de solicitudes |
| TC-112 | CancelarSolicitudVenta | Cancelacion de solicitud de venta |
| TC-113 | AsignarSolicitudVendedor | Asignacion de solicitud a vendedor |
| TC-114 | TransferirSolicitud | Transferencia de solicitud de venta |
| TC-115 | CatalogoHombroAHombro | Catalogo de vendedores hombro a hombro |
| TC-116 | BitacoraVentas | Consulta de bitacora de ventas |

**Ubicacion:** `Test Cases/VENTAS/`
**Suite:** `Test Suites/Regresion_Ventas_Generados.ts`

## Como ejecutar un suite de regresion

```
katalonc -noSplash -runMode=console ^
  -projectPath="Web_KS_NombreProyecto.prj" ^
  -executionProfile="default" ^
  -testSuitePath="Test Suites/Regresion_Equipos_Generados" ^
  -browserType="Chrome"
```

Cambiar `Regresion_Equipos_Generados` por el suite deseado.

## Convenciones aplicadas

- Login via `FuncionesAuxiliares.login(url, user, password)`
- Navegacion via `FuncionesAuxiliares.ingresarMenu(modulo, opcion, subopcion)`
- Esperas via `FuncionesAuxiliares.esperarCargaContenido()`
- Credenciales/URLs via `GlobalVariable.*` (nunca hardcodeadas)
- Timeouts via `GlobalVariable.TIME_OUT`
- Test Objects con prefijo `'Object Repository/'` en `findTestObject()`
- Sin `try/finally` ni `closeBrowser()` (compatibles con ejecucion en suite)
