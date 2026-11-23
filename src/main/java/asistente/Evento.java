package asistente;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Evento {
    private static final ZoneId ZONA_ARGENTINA = ZoneId.of("America/Argentina/Buenos_Aires");
    private static final DateTimeFormatter FORMATO_MOMENTO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Instant momento;
    private String mensaje;

    /**
     * Constructor de un evento de bitácora con marca temporal actual.
     * <b>Pre:</b> mensaje no debe ser nulo.
     * <b>Post:</b> El evento queda inicializado con el mensaje indicado y el instante de creación actual.
     *
     * @param mensaje La descripción textual del evento.
     */
    public Evento(String mensaje) {
        assert mensaje != null : "Precondición fallida: mensaje no puede ser nulo";

        this.mensaje = mensaje;
        this.momento = Instant.now();

        assert this.mensaje.equals(mensaje) : "Postcondición fallida: el mensaje no se asignó correctamente";
        assert this.momento != null : "Postcondición fallida: el momento del evento no fue inicializado";
        assert invariante() : "Fallo invariante en el constructor de Evento";
    }

    /**
     * Testea el invariante de clase garantizando que el mensaje y el instante del evento no sean nulos.
     */
    private boolean invariante() {
        return this.mensaje != null && this.momento != null;
    }

    /**
     * Devuelve la representación textual del evento con su mensaje y marca temporal formateada.
     * <b>Pre:</b> El evento debe tener inicializados su mensaje y su momento.
     * <b>Post:</b> Retorna una cadena no nula con el mensaje del evento y la fecha/hora en zona horaria de Argentina.
     *
     * @return La cadena formateada del evento.
     */
    @Override
    public String toString() {
        String resultado = "Evento: " + mensaje +
                " | Momento: " + momento.atZone(ZONA_ARGENTINA).format(FORMATO_MOMENTO);

        assert resultado != null : "Postcondición fallida: la representación textual del evento no puede ser nula";
        assert invariante() : "Fallo invariante en toString de Evento";
        return resultado;
    }
}