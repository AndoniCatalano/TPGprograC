package Tripulantes;

public class CantidadConsejosInvalidaException extends RuntimeException {
    public CantidadConsejosInvalidaException() {
        super("ERROR: la cantidad de consejos invalida");
    }
}
