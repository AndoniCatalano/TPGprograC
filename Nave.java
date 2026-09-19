public interface Nave { //contiene los métodos comunes a todas las naves
    public void cargaCombustible(int combustible);
    public void cargaEnergia(int energia);
    public void realizarMantenimiento();
    public void consumeCombustible(int cantCombustible);
    public void consumeEnergia(int cantEnergia);
    public void incrementaDesgaste(int cantDesgaste);
    public double liquidarHaberes();


    public void insertaTripulante(Tripulante tripulante);
    public void insertaMision(Mision mision);
    public void insertaBitacora(Bitacora bitacora);

    public int getCombustible();
    public int getEnergia();
    public int getDesgaste();
    public boolean getNecesitaMantenimiento();
    public Motor getMotorWarp();
    public Tripulante[] getArrayTripulantes();
    public Mision[] getArrayInformes();
    public Bitacora[] getArrayBitacoras();

}