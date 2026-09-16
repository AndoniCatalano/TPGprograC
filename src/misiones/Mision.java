package misiones;

public abstract class Mision {

    protected boolean preparada = false;
    protected String resultado = null;
    public final void ejecutarCicloMision(Asistente asis) {
        preparar(asis);
        if (preparada){
            ejecutarObjetivo(asis);
        }
        else{
            // Tirar excepcion
        }
        evaluar(asis);
        if(resultado!=null && this.asis){
            cerrar(asis);
        }
        else{
            // Tirar excepcion
        }

    }

    public void preparar(Asistente asis){
        if (this.asis.getCombustible()>3 && this.asis.getDesgaste()>3) {
            this.preparada = true;
        }
    }

    protected abstract void ejecutarObjetivo(Asistente asis);


    public abstract void evaluar(Asistente asis);


    public abstract void cerrar(Asistente asis);


}

