package paquete;

public abstract class NaveInicial implements Nave {
        protected int combustible;
        protected int energia;
        protected int desgaste;
        protected boolean necesitaMantenimiento; //depende del desgaste

        public NaveInicial(int combustibleInicial, int energiaInicial){ //la diferencia entre los tipos es por sus configs iniciales
            this.combustible = combustibleInicial;
            this.energia = energiaInicial;
            this.desgaste = 0;
            this.necesitaMantenimiento = false;
        }

        @Override
        public void cargaCombustible(int combustible){

        }

        @Override
        public void cargaEnergia(int energia){

        }

        @Override
        public void realizarMantenimiento(){
            this.desgaste = 0;
            this.necesitaMantenimiento = false;
        }

        //getters

        public int getCombustible(){
            return this.combustible;
        }

        public int getEnergia(){
            return this.energia;
        }

        public int getDesgaste(){
            return this.desgaste;
        }

        public boolean getNecesitaMantenimiento() {
            return this.necesitaMantenimiento;
        }
}
