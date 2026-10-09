package Tripulantes;
import java.util.ArrayList;

public class Consejero extends Tripulante{

    private static final double plusConsejo = 2;
    private int cantidadConsejos;
    public Consejero(String identidad, int antiguedad){
        super(identidad,antiguedad);

        this.sueldoBase = 600;
        this.plusAntiguedad = 0.05;
        this.cantidadConsejos = 0;
    }

    @Override
    public double getSueldo(){
        return this.sueldoBase +
                this.sueldoBase * this.plusAntiguedad * this.antiguedad +
                this.cantidadConsejos * this.plusConsejo;
    }

    /**
     * precondicion:
     * -la cantidad de consejos no puede ser negativa
     * @param cantidadConsejos
     * @throws CantidadConsejosInvalidaException
     */
    public void setCantidadConsejos(int cantidadConsejos) throws CantidadConsejosInvalidaException{
        if (cantidadConsejos < 0)
            throw new CantidadConsejosInvalidaException();
        this.cantidadConsejos = cantidadConsejos;
    }
}
