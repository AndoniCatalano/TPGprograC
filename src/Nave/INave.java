package paquete;

import Asistente.Asistente;
import Tripulantes.Tripulante;

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
    public Motor getMotorWarp();
    public Asistente getAsistente();
    public void setAsistente(Asistente asistente);     //para la relación bidireccional

    public void addTripulante(Tripulante tripulante);
    public void removeTripulante(Tripulante tripulante);
    public void liquidarHaberes();
}