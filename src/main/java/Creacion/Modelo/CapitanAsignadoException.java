package Creacion.Modelo;

public class CapitanAsignadoException extends RuntimeException {
    public CapitanAsignadoException() {
        super("No se puede asignar mas de un capitan");
    }
}
