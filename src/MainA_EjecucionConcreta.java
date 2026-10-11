import nave.*;
import Tripulantes.*;
import misiones.*;
import asistente.Asistente;

public class MainA_EjecucionConcreta {

    public static void main(String[] args) throws Exception{
        System.out.println("--- ESCENARIO A: Ejecución Correcta ---");

        // Crear una nave de cada tipo
        INave exploradora = NaveFactory.getNave("exploradora");
        INave carguero = NaveFactory.getNave("carguero");
        INave combate = NaveFactory.getNave("combate");

        // Seleccionar una y asignarle una tripulación válida
        System.out.println("Seleccionando nave Exploradora y asignando tripulación mínima válida...");
        exploradora.addTripulante(TripulanteFactory.creaTripulante("CAPITAN", "VULCANO", "C-001", 10));
        exploradora.addTripulante(TripulanteFactory.creaTripulante("CONSEJERO", "TERRICOLA", "CO-001", 4));
        exploradora.addTripulante(TripulanteFactory.creaTripulante("TENIENTE", "MARCIANO", "T-001", 5));
        exploradora.addTripulante(TripulanteFactory.creaTripulante("ALFEREZ", "TERRICOLA", "A-001", 1));
        exploradora.addTripulante(TripulanteFactory.creaTripulante("ALFEREZ", "VULCANO", "A-002", 2));

        Asistente asistente = exploradora.getAsistente();

        //Preparar, ejecutar, evaluar y cerrar M-01, M-02 y M-03
        System.out.println("\n--- Ejecutando Ciclo de Misiones ---");
        asistente.ejecutarMision(new M01());
        asistente.ejecutarMision(new M02());
        asistente.ejecutarMision(new M03());

        //Mostrar para cada nave los recursos finales, la Bitácora y el informe
        System.out.println("\n--- Recursos Finales ---");
        System.out.println("Combustible: " + exploradora.getCombustible());
        System.out.println("Energía: " + exploradora.getEnergia());
        System.out.println("Desgaste: " + exploradora.getDesgaste());

        System.out.println("\n--- Bitácora Final ---");
        asistente.mostrarBitacora();
    }
}
