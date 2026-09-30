package motor;

public interface State {
    void iniciarSalto() throws TransicionInvalidaException;
    void ejecutarSalto() throws TransicionInvalidaException;
    void finalizarSalto() throws TransicionInvalidaException;
    void terminarEnfriamiento() throws TransicionInvalidaException;

}
