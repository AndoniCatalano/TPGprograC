package motor;

public class EnWarpState implements State{
    private MotorWarp m;

    public EnWarpState(MotorWarp m){
        this.m = m;
    }

    @Override
    public String toString() {
        return "En Warp";
    }

    @java.lang.Override
    public void iniciarSalto() {
        //arroja excepcion el salto ya fue iniciado
        System.out.println("ERROR");
    }

    @java.lang.Override
    public void ejecutarSalto() {
        // ya esta siendo ejecutad
        System.out.println("ERROR");
    }

    @java.lang.Override
    public void finalizarSalto() {
        this.m.setEstado(new EnfriamientoState(m));
    }

    @java.lang.Override
    public void terminarEnfriamiento() {
        //arroja excepcion no puede terminar enfriamiento, esto lo maneja el estado Enfriamiento
        System.out.println("ERROR");
    }
}
