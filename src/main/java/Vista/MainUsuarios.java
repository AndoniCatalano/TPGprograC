package Vista;

import Controlador.CUsuarios;

public class MainUsuarios {
    public static void main(String[] args) {
        VUsuarios vista = new VUsuarios();
        CUsuarios controller = new CUsuarios(vista);
        vista.setController(controller);
        vista.arrancar();
    }
}