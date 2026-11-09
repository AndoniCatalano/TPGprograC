package misiones;
import asistente.Asistente;

public class M03 extends Mision {

    public M03() {
        this.consumeCombustible = 4;
        this.generaDesgaste = 4;
        this.energia = 0;
    }


    @Override
    protected boolean verificarObjetivo(Asistente asistente){
        //retorna siempre verdadero ya que si llego hasta este punto significa que la nave esta con vida
        return true;
    }

    @Override
    protected void impactaRecursosFinales(Asistente asistente){

    }


}