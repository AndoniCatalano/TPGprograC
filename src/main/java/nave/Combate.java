package nave;


public class Combate extends Nave {

    /**
     * Constructor de la nave tipo Combate.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Se instancia una nave de Combate con 80 de combustible inicial y 100 de energía inicial.
     */
    public Combate() {
        super(80, 100); //indico los valores de inicio a naveInicial
    }

    @Override
    public void interactuarConCarguero(NaveEnemiga enemiga) {
        this.destruir();
    }

    @Override
    public void interactuarConCombate(NaveEnemiga enemiga) {}

    @Override
    public void interactuarConExploradora(NaveEnemiga enemiga){
        enemiga.destruir();
    }

}