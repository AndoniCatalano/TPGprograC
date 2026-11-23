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

    @Override
    public void interactuarConCarguero(NaveEnemiga enemiga) {
        enemiga.destruir();
    }

    @Override
    public void interactuarConCombate(NaveEnemiga enemiga) {
        this.destruir();
    }

    @Override
    public void interactuarConExploradora(NaveEnemiga enemiga){}

}