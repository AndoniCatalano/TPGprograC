package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class DisponibleState implements State{
    private MotorWarp m;

    public DisponibleState(MotorWarp m){
        this.m = m;
    }

    @Override
    public void saltar(Asistente asistente, Posicion destino) {
        asistente.consumeCombustible(2);
        asistente.moverA(destino);
    }

    @Override
    public void saltarWarp(Asistente asistente, Posicion destino) {
        this.m.setEstado(new PreparandoState(this.m));
        asistente.registrarEventoBitacora("Iniciando preparación de Motor Warp (turno 1/2).");
    }

    @Override
    public String toString() {
        return "Disponible";
    }
}
