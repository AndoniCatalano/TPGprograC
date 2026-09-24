package Tripulantes;

public class Teniente extends Tripulante{
    public Teniente(String identidad, int antiguedad){
        super(identidad, antiguedad);
        this.sueldoBase = 400;
        this.plusAntiguedad = 0.03;
    }


}
