package Tripulantes;

public abstract class Tripulante{
    protected String identidad;
    protected int antiguedad;
    protected double sueldoBase;
    protected double plusAntiguedad;

    public Tripulante(String identidad, int antiguedad){
        this.identidad = identidad;
        this.antiguedad = antiguedad;
    }

    public  String getIdentidad() {
        return this.identidad;
    }


    public double getSueldo(){
        return  this.sueldoBase + this.sueldoBase * this.antiguedad * this.plusAntiguedad;
    }

}