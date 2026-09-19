package misiones;
import java.util.List;
import java.util.ArrayList;

public abstract class Mision {

    protected boolean preparada = false;
    protected String resultado = null;
    protected List<String> acciones = new ArrayList<>();
    protected double combustibleGastado;
    protected List<String> observaciones = new ArrayList<>();
    Informe inf = new Informe();

    public final void ejecutarCicloMision(double combustible, double desgaste,) {

        preparar(combustible, desgaste);
        if (!this.preparada) {
            throw new IllegalStateException("Error de contrato: Una misión no podrá ejecutarse sin preparación previa.");
        }

        ejecutarObjetivo();
        evaluar();
        if (this.resultado == null) {
            throw new IllegalStateException("Error de contrato: Una misión no podrá cerrarse sin resultado e informe.");
        }

        cerrar();
    }

    public void preparar(double combustible, double desgaste){
        if (desgaste>3 && combustible>3) {
            this.preparada = true;
        }
    }

    protected abstract void ejecutarObjetivo();

    public void evaluar() {
        double exito = Math.random();
        if (exito > 0.3) {
            this.resultado = "EXITO";
        } else {
            this.resultado = "FRACASO";
        }
    }

    public void cerrar() {

        this.inf.setMision_ejecutada(this);
        this.inf.setResultado(this.resultado);
        this.inf.setEnergiaCons((int) this.getEnergia());
        this.inf.setAccionesRealizadas(this.acciones);
        this.inf.setCombustibleCons(this.combustibleGastado);
        this.inf.setEstadoFinal(this.estado);
        this.inf.setObservaciones(this.observaciones);

    }

    public abstract double getEnergia();

    public Informe getInforme(){
        return inf;
    }


}

