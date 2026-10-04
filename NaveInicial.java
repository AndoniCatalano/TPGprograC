import Asistente.Asistente;
import Tripulantes.Tripulante;
import java.util.ArrayList;

public abstract class NaveInicial implements Nave {
        protected int desgaste;
        protected boolean necesitaMantenimiento; //depende del desgaste
        protected Motor motorWarp;
        protected Asistente asistente;
        protected ArrayList<Tripulante> tripulantes;
        protected Tanque tanque;

        public NaveInicial(int combustibleInicial, int energiaInicial){ //la diferencia entre los tipos de nave es por sus configs iniciales
            this.tanque = new Tanque(combustibleInicial, energiaInicial);
            this.desgaste = 0;
            this.necesitaMantenimiento = false;
            this.motorWarp = new Motor();
            this.tripulantes = new ArrayList<>();
            this.asistente = new Asistente(this);   //Al asistente se le asigna una sola nave
        }

        //Delego a Tanque el combustible y la energía
        @Override
        public void cargaCombustible(int cantCombustible) throws OperacionRechazadaException {
            this.tanque.cargaCombustible(cantCombustible);
        }

        @Override
        public void cargaEnergia(int cantEnergia) throws OperacionRechazadaException {
            this.tanque.cargaEnergia(cantEnergia);
        }

        @Override
        public void consumeCombustible(int cantCombustible) throws OperacionRechazadaException {
            this.tanque.consumeCombustible(cantCombustible);
        }

        @Override
        public void consumeEnergia(int cantEnergia) throws OperacionRechazadaException {
            this.tanque.consumeEnergia(cantEnergia);
        }

        @Override
        public void realizarMantenimiento() throws OperacionRechazadaException{
            if (this.desgaste < 80)
                throw new OperacionRechazadaException("La nave no requiere mantenimiento. El desgaste actual (" + this.desgaste + ") es menor a 80.");
            else{
                this.desgaste = 0;
                this.necesitaMantenimiento = false;
            }
        }

        @Override
        public void incrementaDesgaste(int cantDesgaste) throws OperacionRechazadaException{
            if (cantDesgaste <= 0)
                throw new OperacionRechazadaException("El desgaste a incrementar debe ser mayor a cero.");
            else {
                this.desgaste += cantDesgaste;
                if (this.desgaste >= 80)
                    this.necesitaMantenimiento = true;
                if (this.desgaste > 100)                //valor tope del desgaste
                    this.desgaste = 100;
            }
        }

        //Tripulantes
        @Override
        public void addTripulante(Tripulante tripulante) {
            this.tripulantes.add(tripulante);
        }

        @Override
        public void removeTripulante(Tripulante tripulante) {
            this.tripulantes.remove(tripulante);
        }

        @Override
        public void liquidarHaberes() {
            double total = 0;
            for (Tripulante t : this.tripulantes)
                total += t.getSueldo();
            System.out.println("Liquidación total de haberes: $" + total);
        }

        //Getters
        @Override
        public int getCombustible() { return this.tanque.getCombustible(); }

        @Override
        public int getEnergia() { return this.tanque.getEnergia(); }

        @Override
        public int getDesgaste() { return this.desgaste; }

        @Override
        public boolean getNecesitaMantenimiento() { return this.necesitaMantenimiento; }

        @Override
        public Motor getMotorWarp() { return this.motorWarp; }

        @Override
        public Tanque getTanque() { return this.tanque; }

        @Override
        public Asistente getAsistente() { return this.asistente; }
}

