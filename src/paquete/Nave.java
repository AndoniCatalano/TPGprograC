package paquete;

public interface Nave { //contiene los métodos comunes a todas las naves
    public void cargaCombustible(int combustible);
    public void cargaEnergia(int energia);
    public void realizarMantenimiento();

    public int getCombustible();
    public int getEnergia();
    public int getDesgaste();
    public boolean getNecesitaMantenimiento();
}