package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class PreparandoState implements IState {
    private MotorWarp m;

    /**
     * Constructor del estado Preparando vinculado al motor Warp.
     * <b>Pre:</b> m no debe ser nulo.
     * <b>Post:</b> El estado queda asociado al motor Warp indicado.
     *
     * @param m El motor Warp propietario de este estado.
     */
    public PreparandoState(MotorWarp m) {
        assert m != null : "Precondición fallida: el motor Warp no puede ser nulo";

        this.m = m;

        assert this.m == m : "Postcondición fallida: el motor Warp no quedó vinculado";
        assert invariante() : "Fallo invariante en el constructor de PreparandoState";
    }

    /**
     * Testea el invariante de clase garantizando que la referencia al motor Warp no sea nula.
     */
    private boolean invariante() {
        return this.m != null;
    }

    /**
     * Cancela la preparación Warp, devuelve el motor a estado Disponible y ejecuta un salto normal.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> El motor pasa a DisponibleState y se ejecuta el salto normal hacia destino.
     *
     * @param asistente El asistente que gestiona la nave.
     * @param destino La posición destino del salto normal.
     */
    @Override
    public void saltar(Asistente asistente, Posicion destino) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";
        assert destino != null : "Precondición fallida: destino no puede ser nulo";

        this.m.setEstado(new DisponibleState(this.m));
        this.m.saltar(asistente, destino);

        assert invariante() : "Fallo invariante en saltar de PreparandoState";
    }

    /**
     * Completa la preparación del motor si la nave dispone de energía suficiente para pasar a estado En Warp.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> Si la energía es mayor o igual a 5, el motor transiciona a EnWarpState;
     * en caso contrario permanece en preparación. En ambos casos se registra el evento en la bitácora.
     *
     * @param asistente El asistente que consulta la energía y registra el evento en bitácora.
     * @param destino La posición destino del salto Warp.
     */
    @Override
    public void saltarWarp(Asistente asistente, Posicion destino) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";
        assert destino != null : "Precondición fallida: destino no puede ser nulo";

        if (asistente.consultarEnergia() >= 5) {
            this.m.setEstado(new EnWarpState(this.m));
            asistente.registrarEventoBitacora("Motor Warp listo para ejecutar el salto (turno 2/2).");
        } else {
            asistente.registrarEventoBitacora("Energía insuficiente para preparar Salto Warp. Esperando...");
        }

        assert invariante() : "Fallo invariante en saltarWarp de PreparandoState";
    }

    @Override
    public String toString() {
        return "Preparando";
    }
}