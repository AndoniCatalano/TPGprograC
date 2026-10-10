import nave.*;
import Tripulantes.*;
import misiones.*;

public class MainA_EjecucionConcreta {

    public static void main(String[] args) throws Exception{
        System.out.println("--- ESCENARIO A: Ejecución Correcta ---");
        // Crear una nave de cada tipo
        NaveFactory factory = new NaveFactory();
        Nave exploradora = (Nave) factory.getNave("exploradora");
        Nave carguero = (Nave) factory.getNave("carguero");
        Nave combate = (Nave) factory.getNave("combate");

        // Seleccionar una y asignarle una tripulación válida
        Tripulante capitan = TripulanteFactory.creaTripulante("CAPITAN", "TERRICOLA", "C-01", 5);
        Tripulante alferez = TripulanteFactory.creaTripulante("ALFEREZ", "MARCIANO", "A-01", 2);
        exploradora.addTripulante(capitan);
        exploradora.addTripulante(alferez);

        //Preparar, ejecutar, evaluar y cerrar M-01, M-02 y M-03
        Mision m01 = new M01();
        Mision m02 = new M02();
        Mision m03 = new M03();

        Sistema sistema = Sistema.getSistema();
        sistema.addNave(exploradora);

        System.out.println("\nEjecutando M-01...");
        sistema.ejecutarMision(exploradora, m01);

        System.out.println("\nEjecutando M-02...");
        sistema.ejecutarMision(exploradora, m02);

        System.out.println("\nEjecutando M-03...");
        sistema.ejecutarMision(exploradora, m03);

        //Mostrar para cada nave los recursos finales, la Bitácora y el informe
        System.out.println("\n--- Recursos Finales ---");
        System.out.println("Combustible: " + exploradora.getCombustible());
        System.out.println("Energía: " + exploradora.getEnergia());
        System.out.println("Desgaste: " + exploradora.getDesgaste());

        System.out.println("\n--- Bitácora Final ---");
        exploradora.getAsistente().mostrarBitacora();
    }
}
