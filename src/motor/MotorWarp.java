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

    public void iniciarSalto(){
        estado.iniciarSalto();
    }

    public void ejecutarSalto(){
        estado.ejecutarSalto();
    }

    public void finalizarSalto(){
        estado.finalizarSalto();
    }

    public void terminarEnfriamiento(){
        estado.terminarEnfriamiento();
    }
}
