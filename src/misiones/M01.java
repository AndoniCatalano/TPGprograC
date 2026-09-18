package misiones;

public class M01 extends Mision{

    @Override
    protected abstract void ejecutarObjetivo(){

    }

    @Override
    public abstract void evaluar(){
        if (){ // condicion de exito
            resultado = "Mision exitosa"
        }
        else{
            resultado = "Mision fallida"
        }
    }
    @Override
    public abstract void cerrar(){
        // RELLENAR INFORME
    }
    @Override
    public double getEnergia(){
        return 5;
    }
}
