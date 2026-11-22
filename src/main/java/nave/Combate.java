package nave;

import asistente.Asistente;

public class Combate extends Nave {

    /**
     * Constructor de la nave tipo Combate.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Se instancia una nave de Combate con 80 de combustible inicial y 100 de energía inicial.
     */
    public Combate() {
        super(80, 100); //indico los valores de inicio a naveInicial
    }

    /**
     * Inicia la resolución por Double Dispatch cuando la nave del jugador se encuentra con esta nave de Combate enemiga.
     * <b>Pre:</b> asistente no debe ser nulo y debe tener una nave vinculada.
     * <b>Post:</b> Se delega la resolución del combate al método defenderContraCombate de la nave del jugador.
     *
     * @param asistente El asistente de la nave del jugador que interactúa con esta nave de Combate.
     */
    @Override
    public void interactuarCon(Asistente asistente) {
        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";
        assert asistente.getNave() != null : "Precondición fallida: el asistente no tiene una nave vinculada";

        asistente.getNave().defenderContraCombate(this);
    }

    /**
     * Consulta mediante Double Dispatch si esta nave de Combate representa una amenaza para la nave del jugador.
     * <b>Pre:</b> asistente no debe ser nulo y debe tener una nave vinculada.
     * <b>Post:</b> Se retorna true si la nave del jugador pierde contra una nave de Combate, false en caso contrario.
     *
     * @param asistente El asistente de la nave del jugador.
     * @return true si esta nave de Combate es una amenaza para la nave del jugador; false en caso contrario.
     */
    @Override
    public boolean esAmenaza(Asistente asistente) {
        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";
        assert asistente.getNave() != null : "Precondición fallida: el asistente no tiene una nave vinculada";

        return asistente.getNave().esAmenazaCombate();
    }

    /**
     * Resuelve la defensa de esta nave de Combate frente a una Exploradora enemiga (victoria).
     * <b>Pre:</b> naveEnemiga no debe ser nula.
     * <b>Post:</b> La nave sobrevive sin pérdida de energía y, si posee asistente, se registra la victoria en la bitácora.
     *
     * @param naveEnemiga La nave Exploradora enemiga con la que se produjo el encuentro.
     */
    @Override
    public void defenderContraExploradora(INave naveEnemiga) { // Combate le gana a Exploradora
        assert naveEnemiga != null : "Precondición fallida: naveEnemiga no puede ser nula";

        if (this.getAsistente() != null) {
            this.getAsistente().registrarEventoBitacora("¡Victoria contra nave Exploradora enemiga!");
        }
    }

    /**
     * Resuelve la defensa de esta nave de Combate frente a un Carguero enemigo (derrota).
     * <b>Pre:</b> naveEnemiga no debe ser nula.
     * <b>Post:</b> La energía de esta nave de Combate queda en 0, provocando la falla de la misión.
     *
     * @param naveEnemiga La nave Carguero enemiga con la que se produjo el encuentro.
     */
    @Override
    public void defenderContraCarguero(INave naveEnemiga) {
        assert naveEnemiga != null : "Precondición fallida: naveEnemiga no puede ser nula";

        if (this.getEnergia() > 0) {
            if (this.getAsistente() != null) {
                this.getAsistente().consumeEnergia(this.getEnergia()); //consume toda la energía y falla la misión
            } else {
                try {
                    this.consumeEnergia(this.getEnergia());
                } catch (OperacionRechazadaException e) {
                    // No debería pasar porque ya se revisa que energia > 0
                }
            }
        }

        assert this.getEnergia() == 0 : "Postcondición fallida: la energía de Combate no quedó en 0 tras perder";
    }

    /**
     * Resuelve el encuentro de esta nave de Combate frente a otra nave de Combate (pacto de paz).
     * <b>Pre:</b> naveEnemiga no debe ser nula.
     * <b>Post:</b> Los recursos de la nave no se ven afectados y, si posee asistente, se registra el pacto de paz en la bitácora.
     *
     * @param naveEnemiga La nave de Combate con la que se produjo el encuentro.
     */
    @Override
    public void defenderContraCombate(INave naveEnemiga) { // Pacto de paz, no se hace nada
        assert naveEnemiga != null : "Precondición fallida: naveEnemiga no puede ser nula";

        if (this.getAsistente() != null)
            this.getAsistente().registrarEventoBitacora("Encuentro con nave de Combate aliada: Pacto de paz acordado.");
    }

    /**
     * Indica si una nave Exploradora representa una amenaza para esta nave de Combate.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Retorna false, ya que Combate vence a Exploradora.
     *
     * @return false.
     */
    @Override
    public boolean esAmenazaExploradora() { return false; }

    /**
     * Indica si un Carguero representa una amenaza para esta nave de Combate.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Retorna true, ya que Combate pierde contra Carguero.
     *
     * @return true.
     */
    @Override
    public boolean esAmenazaCarguero() { return true; }

    /**
     * Indica si otra nave de Combate representa una amenaza para esta nave de Combate.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Retorna false, ya que acuerdan pacto de paz.
     *
     * @return false.
     */
    @Override
    public boolean esAmenazaCombate() { return false; }
}