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

    @Override
    public void iniciarSalto() throws TransicionInvalidaException {
        //arroja excepcion el salto ya fue iniciado
        //System.out.println("ERROR: el salto ya a sido iniciado");
        throw new TransicionInvalidaException("ERROR: el salto ya a sido iniciado");
    }

    @Override
    public void ejecutarSalto() {
        this.m.setEstado(new EnWarpState(m));
    }

    @Override
    public void finalizarSalto() throws TransicionInvalidaException{
        //arroja excepcion el salto lo termina el estado EnWarp
        //System.out.println("ERROR: el salto aun no fue ejecutado");
        throw new TransicionInvalidaException("ERROR: el salto aun no fue ejecutado");
    }

    @Override
    public void terminarEnfriamiento() throws TransicionInvalidaException {
        //arroja excepcion no puede terminar enfriamiento, esto lo maneja el estado Enfriamiento
        //System.out.println("ERROR: el motor no necesita enfriarse");
        throw new TransicionInvalidaException("ERROR: el motor no necesita enfriarse");
    }
}
