public abstract class NaveInicial implements Nave {
        protected int combustible;
        protected int energia;
        protected int desgaste;
        protected boolean necesitaMantenimiento; //depende del desgaste
        protected Motor motorWarp;
        protected Tripulacion[] arrayTripulacion;
        protected Mision[] arrayInformes;


        public NaveInicial(int combustibleInicial, int energiaInicial, int maxTripulacion, int maxMision){ //la diferencia entre los tipos de nave es por sus configs iniciales
            this.combustible = combustibleInicial;
            this.energia = energiaInicial;
            this.desgaste = 0;
            this.necesitaMantenimiento = false;
            this.motorWarp = new Motor();
            this.arrayTripulacion = new Tripulacion[maxTripulacion];
            this.arrayInformes = new Mision[maxMision];
        }

        @Override
        public void cargaCombustible(int combustible){
            this.combustible += combustible;
        }

        @Override
        public void cargaEnergia(int energia){
            this.energia += energia;
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

        public Motor getMotorWarp() {
            return this.motorWarp;
        }

        public Tripulacion[] getArrayTripulacion() {
            return this.arrayTripulacion;
        }

        public Mision[] getArrayInformes() {
            return this.arrayInformes;
        }
}

