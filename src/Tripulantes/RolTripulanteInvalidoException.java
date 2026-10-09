package Tripulantes;

public class RolTripulanteInvalidoException extends RuntimeException {
    public RolTripulanteInvalidoException() {
        super("ERROR: el rol seleccionado es invalido");
    }
}
