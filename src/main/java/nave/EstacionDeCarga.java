package nave;

import asistente.Asistente;

public class EstacionDeCarga implements IElementosUniverso {
    private final double RECARGA = 30;

    /**
     * Constructor por defecto de la estación de carga.
     * <b>Pre:</b> Ninguna.
     * <b>Post:</b> Se instancia una EstacionDeCarga configurada con un porcentaje fijo de recarga del 30%.
     */
    public EstacionDeCarga() {
    }

    /**
     * Recarga el combustible y la energía de la nave en un 30% de su valor actual, sin superar el tope de 100.
     * <b>Pre:</b> asistente no debe ser nulo.
     * <b>Post:</b> El combustible y la energía de la nave aumentan hasta un máximo de 100 según la recarga porcentual
     * y se registra el evento en la bitácora del asistente.
     *
     * @param asistente El asistente de la nave que llega a la estación de carga.
     */
    @Override
    public void interactuarCon(Asistente asistente) {
        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";

        int combustibleActual = asistente.consultarCombustible();
        int combustibleARecargar = (int) (combustibleActual + RECARGA);
        int faltaCombustible = 100 - combustibleActual;
        int cargaRealComb = Math.min(combustibleARecargar, faltaCombustible);

        if (cargaRealComb > 0) {
            asistente.cargaCombustible(cargaRealComb);
        }

        int energiaActual = asistente.consultarEnergia();
        int energiaARecargar = (int) (energiaActual + RECARGA);
        int faltaEnergia = 100 - energiaActual;
        int cargaRealEnergia = Math.min(energiaARecargar, faltaEnergia);

        if (cargaRealEnergia > 0) {
            asistente.cargaEnergia(cargaRealEnergia);
        }

        asistente.registrarEventoBitacora("Recarga porcentual exitosa (30%). Comb: +" + cargaRealComb + " | Energía: +" + cargaRealEnergia);

        assert asistente.consultarCombustible() >= combustibleActual && asistente.consultarCombustible() <= 100
                : "Postcondición fallida: el combustible quedó fuera de rango tras la recarga";
        assert asistente.consultarEnergia() >= energiaActual && asistente.consultarEnergia() <= 100
                : "Postcondición fallida: la energía quedó fuera de rango tras la recarga";
    }

    /**
     * Indica si la estación de carga representa una amenaza para la nave.
     * <b>Pre:</b> asistente no debe ser nulo.
     * <b>Post:</b> Retorna siempre false, ya que la estación de carga es un punto de asistencia.
     *
     * @param asistente El asistente de la nave que consulta el elemento.
     * @return false.
     */
    @Override
    public boolean esAmenaza(Asistente asistente) {
        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";

        return false;
    }
}