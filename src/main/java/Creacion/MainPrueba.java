package Creacion;

import Creacion.Modelo.ModeloCreacion;
import Creacion.Vista.VistaCreacion;
import Creacion.Controlador.ControladorCreacion;

public class MainPrueba {
    public static void main(String[] args) {
        ModeloCreacion modelo = new ModeloCreacion();
        VistaCreacion vista = new VistaCreacion(modelo);
        ControladorCreacion controlador = new ControladorCreacion(vista, modelo);

        vista.setControlador(controlador);
        vista.arrancar();
    }
}
