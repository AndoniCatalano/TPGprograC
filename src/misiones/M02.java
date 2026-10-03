package misiones;

public class M02 extends Mision{

    @Override
    protected void ejecutarObjetivo(){
        asis.escribirEvento("[M-02] Ejecutando objetivo");
        asis.escribirEvento("[M-02] Recolectando recursos");
        combustibleGastado = 4;
        desgasteAcumulado = 4;
        energia = 5;
    }

}

