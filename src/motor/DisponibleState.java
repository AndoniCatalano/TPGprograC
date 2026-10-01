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

    @Override
    public void iniciarSalto() {
        this.m.setEstado(new PreparandoState(m));
    }

    @Override
    public void ejecutarSalto()  throws TransicionInvalidaException{
       //arrojar excepcion este metodo lo maneja el estado Preparando
        //System.out.println("ERROR: intenta ejecutar en salto cuando no esta iniciado");
        throw new TransicionInvalidaException("ERROR: intenta ejecutar en salto cuando no esta iniciado");
    }

    @Override
    public void finalizarSalto()  throws TransicionInvalidaException{
        //arrojar excepcion este metodo lo maneja el estado EnWarp
        //System.out.println("ERROR: intenta finalizar el salto cuando no esta iniciado");
        throw new TransicionInvalidaException("ERROR: intenta finalizar el salto cuando no esta iniciado");
    }

    @Override
    public void terminarEnfriamiento()  throws TransicionInvalidaException{
        //arrojar excepcion este metodo lo maneja el estado Enfriamiento
        //System.out.println("ERROR: el motor no necesita enfriarse");
        throw new TransicionInvalidaException("ERROR: el motor no necesita enfriarse");
    }
}
