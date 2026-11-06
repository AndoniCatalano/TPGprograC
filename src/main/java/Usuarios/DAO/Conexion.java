package Usuarios.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private static final String URL = "jdbc:sqlite:usuarios.db";

    public static Connection getConexion() throws SQLException {
        try {
            // Forzar la carga del driver de SQLite
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se pudo cargar el driver de SQLite", e);
        }

        Connection conn = DriverManager.getConnection(URL);
        try {
            crearTablaSiNoExiste(conn);
            return conn;
        } catch (SQLException ex) {
            try {
                conn.close();
            } catch (SQLException closeException) {
                ex.addSuppressed(closeException);
            }
            throw ex;
        }
    }

    private static void crearTablaSiNoExiste(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }

        boolean usernameExists = false;
        String oldUsernameColumn = null;
        try (Statement stmt = conn.createStatement();
             ResultSet columns = stmt.executeQuery("PRAGMA table_info(usuarios)")) {
            while (columns.next()) {
                String column = columns.getString("name");
                if ("username".equalsIgnoreCase(column)) {
                    usernameExists = true;
                } else if ("usuario".equalsIgnoreCase(column) || "usuarios".equalsIgnoreCase(column)) {
                    oldUsernameColumn = column;
                }
            }
        }

        if (!usernameExists) {
            if (oldUsernameColumn == null) {
                throw new SQLException("La tabla usuarios no contiene la columna username ni una columna anterior reconocida");
            }
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("ALTER TABLE usuarios RENAME COLUMN " + oldUsernameColumn + " TO username");
            }
        }
    }
}