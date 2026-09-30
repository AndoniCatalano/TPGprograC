import Asistente.*;
import Nave.*;
import Tripulantes.Tripulante;

import java.util.ArrayList;

public class Sistema {
    private static Sistema _instancia = null;
    private ArrayList<Asistente> asistentes;

    private Sistema() {
        this.asistentes = new ArrayList<>();
    }

    public static Sistema getSistema(){
        if (_instancia == null){
            _instancia = new Sistema();
        }
        return _instancia;
    }

    public void addNave(Nave nave){
        Asistente asistente = new Asistente();
        this.asistentes.add(asistente);
        asistente.setNave(nave);
    }

    public void removeNave(Nave nave){
        Nave auxNave;
        this.asistentes.removeIf(asistente ->
                asistente.getNave() != null &&
                asistente.getNave.equals(nave));
    }

    public void asignaMision(Asistente asistente, Mision mision){
        asistente.setMision(mision);
    }

    public void addTripulante(Asistente asistente, Tripulante tripulante){
        asistente.addTripulante(tripulante);
    }

    public void removeTripulante(Asistente asistente, Tripulante tripulante){
        asistente.removeTripulante(tripulante);
    }

    public void liquidarHaberes(Asistente asistente){
        asistente.liquidarHaberes();
    }

    public ArrayList<Asistente> getAsistentes(){
        return new ArrayList<>(this.asistentes);
    }

    public void ejecutarMision(Asistente asistente){
        asistente.ejecutarMision();
    }

}
