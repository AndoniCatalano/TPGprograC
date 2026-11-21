package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class EnWarpState implements State{
    private MotorWarp m;

    public EnWarpState(MotorWarp m){
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
            asistente.consumeEnergia(5);
            asistente.moverA(destino);
            this.m.setEstado(new EnfriamientoState(this.m));
            asistente.registrarEventoBitacora("Salto Warp ejecutado (-5 energía).");
        } else {
            this.m.setEstado(new PreparandoState(this.m));
            asistente.registrarEventoBitacora("Salto Warp abortado por falta de energía. Volviendo a preparación.");
        }
    }

    @Override
    public String toString() {
        return "En Warp";
    }
}
