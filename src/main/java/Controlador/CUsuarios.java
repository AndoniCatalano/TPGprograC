package Controlador;

import Exceptions.DatosIncorrectosException;
import Exceptions.UsernameOcupadoException;
import Vista.VUsuarios;
import dao.UsuarioDAO;
import dto.UsuarioDTO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CUsuarios implements ActionListener {
    private final VUsuarios vista;
    private final UsuarioDAO dao;

    public CUsuarios(VUsuarios vista) {
        this.vista = vista;
        dao = new UsuarioDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = vista.getUsername();
        String password = vista.getPassword();

        UsuarioDTO usuario = new UsuarioDTO(username, password);
        try {
            switch (e.getActionCommand()) {
                case "LOGIN":
                    login(usuario);
                    break;
                case "REGISTRAR":
                    registrar(usuario);
                    break;
                default:break;
            }
        }
        catch (DatosIncorrectosException ex) {
            vista.avisarError("Datos incorrectos");
        }
        catch (UsernameOcupadoException ex) {
            vista.avisarError("Usuario ocupado");
        }
    }

    public void login(UsuarioDTO usuario) throws DatosIncorrectosException {
        if(dao.verificarUsuario()){
            //vista2.arrancar();
        } else {
            throw new DatosIncorrectosException();
        }
    }

    public void registrar(UsuarioDTO usuario) throws UsernameOcupadoException {
        if (dao.obtenerUsuario(usuario.getUsername()) == null) {
            dao.registrar(usuario);
        } else {
            throw new UsernameOcupadoException();
        }
    }
}
