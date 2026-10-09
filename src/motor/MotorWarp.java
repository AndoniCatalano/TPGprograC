package motor;

public class MotorWarp{
    private IState estado;

    public MotorWarp(){
        this.estado = new DisponibleState(this);
    }

    public void setEstado(IState estado){
        this.estado = estado;
    }

    public IState getEstado(){
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
