package misiones;

public class M03 extends Mision {

    @Override
    protected void ejecutarObjetivo() {
        asis.escribirEvento("[M-03] Ejecutando objetivo");
        asis.escribirEvento("[M-03] Completando regreso");
        combustibleGastado = 4;
        desgasteAcumulado = 4;
        energia = 0;
    }

}