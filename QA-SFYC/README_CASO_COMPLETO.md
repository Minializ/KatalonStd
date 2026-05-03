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
