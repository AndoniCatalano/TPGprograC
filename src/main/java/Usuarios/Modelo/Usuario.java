package Usuarios.Modelo;

import Usuarios.Exceptions.DatosIncorrectosException;
import Usuarios.Exceptions.UsernameOcupadoException;
import Usuarios.DAO.UsuarioDAO;
import Usuarios.DTO.UsuarioDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class Usuario {
    private final String username;
    private final String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    public void register() throws UsernameOcupadoException, SQLException {
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
