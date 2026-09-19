public abstract class NaveInicial implements Nave {
        protected int combustible;
        protected int energia;
        protected int desgaste;
        protected boolean necesitaMantenimiento; //depende del desgaste
        protected Motor motorWarp;
        protected Tripulante[] arrayTripulantes;
        protected Mision[] arrayInformes;
        protected Bitacora[] arrayBitacoras;


        public NaveInicial(int combustibleInicial, int energiaInicial, int maxTripulantes, int maxMision){ //la diferencia entre los tipos de nave es por sus configs iniciales
            this.combustible = combustibleInicial;
            this.energia = energiaInicial;
            this.desgaste = 0;
            this.necesitaMantenimiento = false;
            this.motorWarp = new Motor();
            this.arrayTripulantes = new Tripulante[maxTripulantes];
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

        public void consumeCombustible(int cantCombustible){
            if (cantCombustible <= this.combustible)
                this.combustible -= cantCombustible;
            else
                this.combustible = 0;
        }

        public void consumeEnergia(int cantEnergia){
            if (cantEnergia <= this.energia)
                this.energia -= cantEnergia;
            else
                this.energia = 0;
        }

        public void incrementaDesgaste(int cantDesgaste){
            this.desgaste += cantDesgaste;
            if (this.desgaste >= 80)
                this.necesitaMantenimiento = true;
            if (this.desgaste > 100)                //valor tope del desgaste
                this.desgaste = 100;
        }

        @Override
        public double liquidarHaberes(){
            double total = 0;
            int n = this.arrayTripulantes.length;
            int i;
            for (i = 0; i < n; i++)
                if(this.arrayTripulantes[i] != null)
                    total += this.arrayTripulantes[i].getSueldo();
            return total;
        }

        public void insertaTripulante(Tripulante tripulante){
            int i = 0;
            int n = this.arrayTripulantes.length;
            while (i < n && this.arrayTripulantes[i] != null)
                i++;
            if (i < n)
                this.arrayTripulantes[i] = tripulante;
        }

        public void insertaMision(Mision mision){
            int i = 0;
            int n = this.arrayInformes.length;
            while (i < n && this.arrayInformes[i] != null)
                i++;
            if (i < n)
                this.arrayInformes[i] = mision;
        }

        public void insertaBitacora(Bitacora bitacora){
            int i = 0;
            int n = this.arrayBitacoras.length;
            while (i < n && this.arrayBitacoras[i] != null)
                i++;
            if (i < n)
                this.arrayBitacoras[i] = bitacora;
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

        public Tripulante[] getArrayTripulantes() {
            return this.arrayTripulante;
        }

        public Mision[] getArrayInformes() {
            return this.arrayInformes;
        }

        public Bitacora[] getArrayBitacoras() {
                return this.arrayBitacoras;
        }
}

