package misiones;

public class M01 extends Mision{

    @Override
    protected void ejecutarObjetivo(){
        this.acciones.add("[M-02] Ejecutando objetivo");
        this.acciones.add("[M-02] Recolectando recursos");
    }

    @Override
    public void evaluar(){
        if (){ // condicion de exito
            resultado = "Mision exitosa"
        }
        else{
            resultado = "Mision fallida"
        }
    }

    @Override
    public double getEnergia(){
        return 5;
    }
}

