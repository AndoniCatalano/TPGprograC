package asistente;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Evento {
    private static final ZoneId ZONA_ARGENTINA = ZoneId.of("America/Argentina/Buenos_Aires");
    private static final DateTimeFormatter FORMATO_MOMENTO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Instant momento;
    private String mensaje;

    public Evento(String mensaje){
        this.mensaje = mensaje;
        momento = Instant.now();
    }

    @Override
    public String toString(){
        return "Evento: " + mensaje +
                " | Momento: " + momento.atZone(ZONA_ARGENTINA).format(FORMATO_MOMENTO);
    }

}
