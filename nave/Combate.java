package nave;

public class Combate extends Nave {
    public Combate() {

        super(80, 100); //indico los valores de inicio a naveInicial
    }

    @Override
    public void interactuarCon(INave naveJugador) {
        naveJugador.defenderContraCombate(this);
    }

    @Override
    public void defenderContraExploradora(INave naveEnemiga) {
        // Carguero le gana a Exploradora
        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("¡Victoria contra nave Exploradora enemiga!");
        }
    }

    @Override
    public void defenderContraCarguero(INave naveEnemiga) {
        try{
            this.consumeEnergia(this.getEnergia());     //consume toda la energía y falla la misión
        }catch(OperacionRechazadaException e){
            if (this.getAsistente() != null)
                this.getAsistente().registrarEventoBitacora("Destrucción total contra nave Carguero enemigo" + e.getMessage());
        }
    }

    @Override
    public void defenderContraCombate(INave naveEnemiga) {   // Pacto de paz, no se hace nada
        if (this.getAsistente() != null)
            this.getAsistente().registrarEventoBitacora("Encuentro con nave de Combate aliada: Pacto de paz acordado.");
    }
}
