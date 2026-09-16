package misiones;

public abstract class Mision {

    protected boolean preparada = false;
    protected String resultado = null;
    Informe inf = new Informe();
    public final void ejecutarCicloMision(combustible, desgaste,) { // TENER TODOS LOS PARAMETROS
        preparar(combustible, desgaste);
        if (preparada){
            ejecutarObjetivo();
        }
        else{
            // Tirar excepcion
        }
        evaluar();
        if(resultado!=null && inf!=null){
            cerrar();
        }
        else{
            // Tirar excepcion
        }

    }

    public void preparar(double combustible, double desgaste){
        if (desgaste>3 && desgaste>3) {
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

