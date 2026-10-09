package nave;

import asistente.Asistente;
import Tripulantes.Tripulante;
import java.util.ArrayList;
import motor.MotorWarp;
import motor.TransicionInvalidaException;

public abstract class Nave implements INave {
    protected int combustible;
    protected int energia;
    protected int desgaste;
    protected boolean necesitaMantenimiento; //depende del desgaste
    protected MotorWarp motorWarp;
    protected Asistente asistente;
    protected ArrayList<Tripulante> tripulantes;

    public Nave(int combustibleInicial, int energiaInicial){ //la diferencia entre los tipos de nave es por sus configs iniciales
        this.combustible = combustibleInicial;
        this.energia = energiaInicial;
        this.desgaste = 0;
        this.necesitaMantenimiento = false;
        this.motorWarp = new MotorWarp();
        this.tripulantes = new ArrayList<>();
    }

    @Override
    public void setAsistente(Asistente asistente) {
        this.asistente = asistente;
    }

    @Override
    public void cargaCombustible(int cantCombustible) throws OperacionRechazadaException {
        if (cantCombustible <= 0 || (this.combustible + cantCombustible) > 100)
            throw new OperacionRechazadaException("La carga de combustible supera la capacidad máxima (100) o es inválida.");
        else
            this.combustible += cantCombustible;
    }

    @Override
    public void cargaEnergia(int cantEnergia) throws OperacionRechazadaException {
        if (cantEnergia <= 0 || (this.energia + cantEnergia) > 100)
            throw new OperacionRechazadaException("La carga de energía supera la capacidad máxima (100) o es inválida.");
        else
            this.energia += cantEnergia;
    }

    @Override
    public void consumeCombustible(int cantCombustible) throws OperacionRechazadaException {
        if (cantCombustible <= 0 || cantCombustible > this.combustible)
            throw new OperacionRechazadaException("Combustible insuficiente para el consumo solicitado.");
        else
            this.combustible -= cantCombustible;
    }

    @Override
    public void consumeEnergia(int cantEnergia) throws OperacionRechazadaException {
        if (cantEnergia <= 0 || cantEnergia > this.energia)
            throw new OperacionRechazadaException("Energía insuficiente para el consumo solicitado.");
        else
            this.energia -= cantEnergia;
    }

    @Override
    public void realizarMantenimiento() throws OperacionRechazadaException{
        if (this.desgaste < 80)
            throw new OperacionRechazadaException("La nave no requiere mantenimiento. El desgaste actual (" + this.desgaste + ") es menor a 80.");
        else{
            this.desgaste = 0;
            this.necesitaMantenimiento = false;
        }
    }

    @Override
    public void incrementaDesgaste(int cantDesgaste) throws OperacionRechazadaException {
        if (cantDesgaste <= 0)
            throw new OperacionRechazadaException("El desgaste a incrementar debe ser mayor a cero.");
        else {
            if(cantDesgaste > 100){
              this.desgaste = 100;
            } else {
                this.desgaste += cantDesgaste;
                if (this.desgaste > 100)
                    this.desgaste = 100;
            }
            if(this.desgaste >= 80){
                this.necesitaMantenimiento = true;
            }
        }
    }

    //Tripulantes

    @Override
    public void addTripulante(Tripulante tripulante) {
        this.tripulantes.add(tripulante);
    }

    @Override
    public void removeTripulante(Tripulante tripulante) {
        this.tripulantes.remove(tripulante);
    }

    @Override
    public void liquidarHaberes() {
        double total = 0;
        for (Tripulante t : this.tripulantes)
            total += t.getSueldo();
        System.out.println("Liquidación total de haberes: $" + total);
    }

    //Getters
    @Override
    public int getCombustible() { return this.combustible; }

    @Override
    public int getEnergia() { return this.energia; }

    @Override
    public int getDesgaste() { return this.desgaste; }

    @Override
    public boolean getNecesitaMantenimiento() { return this.necesitaMantenimiento; }

    @Override
    public MotorWarp getMotorWarp() { return this.motorWarp; }

    @Override
    public Asistente getAsistente() { return this.asistente; }

    @Override
    public void prepararSalto() throws TransicionInvalidaException {
        this.motorWarp.prepararSalto();
    }

    @Override
    public void ejecutarSalto() throws TransicionInvalidaException {
        this.motorWarp.ejecutarSalto();
    }

    @Override
    public void terminarEnfriamiento() throws TransicionInvalidaException {
        this.motorWarp.terminarEnfriamiento();
    }

    @Override
    public void mostrarSueldos() {
        System.out.println("\nSueldos de la Tripulación: ");
        for (Tripulante t : this.tripulantes) {
            double sueldo = t.getSueldo();
            System.out.println("Tripulante " + t.getIdentidad() + ": $" + sueldo);
        }
    }

}

