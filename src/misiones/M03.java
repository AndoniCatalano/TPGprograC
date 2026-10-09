package misiones;
import asistente.Asistente;

public class M03 extends Mision {

    public M03() {
        this.combustibleGastado = 4;
        this.desgasteAcumulado = 4;
        this.energia = 0;
    }

    @Override
    protected void ejecutarObjetivo(Asistente asistente) {
        asistente.registrarEventoBitacora("[M-03] Ejecutando objetivo");
        asistente.registrarEventoBitacora("[M-03] Completando regreso");
        asistente.registrarEventoBitacora("[M-03] Mision ejecutada");
    }

    @Override
    public void cerrar(Asistente asistente){
        asistente.registrarEventoBitacora("Mision ejecutada: " + this.getClass().getSimpleName());
        asistente.registrarEventoBitacora("Resultado de la mision: " + this.resultado);
        asistente.consumeCombustible(this.combustibleGastado);
        asistente.incrementaDesgaste(this.desgasteAcumulado);
        asistente.registrarEventoBitacora("Energia agregada: " + energia);
        asistente.registrarEventoBitacora("Combustible gastado: " + combustibleGastado);
        asistente.registrarEventoBitacora("Desgaste consumado: " + desgasteAcumulado);
        asistente.registrarEventoBitacora("Mision cerrada");
    }
}