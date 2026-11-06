package nave;

public class EstacionDeCarga implements IElementosUniverso {
    private final double RECARGA = 0.3;

    public EstacionDeCarga(){}

    @Override
    public void interactuarCon(INave naveJugador){     //aumenta combustible y energía
        try {
            int combustibleARecargar = (int) (naveJugador.getCombustible() * RECARGA);

            int faltaCombustible = 100 - naveJugador.getCombustible();             //cant de combustible máx que se puede cargar
            int cargaRealComb = Math.min(combustibleARecargar, faltaCombustible);

            if (cargaRealComb > 0) {
                naveJugador.cargaCombustible(cargaRealComb);
            }

            int energiaARecargar = (int) (naveJugador.getEnergia() * RECARGA);

            int faltaEnergia = 100 - naveJugador.getEnergia();             //cant de combustible máx que se puede cargar
            int cargaRealEnergia = Math.min(energiaARecargar, faltaEnergia);

            if (cargaRealEnergia > 0) {
                naveJugador.cargaEnergia(cargaRealEnergia);
            }

            if (naveJugador.getAsistente() != null) {
                naveJugador.getAsistente().registrarEventoBitacora("Recarga porcentual exitosa (30%). Comb: +" + cargaRealComb + " | Energía: +" + cargaRealEnergia);
            }
        }catch(OperacionRechazadaException e){
            if (naveJugador.getAsistente() != null) {
                naveJugador.getAsistente().registrarEventoBitacora("Error de recarga en estación: " + e.getMessage());
            }
        }
    }
}
