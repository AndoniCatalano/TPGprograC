package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class EnWarpState implements IState {
    private MotorWarp m;

    /**
     * Constructor del estado En Warp vinculado al motor Warp.
     * <b>Pre:</b> m no debe ser nulo.
     * <b>Post:</b> El estado queda asociado al motor Warp indicado.
     *
     * @param m El motor Warp propietario de este estado.
     */
    public EnWarpState(MotorWarp m) {
        assert m != null : "Precondición fallida: el motor Warp no puede ser nulo";

        this.m = m;

        assert this.m == m : "Postcondición fallida: el motor Warp no quedó vinculado";
        assert invariante() : "Fallo invariante en el constructor de EnWarpState";
    }

    /**
     * Testea el invariante de clase garantizando que la referencia al motor Warp no sea nula.
     */
    private boolean invariante() {
        return this.m != null;
    }

    /**
     * Abandona el estado En Warp, devuelve el motor a Disponible y ejecuta un salto normal.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> El motor pasa a DisponibleState y ejecuta el salto normal hacia destino.
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

        assert invariante() : "Fallo invariante en saltar de EnWarpState";
    }

    /**
     * Ejecuta el salto Warp hacia destino si hay energía suficiente, o aborta volviendo a preparación.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> Si la energía es mayor o igual a 5, se consumen 5 unidades de energía, la nave se mueve a destino
     * y el motor pasa a EnfriamientoState; en caso contrario vuelve a PreparandoState. Se registra el resultado en bitácora.
     *
     * @param asistente El asistente encargado de los recursos, movimiento y bitácora.
     * @param destino La posición destino del salto Warp.
     */
    @Override
    public void saltarWarp(Asistente asistente, Posicion destino) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";
        assert destino != null : "Precondición fallida: destino no puede ser nulo";

        if (asistente.consultarEnergia() >= 5) {
            asistente.consumeEnergia(5);
            asistente.moverA(destino);
            this.m.setEstado(new EnfriamientoState(this.m));
            asistente.registrarEventoBitacora("Salto Warp ejecutado (-5 energía).");
        } else {
            this.m.setEstado(new PreparandoState(this.m));
            asistente.registrarEventoBitacora("Salto Warp abortado por falta de energía. Volviendo a preparación.");
        }

        assert invariante() : "Fallo invariante en saltarWarp de EnWarpState";
    }

    @Override
    public String toString() {
        return "En Warp";
    }
}