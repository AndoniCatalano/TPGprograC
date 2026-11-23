package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class MotorWarp {
    private IState estado;

    /**
     * Constructor del motor Warp en su estado inicial disponible.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> El motor queda instanciado en el estado DisponibleState.
     */
    public MotorWarp() {
        this.estado = new DisponibleState(this);

        assert this.estado instanceof DisponibleState : "Postcondición fallida: el motor no inició en estado Disponible";
        assert invariante() : "Fallo invariante en el constructor de MotorWarp";
    }

    /**
     * Testea el invariante de clase garantizando que el motor tenga siempre un estado válido asignado.
     */
    private boolean invariante() {
        return this.estado != null;
    }

    /**
     * Actualiza el estado interno del motor Warp.
     * <b>Pre:</b> estado no debe ser nulo.
     * <b>Post:</b> El estado actual del motor pasa a ser el indicado por parámetro.
     *
     * @param estado El nuevo estado al que transiciona el motor.
     */
    protected void setEstado(IState estado) {
        assert estado != null : "Precondición fallida: el nuevo estado no puede ser nulo";

        this.estado = estado;

        assert this.estado == estado : "Postcondición fallida: el estado del motor no se actualizó";
        assert invariante() : "Fallo invariante en setEstado";
    }

    /**
     * Delega la ejecución de un salto normal al estado actual del motor.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> El estado actual procesa el salto normal y mantiene un estado válido en el motor.
     *
     * @param asistente El asistente encargado de operar la nave.
     * @param destino La posición destino del salto.
     */
    public void saltar(Asistente asistente, Posicion destino) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";
        assert destino != null : "Precondición fallida: destino no puede ser nulo";

        this.estado.saltar(asistente, destino);

        assert invariante() : "Fallo invariante en saltar de MotorWarp";
    }

    /**
     * Delega la ejecución o avance del ciclo de salto Warp al estado actual del motor.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> El estado actual procesa la solicitud Warp y mantiene un estado válido en el motor.
     *
     * @param asistente El asistente encargado de operar la nave.
     * @param destino La posición destino del salto Warp.
     */
    public void saltarWarp(Asistente asistente, Posicion destino) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";
        assert destino != null : "Precondición fallida: destino no puede ser nulo";

        this.estado.saltarWarp(asistente, destino);

        assert invariante() : "Fallo invariante en saltarWarp de MotorWarp";
    }
}