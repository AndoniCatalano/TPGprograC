package Concurrencia.Vista;

import java.awt.event.ActionListener;
public interface IVista {
        String CREAR_NAVE = "CREAR_NAVE";
        String CREAR_GENERADORA = "CREAR_GENERADORA";
        String DETENER_SIMULACION = "DETENER_SIMULACION";
        String REINICIAR_SIMULACION = "REINICIAR_SIMULACION";

        void setActionListener(ActionListener controlador);
        void agregarMensajeConsola(String mensaje);
        void deshabilitarControles();
        void habilitarControles();
}
