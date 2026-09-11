package Tripulantes;

public class Teniente extends Tripulante{
    public Teniente(String identidad, String origen, int antiguedad){
        super(identidad,origen,antiguedad);
        this.cargo = "Teniente";
        this.sueldoBase = 400;
        this.plusAntiguedad = 0.03;
    }


}
