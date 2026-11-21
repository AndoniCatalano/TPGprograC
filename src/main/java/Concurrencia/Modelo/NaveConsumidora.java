package Concurrencia.Modelo;

import java.util.concurrent.ThreadLocalRandom;

public class NaveConsumidora implements Runnable {
    private final PlataformaDeCarga plataforma;

    public NaveConsumidora(PlataformaDeCarga plataforma) {
        this.plataforma = plataforma;
    }

    @Override
    public void run() {
        String nombreNave = Thread.currentThread().getName();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                this.plataforma.registrarEvento("[" + nombreNave + "] solicitando consumir batería de la plataforma.");
                this.plataforma.consumirBateria();      // Intenta retirarla de la plataforma
                Thread.sleep(ThreadLocalRandom.current().nextInt(1200, 3000));      // Simula el tiempo que tarda en consumir la batería
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}