package Tripulantes;

public class Terricola extends TripulanteDecorator{

    public Terricola(Tripulante tripulante){
        super(tripulante);
    }

    @Override
    public double getSueldo(){
        return this.tripulanteDecorado.getSueldo() + 20;
    }
}
