package nave;

public class Asteroide implements IElementosUniverso {
    private final int ENERGIAASTEROIDE= 30;

    public Asteroide() {
    }

    @Override
    public void interactuarCon(Asistente asistente) {
        int energiaActual = asistente.consultarEnergia();
        int danioReal = Math.min(ENERGIAASTEROIDE, energiaActual);

        if (danioReal > 0) {
            asistente.consumeEnergia(danioReal);
        }

        if (asistente.consultarEnergia() == 0) {
            asistente.registrarEventoBitacora("Impacto crítico contra asteroide: La energía llegó a 0. Nave destruida.");
        } else {
            asistente.registrarEventoBitacora("Impacto contra asteroide: Energía reducida en " + danioReal);
        }
    }

    @Override
    public boolean esAmenaza(Asistente asistente) {
        return true;
    }
}
