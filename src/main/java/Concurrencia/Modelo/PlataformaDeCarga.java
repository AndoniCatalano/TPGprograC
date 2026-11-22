package Concurrencia.Modelo;

import java.util.ArrayList;
import java.util.List;

public class PlataformaDeCarga {
    private ArrayList<Bateria> baterias = new ArrayList<>();    //no es final para poder reiniciarlo
    private final int capacidadMax;
    private final List<IObserverPlataforma> observadores = new ArrayList<>();

    /**
     * Constructor del recurso compartido de baterías.
     * <b>Pre:</b> capacidadMax > 0.
     * <b>Post:</b> La plataforma se instancia vacía con la capacidad máxima indicada y su lista de observadores inicializada.
     *
     * @param capacidadMax La cantidad máxima de baterías que puede alojar la plataforma.
     */
    public PlataformaDeCarga(int capacidadMax) {
        assert capacidadMax > 0 : "Precondición fallida: capacidadMax debe ser mayor a 0";

        this.capacidadMax = capacidadMax;

        assert this.baterias.isEmpty() : "Postcondición fallida: la plataforma no inició vacía";
        assert invariante() : "Fallo invariante en el constructor de PlataformaDeCarga";
    }

    /**
     * Testea el invariante de clase garantizando los límites del stock y la integridad de las estructuras internas.
     */
    private boolean invariante() {
        return this.capacidadMax > 0
                && this.baterias != null
                && this.baterias.size() <= this.capacidadMax
                && this.observadores != null;
    }

    /**
     * Suscribe un nuevo observador para recibir notificaciones de eventos de la plataforma.
     * <b>Pre:</b> observador no debe ser nulo.
     * <b>Post:</b> El observador queda agregado a la lista de observadores.
     *
     * @param observador El observador a suscribir.
     */
    public synchronized void agregarObservador(IObserverPlataforma observador) {
        assert observador != null : "Precondición fallida: el observador a agregar no puede ser nulo";

        int cantAnt = this.observadores.size();
        this.observadores.add(observador);

        assert this.observadores.size() == cantAnt + 1 : "Postcondición fallida: no se agregó el observador";
        assert invariante() : "Fallo invariante en agregarObservador";
    }

    /**
     * Elimina un observador previamente suscripto de la lista de notificaciones.
     * <b>Pre:</b> observador no debe ser nulo.
     * <b>Post:</b> El observador deja de recibir eventos de la plataforma.
     *
     * @param observador El observador a remover.
     */
    public synchronized void quitarObservador(IObserverPlataforma observador) {
        assert observador != null : "Precondición fallida: el observador a quitar no puede ser nulo";

        this.observadores.remove(observador);

        assert invariante() : "Fallo invariante en quitarObservador";
    }

    /**
     * Registra y difunde un evento informativo hacia todos los observadores suscriptos.
     * <b>Pre:</b> mensaje no debe ser nulo.
     * <b>Post:</b> Todos los observadores son notificados con el mensaje indicado.
     *
     * @param mensaje El texto del evento a registrar.
     */
    public synchronized void registrarEvento(String mensaje) {
        assert mensaje != null : "Precondición fallida: el mensaje del evento no puede ser nulo";

        notificarObservadores(mensaje);
    }

    private void notificarObservadores(String mensaje) {
        for (IObserverPlataforma observador : this.observadores) {
            observador.actualizarEvento(mensaje);
        }
    }

    /**
     * Deposita una batería en la plataforma respetando la capacidad máxima mediante exclusión mutua.
     * <b>Pre:</b> nuevaBateria no debe ser nula.
     * <b>Post:</b> La batería queda almacenada en la plataforma, el stock se incrementa en 1 y se notifica a los hilos en espera.
     * @param nuevaBateria La batería producida que se desea depositar.
     * @throws InterruptedException Si el hilo es interrumpido mientras espera espacio disponible o durante la transferencia.
     */
    public synchronized void depositarBateria(Bateria nuevaBateria) throws InterruptedException {
        assert nuevaBateria != null : "Precondición fallida: nuevaBateria no puede ser nula";

        while (this.baterias.size() >= this.capacidadMax) {
            notificarObservadores("[" + nombreHilo() + "] esperando a que se libere espacio en la Plataforma.");
            wait();
        }

        int stockAnt = this.baterias.size();
        this.baterias.add(nuevaBateria);

        assert this.baterias.size() == stockAnt + 1 : "Postcondición fallida: el stock no se incrementó tras depositar";
        assert invariante() : "Fallo invariante en depositarBateria";

        notificarObservadores("[" + nombreHilo() + "] depositó " + nuevaBateria +
                ". (Stock: " + this.baterias.size() + "/" + this.capacidadMax + ")");

        // Mantiene visible el stock actualizado mientras la generadora ocupa la plataforma
        Thread.sleep(650);
        notifyAll();
    }

    /**
     * Retira y devuelve la batería más antigua disponible en la plataforma (orden FIFO).
     * <b>Pre:</b> La plataforma debe estar inicializada.
     * <b>Post:</b> Se elimina una batería de la plataforma, el stock disminuye en 1, se notifica a los hilos en espera y se retorna la batería retirada.
     * @return La batería retirada de la plataforma.
     * @throws InterruptedException Si el hilo es interrumpido mientras espera disponibilidad de baterías o durante la transferencia.
     */
    public synchronized Bateria consumirBateria() throws InterruptedException {
        while (this.baterias.isEmpty()) {
            notificarObservadores("[" + nombreHilo() + "] esperando por falta de baterías en la Plataforma.");
            wait();
        }

        int stockAnt = this.baterias.size();
        Bateria bateriaRetirada = this.baterias.remove(0);

        assert bateriaRetirada != null : "Postcondición fallida: la batería retirada es nula";
        assert this.baterias.size() == stockAnt - 1 : "Postcondición fallida: el stock no disminuyó tras consumir";
        assert invariante() : "Fallo invariante en consumirBateria";

        notificarObservadores("Batería retirada y utilizada por [" + nombreHilo() + "]: " +
                bateriaRetirada + ". (Stock: " + this.baterias.size() + "/" + this.capacidadMax + ")");

        // Mantiene visible el stock actualizado mientras la nave ocupa la plataforma
        Thread.sleep(650);
        notifyAll();
        return bateriaRetirada;
    }

    /**
     * Vacía el stock de baterías de la plataforma y despierta a los hilos en espera.
     * <b>Pre:</b> La plataforma debe estar inicializada.
     * <b>Post:</b> La lista de baterías queda vacía (stock en 0) y se notifica el reinicio a los observadores.
     */
    public synchronized void reiniciar() {
        this.baterias = new ArrayList<>();
        notifyAll();

        assert this.baterias.isEmpty() : "Postcondición fallida: la plataforma no quedó vacía tras reiniciar";
        assert invariante() : "Fallo invariante en reiniciar";

        notificarObservadores("--- Simulación reiniciada. Plataforma lista (Stock: 0/" + this.capacidadMax + ") ---");
    }

    private String nombreHilo() {
        return Thread.currentThread().getName();
    }
}