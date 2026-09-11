package Tripulantes;

public abstract class Tripulante {
    protected String identidad;
    protected String cargo;
    protected String origen;
    protected double sueldoBase;
    protected double plusAntiguedad;
    protected int antiguedad;

    protected Tripulante(String identidad, String origen, int antiguedad){
        this.identidad = identidad;
        this.origen = origen;
        this.antiguedad = antiguedad;
    }

    private double sumaConsejo(){return 0;}

    public double getSueldo(){
        double subsidio;

        switch (this.origen){
            case "terricola": subsidio = 20; break;
            case "vulcano": subsidio = 30; break;
            case "marciano": subsidio = 18; break;
            default: subsidio = 0; break;
        }

        return this.sueldoBase + this.sueldoBase * this.antiguedad * this.plusAntiguedad + subsidio + sumaConsejo();
    }

}
