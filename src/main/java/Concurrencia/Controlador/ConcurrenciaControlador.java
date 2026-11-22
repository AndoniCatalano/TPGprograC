package Concurrencia.Controlador;

import Concurrencia.Modelo.UniversoConcurrente;
import Concurrencia.Vista.IVista;
import Concurrencia.Vista.VisorConcurrencia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConcurrenciaControlador implements ActionListener {
    private final UniversoConcurrente universoConcurrente;
    private final IVista vistaVisor;

    /**
     * Constructor del controlador que vincula el modelo concurrente con la vista del visor.
     * <b>Pre:</b> universoConcurrente y vistaVisor no deben ser nulos.
     * <b>Post:</b> El controlador queda inicializado con ambas referencias y registrado como ActionListener de la vista.
     *
     */
    public ConcurrenciaControlador() {

        this.universoConcurrente = new UniversoConcurrente(3);
        this.vistaVisor = new VisorConcurrencia();

        assert invariante() : "Fallo invariante en el constructor de Controlador";
    }



    /**
     * Testea el invariante de clase garantizando que las referencias al modelo y la vista sean válidas.
     */
    private boolean invariante() {
        return this.universoConcurrente != null && this.vistaVisor != null;
    }

    /**
     * Procesa los eventos disparados por los botones de la vista.
     * <b>Pre:</b> El evento e no debe ser nulo.
     * <b>Post:</b> Se ejecuta en el modelo y/o la vista la operación correspondiente al comando recibido.
     *
     * @param e El evento generado por un componente de la vista
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        assert e != null : "Precondición fallida: el ActionEvent no puede ser nulo";

        String comando = e.getActionCommand();

        if (IVista.CREAR_NAVE.equals(comando)) {
            this.universoConcurrente.crearNaveConsumidora();
        } else if (IVista.CREAR_GENERADORA.equals(comando)) {
            this.universoConcurrente.crearGeneradoraDeEnergia();
        } else if (IVista.DETENER_SIMULACION.equals(comando)) {
            this.universoConcurrente.detenerSimulacion();
            this.vistaVisor.deshabilitarControles();
        } else if (IVista.REINICIAR_SIMULACION.equals(comando)) {
            this.vistaVisor.habilitarControles();
            this.universoConcurrente.reiniciarSimulacion();
        }

        assert invariante() : "Fallo invariante en actionPerformed";
    }
}