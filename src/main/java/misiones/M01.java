package misiones;
import asistente.Asistente;

public class M01 extends Mision {

    public M01() {
        this.combustibleGastado = 4;
        this.desgasteAcumulado = 4;
        this.energia = 5;
    }

    @Override
    protected boolean verificarObjetivo(Asistente asistente){
        if (asistente.tieneRecursosParaEntregar()) {
            return true;
        } else {
            asistente.registrarEventoBitacora("Llegó al destino, pero no tiene los recursos para asistir.");
            return false;
        }
    }

    @Override
    protected void impactaRecursosFinales(Asistente asistente){
        asistente.entregarRecursos();

        asistente.cargaEenergia(5);
    }

}
