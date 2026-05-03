package db

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GlobalVariable

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.SQLException
import java.sql.Statement

import com.kms.katalon.core.configuration.RunConfiguration

/**
 * QueryKeyword
 * ----------------------------------------------------------------------------
 * Utilidades para consultar y operar sobre SQL Server desde Test Cases de Katalon.
 *
 * Requisitos:
 *   - Driver JDBC de Microsoft en Drivers/ (mssql-jdbc-<version>.jre8.jar o jre11).
 *   - Las siguientes GlobalVariable por profile:
 *       SQL_SERVER_HOST      (String, ej: 'sql01.qa.sfycnextgen.com.mx')
 *       SQL_SERVER_PORT      (Number, ej: 1433)
 *       SQL_SERVER_DB        (String, ej: 'QA_DATA')
 *       SQL_SERVER_USER      (String)
 *       SQL_SERVER_PASSWORD  (String, cifrado con Katalon Encryption)
 *
 * Uso tipico:
 *   List<Map> filas  = CustomKeywords.'db.QueryKeyword.getTestData'(
 *       "SELECT TOP 1 suscriptor, tipo FROM tbl_quejas WHERE estado = ?",
 *       ['ACTIVO'])
 *
 *   String folio     = CustomKeywords.'db.QueryKeyword.getSingleValue'(
 *       "SELECT MAX(folio) FROM tbl_quejas")
 *
 *   int afectados    = CustomKeywords.'db.QueryKeyword.executeUpdate'(
 *       "UPDATE tbl_quejas SET estado='CERRADO' WHERE folio=?", [1234])
 *       // requiere confirmacion del usuario en el flujo del agente.
 */
class QueryKeyword {

    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver"

    // ------------------------------------------------------------------------
    // Conexion
    // ------------------------------------------------------------------------

    /**
     * Abre y devuelve una conexion JDBC a SQL Server usando las GlobalVariable
     * del profile activo. El caller es responsable de cerrarla (o usar los
     * helpers getTestData / getSingleValue / executeUpdate que la cierran solos).
     */
    @Keyword
    static Connection getConnection() {
        try {
            Class.forName(DRIVER)
        } catch (ClassNotFoundException cnfe) {
            KeywordUtil.markFailedAndStop(
                "Driver JDBC de SQL Server no encontrado. " +
                "Copia mssql-jdbc-<ver>.jre8.jar a la carpeta Drivers/ del proyecto."
            )
        }

        String host = String.valueOf(GlobalVariable.SQL_SERVER_HOST)
        String port = String.valueOf(GlobalVariable.SQL_SERVER_PORT)
        String db   = String.valueOf(GlobalVariable.SQL_SERVER_DB)
        String user = String.valueOf(GlobalVariable.SQL_SERVER_USER)
        // Katalon descifra automaticamente las variables con protected=true
        String pass = String.valueOf(GlobalVariable.SQL_SERVER_PASSWORD)

        String url = "jdbc:sqlserver://${host}:${port};" +
                     "databaseName=${db};" +
                     "encrypt=true;" +
                     "trustServerCertificate=true;" +
                     "loginTimeout=15;"

        try {
            Connection conn = DriverManager.getConnection(url, user, pass)
            KeywordUtil.logInfo("Conexion a SQL Server abierta: ${host}/${db}")
            return conn
        } catch (SQLException se) {
            KeywordUtil.markFailedAndStop(
                "No se pudo abrir conexion a SQL Server (${host}/${db}): ${se.message}"
            )
        }
        return null
    }

    // ------------------------------------------------------------------------
    // Lectura (SELECT)
    // ------------------------------------------------------------------------

    /**
     * Ejecuta un SELECT y devuelve todas las filas como List<Map<String,Object>>.
     * Cada Map tiene como llaves los nombres de columna en minusculas.
     *
     * Si se pasan params, se usa PreparedStatement para evitar SQL injection.
     * Las queries que no empiecen con SELECT son bloqueadas (usar executeUpdate).
     */
    @Keyword
    static List<Map<String, Object>> getTestData(String sql, List params = []) {
        validateReadOnly(sql)

        Connection conn = null
        PreparedStatement ps = null
        ResultSet rs = null
        List<Map<String, Object>> result = []

        try {
            conn = getConnection()
            ps = conn.prepareStatement(sql)
            bindParams(ps, params)
            rs = ps.executeQuery()

            ResultSetMetaData meta = rs.getMetaData()
            int cols = meta.getColumnCount()

            while (rs.next()) {
                Map<String, Object> row = [:]
                for (int i = 1; i <= cols; i++) {
                    row[meta.getColumnLabel(i).toLowerCase()] = rs.getObject(i)
                }
                result << row
            }
            KeywordUtil.logInfo("Query OK, ${result.size()} fila(s): ${truncate(sql)}")
            return result

        } catch (SQLException se) {
            KeywordUtil.markFailed("Error en getTestData: ${se.message}. SQL: ${truncate(sql)}")
            throw se
        } finally {
            closeQuietly(rs, ps, conn)
        }
    }

    /**
     * Helper para cuando solo quieres un unico valor (MAX, COUNT, un ID, etc.).
     * Devuelve el valor de la primera columna de la primera fila, o null.
     */
    @Keyword
    static Object getSingleValue(String sql, List params = []) {
        List<Map<String, Object>> rows = getTestData(sql, params)
        if (rows.isEmpty()) return null
        Map first = rows[0]
        return first.values().iterator().next()
    }

    /**
     * Helper para cuando quieres solo la primera fila como Map.
     * Devuelve null si no hay resultados.
     */
    @Keyword
    static Map<String, Object> getFirstRow(String sql, List params = []) {
        List<Map<String, Object>> rows = getTestData(sql, params)
        return rows.isEmpty() ? null : rows[0]
    }

    // ------------------------------------------------------------------------
    // Escritura (INSERT / UPDATE / DELETE) - uso restringido
    // ------------------------------------------------------------------------

    /**
     * Ejecuta un INSERT / UPDERATE / DELETE y devuelve el numero de filas afectadas.
     *
     * IMPORTANTE: Por politica del agente, este metodo solo debe llamarse cuando
     * el usuario ha autorizado explicitamente la operacion. No usar en Test Cases
     * generados automaticamente sin revision humana.
     */
    @Keyword
    static int executeUpdate(String sql, List params = []) {
        if (sql == null || sql.trim().isEmpty()) {
            KeywordUtil.markFailedAndStop("SQL vacio en executeUpdate")
        }

        String trimmed = sql.trim().toUpperCase()
        if (trimmed.startsWith("SELECT")) {
            KeywordUtil.markFailedAndStop("Usa getTestData para SELECT, no executeUpdate.")
        }
        if (trimmed.startsWith("DROP") || trimmed.startsWith("TRUNCATE")) {
            KeywordUtil.markFailedAndStop(
                "DROP / TRUNCATE bloqueados por politica de seguridad del agente."
            )
        }

        Connection conn = null
        PreparedStatement ps = null
        try {
            conn = getConnection()
            conn.setAutoCommit(false)
            ps = conn.prepareStatement(sql)
            bindParams(ps, params)
            int affected = ps.executeUpdate()
            conn.commit()
            KeywordUtil.logInfo("Update OK, ${affected} fila(s) afectada(s): ${truncate(sql)}")
            return affected

        } catch (SQLException se) {
            try { if (conn != null) conn.rollback() } catch (Exception ignore) {}
            KeywordUtil.markFailed("Error en executeUpdate: ${se.message}. SQL: ${truncate(sql)}")
            throw se
        } finally {
            closeQuietly(null, ps, conn)
        }
    }

    // ------------------------------------------------------------------------
    // Helpers internos
    // ------------------------------------------------------------------------

    private static void validateReadOnly(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            KeywordUtil.markFailedAndStop("SQL vacio en getTestData")
        }
        String t = sql.trim().toUpperCase()
        if (!t.startsWith("SELECT") && !t.startsWith("WITH") && !t.startsWith("EXEC")) {
            KeywordUtil.markFailedAndStop(
                "getTestData solo permite SELECT / WITH / EXEC (stored procs de lectura). " +
                "SQL recibido: ${truncate(sql)}"
            )
        }
    }

    private static void bindParams(PreparedStatement ps, List params) {
        if (params == null) return
        params.eachWithIndex { value, i ->
            ps.setObject(i + 1, value)
        }
    }

    private static void closeQuietly(ResultSet rs, Statement st, Connection conn) {
        try { if (rs != null) rs.close() } catch (Exception ignore) {}
        try { if (st != null) st.close() } catch (Exception ignore) {}
        try { if (conn != null) conn.close() } catch (Exception ignore) {}
    }

    private static String truncate(String s) {
        if (s == null) return ""
        return s.length() <= 120 ? s : s.substring(0, 117) + "..."
    }
}
