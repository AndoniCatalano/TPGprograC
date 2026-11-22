package Concurrencia.Modelo;

import java.util.ArrayList;
import java.util.List;

public class UniversoConcurrente {
    private final PlataformaDeCarga plataforma;
    private List<Thread> hilosActivos = new ArrayList<>();
    private int contNaves = 0;
    private int contGeneradoras = 0;
    private boolean simulacionActiva = true;

    /**
     * Constructor de la simulación del universo paralelo.
     * <b>Pre:</b> capacidadMaxPlataforma > 0.
     * <b>Post:</b> Se instancia la plataforma de carga con la capacidad indicada, los contadores inician en 0,
     * la lista de hilos activos comienza vacía y la simulación arranca activa.
     * @param capacidadMaxPlataforma La capacidad máxima de baterías de la plataforma de carga.
     */
    public UniversoConcurrente(int capacidadMaxPlataforma) {
        assert capacidadMaxPlataforma > 0 : "Precondición fallida: capacidadMaxPlataforma debe ser mayor a 0";

        this.plataforma = new PlataformaDeCarga(capacidadMaxPlataforma);

        assert this.contNaves == 0 && this.contGeneradoras == 0 : "Postcondición fallida: los contadores no iniciaron en 0";
        assert this.simulacionActiva : "Postcondición fallida: la simulación no inició activa";
        assert invariante() : "Fallo invariante en el constructor de UniversoConcurrente";
    }

    /**
     * Testea el invariante de clase garantizando la integridad de la plataforma, la lista de hilos y los contadores.
     */
    private boolean invariante() {
        return this.plataforma != null
                && this.hilosActivos != null
                && this.contNaves >= 0
                && this.contGeneradoras >= 0;
    }

    /**
     * Crea e inicia un nuevo hilo de NaveConsumidora si la simulación está activa.
     * <b>Pre:</b> La plataforma y la lista de hilos activos deben estar inicializadas.
     * <b>Post:</b> Si la simulación está activa, se incrementa contNaves, se registra el ingreso, se añade el hilo a hilosActivos y se inicia su ejecución.
     */
    public synchronized void crearNaveConsumidora() {
        if (!this.simulacionActiva)
            return;
        int navesAnt = this.contNaves;
        int hilosAnt = this.hilosActivos.size();

        this.contNaves++;
        String nombreNave = "Nave " + this.contNaves;
        this.plataforma.registrarEvento("¡Ingresó una nueva Nave (" + nombreNave + ") al universo paralelo!");

        Thread hiloNave = new Thread(new NaveConsumidora(this.plataforma), nombreNave);
        this.hilosActivos.add(hiloNave);
        hiloNave.start();

        assert this.contNaves == navesAnt + 1 : "Postcondición fallida: contNaves no se incrementó";
        assert this.hilosActivos.size() == hilosAnt + 1 : "Postcondición fallida: el hilo de la nave no se agregó a hilosActivos";
        assert invariante() : "Fallo invariante en crearNaveConsumidora";
    }

    /**
     * Crea e inicia un nuevo hilo de GeneradoraDeEnergia si la simulación está activa.
     * <b>Pre:</b> La plataforma y la lista de hilos activos deben estar inicializadas.
     * <b>Post:</b> Si la simulación está activa, se incrementa contGeneradoras, se registra el ingreso, se añade el hilo a hilosActivos y se inicia su ejecución.
     */
    public synchronized void crearGeneradoraDeEnergia() {
        if (!this.simulacionActiva)
            return;
        int generadorasAnt = this.contGeneradoras;
        int hilosAnt = this.hilosActivos.size();

        this.contGeneradoras++;
        String nombreGeneradora = "Generadora " + this.contGeneradoras;
        this.plataforma.registrarEvento("¡Ingresó una nueva Generadora (" + nombreGeneradora + ") al universo paralelo!");

        Thread hiloGeneradora = new Thread(new GeneradoraDeEnergia(this.plataforma), nombreGeneradora);
        this.hilosActivos.add(hiloGeneradora);
        hiloGeneradora.start();

        assert this.contGeneradoras == generadorasAnt + 1 : "Postcondición fallida: contGeneradoras no se incrementó";
        assert this.hilosActivos.size() == hilosAnt + 1 : "Postcondición fallida: el hilo de la generadora no se agregó a hilosActivos";
        assert invariante() : "Fallo invariante en crearGeneradoraDeEnergia";
    }

    /**
     * Detiene la simulación interrumpiendo todos los hilos activos y limpiando el registro de hilos.
     * <b>Pre:</b> La lista de hilos activos debe estar inicializada.
     * <b>Post:</b> Si la simulación estaba activa, todos los hilos activos son interrumpidos, hilosActivos queda vacía y se registra el evento de detención.
     */
    public synchronized void detenerSimulacion() {
        if (!this.simulacionActiva)
            return;
        for (Thread hilo : this.hilosActivos) {
            hilo.interrupt();
        }
        this.hilosActivos = new ArrayList<>();
        this.plataforma.registrarEvento("--- Simulación detenida. Todos los hilos activos fueron finalizados. ---");

        assert this.hilosActivos.isEmpty() : "Postcondición fallida: hilosActivos no quedó vacía al detener";
        assert invariante() : "Fallo invariante en detenerSimulacion";
    }

    /**
     * Reinicia la simulación.
     * <b>Pre:</b> La plataforma y la lista de hilos activos deben estar inicializadas.
     * <b>Post:</b> hilosActivos queda vacía, contNaves y contGeneradoras vuelven a 0, simulacionActiva queda en true y la plataforma se reinicia.
     */
    public synchronized void reiniciarSimulacion() {
        for (Thread hilo : this.hilosActivos) {
            hilo.interrupt();
        }
        this.hilosActivos = new ArrayList<>();
        this.contNaves = 0;
        this.contGeneradoras = 0;
        this.simulacionActiva = true;
        this.plataforma.reiniciar();

        assert this.hilosActivos.isEmpty() : "Postcondición fallida: hilosActivos no quedó vacía al reiniciar";
        assert this.contNaves == 0 && this.contGeneradoras == 0 : "Postcondición fallida: los contadores no se reiniciaron a 0";
        assert this.simulacionActiva : "Postcondición fallida: simulacionActiva no quedó en true";
        assert invariante() : "Fallo invariante en reiniciarSimulacion";
    }

    public PlataformaDeCarga getPlataforma() {
        return this.plataforma;
    }
}