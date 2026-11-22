package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class PreparandoState implements State{
    private MotorWarp m;

    public PreparandoState(MotorWarp m){
        this.m = m;
    }

    @Override
    public void saltar(Asistente asistente, Posicion destino) {
        this.m.setEstado(new DisponibleState(this.m));
        this.m.saltar(asistente, destino);
    }

    @Override
    public void saltarWarp(Asistente asistente, Posicion destino) {
        if (asistente.consultarEnergia() >= 5) {
            this.m.setEstado(new EnWarpState(this.m));
            asistente.registrarEventoBitacora("Motor Warp listo para ejecutar el salto (turno 2/2).");
        } else {
            asistente.registrarEventoBitacora("Energía insuficiente para preparar Salto Warp. Esperando...");
        }
    }

    @Override
    public String toString() {
        return "Preparando";
    }
}
