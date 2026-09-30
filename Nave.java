public interface Nave { //contiene los métodos comunes a todas las naves
    public void cargaCombustible(int cantCombustible) throws OperacionRechazadaException;
    public void cargaEnergia(int cantEnergia) throws OperacionRechazadaException;
    public void realizarMantenimiento() throws OperacionRechazadaException;
    public void consumeCombustible(int cantCombustible) throws OperacionRechazadaException;
    public void consumeEnergia(int cantEnergia) throws OperacionRechazadaException;
    public void incrementaDesgaste(int cantDesgaste) throws OperacionRechazadaException;

    public int getCombustible();
    public int getEnergia();
    public int getDesgaste();
    public boolean getNecesitaMantenimiento();
    public Motor getMotorWarp();
}