package nave;

public class Carguero extends Nave {
    public Carguero() {

        super(100, 60);
    }

    //primer envío
    @Override
    public void interactuarCon(INave naveJugador) {
        naveJugador.defenderContraCarguero(this);
    }

    //segundo envío
    @Override
    public void defenderContraExploradora(INave naveEnemiga) {// Carguero pierde contra Exploradora
        try{
            this.consumeEnergia(this.getEnergia());     //consume toda la energía y falla la misión
        }catch(OperacionRechazadaException e){
            if (this.getAsistente() != null)
                this.getAsistente().registrarEventoBitacora("Destrucción total contra nave Exploradora enemiga " + e.getMessage());
        }
    }

    @Override
    public void defenderContraCarguero(INave naveEnemiga) {         // Pacto de paz, no se hace nada
        if (this.getAsistente() != null)
            this.getAsistente().registrarEventoBitacora("Encuentro con Carguero aliado: Pacto de paz acordado.");
    }

    @Override
    public void defenderContraCombate(INave naveEnemiga) {// Carguero le gana a Combate
        if (this.getAsistente() != null)
            this.getAsistente().registrarEventoBitacora("¡Victoria contra nave de Combate enemiga!");
    }
}