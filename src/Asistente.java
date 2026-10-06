import Nave.Nave;
import Tripulantes.Tripulante;

import java.util.ArrayList;

public class Asistente {
    protected Nave nave;
    protected ArrayList<Tripulante> tripulantes;
    protected ArrayList<ItemBitacora> bitacora;
    protected Mision mision;
    protected String informe;


    /*
    Mapa mapa
    Mision mision

    preparar()
    ejecutar()
    evaluarResultado()
    cerrar()
    * */




    public Asistente(){
        tripulantes = new ArrayList<>();
        bitacora = new ArrayList<>();
    }

    public void cicloEjecucion(){
        if Preparada(){
            this.mision.viajar();
            this.mision.ejecutar();
            this.mision.volver();
        } else {
            throw
        }
    }

    public void addTripulante(Tripulante tripulante){
        this.tripulantes.add(tripulante);
    }

    public void removeTripulante(Tripulante tripulante){}

    public void mostrarSueldos(){
        //recorre vector tripulantes
    }

    public Nave setNave(Nave nave){
        this.nave = nave;
    }

    public double consultarEnergia(){
        return this.nave.getEnergia();
    }

    public double consultarCombustible(){
        return this.nave.getCombustible();
    }

    public double consultarDesgaste(){
        return this.nave.getDesgaste();
    }
    public boolean ordenCargaCombustible(double cantidad){
        return this.nave.cargaComubstible(cantidad);
    }

    public boolean ordenCargaEnergia(double cantidad){

        return this.nave.cargaEnergia(cantidad);
    }

    //al realizar el mantenimiento reseteamos a 0 el desgaste o hacemos que reste cierta cantidad?
    public boolean ordenMantenimientoNave(){

        return this.nave.realizarMantenimiento();
    }

    public void actualizaInforme(Informe informe){

    }


    public Informe entregaInforme(Informe inf){
        return inf;
    }



}