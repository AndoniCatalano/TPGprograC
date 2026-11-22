package misiones;
import asistente.Asistente;
import java.util.List;
import nave.tablero.Posicion;
import nave.tablero.Tablero;
import nave.IElementosUniverso;
import nave.tablero.Destino;

/**
 * <b>precondiciones</b>
 * - El objeto 'asistente' pasado por parámetro no debe ser null.
 * - La nave gestionada por el asistente debe estar inicializada.
 * - El tablero no puede ser null.
 *
 * <b>postcondiciones</b>
 * - Si la nave no tiene recursos suficientes, el estado de la misión y los recursos del asistente quedan intactos.
 * - Si la misión se ejecuta, los recursos de la nave (combustible, desgaste, energía) serán actualizados según los costos de la misión concreta.
 * - Se registrarán todos los eventos en la bitácora del asistente.
 *
 * @param asistente El asistente que coordina la nave y provee los recursos.
 * @param tablero El tablero del unvierso por el cual navegara la nave.
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
    protected int gastaEnergia;
    protected int energiaGastada = 0;
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
        if (asistente.consultarCombustible() >= this.consumeCombustible && asistente.consultarEnergia() >= this.gastaEnergia && (asistente.consultarDesgaste() + this.generaDesgaste) <= 100){
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

        if (this.gastaEnergia > 0) {
            asistente.consumeEnergia(this.gastaEnergia);
            this.energiaGastada += this.gastaEnergia;
            asistente.registrarEventoBitacora("Energia consumida por inicio de mision: " + this.energiaGastada);
        }

        Destino coordinador = new Destino(tablero);

        while (asistente.laNaveEstaViva() && !tablero.getPosicionNave().equals(this.destino)) {

            //TURNO Nave
            IElementosUniverso elemento = tablero.inspeccionarPosicion();
            if (elemento != null) {
                elemento.interactuarCon(asistente);
            }

            if (!asistente.laNaveEstaViva()) {
                this.resultado = "FRACASO";
                break;
            }

            if (!this.ruta.isEmpty()) {
                if (asistente.consultarEnergia() < 1) {
                    asistente.registrarEventoBitacora("Energía agotada para operar el radar.");
                    this.resultado = "FRACASO";
                    break;
                }

                IElementosUniverso[][] alrededores = tablero.observarAlrededores();
                asistente.consumeEnergia(1);
                this.energiaGastada += 1;

                Posicion actual = tablero.getPosicionNave();
                Posicion coordenadaFutura = this.ruta.get(0);
                int dx = coordenadaFutura.getX() - actual.getX();
                int dy = coordenadaFutura.getY() - actual.getY();
                IElementosUniverso futuroEncuentro = alrededores[dx + 1][dy + 1];

                if (futuroEncuentro != null && futuroEncuentro.esAmenaza(asistente)) {

                    if (this.ruta.size() >= 2) {
                        if (asistente.consultarEnergia() < 5) {
                            asistente.registrarEventoBitacora("Energía insuficiente (< 5) para esquivar obstáculo con Warp.");
                            this.resultado = "FRACASO";
                            break;
                        }

                        Posicion destinoWarp = this.ruta.get(1);
                        asistente.saltoWarp(destinoWarp);

                        if (tablero.getPosicionNave().equals(destinoWarp)) {
                            this.ruta.remove(0);
                            this.ruta.remove(0);
                            this.energiaGastada += 5;
                        }
                    } else {
                        if (asistente.consultarEnergia() < 2) {
                            asistente.registrarEventoBitacora("Energía insuficiente (< 2) para recalcular ruta.");
                            this.resultado = "FRACASO";
                            break;
                        }

                        asistente.registrarEventoBitacora("Obstaculo detectado, recalculando ruta");
                        this.ruta = asistente.calcularRuta(this.destino);
                        asistente.consumeEnergia(2);
                        this.energiaGastada += 2;
                    }
                } else {
                    if (asistente.consultarCombustible() < 2) {
                        asistente.registrarEventoBitacora("Combustible insuficiente (< 2) para realizar salto.");
                        this.resultado = "FRACASO";
                        break;
                    }

                    asistente.saltar(coordenadaFutura);

                    if (tablero.getPosicionNave().equals(coordenadaFutura)) {
                        this.ruta.remove(0);
                        this.combustibleGastado += 2;
                    }
                }
            } else {
                asistente.registrarEventoBitacora("No hay ruta disponible hacia el destino");
                this.resultado = "FRACASO";
                break;
            }

            if (!asistente.laNaveEstaViva()) {
                this.resultado = "FRACASO";
                break;
            }

            //TURNO Tablero
            coordinador.turnoDestino();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        if (!asistente.laNaveEstaViva()) {
            this.resultado = "FRACASO";
            asistente.registrarEventoBitacora("Misión fallida: La nave fue destruida o se quedó sin energía.");
            return;
        }
        if (!tablero.getPosicionNave().equals(this.destino)) {
            this.resultado = "FRACASO";
            asistente.registrarEventoBitacora("Misión fallida: No se pudo alcanzar el destino.");
            return;
        }

        boolean objetivoCumplido = verificarObjetivo(asistente);

        if (objetivoCumplido) {
            this.resultado = "EXITO";
        } else {
            this.resultado = "FRACASO";
        }
    }

    protected abstract boolean verificarObjetivo(Asistente asistente);

    protected void evaluar(Asistente asistente){
        asistente.registrarInforme("Mision ejecutada: " + this.getClass().getSimpleName());
        asistente.registrarInforme("Resultado de la mision: " + this.resultado);
        asistente.registrarInforme("Combustible gastado: " + this.combustibleGastado);
        asistente.registrarInforme("Desgaste generado: " + this.desgasteAcumulado);
        asistente.registrarInforme("Energia gastada: " + this.energiaGastada);
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

