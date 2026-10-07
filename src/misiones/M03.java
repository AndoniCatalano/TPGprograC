package misiones;
import asistente.Asistente;

public class M03 extends Mision {

    public M03() {
        this.combustibleGastado = 4;
        this.desgasteAcumulado = 4;
        this.energia = 0;
    }

    @Override
    protected void ejecutarObjetivo(Asistente asis) {
        asis.registrarEventoBitacora("[M-03] Ejecutando objetivo");
        asis.registrarEventoBitacora("[M-03] Completando regreso");
        asis.registrarEventoBitacora("[M-03] Mision ejecutada");
    }

}