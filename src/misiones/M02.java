package misiones;

public class M01 extends Mision{

    @Override
    protected void ejecutarObjetivo(){
        this.acciones.add("[M-02] Ejecutando objetivo");
        this.acciones.add("[M-02] Recolectando recursos");
        combustibleGastado = 4;
    }


    @Override
    public double getEnergia(){
        return 5;
    }
}

