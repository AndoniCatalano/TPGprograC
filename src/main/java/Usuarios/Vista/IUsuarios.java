package Usuarios.Vista;

import Usuarios.Controlador.UsuarioControlador;

public interface IUsuarios {
    public final String LOGIN = "LOGIN";
    public final String REGISTRAR = "REGISTRAR";

    void arrancar();
    void setController(UsuarioControlador controller);
    void setMensaje(String mensaje);
    String getUsername();
    String getPassword();

}
