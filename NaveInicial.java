public abstract class NaveInicial implements Nave {
        protected int combustible;
        protected int energia;
        protected int desgaste;
        protected boolean necesitaMantenimiento; //depende del desgaste
        protected Motor motorWarp;


        public NaveInicial(int combustibleInicial, int energiaInicial){ //la diferencia entre los tipos de nave es por sus configs iniciales
            this.combustible = combustibleInicial;
            this.energia = energiaInicial;
            this.desgaste = 0;
            this.necesitaMantenimiento = false;
            this.motorWarp = new Motor();
        }

        @Override
        public void cargaCombustible(int cantCombustible) throws OperacionRechazadaException{
            if (cantCombustible <= 0 || (this.combustible + cantCombustible) > 100)
                throw new OperacionRechazadaException("La carga de combustible supera la capacidad máxima (100) o es inválida.");
            else
                this.combustible += cantCombustible;
        }

        @Override
        public void cargaEnergia(int cantEnergia) throws OperacionRechazadaException{
            if (cantEnergia <= 0 || (this.energia + cantEnergia) > 100)
                throw new OperacionRechazadaException("La carga de energía supera la capacidad máxima (100) o es inválida.");
            else
                this.energia += cantEnergia;
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
        public void consumeCombustible(int cantCombustible) throws OperacionRechazadaException{
            if (cantCombustible <= 0 || cantCombustible > this.combustible)
                throw new OperacionRechazadaException("Combustible insuficiente para el consumo solicitado.");
            else
                this.combustible -= cantCombustible;
        }

        @Override
        public void consumeEnergia(int cantEnergia) throws OperacionRechazadaException{
            if (cantEnergia <= 0 || cantEnergia > this.energia)
                throw new OperacionRechazadaException("Energía insuficiente para el consumo solicitado.");
            else
                this.energia -= cantEnergia;
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

        //getters

        @Override
        public int getCombustible(){
            return this.combustible;
        }

        @Override
        public int getEnergia(){
            return this.energia;
        }

        @Override
        public int getDesgaste(){
            return this.desgaste;
        }

        @Override
        public boolean getNecesitaMantenimiento() {
            return this.necesitaMantenimiento;
        }

        @Override
        public Motor getMotorWarp() {
            return this.motorWarp;
        }
}

