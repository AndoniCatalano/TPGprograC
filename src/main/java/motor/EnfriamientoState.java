package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class EnfriamientoState implements State{
    private MotorWarp m;

    public EnfriamientoState(MotorWarp m){
        this.m = m;
    }

    @Override
    public void saltar(Asistente asistente, Posicion destino) {
        enfriar(asistente);
    }

    @Override
    public void saltarWarp(Asistente asistente, Posicion destino) {
        enfriar(asistente);
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
