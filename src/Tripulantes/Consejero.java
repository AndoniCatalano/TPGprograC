package Tripulantes;
import java.util.ArrayList;

public class Consejero extends Tripulante{

    private static final double plusConsejo = 2;
    private ArrayList<String> consejos;

    public Consejero(String identidad, String origen, int antiguedad){
        super(identidad,origen,antiguedad);

        this.sueldoBase = 600;
        this.cargo = "Consejero";
        this.plusAntiguedad = 0.05;
        this.consejos = new ArrayList<>();
    }

    public void addConsejo(String consejo){
        this.consejos.add(consejo);
    }

    @Override
    public double sumaConsejo(){
        return this.consejos.size() * plusConsejo;
    }

}
