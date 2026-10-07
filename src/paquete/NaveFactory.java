package paquete;

import Asistente.Asistente;

public class NaveFactory {

    public INave getNave(String tipoNave) throws TipoNaveInvalidoException {
        INave nuevaNave = null;
        if(tipoNave.equalsIgnoreCase("carguero"))
            return new Carguero();
        else
            if (tipoNave.equalsIgnoreCase("combate"))
                return new Combate();
            else
                if (tipoNave.equalsIgnoreCase("exploradora"))
                    return new Exploradora();
                else
                    throw new TipoNaveInvalidoException("Tipo de nave inválido: " + tipoNave);

        if (nuevaNave != null){                 //establezco la relación bidireccional
            Asistente asistente = new Asistente(nuevaNave);     //asistente tiene una sola nave
            nuevaNave.setAsistente(asistente);                  //nave tiene su asistente
        }

        return nuevaNave;
    }

}
