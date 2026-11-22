package asistente;

import nave.*;
import misiones.*;
import motor.*;
import asistente.vista.AsistenteObserver;
import Tripulantes.Tripulante;
import nave.tablero.CantidadDeElementosSuperaCasillaException;
import nave.tablero.DimensionesDeTableroInconsistentesException;
import nave.tablero.Posicion;
import nave.tablero.Tablero;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    private ArrayList<String> informe;
    private ArrayList<Evento> bitacora;
    private INave nave;
    private ArrayList<AsistenteObserver> observadores = new ArrayList<>();
    // private Mision mision; Creo que no hace falta el atributo, ya se pasaria la mision m1,m2 o m3 a la funcion de ejectuarmision

    // Atributos nuevos para el soporte de misiones y navegación
    private Tablero tablero;
    private int recursosAsistencia = 1;

    /*
        <b>pre</b> nave != null
        <b>post</b> el asistente y la nave quedan vinculados
     */
    public Asistente(INave nave){
        assert nave != null : "Error la nave no puede ser  null";
        this.bitacora = new ArrayList<>();
        this.informe = new ArrayList<>();
        this.nave = nave;
        this.tablero = construirTablero();
    }

    public void agregarObservador(AsistenteObserver obs){
        this.observadores.add(obs);
        notificarRecursos();
    }

    private void notificarRecursos() {
        for (AsistenteObserver obs : observadores) {
            obs.onRecursosActualizados(consultarCombustible(), consultarEnergia(), consultarDesgaste());
        }
    }

    private Tablero construirTablero(){
        try {
            int dimX = (int) (Math.random() * 96) + 5;
            int dimY = (int) (Math.random() * 96) + 5;
            int cantElem = (int) (Math.random() * (dimX * dimY / 4) * 3);

            ArrayList<IElementosUniverso> elementos = new ArrayList<>();

            for (int i = 0; i < cantElem; i++) {
                switch ((int) (Math.random() * 3)){
                    case 0: elementos.add(new Asteroide()); break;
                    case 1: elementos.add(new EstacionDeCarga()); break;
                    case 2: elementos.add(new NaveEnemiga());break;
                }
            }

            Tablero tablero = new Tablero(dimX,dimY, elementos);
            return tablero;
        }
        catch (DimensionesDeTableroInconsistentesException | CantidadDeElementosSuperaCasillaException e){

        }

        return null;
    }


    /*
        <b>pre</b> mision != null
        <b>post</b> se intenta la ejecucion de la mision y se registra en la bitacora el resultado
     */
    public void ejecutarMision(Mision mision) {
        assert mision != null: "Error la mision no puede ser null";
        assert this.tablero != null : "Error el tablero no fue asignado";
        try {
            boolean exito = mision.ejecutarCicloMision(this, this.tablero);

            if (exito) {
                // Aca iria lo del salto que dice el pdf, algo como nave.salto() o nave.prepararsalto()
                this.registrarEventoBitacora("Misión finalizada. Éxito");
            }
        } catch (ContratoMisionException error) {
            this.registrarEventoBitacora("Misión abortada: " + error.getMessage());
        }
    }

    public void saltar(Posicion destino) {
        assert destino != null : "Error el destino no puede ser null";
        assert this.tablero != null : "Error el tablero no fue asignado";
        try {
            nave.saltar(this, destino);
            notificarRecursos();
        } catch (Exception e) {
            registrarEventoBitacora("No se pudo realizar el salto normal: " + e.getMessage());
        }
    }

    public void saltoWarp(Posicion destino) {
        assert destino != null : "Error el destino no puede ser null";
        assert this.tablero != null : "Error el tablero no fue asignado";
        try {
            nave.saltarWarp(this, destino);
            notificarRecursos();
        } catch (Exception e) {
            registrarEventoBitacora("Maniobra Warp interrumpida: " + e.getMessage());
        }
    }

    /*
        <b>pre</b> mensaje != null
        <b>post</b> el evento se agrego a la bitacora
     */
    public void registrarEventoBitacora(String mensaje){
        assert mensaje != null : "Error el mensaje no puede ser null";
        this.bitacora.add(new Evento(mensaje));
        for (AsistenteObserver obs : observadores) {
            obs.onEventoBitacora(mensaje);
        }
    }

    public void registrarInforme(String mensaje){
        assert mensaje != null : "Erroe el mensaje no puede ser null";
        this.informe.add(mensaje);
        for (AsistenteObserver obs : observadores) {
            obs.onInformeGenerado(mensaje);
        }
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
            notificarRecursos();
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }

    public void cargaEnergia(int cantidad){
        try{
            nave.cargaEnergia(cantidad);
            registrarEventoBitacora("Se cargaron " + cantidad + " unidades de energia");
            notificarRecursos();
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
            notificarRecursos();
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }

    public void consumeCombustible(int cantidad){
        try{
            nave.consumeCombustible(cantidad);
            registrarEventoBitacora("Se consumieron " + cantidad + " unidades de combustible");
            notificarRecursos();
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }

    public void consumeEnergia(int cantidad){
        try{
            nave.consumeEnergia(cantidad);
            registrarEventoBitacora("Se consumieron " + cantidad + " unidades de energia");
            notificarRecursos();
        }catch(OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }

    public void incrementaDesgaste(int cantidad){
        try {
            nave.incrementaDesgaste(cantidad);
            registrarEventoBitacora("Se incremento el desgaste de la nave en " + cantidad + " unidades");
            notificarRecursos();
        }catch (OperacionRechazadaException e){
            registrarEventoBitacora(e.getMessage());
        }
    }

   // metodos segunda parte, tablero, nave, misiones parte 2

    public void setTablero(Tablero tablero) {
        assert tablero != null : "Error el tablero no puede ser null";
        this.tablero = tablero;
    }

    public void ejecutarMision(Mision mision, Tablero tablero) {
        assert tablero != null : "Error el tablero no puede ser null";
        this.tablero = tablero;
        ejecutarMision(mision);
    }

    public boolean laNaveEstaViva() {
        return this.nave.getDesgaste() < 100 && this.nave.getCombustible() > 0 && this.nave.getEnergia() > 0;
    }

    public void moverA(Posicion proximoPaso) {
        assert proximoPaso != null : "Error la posicion destino no puede ser null";
        assert this.tablero != null : "Error el tablero no fue asignado";
        this.tablero.moverNave(proximoPaso);
        registrarEventoBitacora("Nave desplazada a (" + proximoPaso.getX() + ", " + proximoPaso.getY() + ")");
    }

    public List<Posicion> calcularRuta(Posicion destino) {
        assert destino != null : "Error el destino no puede ser null";
        assert this.tablero != null : "Error el tablero no fue asignado";

        IElementosUniverso[][] grilla = this.tablero.verTablero();
        int filas = this.tablero.getX();
        int columnas = this.tablero.getY();
        Posicion inicio = this.tablero.getPosicionNave();

        if (inicio == null || inicio.equals(destino)) {
            return new ArrayList<>();
        }

        boolean[][] visitado = new boolean[filas][columnas];
        Posicion[][] padre = new Posicion[filas][columnas];
        Queue<Posicion> cola = new LinkedList<>();

        cola.add(inicio);
        visitado[inicio.getX()][inicio.getY()] = true;

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        while (!cola.isEmpty()) {
            Posicion actual = cola.poll();
            if (actual.equals(destino)) {
                break;
            }

            for (int i = 0; i < 8; i++) {
                int nx = actual.getX() + dx[i];
                int ny = actual.getY() + dy[i];

                if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas && !visitado[nx][ny]) {
                    IElementosUniverso elemento = grilla[nx][ny];
                    boolean esDestino = (nx == destino.getX() && ny == destino.getY());

                    if (esDestino || elemento == null || !elemento.esAmenaza(this)) {
                        visitado[nx][ny] = true;
                        padre[nx][ny] = actual;
                        cola.add(new Posicion(nx, ny));
                    }
                }
            }
        }

        LinkedList<Posicion> ruta = new LinkedList<>();
        Posicion paso = destino;
        while (paso != null && !paso.equals(inicio)) {
            ruta.addFirst(paso);
            paso = padre[paso.getX()][paso.getY()];
        }

        if (paso == null) {
            return new ArrayList<>();
        }
        return ruta;
    }

    public boolean tieneRecursosParaEntregar() {
        return this.recursosAsistencia > 0;
    }

    public void entregarRecursos() {
        if (this.recursosAsistencia > 0) {
            this.recursosAsistencia--;
            registrarEventoBitacora("Se entregaron los recursos de asistencia en destino");
        }
    }

    public INave getNave() {
        return nave;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void mantenimientoGeneral() {
        nave.mantenimientoGeneral();
        registrarEventoBitacora("Mantenimiento general completado: desgaste reducido a 0.");
        notificarRecursos();
    }

}
