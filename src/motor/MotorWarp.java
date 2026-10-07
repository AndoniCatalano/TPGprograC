package motor;

public class MotorWarp{
    private State estado;

    public MotorWarp(){
        estado = new DisponibleState(this);
    }

    public void setEstado(State estado){
        this.estado = estado;
    }

    public State getEstado(){
        return this.estado;
    }

    public void prepararSalto() throws TransicionInvalidaException{
        estado.prepararSalto();
    }

    public void ejecutarSalto() throws TransicionInvalidaException{
        estado.ejecutarSalto();
    }

    public void finalizarSalto() throws TransicionInvalidaException{
        estado.enfriar();
    }

    public void terminarEnfriamiento() throws TransicionInvalidaException{
        estado.terminarEnfriamiento();
    }
}
