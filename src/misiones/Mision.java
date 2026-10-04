package misiones;
import Asistente;
import java.util.List;
import java.util.ArrayList;

public abstract class Mision {

    protected boolean preparada = false;
    protected String resultado = null;
    protected double combustibleGastado;
    protected double desgasteAcumulado;
    protected double energia;

    public final boolean ejecutarCicloMision(Asistente asis){
        preparar(asis);
        if (!this.preparada) {
            throw new IllegalStateException("Error de contrato: Una misión no podrá ejecutarse sin preparación previa.");
        }
        ejecutarObjetivo(asis);
        evaluar();
        if (this.resultado == null) {
            throw new IllegalStateException("Error de contrato: Una misión no podrá cerrarse sin resultado e informe.");
        }
        cerrar(asis);

        return this.resultado.equals("EXITO");
    }

    public void preparar(Asistente asis){
        if (asis.consultarCombustible() > combustibleGastado && asis.consultarDesgaste() > desgasteAcumulado){
            this.preparada = true;
            asis.escribirEvento("Mision preparada con exito");
        }

    }

    protected abstract void ejecutarObjetivo(Asistente asis);

    public void evaluar(){
        double exito = Math.random();
        this.acciones.add("Evaluando mision");
        if (exito > 0.5){
            this.resultado = "EXITO";
        }else {
            this.resultado = "FRACASO";
        }
    }

    // public void evaluar(){
    //    double exito = true;
    // }

    public void cerrar(Asistente asis){
        asis.escribirEvento("Mision ejecutada: " + this.getClass().getSimpleName());
        asis.escribirEvento("Resultado de la mision: " + this.resultado);
        asis.agregaEnergia(energia);
        asis.GastarCombustible(combustibleGastado);
        asis.AcumulaDesgaste(desgasteAcumulado);
        asis.escribirEvento("Energia agregada: " + energia);
        asis.escribirEvento("Combustible gastado: " + combustibleGastado);
        asis.escribirEvento("Desgaste consumado: " + desgasteAcumulado);
        asis.escribirEvento("Estado final de la nave: " + asis.estadoNave);
        asis.escribirEvento("Mision cerrada");
    }

}

