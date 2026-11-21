package Concurrencia.Controlador;

import Concurrencia.Modelo.UniversoConcurrente;
import Concurrencia.Vista.IVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {
    private final UniversoConcurrente universoConcurrente;
    private final IVista vistaVisor;

    public Controlador(UniversoConcurrente universoConcurrente, IVista vistaVisor) {
        this.universoConcurrente = universoConcurrente;
        this.vistaVisor = vistaVisor;
        this.vistaVisor.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
    }
}
