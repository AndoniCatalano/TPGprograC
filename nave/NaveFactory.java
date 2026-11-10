package nave;

import asistente.Asistente;

public class NaveFactory {

    /**
     * Fábrica de naves que centraliza la instanciación de los tipos de nave existentes.
     * <b>Pre:</b> tipoNave debe ser un String válido y concordante con un tipo de nave.
     * <b>Post</b> Se devuelve una instancia concreta de INave con su Asistente y relación bidireccional establecida.
     *
     * @param tipoNave Un String que indica el tipo de nave a crear ("carguero", "combate", o "exploradora").
     * @return Un objeto INave, listo para operar.
     * @throws TipoNaveInvalidoException Si el string pasado por parámetro no corresponde a ningún tipo de nave conocido.
     */
    public static INave getNave(String tipoNave) throws TipoNaveInvalidoException {
        assert tipoNave != null && !tipoNave.isEmpty() : "Precondición fallida: el String tipoNave es nulo o está vacío";

        INave nuevaNave = null;
        if(tipoNave.equalsIgnoreCase("carguero"))
            nuevaNave = new Carguero();
        else
            if (tipoNave.equalsIgnoreCase("combate"))
                nuevaNave = new Combate();
            else
                if (tipoNave.equalsIgnoreCase("exploradora"))
                    nuevaNave = new Exploradora();
                else
                    throw new TipoNaveInvalidoException("Tipo de nave inválido: " + tipoNave);

        if (nuevaNave != null){                 //establezco la relación bidireccional
            Asistente asistente = new Asistente(nuevaNave);     //asistente tiene una sola nave
            nuevaNave.setAsistente(asistente);                  //nave tiene su asistente
        }

        assert nuevaNave != null : "Postcondición fallida: la nave a devolver es nula y no lanzó excepción";
        assert nuevaNave.getAsistente() != null : "Postcondición fallida: la nave se está devolviendo sin su asistente inicializado";

        return nuevaNave;
    }

}
