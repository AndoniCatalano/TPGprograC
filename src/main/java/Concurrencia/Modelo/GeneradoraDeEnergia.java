package Concurrencia.Modelo;

import java.util.concurrent.ThreadLocalRandom;

public class GeneradoraDeEnergia implements Runnable {  //hilo
    private final PlataformaDeCarga plataforma;
    private int contBaterias = 1;

    /**
     * Constructor de la tarea productora de baterías.
     * <b>Pre:</b> plataforma no debe ser nula.
     * <b>Post:</b> La generadora queda vinculada a la plataforma de carga compartida con el contador de baterías en 1.
     * @param plataforma La plataforma de carga donde se depositarán las baterías producidas.
     */
    public GeneradoraDeEnergia(PlataformaDeCarga plataforma) {
        assert plataforma != null : "Precondición fallida: plataforma no puede ser nula";

        this.plataforma = plataforma;

        assert this.contBaterias == 1 : "Postcondición fallida: contBaterias no inició en 1";
        assert invariante() : "Fallo invariante en el constructor de GeneradoraDeEnergia";
    }

    /**
     * Testea el invariante de clase garantizando la referencia válida a la plataforma y el contador positivo.
     */
    private boolean invariante() {
        return this.plataforma != null && this.contBaterias >= 1;
    }

    /**
     * Ejecuta el ciclo continuo de producción y depósito de baterías mientras el hilo no sea interrumpido.
     * <b>Pre:</b> La plataforma de carga debe estar inicializada.
     * <b>Post:</b> El hilo produce y deposita baterías repetidamente hasta recibir una señal de interrupción,
     * restaurando su estado de interrupción al finalizar.
     */
    @Override
    public void run() {
        String nombreGeneradora = Thread.currentThread().getName();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                this.plataforma.registrarEvento("[" + nombreGeneradora + "] produciendo nueva batería...");
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2500));
                Bateria nuevaBateria = new Bateria(this.contBaterias++, nombreGeneradora);
                this.plataforma.registrarEvento("Batería producida por [" + nombreGeneradora + "].");
                this.plataforma.registrarEvento("[" + nombreGeneradora + "] solicitando depositar batería en la Plataforma.");
                this.plataforma.depositarBateria(nuevaBateria);
                assert invariante() : "Fallo invariante en run de GeneradoraDeEnergia";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}