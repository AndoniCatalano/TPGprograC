import Usuarios.Controlador.UsuarioControlador;
import Usuarios.Vista.UsuarioVista;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioVista vista = new UsuarioVista();
            UsuarioControlador controller = new UsuarioControlador(vista);
            vista.setController(controller);
            vista.arrancar();
        });
    }
}