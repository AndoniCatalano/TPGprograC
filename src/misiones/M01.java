package misiones;

public class M01 extends Mision{

    @Override
    protected void ejecutarObjetivo(){
        this.acciones.add("[M-01] Ejecutando objetivo");
        this.acciones.add("[M-01] Recolectando datos");
        combustibleGastado = 4;
    }

    @Override
    public double getEnergia(){
        return 5;
    }
}
