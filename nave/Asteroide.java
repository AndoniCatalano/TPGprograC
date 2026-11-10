package nave;

public class Asteroide implements IElementosUniverso {
    private final int ENERGIAASTEROIDE= 30;

    public Asteroide(){

    }

    @Override
    public void interactuarCon(INave naveJugador){
        try{
            naveJugador.consumeEnergia(ENERGIAASTEROIDE);
            if (naveJugador.getAsistente() != null)
                naveJugador.getAsistente().registrarEventoBitacora("Impacto contra asteroide: Energía reducida en " + ENERGIAASTEROIDE);
        }catch(OperacionRechazadaException e){
            if (naveJugador.getAsistente() != null) {
                naveJugador.getAsistente().registrarEventoBitacora("Impacto crítico contra asteroide: La energía llegó a 0. Nave destruida.");
            }
        }
    }
}
