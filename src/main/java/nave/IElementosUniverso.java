package nave;

import asistente.Asistente;

public interface IElementosUniverso { //así los elems del tablreo pueden interactuar más allá del tipo que sean
    void interactuarCon(Asistente asistente);
    boolean esAmenaza(Asistente asistente);
}
