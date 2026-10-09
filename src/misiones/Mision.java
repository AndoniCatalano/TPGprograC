package misiones;
import asistente.Asistente;

public abstract class Mision {

    protected boolean preparada = false;
    protected String resultado = null;
    protected int combustibleGastado;
    protected int desgasteAcumulado;
    protected int energia;

    public final boolean ejecutarCicloMision(Asistente asistente){
        preparar(asistente);
        if(!this.preparada){
            return false;
        } else {
            ejecutarObjetivo(asistente);
            evaluar();
            if(this.resultado != null){
                cerrar(asistente);
                return this.resultado.equals("EXITO");
            } else{
                throw new IllegalStateException("No se dieron las condiciones de exito");
            }
        }
    }

    public void preparar(Asistente asistente){
        if (asistente.consultarCombustible() >= this.combustibleGastado && asistente.consultarDesgaste() >= desgasteAcumulado){
            this.preparada = true;
            asistente.registrarEventoBitacora("Mision preparada con exito");
        }

    }

    protected abstract void ejecutarObjetivo(Asistente asistente);

    public void evaluar(){
        double exito = Math.random();
        if (exito > 0.5){
            this.resultado = "EXITO";
        }else {
            this.resultado = "FRACASO";
        }
    }

    public void cerrar(Asistente asistente){
        asistente.registrarEventoBitacora("Mision ejecutada: " + this.getClass().getSimpleName());
        asistente.registrarEventoBitacora("Resultado de la mision: " + this.resultado);
        asistente.cargaEnergia(this.energia);
        asistente.consumeCombustible(this.combustibleGastado);
        asistente.incrementaDesgaste(this.desgasteAcumulado);
        asistente.registrarEventoBitacora("Energia agregada: " + energia);
        asistente.registrarEventoBitacora("Combustible gastado: " + combustibleGastado);
        asistente.registrarEventoBitacora("Desgaste consumado: " + desgasteAcumulado);
        asistente.registrarEventoBitacora("Mision cerrada");
    }

}

