public class Asistente {
    /*
    Mapa mapa
    Mision mision

    preparar()
    ejecutar()
    evaluarResultado()
    cerrar()
    * */



    public Asistente(){

    }

    public void anadirTripulante(Tripulante tripulante, Nave nave){
        nave.addTripulante(tripulante);
    }

    public void mostrarSueldos(Nave nave){
        nave.mostrarSueldos();
    }

    public Nave creaNave(String tipoNave){

        return fabrica.getNave(tipoNave);
    }

    public double consultarEnergia(Nave nave){

        return nave.getEnergia();
    }

    public double consultarCombustible(Nave nave){

        return nave.getCombustible();
    }

    public double consultarDesgaste(Nave nave){

        return nave.getDesgaste();
    }
    public boolean ordenCargaCombustible(double cantidad, Nave nave){
        return nave.cargaComubstible(cantidad);
    }

    public boolean ordenCargaEnergia(double cantidad, Nave nave){

        return nave.cargaEnergia(cantidad);
    }

    //al realizar el mantenimiento reseteamos a 0 el desgaste o hacemos que reste cierta cantidad?
    public boolean ordenMantenimientoNave(){

        return this.nave.realizarMantenimiento();
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
