package misiones;
import asistente.Asistente;

/**
 * <b>precondiciones</b>
 * - El objeto 'asistente' pasado por parámetro no debe ser null.
 * - La nave gestionada por el asistente debe estar inicializada.
 *
 * <b>postcondiciones</b>
 * - Si la nave no tiene recursos suficientes, el estado de la misión y los recursos del asistente quedan intactos.
 * - Si la misión se ejecuta, los recursos de la nave (combustible, desgaste, energía) serán actualizados según los costos de la misión concreta.
 * - Se registrarán todos los eventos en la bitácora del asistente.
 *
 * @param asistente El asistente que coordina la nave y provee los recursos.
 * @return true si la misión se completó con EXITO, false si fue un FRACASO o si no superó la fase de preparación por falta de recursos.
 * @throws ContratoMisionException Si ocurre un error interno grave de lógica donde la evaluación no genera un estado válido.
 */

public abstract class Mision {

    protected boolean preparada = false;
    protected String resultado = null;
    protected int combustibleGastado;
    protected int desgasteAcumulado;
    protected int energia;

    public final boolean ejecutarCicloMision (Asistente asistente) throws ContratoMisionException {
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
                throw new ContratoMisionException("La evaluación de la misión falló y no generó un estado");
            }
        }
    }

    protected void preparar(Asistente asistente){
        if (asistente.consultarCombustible() >= this.combustibleGastado && (asistente.consultarDesgaste() + this.desgasteAcumulado) <= 100){
            this.preparada = true;
            asistente.registrarEventoBitacora("Mision preparada con exito");
        }

    }

    protected abstract void ejecutarObjetivo(Asistente asistente);

    protected void evaluar(){
        double exito = Math.random();
        if (exito > 0.5){
            this.resultado = "EXITO";
        }else {
            this.resultado = "FRACASO";
        }
    }

    protected void cerrar(Asistente asistente){
        assert this.resultado.equals("EXITO") || this.resultado.equals("FRACASO") : "El resultado de la misión tomó un valor no reconocido";
        asistente.registrarEventoBitacora("Mision ejecutada: " + this.getClass().getSimpleName());
        asistente.registrarEventoBitacora("Resultado de la mision: " + this.resultado);
        if (this.energia > 0){
            asistente.cargaEnergia(this.energia);
            asistente.registrarEventoBitacora("Energia agregada: " + this.energia);
        }
        asistente.consumeCombustible(this.combustibleGastado);
        asistente.incrementaDesgaste(this.desgasteAcumulado);
        asistente.registrarEventoBitacora("Combustible gastado: " + this.combustibleGastado);
        asistente.registrarEventoBitacora("Desgaste consumado: " + this.desgasteAcumulado);
        asistente.registrarEventoBitacora("Mision cerrada");
    }

}

