package misiones;
import asistente.Asistente;

public class M02 extends Mision {

    public M02() {
        this.combustibleGastado = 4;
        this.desgasteAcumulado = 4;
        this.energia = 5;
    }

    @Override
    protected void ejecutarObjetivo(Asistente asis){
        asis.registrarEventoBitacora("[M-02] Ejecutando objetivo");
        asis.registrarEventoBitacora("[M-02] Recolectando recursos");
        asis.registrarEventoBitacora("[M-02] Mision ejecutada");
    }

}

