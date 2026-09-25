package motor;

public class EnfriamientoState implements State{
    private MotorWarp m;

    public EnfriamientoState(MotorWarp m){
        this.m = m;
    }

    @Override
    public String toString() {
        return "En enfriamiento";
    }

    @java.lang.Override
    public void iniciarSalto() {
        //arroja excepcion el salto ya fue iniciado
        System.out.println("ERROR: no es posible iniciar el salto, el motor se encuentra en enfriamiento");
    }

    @java.lang.Override
    public void ejecutarSalto() {
        // ya esta siendo ejecutado
        System.out.println("ERROR: no es posible ejecutar el salto, el motor se encuentra en enfriamiento");
    }

    @java.lang.Override
    public void finalizarSalto() {
        //el salto ya fue finalizado
        System.out.println("ERROR: no es posible finalizar el salto, el motor se encuentra en enfriamiento");
    }

    @java.lang.Override
    public void terminarEnfriamiento() {
        this.m.setEstado(new DisponibleState(m));
    }
}
