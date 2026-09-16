package misiones;

public class M02 extends Mision {

    @Override
    protected abstract void ejecutarObjetivo(Asistente asis){

    }
    @Override
    public abstract void evaluar(Asistente asis){

    }
    public abstract void cerrar(Asistente asis){
        this.asis.setCombustible(this.asis.getCombustible() - 4);
        this.asis.setDesgaste(this.asis.getDesgaste() - 4);
        this.asis.setEnergia(this.asis.getEnergia + 5);
    }
}
