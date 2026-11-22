package nave;

import asistente.Asistente;

public class Asteroide implements IElementosUniverso {
    private final int ENERGIAASTEROIDE = 30;

    /**
     * Constructor por defecto del obstáculo Asteroide.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Se instancia un Asteroide con un daño fijo de 30 unidades de energía.
     */
    public Asteroide() {
    }

    /**
     * Aplica el daño del impacto contra el asteroide reduciendo la energía de la nave hasta un mínimo de 0.
     * <b>Pre:</b> asistente no debe ser nulo.
     * <b>Post:</b> La energía de la nave disminuye en el mínimo entre 30 y su energía actual, quedando >= 0,
     * y se registra el impacto (o destrucción si llega a 0) en la bitácora del asistente.
     *
     * @param asistente El asistente de la nave que colisiona con el asteroide.
     */
    @Override
    public void interactuarCon(Asistente asistente) {
        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";

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

        assert asistente.consultarEnergia() == (energiaActual - danioReal)
                : "Postcondición fallida: no se aplicó el descuento de energía esperado";
        assert asistente.consultarEnergia() >= 0
                : "Postcondición fallida: la energía quedó negativa";
    }

    /**
     * Indica si el asteroide representa una amenaza para la nave.
     * <b>Pre:</b> asistente no debe ser nulo.
     * <b>Post:</b> Retorna siempre true, ya que el asteroide daña a cualquier tipo de nave.
     *
     * @param asistente El asistente de la nave que consulta el elemento.
     * @return true.
     */
    @Override
    public boolean esAmenaza(Asistente asistente) {
        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";

        return true;
    }
}