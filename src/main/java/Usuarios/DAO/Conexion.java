package Usuarios.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private static final String URL = "jdbc:sqlite:usuarios.db";

    public static Connection getConexion() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        crearTabla(conn);
        return conn;
    }

    private static void crearTabla(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}