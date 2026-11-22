package nave;

import asistente.Asistente;

public class Carguero extends Nave {

    /**
     * Constructor de la nave tipo Carguero.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Se instancia un Carguero con 100 de combustible inicial y 60 de energía inicial.
     */
    public Carguero() {
        super(100, 60);
    }

    @Override
    public void interactuarConCarguero(NaveEnemiga enemiga) {}

    @Override
    public void interactuarConCombate(NaveEnemiga enemiga) {
        enemiga.destruir();
    }

    @Override
    public void interactuarConExploradora(NaveEnemiga enemiga){
        this.destruir();
    }



    // --- Primer envío ---
//
//    /**
//     * Inicia la resolución por Double Dispatch cuando la nave del jugador se encuentra con este Carguero enemigo.
//     * <b>Pre:</b> asistente no debe ser nulo y debe tener una nave vinculada.
//     * <b>Post:</b> Se delega la resolución del combate al método defenderContraCarguero de la nave del jugador.
//     *
//     * @param asistente El asistente de la nave del jugador que interactúa con este Carguero.
//     */
//    @Override
//    public void interactuarCon(String tipo) {
//        asistente.getNave().defenderContraCarguero(this);
//    }
//
//    /**
//     * Consulta mediante Double Dispatch si este Carguero representa una amenaza para la nave del jugador.
//     * <b>Pre:</b> asistente no debe ser nulo y debe tener una nave vinculada.
//     * <b>Post:</b> Se retorna true si la nave del jugador pierde contra un Carguero, false en caso contrario.
//     *
//     * @param asistente El asistente de la nave del jugador.
//     * @return true si este Carguero es una amenaza para la nave del jugador; false en caso contrario.
//     */
//    @Override
//    public boolean esAmenaza(Asistente asistente) {
//        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";
//        assert asistente.getNave() != null : "Precondición fallida: el asistente no tiene una nave vinculada";
//
//        return asistente.getNave().esAmenazaCarguero();
//    }
//
//    // --- Segundo envío ---
//
//    /**
//     * Resuelve la defensa de este Carguero frente a una nave Exploradora enemiga (derrota).
//     * <b>Pre:</b> naveEnemiga no debe ser nula.
//     * <b>Post:</b> La energía de este Carguero queda en 0 y, si posee asistente, se registra la destrucción en la bitácora.
//     *
//     * @param naveEnemiga La nave Exploradora enemiga con la que se produjo el encuentro.
//     */
//    @Override
//    public void defenderContraExploradora() { // Carguero pierde contra Exploradora
//        assert naveEnemiga != null : "Precondición fallida: naveEnemiga no puede ser nula";
//
//        if (this.getEnergia() > 0) {
//            if (this.getAsistente() != null) {
//                this.getAsistente().consumeEnergia(this.getEnergia());
//            } else {
//                try {
//                    this.consumeEnergia(this.getEnergia());
//                } catch (OperacionRechazadaException e) {
//                    // No debería ocurrir porque validamos energia > 0
//                }
//            }
//        }
//        if (this.getAsistente() != null) {
//            this.getAsistente().registrarEventoBitacora("Destrucción total contra nave Exploradora enemiga.");
//        }
//
//        assert this.getEnergia() == 0 : "Postcondición fallida: la energía del Carguero no quedó en 0 tras perder";
//    }
//
//    /**
//     * Resuelve el encuentro de este Carguero frente a otro Carguero (pacto de paz).
//     * <b>Pre:</b> naveEnemiga no debe ser nula.
//     * <b>Post:</b> Los recursos de la nave no se ven afectados y, si posee asistente, se registra el pacto de paz en la bitácora.
//     *
//     * @param naveEnemiga La nave Carguero con la que se produjo el encuentro.
//     */
//    @Override
//    public void defenderContraCarguero(INave naveEnemiga) { // Pacto de paz, no se hace nada
//        assert naveEnemiga != null : "Precondición fallida: naveEnemiga no puede ser nula";
//
//        if (this.getAsistente() != null)
//            this.getAsistente().registrarEventoBitacora("Encuentro con Carguero aliado: Pacto de paz acordado.");
//    }
//
//    /**
//     * Resuelve la defensa de este Carguero frente a una nave de Combate enemiga (victoria).
//     * <b>Pre:</b> naveEnemiga no debe ser nula.
//     * <b>Post:</b> La nave sobrevive sin pérdida de energía y, si posee asistente, se registra la victoria en la bitácora.
//     *
//     * @param naveEnemiga La nave de Combate enemiga con la que se produjo el encuentro.
//     */
//    @Override
//    public void defenderContraCombate(INave naveEnemiga) { // Carguero le gana a Combate
//        assert naveEnemiga != null : "Precondición fallida: naveEnemiga no puede ser nula";
//
//        if (this.getAsistente() != null)
//            this.getAsistente().registrarEventoBitacora("¡Victoria contra nave de Combate enemiga!");
//    }
//
//    /**
//     * Indica si una nave Exploradora representa una amenaza para este Carguero.
//     * <b>Pre:</b> Ninguna.
//     * <b>Post:</b> Retorna true, ya que el Carguero pierde contra Exploradora.
//     *
//     * @return true.
//     */
//    @Override
//    public boolean esAmenazaExploradora() { return true; }
//
//    /**
//     * Indica si otro Carguero representa una amenaza para este Carguero.
//     * <b>Pre:</b> Ninguna.
//     * <b>Post:</b> Retorna false, ya que acuerdan pacto de paz.
//     *
//     * @return false.
//     */
//    @Override
//    public boolean esAmenazaCarguero() { return false; }
//
//    /**
//     * Indica si una nave de Combate representa una amenaza para este Carguero.
//     * <b>Pre:</b> Ninguna.
//     * <b>Post:</b> Retorna false, ya que el Carguero vence a Combate.
//     *
//     * @return false.
//     */
//    @Override
//    public boolean esAmenazaCombate() { return false; }
}