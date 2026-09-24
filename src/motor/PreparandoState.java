package motor;

public class PreparandoState implements State{
    private MotorWarp m;

    public PreparandoState(MotorWarp m){
        this.m = m;
    }

    @java.lang.Override
    public void iniciarSalto() {
        //arroja excepcion el salto ya fue iniciado
    }

    @java.lang.Override
    public void ejecutarSalto() {
        this.m.setEstado(new EnWarpState(m));
    }

    @java.lang.Override
    public void finalizarSalto() {
        //arroja excepcion el salto lo termina el estado EnWarp
    }

    @java.lang.Override
    public void terminarEnfriamiento() {
        //arroja excepcion no puede terminar enfriamiento, esto lo maneja el estado Enfriamiento
    }
}
