package misiones;
import asistente.Asistente;

public class M02 extends Mision {

    public M02() {
        this.combustibleGastado = 4;
        this.desgasteAcumulado = 4;
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

