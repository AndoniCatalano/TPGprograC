package paquete;

public class Tanque {
    private int combustible;
    private int energia;

    public Tanque(int combustibleInicial, int energiaInicial) {
        this.combustible = combustibleInicial;
        this.energia = energiaInicial;
    }

    public void cargaCombustible(int cantCombustible) throws OperacionRechazadaException {
        if (cantCombustible <= 0 || (this.combustible + cantCombustible) > 100)
            throw new OperacionRechazadaException("La carga de combustible supera la capacidad máxima (100) o es inválida.");
        else
            this.combustible += cantCombustible;
    }

    public void cargaEnergia(int cantEnergia) throws OperacionRechazadaException {
        if (cantEnergia <= 0 || (this.energia + cantEnergia) > 100)
            throw new OperacionRechazadaException("La carga de energía supera la capacidad máxima (100) o es inválida.");
        else
            this.energia += cantEnergia;
    }

    public void consumeCombustible(int cantCombustible) throws OperacionRechazadaException {
        if (cantCombustible <= 0 || cantCombustible > this.combustible)
            throw new OperacionRechazadaException("Combustible insuficiente para el consumo solicitado.");
        else
            this.combustible -= cantCombustible;
    }

    public void consumeEnergia(int cantEnergia) throws OperacionRechazadaException {
        if (cantEnergia <= 0 || cantEnergia > this.energia)
            throw new OperacionRechazadaException("Energía insuficiente para el consumo solicitado.");
        else
            this.energia -= cantEnergia;
    }

    public int getCombustible() {
        return this.combustible;
    }

    public int getEnergia() {
        return this.energia;
    }
}