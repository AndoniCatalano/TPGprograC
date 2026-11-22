package Creacion.Modelo;

import java.util.ArrayList;
import java.util.List;
import nave.INave;
import nave.NaveFactory;
import nave.TipoNaveInvalidoException;
import Tripulantes.Tripulante;
import Tripulantes.TripulanteFactory;
import Tripulantes.AntiguedadInvalidaException;
import Tripulantes.OrigenTripulanteException;
import Tripulantes.RolTripulanteInvalidoException;

public class ModeloCreacion {

    private INave naveConfigurada;
    private final List<Tripulante> tripulantesAgregados;
    private boolean configuracionCompleta;
    private final List<ObservadorCreacion> observadores;

    private boolean tieneCapitan;

    public ModeloCreacion() {
        this.naveConfigurada = null;
        this.tieneCapitan = false;
        this.configuracionCompleta = false;
        this.observadores = new ArrayList<>();
        this.tripulantesAgregados = new ArrayList<>();
    }

    public void instanciarNave(String tipoNave) throws TipoNaveInvalidoException {
        this.naveConfigurada = NaveFactory.getNave(tipoNave);
        evaluarEstadoTravesia();
    }


    public void agregarTripulante(String rol, String origen, String identidad, int antiguedad)
            throws AntiguedadInvalidaException, OrigenTripulanteException, RolTripulanteInvalidoException {

        if (this.naveConfigurada == null) {
            throw new IllegalStateException("Debe instanciar una nave antes de agregar tripulantes.");
        }

        // se crea tripulante
        Tripulante nuevoTripulante = TripulanteFactory.creaTripulante(rol, origen, identidad, antiguedad);

        // Se añade a la nave
        this.naveConfigurada.addTripulante(nuevoTripulante);
        this.tripulantesAgregados.add(nuevoTripulante);

        if (rol.equalsIgnoreCase("Capitan")) {
            this.tieneCapitan = true;
        }

        evaluarEstadoTravesia();
    }

    private void evaluarEstadoTravesia() {
        if (this.naveConfigurada != null && this.tieneCapitan && this.tripulantesAgregados.size() >= 5) {
            this.configuracionCompleta = true;
        } else {
            this.configuracionCompleta = false;
        }
        notificarObservadores();
    }

    public boolean puedeIniciarTravesia() {
        return this.configuracionCompleta;
    }

    public void agregarObservador(ObservadorCreacion obs) {
        this.observadores.add(obs);
    }

    private void notificarObservadores() {
        for (ObservadorCreacion obs : this.observadores) {
            obs.actualizarEstado();
        }
    }

    public List<Tripulante> getTripulacion() {
        return this.tripulantesAgregados;
    }

    public boolean getTieneCapitan() {
        return this.tieneCapitan;
    }

    public INave getNaveConfigurada() {
        return this.naveConfigurada;
    }

}
