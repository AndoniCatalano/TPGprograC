package misiones;
import asistente.Asistente;

public class M01 extends Mision {

    public M01() {
        this.consumeCombustible = 4;
        this.generaDesgaste = 4;
        this.gastaEnergia = 5;
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

        asistente.cargaEnergia(5);
    }

}
