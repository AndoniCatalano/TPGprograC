package Tripulantes;

public class TripulanteFactory {


    /**
     * precondiciones:
     * -origen valido no nulo (terricola/marciano/vulcano)
     * -rol valido no nulo (capitan/teniente/consejero/alferez)
     * -antiguedad no negativa
     *
     * postcondiciones:
     * se devuelve un tripulante de tipo Tripulante con un rol y un origen asignados
     *
     * @param rol
     * @param origen
     * @param identidad
     * @param antiguedad
     * @return
     * @throws AntiguedadInvalidaException
     * @throws OrigenTripulanteException
     * @throws RolTripulanteInvalidoException
     */
    public static Tripulante creaTripulante(String rol, String origen, String identidad, int antiguedad) throws AntiguedadInvalidaException,OrigenTripulanteException, RolTripulanteInvalidoException {
        Tripulante tripulante;

        if(antiguedad < 0){
            throw new AntiguedadInvalidaException();
        }
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
