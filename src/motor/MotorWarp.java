package motor;

/*
    <b>invariante: estado != null</b>
 */

public class MotorWarp{
    private IState estado;

    /*
        <b>post:<b/> el motor es inicialiozado y esta disponible
     */
    public MotorWarp(){
        this.estado = new DisponibleState(this);
    }

    /*
        <b>pre:</b> el estado != null
     */
    public void setEstado(IState estado){
        assert estado != null : "Error el estado no puede ser null";

        this.estado = estado;
    }

    public IState getEstado(){
        return this.estado;
    }
    /*
        <b>post:</b> el motor pasa a estar en preparacion
        @throws TransicionInvalidaException si se rechaza la accion
     */
    public void prepararSalto() throws TransicionInvalidaException{
        estado.prepararSalto();
    }
    /*
        <b>post:</b> el motor pasa a estar en warp
        @throws TransicionInvalidaException si se rechaza la accion
     */
    public void ejecutarSalto() throws TransicionInvalidaException{
        estado.ejecutarSalto();
    }
    /*
        <b>post:</b> el motor pasa a estar disponible (por ahora, ya que todavia no se contempla el paso del tiempo)
        @throws TransicionInvalidaException si se rechaza la accion
     */
    public void finalizarSalto() throws TransicionInvalidaException{
        estado.enfriar();
    }
    /*
        <b>post:</b> el motor pasa a estar disponible
        @throws TransicionInvalidaException si se rechaza la accion
     */
    public void terminarEnfriamiento() throws TransicionInvalidaException{
        estado.terminarEnfriamiento();
    }
}
