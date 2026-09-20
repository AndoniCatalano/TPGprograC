public interface Nave { //contiene los métodos comunes a todas las naves
    public boolean cargaCombustible(int cantCombustible);
    public boolean cargaEnergia(int cantEnergia);
    public boolean realizarMantenimiento();
    public boolean consumeCombustible(int cantCombustible);
    public boolean consumeEnergia(int cantEnergia);
    public boolean incrementaDesgaste(int cantDesgaste);
    public double liquidarHaberes();
    public void muestraSueldos();


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