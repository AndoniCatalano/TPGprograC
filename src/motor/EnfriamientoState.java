package motor;

public class EnfriamientoState implements State{
    private MotorWarp m;

    public EnfriamientoState(MotorWarp m){
        this.m = m;
    }

    @java.lang.Override
    public void iniciarSalto() {
        //arroja excepcion el salto ya fue iniciado
    }

    @java.lang.Override
    public void ejecutarSalto() {
        // ya esta siendo ejecutado
    }

    @java.lang.Override
    public void finalizarSalto() {
        //el salto ya fue finalizado
    }

    @java.lang.Override
    public void terminarEnfriamiento() {
        this.m.setEstado(new DisponibleState(m));
    }
}
