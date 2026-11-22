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
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> El controlador queda inicializado con el modelo y la vista, registrado como ActionListener
     * y con la vista suscripta como observadora de la plataforma de carga.
     */
    public ConcurrenciaControlador() {
        this.universoConcurrente = new UniversoConcurrente(3);
        VisorConcurrencia visor = new VisorConcurrencia();

        // 1. Conectar los botones de la vista con este controlador (MVC)
        visor.setActionListener(this);

        // 2. Suscribir la vista a los eventos de la plataforma (Observer)
        this.universoConcurrente.getPlataforma().agregarObservador(visor);

        this.vistaVisor = visor;

        assert invariante() : "Fallo invariante en el constructor de ConcurrenciaControlador";
    }

    public void iniciar() {
        this.vistaVisor.arrancar();
    }

    private boolean invariante() {
        return this.universoConcurrente != null && this.vistaVisor != null;
    }

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