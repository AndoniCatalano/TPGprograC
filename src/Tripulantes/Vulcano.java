package Tripulantes;

public class Vulcano extends TripulanteDecorator{

    public Vulcano(Tripulante tripulante){
        super(tripulante);
    }

    @Override
    public double getSueldo(){
        return this.tripulanteDecorado.getSueldo() + 30;
    }
}
