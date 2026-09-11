package Tripulantes;

public class Capitan extends Tripulante{
    public Capitan(String identidad, String origen, int antiguedad){
        super(identidad,origen,antiguedad);
        this.cargo = "Capitan";
        this.sueldoBase = 1000;
        this.plusAntiguedad = 0.2;
    }
}
