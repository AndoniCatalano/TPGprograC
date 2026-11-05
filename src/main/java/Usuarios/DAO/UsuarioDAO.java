package Usuarios.DAO;

import Usuarios.DTO.UsuarioDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    public static UsuarioDTO obtenerUsuario(String username) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        UsuarioDTO usuario = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new UsuarioDTO();
                usuario.setUsername(rs.getString("usuario"));
                usuario.setPassword(rs.getString("password"));
            }

        }
        finally {
            if(rs != null)
                rs.close();
            if(conn != null)
                conn.close();
            if(ps != null)
                ps.close();
        }
        return usuario;
    }

    public static void registrar(UsuarioDTO usuario) throws SQLException {
        String sql = "INSERT INTO usuarios VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Conexion.getConexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            throw new SQLException();
        }
        finally {
            if(ps != null)
                ps.close();
            if(conn != null)
                conn.close();
            if(ps != null)
                ps.close();
        }
    }
}
