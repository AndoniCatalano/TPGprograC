package nave;

import asistente.Asistente;
import Tripulantes.Tripulante;
import motor.MotorWarp;
import motor.TransicionInvalidaException;
import nave.Observer.IObserver;
import nave.tablero.Posicion;

public interface INave {
    public void cargaCombustible(int cantCombustible) throws OperacionRechazadaException;
    public void cargaEnergia(int cantEnergia) throws OperacionRechazadaException;
    public void realizarMantenimiento() throws OperacionRechazadaException;
    public void consumeCombustible(int cantCombustible) throws OperacionRechazadaException;
    public void consumeEnergia(int cantEnergia) throws OperacionRechazadaException;
    public void incrementaDesgaste(int cantDesgaste) throws OperacionRechazadaException;


    public int getCombustible();
    public int getEnergia();
    public int getDesgaste();
    public boolean getNecesitaMantenimiento();
    public MotorWarp getMotorWarp();
    public Asistente getAsistente();
    public void setAsistente(Asistente asistente);     //para la relación bidireccional

    public void addTripulante(Tripulante tripulante);
    public void removeTripulante(Tripulante tripulante);
    public void liquidarHaberes();
    public void mostrarSueldos();


    public void saltar(Asistente asistente, Posicion destino);
    public void saltarWarp(Asistente asistente, Posicion destino);

    public void interactuarConCarguero(NaveEnemiga enemiga);
    public void interactuarConCombate(NaveEnemiga enemiga);
    public void interactuarConExploradora(NaveEnemiga enemiga);

    public void addObservador(IObserver observador);
    public void removeObservador(IObserver observador);
    public void notifyObservadores();
}
