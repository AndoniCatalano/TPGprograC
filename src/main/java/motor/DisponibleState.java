package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class DisponibleState implements IState {
    private MotorWarp m;

    /**
     * Constructor del estado Disponible vinculado al motor Warp.
     * <b>Pre:</b> m no debe ser nulo.
     * <b>Post:</b> El estado queda asociado al motor Warp indicado.
     *
     * @param m El motor Warp propietario de este estado.
     */
    public DisponibleState(MotorWarp m) {
        assert m != null : "Precondición fallida: el motor Warp no puede ser nulo";

        this.m = m;

        assert this.m == m : "Postcondición fallida: el motor Warp no quedó vinculado";
        assert invariante() : "Fallo invariante en el constructor de DisponibleState";
    }

    /**
     * Testea el invariante de clase garantizando que la referencia al motor Warp no sea nula.
     */
    private boolean invariante() {
        return this.m != null;
    }

    /**
     * Realiza un salto normal consumiendo combustible y desplazando la nave al destino.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> Se solicita el consumo de 2 unidades de combustible y el desplazamiento de la nave hacia destino.
     *
     * @param asistente El asistente que gestiona los recursos y el movimiento.
     * @param destino La posición destino del salto normal.
     */
    @Override
    public void saltar(Asistente asistente, Posicion destino) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";
        assert destino != null : "Precondición fallida: destino no puede ser nulo";

        asistente.consumeCombustible(2);
        asistente.moverA(destino);

        assert invariante() : "Fallo invariante en saltar de DisponibleState";
    }

    /**
     * Inicia la preparación del motor para un salto Warp.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> El motor transiciona a PreparandoState y se registra el inicio de la preparación en la bitácora.
     *
     * @param asistente El asistente donde se registra el evento de preparación.
     * @param destino La posición destino solicitada para el salto Warp.
     */
    @Override
    public void saltarWarp(Asistente asistente, Posicion destino) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";
        assert destino != null : "Precondición fallida: destino no puede ser nulo";

        this.m.setEstado(new PreparandoState(this.m));
        asistente.registrarEventoBitacora("Iniciando preparación de Motor Warp (turno 1/2).");

        assert invariante() : "Fallo invariante en saltarWarp de DisponibleState";
    }

    @Override
    public String toString() {
        return "Disponible";
    }
}