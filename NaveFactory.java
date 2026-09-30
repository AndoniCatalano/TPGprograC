public class NaveFactory {

    public Nave getNave(String tipoNave) throws TipoNaveInvalidoException{
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
    }

}
