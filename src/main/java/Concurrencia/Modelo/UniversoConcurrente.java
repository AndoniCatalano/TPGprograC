package Concurrencia.Modelo;

import java.util.ArrayList;
import java.util.List;

public class UniversoConcurrente {
    private final PlataformaDeCarga plataforma;
    private List<Thread> hilosActivos = new ArrayList<>();
    private int contNaves = 0;
    private int contGeneradoras = 0;
    private boolean simulacionActiva = true;

    public UniversoConcurrente(int capacidadMaxPlataforma) {
        this.plataforma = new PlataformaDeCarga(capacidadMaxPlataforma);
    }

    public synchronized void crearNaveConsumidora() {
        if (!this.simulacionActiva)
            return;
        this.contNaves++;
        String nombreNave = "Nave " + this.contNaves;
        this.plataforma.registrarEvento("¡Ingresó una nueva Nave (" + nombreNave + ") al universo paralelo!");

        Thread hiloNave = new Thread(new NaveConsumidora(this.plataforma), nombreNave);
        this.hilosActivos.add(hiloNave);
        hiloNave.start();
    }

    public synchronized void crearGeneradoraDeEnergia() {
        if (!this.simulacionActiva)
            return;
        this.contGeneradoras++;
        String nombreGeneradora = "Generadora " + this.contGeneradoras;
        this.plataforma.registrarEvento("¡Ingresó una nueva Generadora (" + nombreGeneradora + ") al universo paralelo!");

        Thread hiloGeneradora = new Thread(new GeneradoraDeEnergia(plataforma), nombreGeneradora);
        this.hilosActivos.add(hiloGeneradora);
        hiloGeneradora.start();
    }

    public synchronized void detenerSimulacion() {
        if (!this.simulacionActiva)
            return;
        for (Thread hilo : this.hilosActivos) {
            hilo.interrupt();
        }
        this.hilosActivos = new ArrayList<>();
        plataforma.registrarEvento("--- Simulación detenida. Todos los hilos activos fueron finalizados. ---");
    }

    public synchronized void reiniciarSimulacion() {
        for (Thread hilo : this.hilosActivos) {
            hilo.interrupt();
        }
        this.hilosActivos = new ArrayList<>();
        this.contNaves = 0;
        this.contGeneradoras = 0;
        this.simulacionActiva = true;
        this.plataforma.reiniciar();
    }

    public PlataformaDeCarga getPlataforma() {
        return this.plataforma;
    }
}