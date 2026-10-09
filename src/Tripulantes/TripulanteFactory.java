package Tripulantes;

public class TripulanteFactory {
    public static Tripulante creaTripulante(String rol, String origen, String identidad, int antiguedad) throws OrigenTripulanteException, RolTripulanteInvalidoException {
        Tripulante tripulante;

        if (rol == null) {
            throw new RolTripulanteInvalidoException();
        }
        if (origen == null){
            throw new OrigenTripulanteException();
        }
        switch (rol.toUpperCase()){
            case "CAPITAN": tripulante = new Capitan(identidad,antiguedad); break;
            case "ALFEREZ": tripulante = new Alferez(identidad,antiguedad); break;
            case "CONSEJERO": tripulante = new Consejero(identidad,antiguedad); break;
            case "TENIENTE": tripulante = new Teniente(identidad,antiguedad); break;
            default: throw new RolTripulanteInvalidoException();
        }

        switch (origen.toUpperCase()){
            case "TERRICOLA": tripulante = new Terricola(tripulante); break;
            case "MARCIANO": tripulante = new Marciano(tripulante); break;
            case "VULCANO": tripulante = new Vulcano(tripulante); break;
            default: throw new OrigenTripulanteException();
        }

        return tripulante;

    }
}
