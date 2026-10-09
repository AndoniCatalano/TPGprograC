package Tripulantes;

public class AntiguedadInvalidaException extends RuntimeException {
    public AntiguedadInvalidaException() {
        super("ERROR: la antiguedad no puede ser menor a 0" );
    }
}
