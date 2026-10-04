package misiones;

public class M01 extends Mision {

    public M01() {
        this.combustibleGastado = 4;
        this.desgasteAcumulado = 4;
        this.energia = 5;
    }

    @Override
    protected void ejecutarObjetivo(Asistente asis) {
        asis.escribirEvento("[M-01] Ejecutando objetivo");
        asis.escribirEvento("[M-01] Recolectando datos");
    }

}
