package Usuarios.Vista;

import Usuarios.Controlador.UsuarioControlador;

public class MainUsuarios {
    public static void main(String[] args) {
        UsuarioVista vista = new UsuarioVista();
        UsuarioControlador controller = new UsuarioControlador(vista);
        vista.setController(controller);
        vista.arrancar();
    }
}