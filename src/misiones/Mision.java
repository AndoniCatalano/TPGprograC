package misiones;
import Asistente;

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
            asis.registrarEventoBitacora("Mision preparada con exito");
        }

    }

    protected abstract void ejecutarObjetivo(Asistente asis);

    public void evaluar(){
        double exito = Math.random();
        if (exito > 0.5){
            this.resultado = "EXITO";
        }else {
            this.resultado = "FRACASO";
        }
    }

    public void cerrar(Asistente asis){
        asis.registrarEventoBitacora("Mision ejecutada: " + this.getClass().getSimpleName());
        asis.registrarEventoBitacora("Resultado de la mision: " + this.resultado);
        asis.agregaEnergia(energia);
        asis.GastarCombustible(combustibleGastado);
        asis.AcumulaDesgaste(desgasteAcumulado);
        asis.registrarEventoBitacora("Energia agregada: " + energia);
        asis.registrarEventoBitacora("Combustible gastado: " + combustibleGastado);
        asis.registrarEventoBitacora("Desgaste consumado: " + desgasteAcumulado);
        asis.registrarEventoBitacora("Mision cerrada");
    }

}

