package Usuarios.Modelo;

import Usuarios.Exceptions.DatosIncorrectosException;
import Usuarios.Exceptions.UsernameOcupadoException;
import Usuarios.DAO.UsuarioDAO;
import Usuarios.DTO.UsuarioDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;


/**
 * <p><b>Invariantes de clase: </b></p>
 * <ul>
 *     <li>username no debe ser nulo ni vacio </li>
 *     <li>password no debe ser nulo ni vacio</li>
 * </ul>
 */
public class Usuario {
    private final String username;
    private final String password;

    /**
     *
     * @param username Nombre de usuario
     * @param password Password en texto plano
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>username no nulo ni vacio</li>
     *     <li>password no nulo ni vacio</li>
     * </ul>
     *
     */
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }


    /**
     * Realiza el inicio de sesion validando las credenciales contra la base de datos.
     * El password en texto plano se compara con el hash BCrypt almacenado.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>El objeto cumple los invariantes de clase</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>Si el metodo termina sin lanzar excepciones, existe un usuario registrado con ese
     *     username y su password coincide con el hash almacenado</li>
     *     <li>No se modifica la base de datos ni el estado del objeto</li>
     * </ul>
     *
     * @throws DatosIncorrectosException si el par username - password no existe o no coincide
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    public void login() throws DatosIncorrectosException, SQLException {
        try {
            UsuarioDTO usuarioDB = UsuarioDAO.obtenerUsuario(username);

            if(usuarioDB == null || !BCrypt.checkpw(password, usuarioDB.getPassword())) {
                throw new DatosIncorrectosException();
            }
        }
        catch (SQLException ex) {
            throw new SQLException();
        }
    }


    /**
     * Registra al usuario en la base de datos. El password se almacena como hash BCrypt,
     * nunca en texto plano.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>El objeto cumple los invariantes de clase</li>
     *     <li>No existe en la base de datos otro usuario con el mismo username</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>Si el metodo termina sin lanzar excepciones, la base de datos contiene un usuario
     *     con este username y con el hash BCrypt del password</li>
     *     <li>Si lanza UsernameOcupadoException, la base de datos no se modifica</li>
     * </ul>
     *
     * @throws UsernameOcupadoException si ya existe un usuario con ese username
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    public void registrar() throws UsernameOcupadoException, SQLException {
        UsuarioDTO usuarioDB = UsuarioDAO.obtenerUsuario(username);

        if (usuarioDB != null) {
            throw new UsernameOcupadoException();
        } else {
            String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsername(username);
            usuario.setPassword(passwordHash);
            UsuarioDAO.registrar(usuario);
        }
    }

}
