package misiones;
import asistente.Asistente;
import java.util.List;

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
    protected int combustibleGastado = 0;
    protected int desgasteAcumulado = 0;
    protected int consumeCombustible;
    protected int generaDesgaste;
    protected int energia;
    protected Posicion destino;
    protected List<Posicion> ruta;

    public final boolean ejecutarCicloMision (Asistente asistente, Tablero tablero) throws ContratoMisionException {
        preparar(asistente, tablero);
        if(!this.preparada){
            return false;
        } else {
            ejecutarObjetivo(asistente, tablero);
            evaluar(asistente);
            if(this.resultado != null){
                cerrar(asistente);
                return this.resultado.equals("EXITO");
            } else{
                throw new ContratoMisionException("La evaluación de la misión falló y no generó un estado");
            }
        }
    }

    protected void preparar(Asistente asistente, Tablero tablero){
        if (asistente.consultarCombustible() >= this.consumeCombustible && (asistente.consultarDesgaste() + this.generaDesgaste) <= 100){
            this.preparada = true;
            asistente.registrarEventoBitacora("Mision preparada con exito");

            this.destino = tablero.obtenerObjetivo();

            this.ruta = asistente.calcularRuta(destino);
        }
        else{
            asistente.registrarEventoBitacora("Fallo en la preparacion de la mision");
        }
    }

    protected void ejecutarObjetivo(Asistente asistente, Tablero tablero) {
        asistente.consumeCombustible(this.consumeCombustible);
        this.combustibleGastado += consumeCombustible;
        asistente.registrarEventoBitacora("Consumo de despegue: " + this.combustibleGastado);

        asistente.incrementaDesgaste(this.generaDesgaste);
        this.desgasteAcumulado += generaDesgaste;
        asistente.registrarEventoBitacora("Desgaste de arranque: " + this.desgasteAcumulado);


        while (asistente.laNaveEstaViva() && !tablero.getPosicionNave().equals(this.destino)) {

            //TURNO Nave
            ElementoDelUniverso elemento = tablero.inspeccionarPosicion();
            if (elemento != null) {
                elemento.interactuar(asistente);
            }

            if (!asistente.laNaveEstaViva()) {
                this.resultado = "FRACASO";
                break;
            }

            if (!this.ruta.isEmpty()) {
                Posicion coordenadaFutura = this.ruta.get(0);
                ElementoDelUniverso[][] matrizTablero = tablero.verTablero();
                ElementoDelUniverso futuroEncuentro = matrizTablero[coordenadaFutura.getX()][coordenadaFutura.getY()];

                if (futuroEncuentro != null && futuroEncuentro.esAmenaza(asistente)) {
                    asistente.registrarEventoBitacora("Amenaza detectada. Recalculando la ruta");
                    this.ruta = asistente.calcularRuta(this.destino);
                }

                Posicion proximoPaso = this.ruta.remove(0);
                asistente.moverA(proximoPaso);

                asistente.consumeCombustible(this.consumeCombustible);
                this.combustibleGastado += consumeCombustible;
                asistente.incrementaDesgaste(this.generaDesgaste);
                this.desgasteAcumulado += generaDesgaste;

                asistente.registrarEventoBitacora("Combustible gastado: " + this.consumeCombustible);
            }

            //TURNO Tablero

            Destino coordinador = new Destino(tablero);
            coordinador.turnoDestino();

        }

        if (!asistente.laNaveEstaViva()) {
            this.resultado = "FRACASO";
            asistente.registrarEventoBitacora("Misión fallida: La nave fue destruida.");
            return;
        }

        boolean objetivoCumplido = verificarObjetivo(asistente);

        if(objetivoCumplido){
            this.resultado = "EXITO";
        }else{
            this.resultado = "FRACASO";
        }

    }

    protected abstract boolean verificarObjetivo(Asistente asistente);

    protected void evaluar(Asistente asistente){
        asistente.registrarInforme("Mision ejecutada: " + this.getClass().getSimpleName());
        asistente.registrarInforme("Resultado de la mision: " + this.resultado);
        asistente.registrarInforme("Combustible gastado: " + this.combustibleGastado);
        asistente.registrarInforme("Desgaste generado: " + this.desgasteAcumulado);
        asistente.registrarInforme("Mision cerrada");
    }

    protected void cerrar(Asistente asistente){



        if(this.resultado.equals("EXITO")){
            impactaRecursosFinales(asistente);
            asistente.registrarEventoBitacora("Cierre de mision: Impacto de recursos finales aplicado");
        }
        else{
            asistente.registrarEventoBitacora("Cierre de mision: Mision fallida, no hay recompensas");
        }


    }

    protected abstract void impactaRecursosFinales(Asistente asistente);

}

