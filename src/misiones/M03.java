package misiones;

public class M03 extends Mision {

    @Override
    protected abstract void ejecutarObjetivo(Asistente asis) {

    }
    @Override
    public abstract void evaluar(Asistente asis){

    }
    public abstract void cerrar(Asistente asis){

    }

    public double getEnergia(){
        return 0;
    }
}
