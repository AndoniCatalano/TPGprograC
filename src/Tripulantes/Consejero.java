package Tripulantes;
import java.util.ArrayList;

public class Consejero extends Tripulante{

    private static final double plusConsejo = 2;
    private ArrayList<String> consejos;

    public Consejero(String identidad, int antiguedad){
        super(identidad,antiguedad);

        this.sueldoBase = 600;
        this.plusAntiguedad = 0.05;
        this.consejos = new ArrayList<>();
    }

    public void addConsejo(String consejo){
        this.consejos.add(consejo);
    }

    @Override
    public double getSueldo(){
        return this.sueldoBase +
                this.sueldoBase * this.plusAntiguedad * this.antiguedad +
                this.consejos.size() * this.plusConsejo;
    }
}
