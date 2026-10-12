package motor;

public interface IState {
    void prepararSalto() throws TransicionInvalidaException;
    void ejecutarSalto() throws TransicionInvalidaException;
    void enfriar() throws TransicionInvalidaException;
    void terminarEnfriamiento() throws TransicionInvalidaException;
}
