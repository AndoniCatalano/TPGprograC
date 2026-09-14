package misiones;

public abstract class Mision {

    protected boolean preparada = false;
    protected String resultado = null;
    public final void ejecutarCicloMision(Nave nave, InformeMision informe) {
        preparar(nave);
        if (preparada){
            ejecutarObjetivo(nave);
        }
        else{
            // Tirar excepcion
        }
        evaluar(nave);
        cerrar(nave, informe);
    }

    public void preparar(Nave nave){
        // verifica que este en condicion de ejecutar
        this.preparada = true;
    }

    protected abstract void ejecutarObjetivo(Nave nave);

    public abstract void evaluar(Nave nave);


    public void cerrar(Nave nave,InformeMision informe){
        if(resultado == null){
        // Tirar excepcion
        }
        else{
            // Cambio los valores de energia, desgaste, combustible a la nave
            // Relleno el informe
        }
    }
}

