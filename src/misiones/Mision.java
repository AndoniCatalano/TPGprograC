package misiones;

public abstract class Mision {

    protected boolean preparada = false;
    protected String resultado = null;
    Informe inf = new Informe();
    public final void ejecutarCicloMision(combustible, desgaste) { // TENER TODOS LOS PARAMETROS
        preparar(combustible, desgaste);
        if (preparada){
            ejecutarObjetivo();
        }
        else{
            // indicar que no se puede ejecutar
        }
        evaluar();
        if(resultado!=null){
            cerrar();
        }
        else{
            // Indicar que no se puede cerrar
        }

    }

    public void preparar(double combustible, double desgaste){
        if (desgaste>3 && combustible>3) {
            this.preparada = true;
        }
    }

    protected abstract void ejecutarObjetivo();

    public abstract void evaluar(); // poner resultado

    public abstract void cerrar(); // crear inf

    public abstract double getEnergia();

    public Informe getInforme(){
        return inf;
    }


}

