package Concurrencia.Modelo;

public class Bateria {  //el objeto que se produce y consume
    private final int nroBateria;
    private final String producidaPor;

    /**
     * Constructor para instanciar una nueva batería producida por una generadora.
     * <b>Pre:</b> nroBateria > 0 y producidaPor no debe ser nulo ni vacío.
     * <b>Post:</b> La batería queda inicializada con su número identificador y el nombre de la generadora que la produjo.
     *
     * @param nroBateria El número secuencial identificador de la batería.
     * @param producidaPor El nombre de la generadora que produjo la batería.
     */
    public Bateria(int nroBateria, String producidaPor) {
        assert nroBateria > 0 : "Precondición fallida: nroBateria debe ser mayor a 0";
        assert producidaPor != null && !producidaPor.trim().isEmpty() : "Precondición fallida: producidaPor no puede ser nulo ni vacío";

        this.nroBateria = nroBateria;
        this.producidaPor = producidaPor;

        assert invariante() : "Fallo invariante en el constructor de Bateria";
    }

    /**
     * Testea el invariante de clase garantizando que el número y origen de la batería sean válidos.
     */
    private boolean invariante() {
        return this.nroBateria > 0 && this.producidaPor != null && !this.producidaPor.trim().isEmpty();
    }

    public int getNumeroBateria() {
        return this.nroBateria;
    }

    public String getProducidaPor() {
        return this.producidaPor;
    }

    /**
     * Devuelve una representación textual descriptiva de la batería.
     * @return Cadena con el número de batería y su generadora de origen.
     */
    @Override
    public String toString() {
        return "Batería #" + this.nroBateria + " (de " + this.producidaPor + ")";
    }
}