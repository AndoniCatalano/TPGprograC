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
    /**
     * Instancia una nave según el tipo indicado, la almacena como nave configurada y reevalúa si se puede iniciar la travesía.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>{@code tipoNave} no nulo ni vacío y válido para la fábrica de naves</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>{@code naveConfigurada} queda asignada con la nueva instancia creada</li>
     *     <li>Se actualiza {@code configuracionCompleta} y se notifica a los observadores registrados</li>
     * </ul>
     *
     * @param tipoNave Identificador o tipo de nave a instanciar
     * @throws TipoNaveInvalidoException si el tipo de nave no es reconocido o es inválido
     */
    public void instanciarNave(String tipoNave) throws TipoNaveInvalidoException {
        this.naveConfigurada = NaveFactory.getNave(tipoNave);
        evaluarEstadoTravesia();
    }

    /**
     * Crea un tripulante con los datos indicados, lo incorpora a la nave configurada y actualiza el estado de la travesía.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>Debe existir una nave configurada ({@code naveConfigurada != null})</li>
     *     <li>{@code rol}, {@code origen} e {@code identidad} no nulos, y {@code antiguedad} válida</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>El tripulante queda registrado en la nave y en {@code tripulantesAgregados}</li>
     *     <li>Si el rol es {@code "Capitan"} (ignorando mayúsculas/minúsculas), {@code tieneCapitan} pasa a {@code true}</li>
     *     <li>Se reevalúa si la travesía puede iniciarse y se notifica a los observadores</li>
     * </ul>
     *
     * @param rol Rol del tripulante dentro de la nave
     * @param origen Lugar o planeta de origen del tripulante
     * @param identidad Identificación del tripulante
     * @param antiguedad Años de antigüedad del tripulante
     * @throws IllegalStateException si no hay una nave configurada previamente
     * @throws AntiguedadInvalidaException si la antigüedad no cumple con las reglas válidas
     * @throws OrigenTripulanteException si el origen es inválido
     * @throws RolTripulanteInvalidoException si el rol es inválido
     */
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

    /**
     * Determina si la configuración actual cumple las condiciones para iniciar la travesía y notifica a los observadores.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>{@code tripulantesAgregados} y {@code observadores} están inicializados</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>{@code configuracionCompleta} es {@code true} solo si hay nave configurada, capitán asignado y al menos 5 tripulantes (en caso contrario es {@code false})</li>
     *     <li>Todos los observadores registrados reciben {@code actualizarEstado()}</li>
     * </ul>
     */
    private void evaluarEstadoTravesia() {
        if (this.naveConfigurada != null && this.tieneCapitan && this.tripulantesAgregados.size() >= 5) {
            this.configuracionCompleta = true;
        } else {
            this.configuracionCompleta = false;
        }
        notificarObservadores();
    }

    /**
     * Indica si la nave y la tripulación cumplen con todos los requisitos para iniciar la travesía.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>El modelo ha sido inicializado</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>Devuelve {@code true} si {@code configuracionCompleta} está activo, o {@code false} en caso contrario</li>
     *     <li>No modifica el estado del modelo ni notifica a los observadores</li>
     * </ul>
     *
     * @return {@code true} si se puede iniciar la travesía; {@code false} en caso contrario
     */
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
