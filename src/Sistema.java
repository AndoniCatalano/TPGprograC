import asistente.Asistente;
import nave.Nave;
import misiones.Mision;
import nave.NaveFactory;

import java.util.ArrayList;

public class Sistema {
    private static Sistema _instancia = null;
    private final ArrayList<Nave> naves;

    private Sistema() {
        this.naves = new ArrayList<>();
    }

    public static Sistema getSistema(){
        if (_instancia == null){
            _instancia = new Sistema();
        }
        return _instancia;
    }

    public void addNave(String tipo){
        Nave nave = NaveFactory.getNave(tipo);
        this.naves.add(nave);
    }

    public void removeNave(Nave nave){
        this.naves.remove(nave);
    }

    public ArrayList<Nave> getNaves(){
        return new ArrayList<>(this.naves);
    }

    public void ejecutarMision(Nave nave, Mision mision){
        Asistente asistente = nave.getAsistente();
        asistente.ejecutarMision(mision);
    }
}
