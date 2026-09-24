package motor;

public class DisponibleState implements State{
    private MotorWarp m;

    public DisponibleState(MotorWarp m){
        this.m = m;
    }

    @Override
    public String toString() {
        return "Disponible";
    }

    @java.lang.Override
    public void iniciarSalto() {
        this.m.setEstado(new PreparandoState(m));
    }

    @java.lang.Override
    public void ejecutarSalto() {
       //arrojar excepcion este metodo lo maneja el estado Preparando
        System.out.println("ERROR");
    }

    @java.lang.Override
    public void finalizarSalto() {
        //arrojar excepcion este metodo lo maneja el estado EnWarp
        System.out.println("ERROR");
    }

    @java.lang.Override
    public void terminarEnfriamiento() {
        //arrojar excepcion este metodo lo maneja el estado Enfriamiento
        System.out.println("ERROR");
    }
}
