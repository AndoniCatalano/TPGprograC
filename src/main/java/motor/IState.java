package motor;
import asistente.Asistente;
import nave.tablero.Posicion;


public interface State {
    void saltar(Asistente asistente, Posicion destino);
    void saltarWarp(Asistente asistente, Posicion destino);
}
