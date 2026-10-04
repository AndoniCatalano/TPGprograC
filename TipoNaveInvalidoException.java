public class TipoNaveInvalidoException extends Exception {
    public TipoNaveInvalidoException(String mensaje) {
        super(mensaje);         // Invoca al constructor de la superclase con el detalle del error
    }
}