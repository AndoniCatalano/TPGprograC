package Usuarios.Controlador;

import Creacion.Controlador.ControladorCreacion;
import Usuarios.Exceptions.DatosIncorrectosException;
import Usuarios.Exceptions.UsernameOcupadoException;
import Usuarios.Vista.UsuarioVista;
import Usuarios.Modelo.Usuario;
import Creacion.Vista.VistaCreacion;
import Creacion.Modelo.ModeloCreacion;
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
                Usuario usuario = new Usuario(username, password);
                switch (e.getActionCommand()) {
                    case "LOGIN":
                        usuario.login();
                        ControladorCreacion controladorCreacion = new ControladorCreacion();
                        vista.dispose();

                        break;

                    case  "REGISTRAR":
                        usuario.registrar();
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
            vista.setMensaje("Error en la base de datos: " + ex.getMessage());
        }

    }
    
    private void abrirPantallaCreacion() {
        ModeloCreacion modelo = new ModeloCreacion();

        VistaCreacion vistaCreacion = new VistaCreacion(modelo);
        ControladorCreacion controlador = new ControladorCreacion(vistaCreacion, modelo);

        vistaCreacion.setControlador(controlador);
        vistaCreacion.arrancar();

        this.vista.dispose();
    }
}
