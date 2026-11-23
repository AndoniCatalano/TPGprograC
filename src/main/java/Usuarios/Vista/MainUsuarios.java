package Usuarios.Vista;

import Usuarios.Controlador.UsuarioControlador;

import javax.swing.SwingUtilities;

public class MainUsuarios {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioVista vista = new UsuarioVista();
            UsuarioControlador controller = new UsuarioControlador(vista);
            vista.setController(controller);
            vista.arrancar();
        });
    }
}