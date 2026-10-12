package Tripulantes;

public abstract class TripulanteDecorator extends Tripulante{
    protected Tripulante tripulanteDecorado;

    public TripulanteDecorator(Tripulante tripulante){
        super(tripulante.identidad, tripulante.antiguedad);
        this.tripulanteDecorado = tripulante;
    }

    public abstract double getSueldo();
}
