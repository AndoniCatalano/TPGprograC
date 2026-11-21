package Concurrencia.Modelo;

import java.util.ArrayList;
import java.util.List;

public class PlataformaDeCarga {
    private ArrayList<Bateria> baterias = new ArrayList<>();    //no es final para poder reiniciarlo
    private final int capacidadMax;
    private final List<IObserverPlataforma> observadores = new ArrayList<>();
    private final Object lockObservadores = new Object();

    public PlataformaDeCarga(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public void agregarObservador(IObserverPlataforma observador) {
        synchronized (this.lockObservadores) {
            this.observadores.add(observador);
        }
    }

    public void quitarObservador(IObserverPlataforma observador) {
        synchronized (this.lockObservadores) {
            this.observadores.remove(observador);
        }
    }

    public void registrarEvento(String mensaje) {
        notificarObservadores(mensaje);
    }

    private void notificarObservadores(String mensaje) {
        synchronized (this.lockObservadores) {
            for (IObserverPlataforma observador : this.observadores) {
                observador.actualizarEvento(mensaje);
            }
        }
    }

    public synchronized void depositarBateria(Bateria nuevaBateria) throws InterruptedException {
        while (this.baterias.size() >= this.capacidadMax) {
            notificarObservadores("[" + nombreHilo() + "] esperando a que se libere espacio en la Plataforma.");
            wait();
        }

        this.baterias.add(nuevaBateria);
        notificarObservadores("[" + nombreHilo() + "] depositó " + nuevaBateria +
                ". (Stock: " + this.baterias.size() + "/" + this.capacidadMax + ")");

        // Mantiene visible el stock actualizado mientras la generadora ocupa la plataforma
        Thread.sleep(650);
        notifyAll();
    }

    public synchronized Bateria consumirBateria() throws InterruptedException {
        while (this.baterias.isEmpty()) {
            notificarObservadores("[" + nombreHilo() + "] esperando por falta de baterías en la Plataforma.");
            wait();
        }

        Bateria bateriaRetirada = this.baterias.remove(0);
        notificarObservadores("Batería retirada y utilizada por [" + nombreHilo() + "]: " +
                bateriaRetirada + ". (Stock: " + this.baterias.size() + "/" + this.capacidadMax + ")");

        // Mantiene visible el stock actualizado mientras la nave ocupa la plataforma
        Thread.sleep(650);
        notifyAll();
        return bateriaRetirada;
    }

    public synchronized void reiniciar() {
        this.baterias = new ArrayList<>();
        notifyAll();
        notificarObservadores("--- Simulación reiniciada. Plataforma lista (Stock: 0/" + this.capacidadMax + ") ---");
    }

    private String nombreHilo() {
        return Thread.currentThread().getName();
    }
}