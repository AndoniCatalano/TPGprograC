package Concurrencia.Controlador;

import Concurrencia.Modelo.UniversoConcurrente;
import Concurrencia.Vista.IVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {
    private final UniversoConcurrente universoConcurrente;
    private final IVista vistaVisor;

    /**
     * Constructor del controlador que vincula el modelo concurrente con la vista del visor.
     * <b>Pre:</b> universoConcurrente y vistaVisor no deben ser nulos.
     * <b>Post:</b> El controlador queda inicializado con ambas referencias y registrado como ActionListener de la vista.
     *
     * @param universoConcurrente El modelo que gestiona la simulación concurrente.
     * @param vistaVisor La vista que expone los controles e interfaz del visor.
     */
    public Controlador(UniversoConcurrente universoConcurrente, IVista vistaVisor) {
        assert universoConcurrente != null : "Precondición fallida: universoConcurrente no puede ser nulo";
        assert vistaVisor != null : "Precondición fallida: vistaVisor no puede ser nulo";

        this.universoConcurrente = universoConcurrente;
        this.vistaVisor = vistaVisor;
        this.vistaVisor.setActionListener(this);

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