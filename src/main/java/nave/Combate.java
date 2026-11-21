package nave;

import asistente.Asistente;

public class Combate extends Nave {
    public Combate() {

        super(80, 100); //indico los valores de inicio a naveInicial
    }

    @Override
    public void interactuarCon(Asistente asistente) {
        asistente.getNave().defenderContraCombate(this);
    }

    @Override
    public boolean esAmenaza(Asistente asistente) {
        return asistente.getNave().esAmenazaCombate();
    }

    @Override
    public void defenderContraExploradora(INave naveEnemiga) {  // Combate le gana a Exploradora
        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("¡Victoria contra nave Exploradora enemiga!");
        }
    }

    @Override
    public void defenderContraCarguero(INave naveEnemiga) {
        if (this.getEnergia() > 0) {
            if (this.getAsistente() != null) {
                this.getAsistente().consumeEnergia(this.getEnergia());      //consume toda la energía y falla la misión
            } else {
                try {
                    this.consumeEnergia(this.getEnergia());
                } catch (OperacionRechazadaException e) {
                    // No debería pasar porque ya se revisa que energia > 0
                }
            }
        }
    }

    @Override
    public void defenderContraCombate(INave naveEnemiga) {   // Pacto de paz, no se hace nada
        if (this.getAsistente() != null)
            this.getAsistente().registrarEventoBitacora("Encuentro con nave de Combate aliada: Pacto de paz acordado.");
    }

    @Override
    public boolean esAmenazaExploradora() { return false; }

    @Override
    public boolean esAmenazaCarguero() { return true; }

    @Override
    public boolean esAmenazaCombate() { return false; }
}
