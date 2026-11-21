package Concurrencia.Modelo;

public class Bateria {  //el objeto que se produce y consume
    private final int nroBateria;
    private final String producidaPor;

    public Bateria(int nroBateria, String producidaPor) {    //guardo el nro de batería y la nave que la produce
        this.nroBateria = nroBateria;
        this.producidaPor = producidaPor;
    }

    public int getNumeroBateria() {
        return this.nroBateria;
    }

    public String getProducidaPor() {
        return this.producidaPor;
    }

    @Override
    public String toString() {
        return "Batería #" + this.nroBateria + " (de " + this.producidaPor + ")";
    }
}