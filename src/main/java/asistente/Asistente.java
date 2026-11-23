package asistente;

import nave.*;
import misiones.*;
import motor.*;
import asistente.vista.AsistenteObserver;
import Tripulantes.Tripulante;
import nave.tablero.CantidadDeElementosSuperaCasillaException;
import nave.tablero.DimensionesDeTableroInconsistentesException;
import nave.tablero.Posicion;
import nave.tablero.Tablero;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Asistente {
    private ArrayList<String> informe;
    private ArrayList<Evento> bitacora;
    private INave nave;
    private ArrayList<AsistenteObserver> observadores = new ArrayList<>();

    // Atributos nuevos para el soporte de misiones y navegación
    private Tablero tablero;
    private int recursosAsistencia = 1;

    /**
     * Constructor del asistente de comando vinculado a una nave.
     * <b>Pre:</b> nave no debe ser nula.
     * <b>Post:</b> El asistente queda vinculado a la nave, con bitácora e informe inicializados vacíos,
     * recursosAsistencia en 1 y el tablero construido.
     *
     * @param nave La nave que controlará el asistente.
     */
    public Asistente(INave nave) {
        assert nave != null : "Precondición fallida: nave no puede ser nula";

        this.bitacora = new ArrayList<>();
        this.informe = new ArrayList<>();
        this.nave = nave;
        this.tablero = construirTablero();

        assert this.nave == nave : "Postcondición fallida: la nave no quedó vinculada al asistente";
        assert this.bitacora.isEmpty() && this.informe.isEmpty() : "Postcondición fallida: la bitácora o el informe no iniciaron vacíos";
        assert invariante() : "Fallo invariante en el constructor de Asistente";
    }

    /**
     * Testea el invariante de clase garantizando la integridad de la nave, las colecciones internas y los recursos de asistencia.
     */
    private boolean invariante() {
        return this.nave != null
                && this.bitacora != null
                && this.informe != null
                && this.observadores != null
                && this.recursosAsistencia >= 0;
    }

    /**
     * Suscribe un nuevo observador para recibir actualizaciones de recursos, bitácora e informes.
     * <b>Pre:</b> obs no debe ser nulo.
     * <b>Post:</b> El observador queda agregado a la lista de observadores y se notifica el estado actual de recursos.
     *
     * @param obs El observador a suscribir.
     */
    public void agregarObservador(AsistenteObserver obs) {
        assert obs != null : "Precondición fallida: el observador a agregar no puede ser nulo";

        int cantAnt = this.observadores.size();
        this.observadores.add(obs);
        notificarRecursos();

        assert this.observadores.size() == cantAnt + 1 : "Postcondición fallida: no se agregó el observador";
        assert invariante() : "Fallo invariante en agregarObservador";
    }

    private void notificarRecursos() {
        for (AsistenteObserver obs : observadores) {
            obs.onRecursosActualizados(consultarCombustible(), consultarEnergia(), consultarDesgaste());
        }
    }

    private Tablero construirTablero() {
        try {
            int dimX = (int) (Math.random() * 96) + 5;
            int dimY = (int) (Math.random() * 96) + 5;
            int cantElem = (int) (Math.random() * (dimX * dimY / 4) * 3);

            ArrayList<IElementosUniverso> elementos = new ArrayList<>();

            for (int i = 0; i < cantElem; i++) {
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        elementos.add(new Asteroide());
                        break;
                    case 1:
                        elementos.add(new EstacionDeCarga());
                        break;
                    case 2:
                        elementos.add(new NaveEnemiga());
                        break;
                }
            }

            Tablero tablero = new Tablero(dimX, dimY, elementos);
            return tablero;
        } catch (DimensionesDeTableroInconsistentesException | CantidadDeElementosSuperaCasillaException e) {

        }

        return null;
    }

    /**
     * Ejecuta el ciclo completo de una misión sobre el tablero asignado.
     * <b>Pre:</b> mision no debe ser nula y el tablero debe estar asignado (tablero != null).
     * <b>Post:</b> Se intenta ejecutar el ciclo de la misión y, ante éxito o fallo de contrato, se registra el resultado en la bitácora.
     *
     * @param mision La misión a ejecutar.
     */
    public void ejecutarMision(Mision mision) {
        assert mision != null : "Precondición fallida: mision no puede ser nula";
        assert this.tablero != null : "Precondición fallida: el tablero no fue asignado";

        try {
            boolean exito = mision.ejecutarCicloMision(this, this.tablero);

            if (exito) {
                this.registrarEventoBitacora("Misión finalizada. Éxito");
            }
        } catch (ContratoMisionException error) {
            this.registrarEventoBitacora("Misión abortada: " + error.getMessage());
        }

        assert invariante() : "Fallo invariante en ejecutarMision";
    }

    /**
     * Ordena a la nave realizar un salto normal hacia la posición destino indicada.
     * <b>Pre:</b> destino no debe ser nulo y el tablero debe estar asignado (tablero != null).
     * <b>Post:</b> Si el salto se realiza, se notifican los recursos actualizados; ante un error, se registra en la bitácora.
     *
     * @param destino La posición destino del salto.
     */
    public void saltar(Posicion destino) {
        assert destino != null : "Precondición fallida: destino no puede ser nulo";
        assert this.tablero != null : "Precondición fallida: el tablero no fue asignado";

        try {
            nave.saltar(this, destino);
            notificarRecursos();
        } catch (Exception e) {
            registrarEventoBitacora("No se pudo realizar el salto normal: " + e.getMessage());
        }

        assert invariante() : "Fallo invariante en saltar";
    }

    /**
     * Ordena a la nave realizar un salto Warp hacia la posición destino indicada.
     * <b>Pre:</b> destino no debe ser nulo y el tablero debe estar asignado (tablero != null).
     * <b>Post:</b> Si el salto Warp se realiza, se notifican los recursos actualizados; ante un error, se registra en la bitácora.
     *
     * @param destino La posición destino del salto Warp.
     */
    public void saltoWarp(Posicion destino) {
        assert destino != null : "Precondición fallida: destino no puede ser nulo";
        assert this.tablero != null : "Precondición fallida: el tablero no fue asignado";

        try {
            nave.saltarWarp(this, destino);
            notificarRecursos();
        } catch (Exception e) {
            registrarEventoBitacora("Maniobra Warp interrumpida: " + e.getMessage());
        }

        assert invariante() : "Fallo invariante en saltoWarp";
    }

    /**
     * Registra un nuevo evento en la bitácora y notifica a todos los observadores suscriptos.
     * <b>Pre:</b> mensaje no debe ser nulo.
     * <b>Post:</b> Se agrega un nuevo evento a la bitácora (incrementando su tamaño en 1) y todos los observadores son notificados.
     *
     * @param mensaje El texto descriptivo del evento a registrar.
     */
    public void registrarEventoBitacora(String mensaje) {
        assert mensaje != null : "Precondición fallida: el mensaje del evento no puede ser nulo";

        int cantAnt = this.bitacora.size();
        this.bitacora.add(new Evento(mensaje));
        for (AsistenteObserver obs : observadores) {
            obs.onEventoBitacora(mensaje);
        }

        assert this.bitacora.size() == cantAnt + 1 : "Postcondición fallida: el evento no se agregó a la bitácora";
        assert invariante() : "Fallo invariante en registrarEventoBitacora";
    }

    /**
     * Registra un nuevo informe de misión y notifica a todos los observadores suscriptos.
     * <b>Pre:</b> mensaje no debe ser nulo.
     * <b>Post:</b> Se agrega el informe a la lista interna (incrementando su tamaño en 1) y todos los observadores son notificados.
     *
     * @param mensaje El texto del informe generado.
     */
    public void registrarInforme(String mensaje) {
        assert mensaje != null : "Precondición fallida: el mensaje del informe no puede ser nulo";

        int cantAnt = this.informe.size();
        this.informe.add(mensaje);
        for (AsistenteObserver obs : observadores) {
            obs.onInformeGenerado(mensaje);
        }

        assert this.informe.size() == cantAnt + 1 : "Postcondición fallida: el informe no se agregó a la lista";
        assert invariante() : "Fallo invariante en registrarInforme";
    }

    /**
     * Incorpora un tripulante a la nave controlada por el asistente.
     * <b>Pre:</b> tripulante no debe ser nulo.
     * <b>Post:</b> El tripulante queda asignado a la nave vinculada.
     *
     * @param tripulante El tripulante a incorporar.
     */
    public void addTripulante(Tripulante tripulante) {
        assert tripulante != null : "Precondición fallida: tripulante no puede ser nulo";

        this.nave.addTripulante(tripulante);

        assert invariante() : "Fallo invariante en addTripulante";
    }

    /**
     * Solicita a la nave la liquidación de haberes de su tripulación.
     * <b>Pre:</b> La nave debe estar inicializada.
     * <b>Post:</b> Se ejecuta la liquidación de haberes de los tripulantes de la nave.
     */
    public void mostrarSueldos() {
        this.nave.liquidarHaberes();

        assert invariante() : "Fallo invariante en mostrarSueldos";
    }

    public int consultarEnergia() {
        return nave.getEnergia();
    }

    public int consultarCombustible() {
        return nave.getCombustible();
    }

    public int consultarDesgaste() {
        return nave.getDesgaste();
    }

    /**
     * Solicita la carga de combustible en la nave y registra el resultado en la bitácora.
     * <b>Pre:</b> La nave debe estar inicializada.
     * <b>Post:</b> Si la operación es válida, aumenta el combustible y notifica a los observadores;
     * en todos los casos se registra el evento resultante en la bitácora.
     *
     * @param cantidad La cantidad de unidades de combustible a cargar.
     */
    public void cargaCombustible(int cantidad) {
        int bitacoraAnt = this.bitacora.size();

        try {
            nave.cargaCombustible(cantidad);
            registrarEventoBitacora("Se cargaron " + cantidad + " unidades de combustible");
            notificarRecursos();
        } catch (OperacionRechazadaException e) {
            registrarEventoBitacora(e.getMessage());
        }

        assert this.bitacora.size() == bitacoraAnt + 1 : "Postcondición fallida: no se registró la operación en la bitácora";
        assert invariante() : "Fallo invariante en cargaCombustible";
    }

    /**
     * Solicita la carga de energía en la nave y registra el resultado en la bitácora.
     * <b>Pre:</b> La nave debe estar inicializada.
     * <b>Post:</b> Si la operación es válida, aumenta la energía y notifica a los observadores;
     * en todos los casos se registra el evento resultante en la bitácora.
     *
     * @param cantidad La cantidad de unidades de energía a cargar.
     */
    public void cargaEnergia(int cantidad) {
        int bitacoraAnt = this.bitacora.size();

        try {
            nave.cargaEnergia(cantidad);
            registrarEventoBitacora("Se cargaron " + cantidad + " unidades de energia");
            notificarRecursos();
        } catch (OperacionRechazadaException e) {
            registrarEventoBitacora(e.getMessage());
        }

        assert this.bitacora.size() == bitacoraAnt + 1 : "Postcondición fallida: no se registró la operación en la bitácora";
        assert invariante() : "Fallo invariante en cargaEnergia";
    }

    /**
     * Imprime por consola todos los eventos almacenados en la bitácora.
     * <b>Pre:</b> La bitácora debe estar inicializada.
     * <b>Post:</b> Todos los eventos de la bitácora son mostrados por salida estándar sin alterar su contenido.
     */
    public void mostrarBitacora() {
        for (Evento evento : bitacora) {
            System.out.println(evento);
        }

        assert invariante() : "Fallo invariante en mostrarBitacora";
    }

    /**
     * Solicita el mantenimiento de la nave y registra el resultado en la bitácora.
     * <b>Pre:</b> La nave debe estar inicializada.
     * <b>Post:</b> Si la operación es aceptada, se realiza el mantenimiento y se notifican los recursos;
     * en todos los casos se registra el evento resultante en la bitácora.
     */
    public void realizarMantenimiento() {
        int bitacoraAnt = this.bitacora.size();

        try {
            nave.realizarMantenimiento();
            registrarEventoBitacora("Se realizo mantenimiento en la nave");
            notificarRecursos();
        } catch (OperacionRechazadaException e) {
            registrarEventoBitacora(e.getMessage());
        }

        assert this.bitacora.size() == bitacoraAnt + 1 : "Postcondición fallida: no se registró la operación en la bitácora";
        assert invariante() : "Fallo invariante en realizarMantenimiento";
    }

    /**
     * Solicita el consumo de combustible en la nave y registra el resultado en la bitácora.
     * <b>Pre:</b> La nave debe estar inicializada.
     * <b>Post:</b> Si la operación es válida, disminuye el combustible y notifica a los observadores;
     * en todos los casos se registra el evento resultante en la bitácora.
     *
     * @param cantidad La cantidad de unidades de combustible a consumir.
     */
    public void consumeCombustible(int cantidad) {
        int bitacoraAnt = this.bitacora.size();

        try {
            nave.consumeCombustible(cantidad);
            registrarEventoBitacora("Se consumieron " + cantidad + " unidades de combustible");
            notificarRecursos();
        } catch (OperacionRechazadaException e) {
            registrarEventoBitacora(e.getMessage());
        }

        assert this.bitacora.size() == bitacoraAnt + 1 : "Postcondición fallida: no se registró la operación en la bitácora";
        assert invariante() : "Fallo invariante en consumeCombustible";
    }

    /**
     * Solicita el consumo de energía en la nave y registra el resultado en la bitácora.
     * <b>Pre:</b> La nave debe estar inicializada.
     * <b>Post:</b> Si la operación es válida, disminuye la energía y notifica a los observadores;
     * en todos los casos se registra el evento resultante en la bitácora.
     *
     * @param cantidad La cantidad de unidades de energía a consumir.
     */
    public void consumeEnergia(int cantidad) {
        int bitacoraAnt = this.bitacora.size();

        try {
            nave.consumeEnergia(cantidad);
            registrarEventoBitacora("Se consumieron " + cantidad + " unidades de energia");
            notificarRecursos();
        } catch (OperacionRechazadaException e) {
            registrarEventoBitacora(e.getMessage());
        }

        assert this.bitacora.size() == bitacoraAnt + 1 : "Postcondición fallida: no se registró la operación en la bitácora";
        assert invariante() : "Fallo invariante en consumeEnergia";
    }

    /**
     * Solicita incrementar el desgaste de la nave y registra el resultado en la bitácora.
     * <b>Pre:</b> La nave debe estar inicializada.
     * <b>Post:</b> Si la operación es válida, incrementa el desgaste y notifica a los observadores;
     * en todos los casos se registra el evento resultante en la bitácora.
     *
     * @param cantidad La cantidad de unidades de desgaste a incrementar.
     */
    public void incrementaDesgaste(int cantidad) {
        int bitacoraAnt = this.bitacora.size();

        try {
            nave.incrementaDesgaste(cantidad);
            registrarEventoBitacora("Se incremento el desgaste de la nave en " + cantidad + " unidades");
            notificarRecursos();
        } catch (OperacionRechazadaException e) {
            registrarEventoBitacora(e.getMessage());
        }

        assert this.bitacora.size() == bitacoraAnt + 1 : "Postcondición fallida: no se registró la operación en la bitácora";
        assert invariante() : "Fallo invariante en incrementaDesgaste";
    }

    // metodos segunda parte, tablero, nave, misiones parte 2

    /**
     * Asigna un nuevo tablero de navegación al asistente.
     * <b>Pre:</b> tablero no debe ser nulo.
     * <b>Post:</b> El atributo tablero queda actualizado con la referencia recibida.
     *
     * @param tablero El tablero a asignar.
     */
    public void setTablero(Tablero tablero) {
        assert tablero != null : "Precondición fallida: tablero no puede ser nulo";

        this.tablero = tablero;

        assert this.tablero == tablero : "Postcondición fallida: el tablero no fue asignado correctamente";
        assert invariante() : "Fallo invariante en setTablero";
    }

    /**
     * Asigna un tablero y ejecuta sobre él la misión indicada.
     * <b>Pre:</b> mision y tablero no deben ser nulos.
     * <b>Post:</b> El tablero queda asignado y se ejecuta el ciclo de la misión registrando el resultado en la bitácora.
     *
     * @param mision La misión a ejecutar.
     * @param tablero El tablero donde se desarrollará la misión.
     */
    public void ejecutarMision(Mision mision, Tablero tablero) {
        assert mision != null : "Precondición fallida: mision no puede ser nula";
        assert tablero != null : "Precondición fallida: tablero no puede ser nulo";

        this.tablero = tablero;
        ejecutarMision(mision);

        assert invariante() : "Fallo invariante en ejecutarMision";
    }

    /**
     * Verifica si la nave se encuentra operativa según su desgaste, combustible y energía.
     * <b>Pre:</b> La nave debe estar inicializada.
     * <b>Post:</b> Retorna true si el desgaste es menor a 100 y tanto el combustible como la energía son mayores a 0; false en caso contrario.
     *
     * @return true si la nave sigue operativa; false en caso contrario.
     */
    public boolean laNaveEstaViva() {
        return this.nave.getDesgaste() < 100 && this.nave.getCombustible() > 0 && this.nave.getEnergia() > 0;
    }

    /**
     * Desplaza la nave en el tablero hacia la posición indicada y registra el movimiento en la bitácora.
     * <b>Pre:</b> proximoPaso no debe ser nulo y el tablero debe estar asignado (tablero != null).
     * <b>Post:</b> La nave se mueve a proximoPaso en el tablero y se agrega el evento de desplazamiento a la bitácora.
     *
     * @param proximoPaso La nueva posición a la que se desplaza la nave.
     */
    public void moverA(Posicion proximoPaso) {
        assert proximoPaso != null : "Precondición fallida: la posición destino no puede ser nula";
        assert this.tablero != null : "Precondición fallida: el tablero no fue asignado";

        int bitacoraAnt = this.bitacora.size();
        this.tablero.moverNave(proximoPaso);
        registrarEventoBitacora("Nave desplazada a (" + proximoPaso.getX() + ", " + proximoPaso.getY() + ")");

        assert this.bitacora.size() == bitacoraAnt + 1 : "Postcondición fallida: no se registró el desplazamiento en la bitácora";
        assert invariante() : "Fallo invariante en moverA";
    }

    /**
     * Calcula una ruta libre de amenazas desde la posición actual de la nave hasta el destino usando búsqueda en anchura (BFS).
     * <b>Pre:</b> destino no debe ser nulo y el tablero debe estar asignado (tablero != null).
     * <b>Post:</b> Retorna una lista no nula con la secuencia de posiciones hasta el destino, o una lista vacía si no hay camino o la nave ya se encuentra en destino.
     *
     * @param destino La posición objetivo a alcanzar.
     * @return La lista ordenada de posiciones que conforman la ruta calculada.
     */
    public List<Posicion> calcularRuta(Posicion destino) {
        assert destino != null : "Precondición fallida: destino no puede ser nulo";
        assert this.tablero != null : "Precondición fallida: el tablero no fue asignado";

        IElementosUniverso[][] grilla = this.tablero.verTablero();
        int filas = this.tablero.getX();
        int columnas = this.tablero.getY();
        Posicion inicio = this.tablero.getPosicionNave();

        if (inicio == null || inicio.equals(destino)) {
            return new ArrayList<>();
        }

        boolean[][] visitado = new boolean[filas][columnas];
        Posicion[][] padre = new Posicion[filas][columnas];
        Queue<Posicion> cola = new LinkedList<>();

        cola.add(inicio);
        visitado[inicio.getX()][inicio.getY()] = true;

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        while (!cola.isEmpty()) {
            Posicion actual = cola.poll();
            if (actual.equals(destino)) {
                break;
            }

            for (int i = 0; i < 8; i++) {
                int nx = actual.getX() + dx[i];
                int ny = actual.getY() + dy[i];

                if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas && !visitado[nx][ny]) {
                    IElementosUniverso elemento = grilla[nx][ny];
                    boolean esDestino = (nx == destino.getX() && ny == destino.getY());

                    if (esDestino || elemento == null || !elemento.esAmenaza(this)) {
                        visitado[nx][ny] = true;
                        padre[nx][ny] = actual;
                        cola.add(new Posicion(nx, ny));
                    }
                }
            }
        }

        LinkedList<Posicion> ruta = new LinkedList<>();
        Posicion paso = destino;
        while (paso != null && !paso.equals(inicio)) {
            ruta.addFirst(paso);
            paso = padre[paso.getX()][paso.getY()];
        }

        if (paso == null) {
            return new ArrayList<>();
        }

        assert ruta != null : "Postcondición fallida: la ruta calculada no puede ser nula";
        assert invariante() : "Fallo invariante en calcularRuta";
        return ruta;
    }

    /**
     * Indica si el asistente aún cuenta con recursos de asistencia disponibles para entregar.
     * <b>Pre:</b> El asistente debe estar inicializado.
     * <b>Post:</b> Retorna true si recursosAsistencia > 0; false en caso contrario.
     *
     * @return true si hay recursos disponibles para entregar; false en caso contrario.
     */
    public boolean tieneRecursosParaEntregar() {
        return this.recursosAsistencia > 0;
    }

    /**
     * Entrega una unidad de recursos de asistencia en destino si hay disponibilidad.
     * <b>Pre:</b> El asistente debe estar inicializado.
     * <b>Post:</b> Si recursosAsistencia era mayor a 0, se decrementa en 1 y se registra la entrega en la bitácora;
     * en caso contrario no se modifica el estado.
     */
    public void entregarRecursos() {
        if (this.recursosAsistencia > 0) {
            int recursosAnt = this.recursosAsistencia;
            this.recursosAsistencia--;
            registrarEventoBitacora("Se entregaron los recursos de asistencia en destino");

            assert this.recursosAsistencia == recursosAnt - 1 : "Postcondición fallida: no se descontaron los recursos de asistencia";
        }

        assert invariante() : "Fallo invariante en entregarRecursos";
    }

    public INave getNave() {
        return nave;
    }

    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Ejecuta un mantenimiento general en la nave reduciendo su desgaste a 0.
     * <b>Pre:</b> La nave debe estar inicializada.
     * <b>Post:</b> El desgaste de la nave queda en 0, se registra el mantenimiento general en la bitácora y se notifica a los observadores.
     */
    public void mantenimientoGeneral() {
        int bitacoraAnt = this.bitacora.size();

        nave.mantenimientoGeneral();
        registrarEventoBitacora("Mantenimiento general completado: desgaste reducido a 0.");
        notificarRecursos();

        assert this.nave.getDesgaste() == 0 : "Postcondición fallida: el desgaste no se redujo a 0 tras el mantenimiento general";
        assert this.bitacora.size() == bitacoraAnt + 1 : "Postcondición fallida: no se registró el mantenimiento general en la bitácora";
        assert invariante() : "Fallo invariante en mantenimientoGeneral";
    }
}