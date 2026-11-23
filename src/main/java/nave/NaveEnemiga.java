package nave;

import asistente.Asistente;

public class NaveEnemiga implements IElementosUniverso {
    private String tipo;
    private boolean destruida;

    public NaveEnemiga() {
        this.destruida = false;
        switch ((int) (Math.random() * 3)) {
            case 0:
                tipo = "CARGUERO";
                break;
            case 1:
                tipo = "EXPLORADORA";
                break;
            case 2:
                tipo = "COMBATE";
                break;
        }
    }

    /**
     * Resuelve la interacción entre esta nave enemiga y la nave del asistente delegando según el tipo de enemigo.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>{@code asistente} no nulo y con una nave asignada ({@code asistente.getNave() != null})</li>
     *     <li>{@code tipo} tiene un valor válido de tipo de nave</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>Si la nave enemiga ya está destruida, no realiza ninguna acción</li>
     *     <li>Si no está destruida, se invoca el método correspondiente en {@code asistente.getNave()} según {@code tipo} ({@code "CARGUERO"}, {@code "EXPLORADORA"} o {@code "COMBATE"})</li>
     * </ul>
     *
     * @param asistente Asistente que gestiona la nave aliada y la interacción en el universo
     */
    @Override
    public void interactuarCon(Asistente asistente) {
        if (destruida) {
            return;
        }
        INave nave = asistente.getNave();
        switch (tipo) {
            case "CARGUERO":
                nave.interactuarConCarguero(this);
                break;
            case "EXPLORADORA":
                nave.interactuarConExploradora(this);
                break;
            case "COMBATE":
                nave.interactuarConCombate(this);
                break;
            default:
                break;
        }
    }

    /**
     * Marca esta nave enemiga como destruida para que deje de actuar o representar una amenaza.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>La instancia de {@code NaveEnemiga} está inicializada</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>{@code destruida} queda establecido en {@code true}</li>
     * </ul>
     */
    public void destruir() {
        this.destruida = true;
    }


    /**
     * Determina si esta nave enemiga representa una amenaza para la nave actual del asistente según la relación de tipos.
     *
     * <p><b>Precondiciones:</b></p>
     * <ul>
     *     <li>{@code asistente} no nulo y con una nave asignada ({@code asistente.getNave() != null})</li>
     * </ul>
     *
     * <p><b>Postcondiciones:</b></p>
     * <ul>
     *     <li>Devuelve {@code false} si la nave enemiga está destruida</li>
     *     <li>Si no está destruida, devuelve {@code true} si la combinación entre el tipo de esta nave enemiga y el nombre simple de la clase de la nave aliada corresponde a una amenaza ({@code CARGUERO} frente a {@code Combate}, {@code COMBATE} frente a {@code Exploradora}, o {@code EXPLORADORA} frente a {@code Carguero}); {@code false} en caso contrario</li>
     *     <li>No modifica el estado del enemigo ni del asistente</li>
     * </ul>
     *
     * @param asistente Asistente cuya nave actual se evalúa frente a esta nave enemiga
     * @return {@code true} si esta nave enemiga es amenaza para la nave del asistente; {@code false} en caso contrario
     */
    @Override
    public boolean esAmenaza(Asistente asistente) {
        if (destruida) {
            return false;
        }
        String tipoNave = asistente.getNave().getClass().getSimpleName();

        return ("CARGUERO".equalsIgnoreCase(tipo) && "Combate".equalsIgnoreCase(tipoNave)) ||
                ("COMBATE".equalsIgnoreCase(tipo) && "Exploradora".equalsIgnoreCase(tipoNave)) ||
                ("EXPLORADORA".equalsIgnoreCase(tipo) && "Carguero".equalsIgnoreCase(tipoNave));
    }
}