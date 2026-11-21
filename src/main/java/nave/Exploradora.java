package nave;
import asistente.Asistente;

public class Exploradora extends Nave {
    public Exploradora() {
        super(60, 80);
    }

    @Override
    public void interactuarCon(Asistente asistente) {
        asistente.getNave().defenderContraExploradora(this);
    }

    @Override
    public boolean esAmenaza(Asistente asistente) {
        return asistente.getNave().esAmenazaExploradora();
    }

    @Override
    public void defenderContraExploradora(INave naveEnemiga) {  // Pacto de paz, no se hace nada
        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("Encuentro con Exploradora aliada: Pacto de paz acordado.");
        }
    }

    @Override
    public void defenderContraCarguero(INave naveEnemiga) {  // Exploradora le gana a Carguero
        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("¡Victoria contra nave Carguera enemiga!");
        }
    }

    @Override
    public void defenderContraCombate(INave naveEnemiga) {
        if (this.getEnergia() > 0) {
            if (this.getAsistente() != null) {
                this.getAsistente().consumeEnergia(this.getEnergia());   //consume toda la energía y falla la misión
            } else {
                try {
                    this.consumeEnergia(this.getEnergia());
                } catch (OperacionRechazadaException e) {
                    // No debería pasar porque revisa energia > 0
                }
            }
        }
        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("Destrucción total contra nave de Combate enemiga.");
        }
    }

    @Override
    public boolean esAmenazaExploradora() { return false; }

    @Override
    public boolean esAmenazaCarguero() { return false; }

    @Override
    public boolean esAmenazaCombate() { return true; }
}
