public class Asistente {
    /*
    Mapa mapa
    Mision mision

    preparar()
    ejecutar()
    evaluarResultado()
    cerrar()
    * */

    private Nave nave;
    private Mision mision;




    public Asistente(){

    }

    public void addTripulante(Tripulante tripulante){
        nave.addTripulante(tripulante);
    }

    public void mostrarSueldos(){
        nave.mostrarSueldos();
    }

    public void setNave(Nave nave){
        this.nave = nave;
    }

    public double consultarEnergia(){

        return nave.getEnergia();
    }

    public double consultarCombustible(){

        return nave.getCombustible();
    }

    public double consultarDesgaste(){

        return nave.getDesgaste();
    }
    public boolean ordenCargaCombustible(double cantidad){
        return nave.cargaComubstible(cantidad);
    }

    public boolean ordenCargaEnergia(double cantidad){

        return nave.cargaEnergia(cantidad);
    }

    //al realizar el mantenimiento reseteamos a 0 el desgaste o hacemos que reste cierta cantidad?
    public boolean ordenMantenimientoNave(){

        return nave.realizarMantenimiento();
    }



   /*
    public void prepararMision(Mision mision){

    }

    public void ejecutarMision(Mision mision){

    }

    public void iniciarMision(Mision mision){

    }
    */



    public void actualizaInforme(Informe informe){

    }


    public Informe entregaInforme(Informe inf){
        return inf;
    }



}
