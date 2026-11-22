package Concurrencia.Modelo;

import java.util.concurrent.ThreadLocalRandom;

public class NaveConsumidora implements Runnable {
    private final PlataformaDeCarga plataforma;

    /**
     * Constructor de la nave que consume baterías.
     * <b>Pre:</b> plataforma no debe ser nula.
     * <b>Post:</b> La nave consumidora queda vinculada a la plataforma de carga (recurso compartido).
     * @param plataforma La plataforma de la cual se consumen las baterías.
     */
    public NaveConsumidora(PlataformaDeCarga plataforma) {
        assert plataforma != null : "Precondición fallida: plataforma no puede ser nula";

        this.plataforma = plataforma;

        assert invariante() : "Fallo invariante en el constructor de NaveConsumidora";
    }

    /**
     * Testea el invariante de clase garantizando que la referencia a la plataforma no sea nula.
     */
    private boolean invariante() {
        return this.plataforma != null;
    }

    /**
     * Ejecuta el ciclo continuo de solicitud y consumo de baterías mientras el hilo no sea interrumpido.
     * <b>Pre:</b> La plataforma de carga debe estar inicializada.
     * <b>Post:</b> El hilo solicita y consume baterías repetidamente hasta recibir una señal de interrupción,
     * restaurando su estado de interrupción al finalizar.
     */
    @Override
    public void run() {
        String nombreNave = Thread.currentThread().getName();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                this.plataforma.registrarEvento("[" + nombreNave + "] solicitando consumir batería de la plataforma.");
                this.plataforma.consumirBateria();
                Thread.sleep(ThreadLocalRandom.current().nextInt(1200, 3000));
                assert invariante() : "Fallo invariante en run de NaveConsumidora";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}