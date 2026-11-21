package nave;

public class EstacionDeCarga implements IElementosUniverso {
    private final double RECARGA = 0.3;

    public EstacionDeCarga(){}

    @Override
    public void interactuarCon(Asistente asistente) {
        int combustibleActual = asistente.consultarCombustible();
        int combustibleARecargar = (int) (combustibleActual * RECARGA);
        int faltaCombustible = 100 - combustibleActual;
        int cargaRealComb = Math.min(combustibleARecargar, faltaCombustible);

        if (cargaRealComb > 0) {
            asistente.cargaCombustible(cargaRealComb);
        }

        int energiaActual = asistente.consultarEnergia();
        int energiaARecargar = (int) (energiaActual * RECARGA);
        int faltaEnergia = 100 - energiaActual;
        int cargaRealEnergia = Math.min(energiaARecargar, faltaEnergia);

        if (cargaRealEnergia > 0) {
            asistente.cargaEnergia(cargaRealEnergia);
        }

        asistente.registrarEventoBitacora("Recarga porcentual exitosa (30%). Comb: +" + cargaRealComb + " | Energía: +" + cargaRealEnergia);
    }

    @Override
    public boolean esAmenaza(Asistente asistente) {
        return false;
    }
}
