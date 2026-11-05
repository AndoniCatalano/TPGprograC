package Usuarios.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
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
        crearTablaSiNoExiste(conn);
        return conn;
    }

    private static void crearTablaSiNoExiste(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla automáticamente: " + e.getMessage());
        }
    }
}