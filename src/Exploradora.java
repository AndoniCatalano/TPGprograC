package nave;

public class Exploradora extends Nave {
    public Exploradora() {
        super(60, 80);
    }

    @Override
    public void interactuarCon(INave naveJugador) {
        naveJugador.defenderContraExploradora(this);
    }

    @Override
    public void defenderContraExploradora(INave naveEnemiga) {
        // Pacto de paz, no se hace nada
        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("Encuentro con Exploradora aliada: Pacto de paz acordado.");
        }
    }

    @Override
    public void defenderContraCarguero(INave naveEnemiga) {
        // Exploradora le gana a Carguero
        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("¡Victoria contra nave Carguera enemiga!");
        }
    }

    @Override
    public void defenderContraCombate(INave naveEnemiga) {
        try{
            this.consumeEnergia(this.getEnergia());     //consume toda la energía y falla la misión
        }catch(OperacionRechazadaException e){
            if (this.getAsistente() != null)
                this.getAsistente().registrarEventoBitacora("Destrucción total contra nave de Combate enemiga" + e.getMessage());
        }
    }
}
