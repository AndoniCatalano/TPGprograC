package misiones;

public class M01 extends Mision{

    @Override
    protected void ejecutarObjetivo(){
        this.acciones.add("[M-01] Ejecutando objetivo");
        this.acciones.add("[M-01] Recolectando datos");
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
