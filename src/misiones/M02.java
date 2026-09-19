package misiones;

public class M01 extends Mision{

    @Override
    protected void ejecutarObjetivo(){
        this.acciones.add("[M-02] Ejecutando objetivo");
        this.acciones.add("[M-02] Recolectando recursos");
    }


    @Override
    public double getEnergia(){
        return 5;
    }
}

