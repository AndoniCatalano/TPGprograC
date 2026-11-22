package nave;

import asistente.Asistente;

public class NaveEnemiga implements IElementosUniverso{
    private String tipo;
    public NaveEnemiga(){

        switch ((int)(Math.random() * 3)){
            case 0: tipo = "CARGUERO"; break;
            case 1: tipo = "EXPLORADORA"; break;
            case 2: tipo = "COMBATE"; break;
        }
    }


    @Override
    public void interactuarCon(Asistente asistente) {
        INave nave = asistente.getNave();
        switch (tipo){
            case "CARGUERO": nave.interactuarConCarguero(this);break;
            case "EXPLORADORA": nave.interactuarConExploradora(this);break;
            case "COMBATE": nave.interactuarConCombate(this);break;
            default:break;
        }
    }

    public void destruir(){

    }

    @Override
    public boolean esAmenaza(Asistente asistente) {
        String tipoNave = asistente.getNave().getClass().getSimpleName();

        return ("CARGUERO".equalsIgnoreCase(tipo) && "Combate".equalsIgnoreCase(tipoNave)) ||
                ("COMBATE".equalsIgnoreCase(tipo) && "Exploradora".equalsIgnoreCase(tipoNave)) ||
                ("EXPLORADORA".equalsIgnoreCase(tipo) && "Carguero".equalsIgnoreCase(tipoNave));
    }
}
