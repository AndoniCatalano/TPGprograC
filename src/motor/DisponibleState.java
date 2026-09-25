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
        System.out.println("ERROR: intenta ejecutar en salto cuando no esta iniciado");
    }

    @java.lang.Override
    public void finalizarSalto() {
        //arrojar excepcion este metodo lo maneja el estado EnWarp
        System.out.println("ERROR: intenta finalizar el salto cuando no esta iniciado");
    }

    @java.lang.Override
    public void terminarEnfriamiento() {
        //arrojar excepcion este metodo lo maneja el estado Enfriamiento
        System.out.println("ERROR: el motor no necesita enfriarse");
    }
}
