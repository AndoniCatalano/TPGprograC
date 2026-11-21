package misiones;
import asistente.Asistente;

public class M02 extends Mision {

    public M02() {
        this.consumeCombustible = 4;
        this.generaDesgaste = 4;
        this.gastaEnergia = 10;
        this.energia = 5;
    }

    @Override
    protected boolean verificarObjetivo(Asistente asistente){
        return true;
    }

    @Override
    protected void impactaRecursosFinales(Asistente asistente){
        asistente.cargaEnergia(5);
    }

}

