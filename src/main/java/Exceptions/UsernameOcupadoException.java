package Exceptions;

public class UsernameOcupadoException extends Exception {
    public  UsernameOcupadoException(){
        super("nombre de usuario ocupado");
    }
}
