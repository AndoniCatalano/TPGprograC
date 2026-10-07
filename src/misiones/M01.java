package misiones;
import asistente.Asistente;

public class M01 extends Mision {

    public M01() {
        this.combustibleGastado = 4;
        this.desgasteAcumulado = 4;
        this.energia = 5;
    }

    @Override
    protected void ejecutarObjetivo(Asistente asis) {
        asis.registrarEventoBitacora("[M-01] Ejecutando objetivo");
        asis.registrarEventoBitacora("[M-01] Recolectando datos");
        asis.registrarEventoBitacora("[M-01] Mision ejecutada");
    }

}
