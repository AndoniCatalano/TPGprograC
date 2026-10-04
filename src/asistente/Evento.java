package asistente;

import java.time.Instant;

public class Evento {
    private Instant momento;
    private String mensaje;

    public Evento(String mensaje){
        this.mensaje = mensaje;
        momento = Instant.now();
    }

    @Override
    public String toString(){
        return "Evento:" + mensaje +
                "Momento del Evento: " + momento;
    }

}
