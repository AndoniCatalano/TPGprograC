package Tripulantes;

public class Marciano extends TripulanteDecorator{

    public Marciano(Tripulante tripulante){
        super(tripulante);
    }

    @Override
    public double getSueldo(){
        return this.tripulanteDecorado.getSueldo() + 18;
    }
}
