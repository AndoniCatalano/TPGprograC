package Concurrencia.Modelo;

import java.util.concurrent.ThreadLocalRandom;

public class GeneradoraDeEnergia implements Runnable {  //hilo
    private final PlataformaDeCarga plataforma;
    private int contBaterias = 1;

    public GeneradoraDeEnergia(PlataformaDeCarga plataforma) {
        this.plataforma = plataforma;
    }

    @Override
    public void run() {
        String nombreGeneradora = Thread.currentThread().getName();
        try {
            while (!Thread.currentThread().isInterrupted()) {       //la consigna pide que el hilo se ejecute repetidamente
                this.plataforma.registrarEvento("[" + nombreGeneradora + "] produciendo nueva batería...");
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2500));      // Simula el tiempo que tarda en producir una batería
                Bateria nuevaBateria = new Bateria(this.contBaterias++, nombreGeneradora);
                this.plataforma.registrarEvento("Batería producida por [" + nombreGeneradora + "].");
                this.plataforma.registrarEvento("[" + nombreGeneradora + "] solicitando depositar batería en la Plataforma.");
                this.plataforma.depositarBateria(nuevaBateria);         // Solicita acceso a la plataforma para depositarla
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}