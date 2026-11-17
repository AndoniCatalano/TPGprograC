package Usuarios.DAO;

import Usuarios.DTO.UsuarioDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    /**
     * Busca en la base de datos el usuario con el username indicado.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>username no nulo</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>Si existe un usuario con ese username, devuelve un UsuarioDTO con su username y su password
     *     (el hash almacenado, no el texto plano)</li>
     *     <li>Si no existe, devuelve null</li>
     *     <li>No se modifica la base de datos</li>
     * </ul>
     *
     * @param username Nombre de usuario a buscar
     * @return el UsuarioDTO encontrado, o null si no existe un usuario con ese username
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    public static UsuarioDTO obtenerUsuario(String username) throws SQLException {
        String sql = "SELECT username, password FROM usuarios WHERE username = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UsuarioDTO usuario = new UsuarioDTO();
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    return usuario;
                }
            }
        }
        return null;
    }


    /**
     * Inserta un nuevo usuario en la base de datos. El password del DTO se guarda tal cual lo recibe,
     * por lo que debe llegar ya hasheado.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>usuario no nulo</li>
     *     <li>usuario.getUsername() no nulo ni vacio</li>
     *     <li>usuario.getPassword() no nulo ni vacio, y ya hasheado (BCrypt)</li>
     *     <li>No existe en la base de datos otro usuario con el mismo username</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>Si el metodo termina sin lanzar excepciones, la base de datos contiene un usuario
     *     con el username y el password del DTO recibido</li>
     *     <li>Si lanza SQLException, la base de datos no se modifica</li>
     *     <li>No se modifica el DTO recibido</li>
     * </ul>
     *
     * @param usuario DTO con el username y el password (hasheado) a registrar
     * @throws SQLException si ocurre un error al acceder a la base de datos, o si ya existe
     *         un usuario con ese username (viola la clave primaria)
     */
    public static void registrar(UsuarioDTO usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.executeUpdate();
        }
    }
}
