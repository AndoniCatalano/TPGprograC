package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class MotorWarp{
    private State estado;


    public MotorWarp() {
        this.estado = new DisponibleState(this);
    }

    protected void setEstado(State estado) {
        this.estado = estado;
    }

    public void saltar(Asistente asistente, Posicion destino) {
        this.estado.saltar(asistente, destino);
    }

    public void saltarWarp(Asistente asistente, Posicion destino) {
        this.estado.saltarWarp(asistente, destino);
    }
}
