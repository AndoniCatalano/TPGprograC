package Tripulantes;

public class OrigenTripulanteException extends RuntimeException {
    public OrigenTripulanteException() {
        super("ERROR: el origen del tripulante es invalido");
    }
}
