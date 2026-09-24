package motor;

public class DisponibleState implements State{
    private MotorWarp m;

    public DisponibleState(MotorWarp m){
        this.m = m;
    }

    @java.lang.Override
    public void iniciarSalto() {
        this.m.setEstado(new PreparandoState(m));
    }

    @java.lang.Override
    public void ejecutarSalto() {
       //arrojar excepcion este metodo lo maneja el estado Preparando
    }

    @java.lang.Override
    public void finalizarSalto() {
        //arrojar excepcion este metodo lo maneja el estado EnWarp
    }

    @java.lang.Override
    public void terminarEnfriamiento() {
        //arrojar excepcion este metodo lo maneja el estado Enfriamiento
    }
}
