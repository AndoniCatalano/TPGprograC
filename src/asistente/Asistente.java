public class Asistente {
    /*
    Mapa mapa
    Mision mision

    preparar()
    ejecutar()
    evaluarResultado()
    cerrar()
    * */
    Mision mision;
    Nave nave;
    NaveFactory fabrica = new NaveFactory();

    public Asistente(){

    }



    public void creaNave(String tipoNave){
        nave = fabrica.getNave(tipoNave);
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
    public void ordenCargaCombustible(double cantidad){
        this.nave.cargaComubstible(cantidad);
    }

    public void ordenCargaEnergia(double cantidad){
        this.nave.cargaEnergia(cantidad);
    }

    //al realizar el mantenimiento reseteamos a 0 el desgaste o hacemos que reste cierta cantidad?
    public void ordenMantenimientoNave(){
        this.nave.realizarMantenimiento();
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
