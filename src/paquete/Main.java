package paquete; // Asegurate de que coincida con tu paquete

public class Main {
    public static void main(String[] args) {
        NaveFactory factoryNaves = new NaveFactory();

        try {
            // Utilizamos INave ya que la fábrica devuelve la interfaz
            INave naveExploradora = factoryNaves.getNave("exploradora");
            INave naveCarguero = factoryNaves.getNave("Carguero");
            INave naveCombate = factoryNaves.getNave("COMbaTe");

            System.out.println("--- ESTADO INICIAL ---");
            System.out.println("Carguero -> Combustible: " + naveCarguero.getCombustible() + " | Energía: " + naveCarguero.getEnergia() + " | Desgaste: " + naveCarguero.getDesgaste());
            System.out.println("Combate -> Combustible: " + naveCombate.getCombustible() + " | Energía: " + naveCombate.getEnergia() + " | Desgaste: " + naveCombate.getDesgaste());
            System.out.println("Exploradora -> Combustible: " + naveExploradora.getCombustible() + " | Energía: " + naveExploradora.getEnergia() + " | Desgaste: " + naveExploradora.getDesgaste());

            System.out.println("\n--- PRUEBA DE FUNCIONAMIENTO ---");

            // Probamos consumir combustible delegándolo al Tanque
            System.out.println("Consumiendo 20 de combustible en el Carguero...");
            naveCarguero.consumeCombustible(20);
            System.out.println("Combustible restante en Carguero: " + naveCarguero.getCombustible());

            // Probamos la lógica de desgaste y necesidad de mantenimiento
            System.out.println("\nIncrementando desgaste de la Exploradora en 85 puntos...");
            naveExploradora.incrementaDesgaste(85);
            System.out.println("Desgaste actual Exploradora: " + naveExploradora.getDesgaste() + " | ¿Necesita mantenimiento?: " + naveExploradora.getNecesitaMantenimiento());

            System.out.println("Realizando mantenimiento a la Exploradora...");
            naveExploradora.realizarMantenimiento();
            System.out.println("Desgaste tras mantenimiento: " + naveExploradora.getDesgaste() + " | ¿Necesita mantenimiento?: " + naveExploradora.getNecesitaMantenimiento());

            // Probamos el manejo de excepciones
            System.out.println("\n--- PRUEBA DE EXCEPCIONES ---");
            System.out.println("Intentando cargar 50 de energía a la nave de Combate (Energía actual: " + naveCombate.getEnergia() + ")...");
            // Esto debería frenar la ejecución y saltar al bloque catch porque el límite es 100
            naveCombate.cargaEnergia(50);

        } catch (TipoNaveInvalidoException e) {
            System.out.println("Error al intentar construir en la fábrica: " + e.getMessage());
        } catch (OperacionRechazadaException e) {
            System.out.println("Operación denegada por el sistema: " + e.getMessage());
        }
    }
}