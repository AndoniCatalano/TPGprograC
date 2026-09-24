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
        public boolean cargaCombustible(int cantCombustible){
            boolean exito;
            if (cantCombustible <= 0 || (this.combustible + cantCombustible) > 100)
                exito = false;
            else{
                this.combustible += cantCombustible;
                exito = true;
            }
            return exito;
        }

        @Override
        public boolean cargaEnergia(int cantEnergia){
            boolean exito;
            if(cantEnergia <= 0 || (this.energia + cantEnergia) > 100)
                exito = false;
            else{
                this.energia += cantEnergia;
                exito = true;
            }
            return exito;
        }

        @Override
        public boolean realizarMantenimiento(){
            boolean exito;
            if (this.desgaste < 80)
                exito = false;
            else {
                this.desgaste = 0;
                this.necesitaMantenimiento = false;
                exito = true;
            }
            return exito;
        }

        @Override
        public boolean consumeCombustible(int cantCombustible){
            boolean exito;
            if (cantCombustible <= 0 || cantCombustible > this.combustible)
                exito = false;
            else {
                this.combustible -= cantCombustible;
                exito = true;
            }
            return exito;
        }

        @Override
        public boolean consumeEnergia(int cantEnergia){
            boolean exito;
            if (cantEnergia <= 0 || cantEnergia > this.energia)
                exito = false;
            else{
                this.energia -= cantEnergia;
                exito = true;
            }
            return exito;
        }

        @Override
        public boolean incrementaDesgaste(int cantDesgaste){
            boolean exito;
            if(cantDesgaste <= 0)
                exito = false;
            else {
                this.desgaste += cantDesgaste;
                if (this.desgaste >= 80)
                    this.necesitaMantenimiento = true;
                if (this.desgaste > 100)                //valor tope del desgaste
                    this.desgaste = 100;
                exito = true;
            }
            return exito;
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

        @Override
        public void muestraSueldos(){
            double sueldo;
            int n = this.arrayTripulantes.length;
            int i;
            System.out.println("\nSueldos de la Tripulación: ");
            for (i=0; i < n; i++)
                if (this.arrayTripulantes[i] != null){
                    sueldo = this.arrayTripulantes[i].getSueldo();
                    System.out.println("\nTripulante " + this.arrayTripulantes[i].getIdentidad() + ": $" + sueldo);
                }
        }

        @Override
        public void insertaTripulante(Tripulante tripulante){
            int i = 0;
            int n = this.arrayTripulantes.length;
            while (i < n && this.arrayTripulantes[i] != null)
                i++;
            if (i < n)
                this.arrayTripulantes[i] = tripulante;
        }

        @Override
        public void insertaMision(Mision mision){
            int i = 0;
            int n = this.arrayInformes.length;
            while (i < n && this.arrayInformes[i] != null)
                i++;
            if (i < n)
                this.arrayInformes[i] = mision;
        }

        @Override
        public void insertaBitacora(Bitacora bitacora){
            int i = 0;
            int n = this.arrayBitacoras.length;
            while (i < n && this.arrayBitacoras[i] != null)
                i++;
            if (i < n)
                this.arrayBitacoras[i] = bitacora;
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

        @Override
        public Tripulante[] getArrayTripulantes() {
            return this.arrayTripulantes;
        }

        @Override
        public Mision[] getArrayInformes() {
            return this.arrayInformes;
        }

        @Override
        public Bitacora[] getArrayBitacoras() {
                return this.arrayBitacoras;
        }
}

