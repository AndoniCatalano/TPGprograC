// Almacenar los asistente
// Insertar y buscar naves
//

import Asistente.*;
import Nave.*;
import Tripulantes.Tripulante;

import java.util.ArrayList;

public class Sistema {
    private Sistema _instancia = null;
    private ArrayList<Asistente> asistentes;

    private Sistema() {
        this.asistentes = new ArrayList<>();
    }

    public Sistema getSistema(){
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

        this.asistentes.removeIf(asistente ->
                asistente.nave != null &&
                asistente.nave.equals(nave));
    }

    public void asignaMision(Mision mision){
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

}
