package nave;

import asistente.Asistente;

public class Exploradora extends Nave {

    /**
     * Constructor de la nave tipo Exploradora.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Se instancia una Exploradora con 60 de combustible inicial y 80 de energía inicial.
     */
    public Exploradora() {
        super(60, 80);
    }

    /**
     * Inicia la resolución por Double Dispatch cuando la nave del jugador se encuentra con esta Exploradora enemiga.
     * <b>Pre:</b> asistente no debe ser nulo y debe tener una nave vinculada.
     * <b>Post:</b> Se delega la resolución del combate al método defenderContraExploradora de la nave del jugador.
     *
     * @param asistente El asistente de la nave del jugador que interactúa con esta Exploradora.
     */
    @Override
    public void interactuarCon(Asistente asistente) {
        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";
        assert asistente.getNave() != null : "Precondición fallida: el asistente no tiene una nave vinculada";

        asistente.getNave().defenderContraExploradora(this);
    }

    /**
     * Consulta mediante Double Dispatch si esta Exploradora representa una amenaza para la nave del jugador.
     * <b>Pre:</b> asistente no debe ser nulo y debe tener una nave vinculada.
     * <b>Post:</b> Se retorna true si la nave del jugador pierde contra una Exploradora, false en caso contrario.
     *
     * @param asistente El asistente de la nave del jugador.
     * @return true si esta Exploradora es una amenaza para la nave del jugador; false en caso contrario.
     */
    @Override
    public boolean esAmenaza(Asistente asistente) {
        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";
        assert asistente.getNave() != null : "Precondición fallida: el asistente no tiene una nave vinculada";

        return asistente.getNave().esAmenazaExploradora();
    }

    /**
     * Resuelve el encuentro de esta Exploradora frente a otra Exploradora (pacto de paz).
     * <b>Pre:</b> naveEnemiga no debe ser nula.
     * <b>Post:</b> Los recursos de la nave no se ven afectados y, si posee asistente, se registra el pacto de paz en la bitácora.
     *
     * @param naveEnemiga La nave Exploradora con la que se produjo el encuentro.
     */
    @Override
    public void defenderContraExploradora(INave naveEnemiga) { // Pacto de paz, no se hace nada
        assert naveEnemiga != null : "Precondición fallida: naveEnemiga no puede ser nula";

        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("Encuentro con Exploradora aliada: Pacto de paz acordado.");
        }
    }

    /**
     * Resuelve la defensa de esta Exploradora frente a un Carguero enemigo (victoria).
     * <b>Pre:</b> naveEnemiga no debe ser nula.
     * <b>Post:</b> La nave sobrevive sin pérdida de energía y, si posee asistente, se registra la victoria en la bitácora.
     *
     * @param naveEnemiga La nave Carguero enemiga con la que se produjo el encuentro.
     */
    @Override
    public void defenderContraCarguero(INave naveEnemiga) { // Exploradora le gana a Carguero
        assert naveEnemiga != null : "Precondición fallida: naveEnemiga no puede ser nula";

        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("¡Victoria contra nave Carguera enemiga!");
        }
    }

    /**
     * Resuelve la defensa de esta Exploradora frente a una nave de Combate enemiga (derrota).
     * <b>Pre:</b> naveEnemiga no debe ser nula.
     * <b>Post:</b> La energía de esta Exploradora queda en 0 y, si posee asistente, se registra la destrucción en la bitácora.
     *
     * @param naveEnemiga La nave de Combate enemiga con la que se produjo el encuentro.
     */
    @Override
    public void defenderContraCombate(INave naveEnemiga) {
        assert naveEnemiga != null : "Precondición fallida: naveEnemiga no puede ser nula";

        if (this.getEnergia() > 0) {
            if (this.getAsistente() != null) {
                this.getAsistente().consumeEnergia(this.getEnergia()); //consume toda la energía y falla la misión
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

        assert this.getEnergia() == 0 : "Postcondición fallida: la energía de la Exploradora no quedó en 0 tras perder";
    }

    /**
     * Indica si otra Exploradora representa una amenaza para esta Exploradora.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Retorna false, ya que acuerdan pacto de paz.
     *
     * @return false.
     */
    @Override
    public boolean esAmenazaExploradora() { return false; }

    /**
     * Indica si un Carguero representa una amenaza para esta Exploradora.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Retorna false, ya que la Exploradora vence al Carguero.
     *
     * @return false.
     */
    @Override
    public boolean esAmenazaCarguero() { return false; }

    /**
     * Indica si una nave de Combate representa una amenaza para esta Exploradora.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Retorna true, ya que la Exploradora pierde contra Combate.
     *
     * @return true.
     */
    @Override
    public boolean esAmenazaCombate() { return true; }
}