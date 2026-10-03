package misiones;

public class M01 extends Mision {

    @Override
    protected void ejecutarObjetivo() {
        asis.escribirEvento("[M-01] Ejecutando objetivo");
        asis.escribirEvento("[M-01] Recolectando datos");
        combustibleGastado = 4;
        desgasteAcumulado = 4;
        energia = 5;
    }

}
