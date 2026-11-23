package nave;


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
}