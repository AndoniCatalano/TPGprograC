package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class EnfriamientoState implements IState {
    private MotorWarp m;

    /**
     * Constructor del estado Enfriamiento vinculado al motor Warp.
     * <b>Pre:</b> m no debe ser nulo.
     * <b>Post:</b> El estado queda asociado al motor Warp indicado.
     *
     * @param m El motor Warp propietario de este estado.
     */
    public EnfriamientoState(MotorWarp m) {
        assert m != null : "Precondición fallida: el motor Warp no puede ser nulo";

        this.m = m;

        assert this.m == m : "Postcondición fallida: el motor Warp no quedó vinculado";
        assert invariante() : "Fallo invariante en el constructor de EnfriamientoState";
    }

    /**
     * Testea el invariante de clase garantizando que la referencia al motor Warp no sea nula.
     */
    private boolean invariante() {
        return this.m != null;
    }

    /**
     * Ejecuta el turno de enfriamiento del motor en respuesta a una solicitud de salto normal.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> Se incrementa el desgaste de la nave en 3 unidades, el motor vuelve a DisponibleState
     * y se registra el enfriamiento en la bitácora.
     *
     * @param asistente El asistente encargado de aplicar el desgaste y registrar el evento.
     * @param destino La posición destino recibida por contrato de estado.
     */
    @Override
    public void saltar(Asistente asistente, Posicion destino) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";
        assert destino != null : "Precondición fallida: destino no puede ser nulo";

        enfriar(asistente);

        assert invariante() : "Fallo invariante en saltar de EnfriamientoState";
    }

    /**
     * Ejecuta el turno de enfriamiento del motor en respuesta a una solicitud de salto Warp.
     * <b>Pre:</b> asistente y destino no deben ser nulos.
     * <b>Post:</b> Se incrementa el desgaste de la nave en 3 unidades, el motor vuelve a DisponibleState
     * y se registra el enfriamiento en la bitácora.
     *
     * @param asistente El asistente encargado de aplicar el desgaste y registrar el evento.
     * @param destino La posición destino recibida por contrato de estado.
     */
    @Override
    public void saltarWarp(Asistente asistente, Posicion destino) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";
        assert destino != null : "Precondición fallida: destino no puede ser nulo";

        enfriar(asistente);

        assert invariante() : "Fallo invariante en saltarWarp de EnfriamientoState";
    }

    private void enfriar(Asistente asistente) {
        asistente.incrementaDesgaste(3);
        this.m.setEstado(new DisponibleState(this.m));
        asistente.registrarEventoBitacora("Motor Warp enfriado (+3 desgaste). Motor disponible.");
    }

    @Override
    public String toString() {
        return "En enfriamiento";
    }
}