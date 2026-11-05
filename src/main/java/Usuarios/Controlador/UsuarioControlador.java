package Usuarios.Controlador;

import Usuarios.Exceptions.DatosIncorrectosException;
import Usuarios.Exceptions.UsernameOcupadoException;
import Usuarios.Vista.UsuarioVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UsuarioControlador implements ActionListener {
    UsuarioVista vista;
    public UsuarioControlador(UsuarioVista vista) {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String username = vista.getUsername();
            String password = vista.getPassword();

            if (username.isEmpty() || password.isEmpty())
                throw new DatosIncorrectosException();
            else {
                Usuarios.Modelo.Usuario usuario = new Usuarios.Modelo.Usuario(username, password);
                switch (e.getActionCommand()) {
                    case "LOGIN":
                        usuario.login();
                        vista.setMensaje("Usuario Logueado");
                        break;

                    case  "REGISTRAR":
                        usuario.register();
                        vista.setMensaje("Usuario registrado exitosamente");
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        catch(DatosIncorrectosException ex){
            vista.setMensaje("Datos incorrectos!");
        }
        catch (UsernameOcupadoException ex){
            vista.setMensaje("Usuario ocupado!");
        }
        catch (SQLException ex){
            vista.setMensaje("Error en la base de datos!");
        }

    }
}