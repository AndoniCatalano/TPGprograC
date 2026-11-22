package motor;

import asistente.Asistente;
import nave.tablero.Posicion;

public class MotorWarp{
    private IState estado;


    public MotorWarp() {
        this.estado = new DisponibleState(this);
    }

    protected void setEstado(IState estado) {
        this.estado = estado;
    }

    public void saltar(Asistente asistente, Posicion destino) {
        this.estado.saltar(asistente, destino);
    }

    public void saltarWarp(Asistente asistente, Posicion destino) {
        this.estado.saltarWarp(asistente, destino);
    }
}
