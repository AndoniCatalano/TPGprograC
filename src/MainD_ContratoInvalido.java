import nave.*;

public class MainD_ContratoInvalido{
    public static void main(String[] args) throws Exception{
        System.out.println("--- ESCENARIO D: Contrato Inválido ---");

        INave combate = NaveFactory.getNave("combate");

        int energiaInicial = combate.getEnergia();
        System.out.println("Energía inicial: " + energiaInicial);

        // Aislamos el intento de carga en su propio try-catch
        try {
            System.out.println("\nIntentando cargar 50 unidades de energía (el máximo es 100)...");
            combate.cargaEnergia(50);
        } catch (OperacionRechazadaException e) {
            // Atrapamos el error para que el programa no se corte
            System.out.println("Operación denegada: " + e.getMessage());
        }

        // DEMOSTRACIÓN: Verificamos el estado después del fallo
        int energiaFinal = combate.getEnergia();
        System.out.println("\nEnergía final tras el rechazo: " + energiaFinal);

        if (energiaInicial == energiaFinal) {
            System.out.println("¡Comprobado! El estado anterior se conservó perfectamente y no quedó en 150.");
        }
    }
}