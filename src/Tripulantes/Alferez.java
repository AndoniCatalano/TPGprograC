package Tripulantes;

public class Alferez extends Tripulante{
    public Alferez(String identidad, String origen, int antiguedad){
        super(identidad,origen,antiguedad);
        this.cargo = "Alferez";
        this.sueldoBase = 200;
        this.plusAntiguedad = 0.005;
    }


}
