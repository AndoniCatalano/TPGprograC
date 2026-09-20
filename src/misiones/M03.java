package misiones;

public class M01 extends Mision{

    @Override
    protected void ejecutarObjetivo(){
        this.acciones.add("[M-03] Ejecutando objetivo");
        this.acciones.add("[M-03] Completando regreso");
        combustibleGastado = 4;
    }


    @Override
    public double getEnergia(){
        return 0;
    }
}

