package motor;

public class PreparandoState implements State{
    private MotorWarp m;

    public PreparandoState(MotorWarp m){
        this.m = m;
    }

    @Override
    public String toString() {
        return "Preparando";
    }

    @java.lang.Override
    public void iniciarSalto() {
        //arroja excepcion el salto ya fue iniciado
        System.out.println("ERROR: el salto ya a sido iniciado");
    }

    @java.lang.Override
    public void ejecutarSalto() {
        this.m.setEstado(new EnWarpState(m));
    }

    @java.lang.Override
    public void finalizarSalto() {
        //arroja excepcion el salto lo termina el estado EnWarp
        System.out.println("ERROR: el salto aun no fue ejecutado");
    }

    @java.lang.Override
    public void terminarEnfriamiento() {
        //arroja excepcion no puede terminar enfriamiento, esto lo maneja el estado Enfriamiento
        System.out.println("ERROR: el motor no necesita enfriarse");
    }
}
