package misiones;

public class M01 extends Mision{

    @Override
    protected void ejecutarObjetivo(){
        this.acciones.add("[M-03] Ejecutando objetivo");
        this.acciones.add("[M-03] Completando regreso");
    }
    

    @Override
    public double getEnergia(){
        return 0;
    }
}

