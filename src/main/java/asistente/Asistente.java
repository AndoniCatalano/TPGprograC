package asistente;
import nave.*;
import misiones.*;
import motor.*;

import Tripulantes.Tripulante;


import java.util.ArrayList;

/*
    <b>invariante:</b> nave != null
    <b>invariante:</b> bitacora != null
 */

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

    /*
        <b>pre</b> nave != null
        <b>post</b> el asistente y la nave quedan vinculados
     */
    public Asistente(INave nave){
        assert nave != null : "Error la nave no puede ser  null";
        this.bitacora = new ArrayList<>();
        this.nave = nave;
    }

    /*
        <b>pre</b> mision != null
        <b>post</b> se intenta la ejecucion de la mision y se registra en la bitacora el resultado
     */
    public void ejecutarMision(Mision mision) {
        assert mision != null: "Error la mision no puede ser null";
        try {
            boolean exito = mision.ejecutarCicloMision(this);

            if (exito) {
                // Aca iria lo del salto que dice el pdf, algo como nave.salto() o nave.prepararsalto()
                this.registrarEventoBitacora("Misión finalizada. Éxito");
            }
        } catch (ContratoMisionException error) {
            this.registrarEventoBitacora("Misión abortada: " + error.getMessage());
        }
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

    /*
        <b>pre</b> mensaje != null
        <b>post</b> el evento se agrego a la bitacora
     */
    public void registrarEventoBitacora(String mensaje){
        assert mensaje != null : "Error el mensaje no puede ser null";
        this.bitacora.add(new Evento(mensaje));
    }

    /*
        <b>pre</b> tripulante != null
     */
    public void addTripulante(Tripulante tripulante){
        assert tripulante != null : "Error el tripulante no puede ser nulo";
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
            registrarEventoBitacora("Se cargaron " + cantidad + " unidades de combustible");
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }

    public void cargaEnergia(int cantidad){

        try{
            nave.cargaEnergia(cantidad);
            registrarEventoBitacora("Se cargaron " + cantidad + " unidades de energia");
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
            registrarEventoBitacora("Se realizo mantenimiento en la nave");
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }

    public void consumeCombustible(int cantidad){
        try{
            nave.consumeCombustible(cantidad);
            registrarEventoBitacora("Se consumieron " + cantidad + " unidades de combustible");
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }

    }

    public void consumeEnergia(int cantidad){
        try{
            nave.consumeEnergia(cantidad);
            registrarEventoBitacora("Se consumieron " + cantidad + " unidades de energia");
        }catch(OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }

    }


    public void incrementaDesgaste(int cantidad){
        try {
            nave.incrementaDesgaste(cantidad);
            registrarEventoBitacora("Se incremento el desgaste de la nave en " + cantidad + " unidades");
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }





}
