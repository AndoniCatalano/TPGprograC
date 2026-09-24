public class NaveFactory {
    public Nave getNave(String tipoNave){
        if(tipoNave.equalsIgnoreCase("carguero"))
            return new Carguero();
        else
            if (tipoNave.equalsIgnoreCase("combate"))
                return new Combate();
            else
                if (tipoNave.equalsIgnoreCase("exploradora"))
                    return new Exploradora();
                else
                    return null; //acá quizá debería lanzar alguna excepción, por ahora lo soluciono así
    }
}
