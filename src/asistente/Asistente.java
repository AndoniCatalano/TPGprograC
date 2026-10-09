package asistente;
import nave.*;
import misiones.*;
import motor.*;

import Tripulantes.Tripulante;


import java.util.ArrayList;

public class Asistente{
    /*
    Mapa mapa


    preparar()
    ejecutar()
    evaluarResultado()
    cerrar()
    * */
    private ArrayList<Evento> bitacora;
    private INave nave;
    // private Mision mision; Creo que no hace falta el atributo, ya se pasaria la mision m1,m2 o m3 a la funcion de ejectuarmision

    public void ejecutarMision(Mision mision) {
        try {
            boolean exito = mision.ejecutarCicloMision(this);

            if (exito) {
                // Aca iria lo del salto que dice el pdf, algo como nave.salto() o nave.prepararsalto()
                this.registrarEventoBitacora("Misión finalizada. Éxito");
            }
        } catch (IllegalStateException error) {
            this.registrarEventoBitacora("Misión abortada: " + error.getMessage());
        }
    }



    public Asistente(INave nave){
        this.bitacora = new ArrayList<>();
        this.nave = nave;
    }

    public void saltoWarp(){
        try {
            nave.prepararSalto();
            nave.ejecutarSalto();
            nave.terminarEnfriamiento();
            //terminamos el enfriamiento, que como tal no existe, ya que ahora no podemos medir el paso del tiempo
            System.out.println("Salto realizado con exito");//esto queda para el testeo nomas
            //bitacora
            registrarEventoBitacora("Salto realizado con exito");
        }catch (TransicionInvalidaException e){
            System.out.println(e.getMessage());//esto tambien queda para el testeo
            //bitacora
            registrarEventoBitacora(e.getMessage());
        }
    }

    public void registrarEventoBitacora(String mensaje){
        this.bitacora.add(new Evento(mensaje));
    }

    public void addTripulante(Tripulante tripulante){
        this.nave.addTripulante(tripulante);
    }

    public void mostrarSueldos(){
        this.nave.liquidarHaberes();
    }



    public int consultarEnergia(){

        return nave.getEnergia();
    }

    public int consultarCombustible(){

        return nave.getCombustible();
    }

    public int consultarDesgaste(){

        return nave.getDesgaste();
    }
    public void cargaCombustible(int cantidad){
        try{
            nave.cargaCombustible(cantidad);
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }

    public void cargaEnergia(int cantidad){

        try{
            nave.cargaEnergia(cantidad);
        }catch(OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }

    }


    public void mostrarBitacora(){
        for (Evento evento: bitacora){
            System.out.println(evento);
        }
    }

    public void realizarMantenimiento(){

        try{
            nave.realizarMantenimiento();
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }

    public void consumeCombustible(int cantidad){
        try{
            nave.consumeCombustible(cantidad);
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }

    }

    public void consumeEnergia(int cantidad){
        try{
            nave.consumeEnergia(cantidad);
        }catch(OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }

    }


    public void incrementaDesgaste(int cantidad){
        try {
            nave.incrementaDesgaste(cantidad);
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }





}
