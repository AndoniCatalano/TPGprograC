package nave.tablero;

public final class Posicion {
    private final int x;
    private final int y;

    /**
     * Crea un par de coordenadas inmutable sin validar límites de tablero.
     * <p><b>Precondiciones:</b> ninguna. Admite cualquier valor de tipo {@code int}.</p>
     * <p><b>Postcondiciones:</b> conserva las coordenadas recibidas y no permite cambiarlas.</p>
     * @param x índice de fila
     * @param y índice de columna
     */
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    /**
     * Compara posiciones por sus dos coordenadas.
     * <p><b>Precondiciones:</b> ninguna. Acepta {@code null} y otros tipos.</p>
     * <p><b>Postcondiciones:</b> no modifica ninguno de los objetos comparados.</p>
     * @param otro objeto con el que se compara
     * @return {@code true} si es una posición con las mismas coordenadas
     */
    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Posicion)) {
            return false;
        }
        Posicion posicion = (Posicion) otro;
        return x == posicion.x && y == posicion.y;
    }

    /**
     * Calcula un código hash a partir de ambas coordenadas.
     * <p><b>Precondiciones:</b> ninguna.</p>
     * <p><b>Postcondiciones:</b> no modifica la posición. Las posiciones iguales
     * producen el mismo código, estable durante toda su vida.</p>
     * @return código hash de la posición
     */
    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
