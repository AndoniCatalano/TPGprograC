package Vista;

import Controlador.CUsuarios;

public interface IVUsuarios {
    public final String LOGIN = "LOGIN";
    public final String REGISTRAR = "REGISTRAR";

    void arrancar();
    void setController(CUsuarios controller);
    void avisarError(String mensaje);
    String getUsername();
    String getPassword();

}
