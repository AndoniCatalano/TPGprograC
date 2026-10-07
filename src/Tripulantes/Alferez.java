package Tripulantes;

public class Alferez extends Tripulante{
    public Alferez(String identidad, int antiguedad){
        super(identidad,antiguedad);
        this.sueldoBase = 200;
        this.plusAntiguedad = 0.005;
    }


}
