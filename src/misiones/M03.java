package misiones;

public class M03 extends Mision {

    public M03() {
        this.combustibleGastado = 4;
        this.desgasteAcumulado = 4;
        this.energia = 0;
    }

    @Override
    protected void ejecutarObjetivo(Asistente asis) {
        asis.escribirEvento("[M-03] Ejecutando objetivo");
        asis.escribirEvento("[M-03] Completando regreso");
    }

}