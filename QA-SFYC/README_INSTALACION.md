# Agente QA Katalon - Paquete de instalacion

Este paquete contiene los archivos para montar el agente de QA sobre tu proyecto
`Web_KS_NombreProyecto` (Katalon 10.4.3).

## Contenido

```
agente-katalon/
├── CLAUDE.md                         <- instrucciones del agente (raiz del proyecto)
├── .claude/
│   └── settings.json                 <- permisos de Claude Code (raiz del proyecto)
├── Keywords/
│   └── db/
│       └── QueryKeyword.groovy       <- keyword para SQL Server
├── Profiles/
│   └── qa.glbl                       <- profile QA explicito
└── README_INSTALACION.md             <- este archivo
```

## Instalacion paso a paso

### 1. Copiar archivos al proyecto

Copia cada archivo a su ubicacion final respetando la estructura:

| Origen | Destino en tu proyecto Katalon |
|---|---|
| `CLAUDE.md` | raiz del proyecto (junto al `.prj`) |
| `.claude/settings.json` | `.claude/settings.json` en raiz |
| `Keywords/db/QueryKeyword.groovy` | `Keywords/db/QueryKeyword.groovy` |
| `Profiles/qa.glbl` | `Profiles/qa.glbl` |

Nota: tu proyecto ya tiene un `CLAUDE.md`. Revisa si puedes reemplazarlo o si
quieres hacer merge del contenido.

### 2. Descargar el driver JDBC de SQL Server

1. Ve a `https://learn.microsoft.com/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server`
2. Descarga la version `.jre8.jar` (compatible con Katalon).
3. Colocalo en `Drivers/` del proyecto (ya existe esa carpeta).

Ejemplo de nombre: `mssql-jdbc-12.8.1.jre8.jar`.

### 3. Configurar SQL Server en los profiles

En Katalon Studio:

1. Abre `Profiles → default` (y `dev`, y el nuevo `qa`).
2. Agrega las variables globales que estan en `qa.glbl` al final:
   - `SQL_SERVER_HOST`
   - `SQL_SERVER_PORT`
   - `SQL_SERVER_DB`
   - `SQL_SERVER_USER`
   - `SQL_SERVER_PASSWORD`
3. Para cifrar el password:
   - En Katalon: `Tools → Encrypt Text`
   - Pega el password en claro, copia el resultado y pegalo en `SQL_SERVER_PASSWORD`.

### 4. Refrescar variables globales en Katalon

Cada vez que modificas un `.glbl`, Katalon regenera `Libs/internal/GlobalVariable.groovy`.
Haz click derecho sobre `Profiles` > `Refresh` si no lo ves.

### 5. Probar el Keyword de SQL Server

Crea un Test Case temporal con este contenido para validar conectividad:

```groovy
import com.kms.katalon.core.util.KeywordUtil

List<Map> filas = CustomKeywords.'db.QueryKeyword.getTestData'("SELECT TOP 1 1 AS ok")
KeywordUtil.logInfo("Conexion SQL Server: " + filas)
```

Si ves `[[ok:1]]` en el log, la conexion funciona.

### 6. Cuando el admin habilite Claude Code

```powershell
npm install -g @anthropic-ai/claude-code
cd C:\ruta\a\Web_KS_NombreProyecto
claude
```

Claude Code leera automaticamente el `CLAUDE.md` y los `settings.json` y ya sabra
tu stack, convenciones y reglas.

## Verificacion

Dentro de Claude Code, como primer prompt escribe:

```
Lee CLAUDE.md y dame un resumen de:
1. Los 5 modulos del proyecto.
2. La convencion de naming de Test Cases.
3. Las reglas de seguridad que no debes violar.
```

Si responde correctamente los tres puntos, el agente ya esta calibrado.

## Siguiente paso recomendado

Pide al agente:

```
Usando TC-003_AgregarQueja como caso dorado, genera el plan (solo el plan, aun no
escribas archivos) de 5 Test Cases nuevos para el modulo AGENDA que cubran:
- agregar queja administrativa (caso positivo)
- agregar queja profeco (caso positivo)
- validar campos obligatorios en el formulario
- cancelar el formulario sin guardar
- suscriptor inexistente
```

Revisas el plan, das OK, y ahi si le pides que genere los `.groovy` y `.tc`.
