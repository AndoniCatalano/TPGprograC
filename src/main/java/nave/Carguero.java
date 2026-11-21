package nave;

public class Carguero extends Nave {
    public Carguero() {

        super(100, 60);
    }

    //primer envío
    @Override
    public void interactuarCon(Asistente asistente) {
        asistente.getNave().defenderContraCarguero(this);
    }

    @Override
    public boolean esAmenaza(Asistente asistente) {
        return asistente.getNave().esAmenazaCarguero();
    }

    //segundo envío
    @Override
    public void defenderContraExploradora(INave naveEnemiga) { // Carguero pierde contra Exploradora
        if (this.getEnergia() > 0) {
            if (this.getAsistente() != null) {
                this.getAsistente().consumeEnergia(this.getEnergia());
            } else {
                try {
                    this.consumeEnergia(this.getEnergia());
                } catch (OperacionRechazadaException e) {
                    // No debería ocurrir porque validamos energia > 0
                }
            }
        }
        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("Destrucción total contra nave Exploradora enemiga.");
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

    @Override
    public boolean esAmenazaExploradora() { return true; }

    @Override
    public boolean esAmenazaCarguero() { return false; }

    @Override
    public boolean esAmenazaCombate() { return false; }
}