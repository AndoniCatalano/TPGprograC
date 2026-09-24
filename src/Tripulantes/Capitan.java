package Tripulantes;

public class Capitan extends Tripulante{
    public Capitan(String identidad, int antiguedad){
        super(identidad,antiguedad);
        this.sueldoBase = 1000;
        this.plusAntiguedad = 0.2;
    }
}
