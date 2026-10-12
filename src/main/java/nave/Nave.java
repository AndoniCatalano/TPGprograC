package nave;

import asistente.Asistente;
import Tripulantes.Tripulante;
import java.util.ArrayList;
import motor.MotorWarp;
import motor.TransicionInvalidaException;

public abstract class Nave implements INave {
    protected int combustible;
    protected int energia;
    protected int desgaste;
    protected boolean necesitaMantenimiento; //depende del desgaste
    protected MotorWarp motorWarp;
    protected Asistente asistente;
    protected ArrayList<Tripulante> tripulantes;

    /**
     * Constructor para la inicialización de los distintos tipos de naves.
     * <b>Pre:</b> combustibleInicial y energiaInicial deben ser mayores a 0 y no superar la capacidad máxima de 100.
     * <b>Post:</b> La nave se instancia con los recursos especificados, el desgaste se inicializa en 0 y
     * la necesidad de mantenimiento en false. Además se instancia un nuevo MotorWarp (que arranca Disponible)
     * y se inicializa la lista de tripulantes vacía.
     *
     * @param combustibleInicial La cantidad de combustible con la que arranca la nave.
     * @param energiaInicial La cantidad de energía con la que arranca la nave.
     */
    public Nave(int combustibleInicial, int energiaInicial){ //la diferencia entre los tipos de nave es por sus configs iniciales
        assert combustibleInicial > 0 && combustibleInicial <= 100 : "Precondición fallida: combustibleInicial inválido";
        assert energiaInicial > 0 && energiaInicial <= 100 : "Precondición fallida: energiaInicial inválida";

        this.combustible = combustibleInicial;
        this.energia = energiaInicial;
        this.desgaste = 0;
        this.necesitaMantenimiento = false;
        this.motorWarp = new MotorWarp();
        this.tripulantes = new ArrayList<>();

        assert this.desgaste == 0 : "Postcondición fallida: el desgaste no se inicializó en 0";
        assert !this.necesitaMantenimiento : "Postcondición fallida: necesitaMantenimiento no inició en false";
        assert invariante() : "Fallo invariante en el constructor: El estado inicial de la nave es erróneo";
    }

    /**
     * Testea el invariante de clase, garantizando la integridad estructural y
     * los límites de dominio de los atributos de la nave.
     */
    private boolean invariante() {
        boolean recursosValidos = (combustible >= 0 && combustible <= 100) &&
                (energia >= 0 && energia <= 100) &&
                (desgaste >= 0 && desgaste <= 100);
        boolean estructurasValidas = motorWarp != null && tripulantes != null;
        boolean logicaMantenimiento = (desgaste < 80) || necesitaMantenimiento;

        return recursosValidos && estructurasValidas && logicaMantenimiento;
    }

    /**
     * Carga el asistente para establecer la relación bidireccional.
     * <b>Pre:</b> El objeto asistente pasado por parámetro no debe ser nulo.
     * <b>Post:</b> El asistente queda vinculado a la nave.
     *
     * @param asistente El objeto Asistente a vincular con la nave.
     */
    @Override
    public void setAsistente(Asistente asistente) {
        assert asistente != null : "Precondición fallida: el asistente no puede ser nulo";

        this.asistente = asistente;

        assert this.asistente != null : "Postcondición fallida: el asistente no se guardó";
    }

    /**
     * Carga combustible en la nave.
     * <b>Pre:</b> cantCombustible > 0 y (combustible actual + cantCombustible) <= 100.
     * <b>Post</b> El combustible de la nave se incrementa en cantCombustible.
     *
     * @param cantCombustible La cantidad de combustible a cargar.
     * @throws OperacionRechazadaException Si la cantidad es inválida (menor o igual a cero) o si la carga supera la capacidad máxima de 100.
     */
    @Override
    public void cargaCombustible(int cantCombustible) throws OperacionRechazadaException {
        int combustibleAnt;
        assert cantCombustible > 0 : "Precondición fallida: cantCombustible debe ser mayor a 0";
        assert (this.combustible + cantCombustible) <= 100 : "Precondición fallida: la carga superará el límite de 100";

        if (cantCombustible <= 0 || (this.combustible + cantCombustible) > 100)
            throw new OperacionRechazadaException("La carga de combustible supera la capacidad máxima (100) o es inválida.");
        else {
            combustibleAnt = this.combustible;     //para el assert
            this.combustible += cantCombustible;

            assert this.combustible == (combustibleAnt + cantCombustible) : "Postcondición fallida: el combustible no se sumó";
            assert invariante() : "Fallo invariante durante la carga de combustible";
        }
    }

    /**
     * Carga energía en la nave.
     * <b>Pre:</b> cantEnergia > 0 y (energia actual + cantEnergia) <= 100.
     * <b>Post:</b> La energía de la nave se incrementa cantEnergia unidades.
     *
     * @param cantEnergia La cantidad de energía a cargar.
     * @throws OperacionRechazadaException Si la cantidad es inválida (menor o igual a cero) o si la carga supera la capacidad máxima de 100.
     */
    @Override
    public void cargaEnergia(int cantEnergia) throws OperacionRechazadaException {
        int energiaAnt;
        if (cantEnergia <= 0 || (this.energia + cantEnergia) > 100)
            throw new OperacionRechazadaException("La carga de energía supera la capacidad máxima (100) o es inválida.");
        else{
            energiaAnt = this.energia;
            this.energia += cantEnergia;

            assert this.energia == (energiaAnt + cantEnergia) : "Postcondición fallida: la energía no se sumó correctamente";
            assert invariante() : "Fallo invariante durante la carga de energía";
        }
    }

    /**
     * Consume combustible de la nave.
     * <b>Pre:</b> cantCombustible > 0 y cantCombustible <= combustible actual.
     * <b>Post:</b> El combustible de la nave disminuye cantCombustible unidades.
     *
     * @param cantCombustible La cantidad de combustible a consumir.
     * @throws OperacionRechazadaException Si la cantidad de combustible a consumir es mayor al combustible disponible o si es menor o igual a cero.
     */
    @Override
    public void consumeCombustible(int cantCombustible) throws OperacionRechazadaException {
        int combustibleAnt;
        assert cantCombustible > 0 : "Precondición fallida: cantCombustible debe ser mayor a 0";
        assert cantCombustible <= this.combustible : "Precondición fallida: no hay suficiente combustible para consumir";

        if (cantCombustible <= 0 || cantCombustible > this.combustible)
            throw new OperacionRechazadaException("Combustible insuficiente para el consumo solicitado.");
        else {
            combustibleAnt = this.combustible;
            this.combustible -= cantCombustible;

            assert this.combustible == (combustibleAnt - cantCombustible) : "Postcondición fallida: el combustible no se restó correctamente";
            assert invariante() : "Fallo invariante al consumir combustible";
        }
    }

    /**
     * Consume energía de la nave.
     * <b>Pre:</b> cantEnergia > 0 y cantEnergia <= energia actual.
     * <b>Post:</b> La energía de la nave disminuye cantEnergia unidades.
     *
     * @param cantEnergia La cantidad de energía a consumir.
     * @throws OperacionRechazadaException Si la cantidad de energía a consumir es mayor a la energía disponible o si es menor o igual a cero.
     */
    @Override
    public void consumeEnergia(int cantEnergia) throws OperacionRechazadaException {
        int energiaAnt;
        assert cantEnergia > 0 : "Precondición fallida: cantEnergia debe ser mayor a 0";
        assert cantEnergia <= this.energia : "Precondición fallida: no hay suficiente energía para consumir";

        if (cantEnergia <= 0 || cantEnergia > this.energia)
            throw new OperacionRechazadaException("Energía insuficiente para el consumo solicitado.");
        else {
            energiaAnt = this.energia;
            this.energia -= cantEnergia;

            assert this.energia == (energiaAnt - cantEnergia) : "Postcondición fallida: la energía no se restó correctamente";
            assert invariante() : "Fallo invariante al consumir energía";
        }
    }

    /**
     * Realiza el mantenimiento de la nave.
     * <b>Pre:<b/> El desgaste actual de la nave debe ser >= 80.
     * <b>Post:<b/> El desgaste disminuye a 0 y la necesidad de mantenimiento se vuelve false.
     *
     * @throws OperacionRechazadaException Si la nave no requiere mantenimiento (desgaste menor a 80).
     */
    @Override
    public void realizarMantenimiento() throws OperacionRechazadaException{
        assert this.desgaste >= 80 : "Precondición fallida: el desgaste es menor a 80, no necesita mantenimiento";
        if (this.desgaste < 80)
            throw new OperacionRechazadaException("La nave no requiere mantenimiento. El desgaste actual (" + this.desgaste + ") es menor a 80.");
        else{
            this.desgaste = 0;
            this.necesitaMantenimiento = false;

            assert this.desgaste == 0 : "Postcondición fallida: el desgaste no quedó en 0";
            assert !this.necesitaMantenimiento : "Postcondición fallida: necesitaMantenimiento no cambió a false";
            assert invariante() : "Fallo invariante al realizar mantenimiento";
        }
    }

    /**
     * Incrementa el desgaste de la nave.
     * <b>Pre:</b> cantDesgaste > 0.
     * <b>Post:</b> El desgaste aumenta. Si alcanza o supera 80, necesitaMantenimiento es true. El desgaste tiene un tope máximo de 100 unidades.
     *
     * @param cantDesgaste La cantidad de desgaste a incrementar.
     * @throws OperacionRechazadaException Si la cantidad de desgaste a incrementar es menor o igual a cero.
     */
    @Override
    public void incrementaDesgaste(int cantDesgaste) throws OperacionRechazadaException {
        int desgasteAnt;
        assert cantDesgaste > 0 : "Precondición fallida: el desgaste a incrementar debe ser positivo";

        if (cantDesgaste <= 0)
            throw new OperacionRechazadaException("El desgaste a incrementar debe ser mayor a cero.");
        else {
            desgasteAnt = this.desgaste;

            if(cantDesgaste > 100){
              this.desgaste = 100;
            }
            else {
                this.desgaste += cantDesgaste;
                if (this.desgaste > 100)
                    this.desgaste = 100;
            }
            if(this.desgaste >= 80)
                this.necesitaMantenimiento = true;

            assert this.desgaste >= desgasteAnt : "Postcondición fallida: el desgaste nuevo es menor al anterior";
            assert this.desgaste <= 100 : "Postcondición fallida: el desgaste superó el límite de 100";
            if (this.desgaste >= 80)
                assert this.necesitaMantenimiento : "Postcondición fallida: el desgaste llegó a 80 pero necesitaMantenimiento es false";
            assert invariante() : "Fallo invariante al incrementar el desgaste";
        }
    }

    //Tripulantes

    /**
     * Añade un nuevo tripulante a la nave.
     * <b>Pre:</b> tripulante no debe ser nulo.
     * <b>Post:</b> El tripulante se añade a la lista de tripulantes de la nave.
     *
     * @param tripulante El objeto Tripulante a insertar.
     */
    @Override
    public void addTripulante(Tripulante tripulante) {
        int cantidadAnt;
        assert tripulante != null : "Precondición fallida: el tripulante a agregar es nulo";
        cantidadAnt = this.tripulantes.size();

        this.tripulantes.add(tripulante);

        assert this.tripulantes.size() == (cantidadAnt + 1) : "Postcondición fallida: no se agregó el tripulante a la lista";
        assert invariante() : "Fallo invariante al insertar un tripulante";
    }

    /**
     * Elimina un tripulante de la nave.
     *
     * @param tripulante El objeto Tripulante a eliminar.
     */
    @Override
    public void removeTripulante(Tripulante tripulante) {
        assert tripulante != null : "Precondición fallida: el tripulante a remover es nulo";

        this.tripulantes.remove(tripulante);

        assert invariante() : "Fallo invariante al eliminar un tripulante";
    }

    /**
     * Calcula e imprime los haberes de la tripulación.
     * <b>Pre:</b> La lista de tripulantes debe estar inicializada.
     * <b>Post</b> Se imprime el monto total acumulado.
     */
    @Override
    public void liquidarHaberes() {
        double total = 0;
        for (Tripulante t : this.tripulantes)
            total += t.getSueldo();
        System.out.println("Liquidación total de haberes: $" + total);
    }

    /**
     * Muestra los sueldos individuales de la tripulación.
     * <b>Pre:</b> La lista de tripulantes debe estar inicializada.
     * <b>Post:</b> Se imprime la identidad y el sueldo de cada tripulante.
     */
    @Override
    public void mostrarSueldos() {
        System.out.println("\nSueldos de la Tripulación: ");
        for (Tripulante t : this.tripulantes) {
            double sueldo = t.getSueldo();
            System.out.println("Tripulante " + t.getIdentidad() + ": $" + sueldo);
        }
    }

    //Getters
    @Override
    public int getCombustible() { return this.combustible; }

    @Override
    public int getEnergia() { return this.energia; }

    @Override
    public int getDesgaste() { return this.desgaste; }

    @Override
    public boolean getNecesitaMantenimiento() { return this.necesitaMantenimiento; }

    @Override
    public MotorWarp getMotorWarp() { return this.motorWarp; }

    @Override
    public Asistente getAsistente() { return this.asistente; }

    //Motor

    /**
     * Delega la preparación del salto al motor.
     *
     * @throws TransicionInvalidaException Si el estado del motor no permite preparar un salto.
     */
    @Override
    public void prepararSalto() throws TransicionInvalidaException {
        this.motorWarp.prepararSalto();
    }

    /**
     * Delega la ejecución del salto warp al motor.
     *
     * @throws TransicionInvalidaException Si el motor no está preparado para ejecutar el salto.
     */
    @Override
    public void ejecutarSalto() throws TransicionInvalidaException {
        this.motorWarp.ejecutarSalto();
    }

    @Override
    public void finalizarSalto() throws TransicionInvalidaException{
        this.motorWarp.finalizarSalto();
    }

    /**
     * Delega la finalización del enfriamiento al motor.
     *
     * @throws TransicionInvalidaException Si el motor no se encuentra en estado de enfriamiento.
     */
    @Override
    public void terminarEnfriamiento() throws TransicionInvalidaException {
        this.motorWarp.terminarEnfriamiento();
    }
}