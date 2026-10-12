import nave.*;
import misiones.*;
import asistente.Asistente;

public class MainB_RecursosInsuficientes{

    public static void main(String[] args) throws Exception {
        System.out.println("--- ESCENARIO B: Recursos Insuficientes ---");
        INave carguero = NaveFactory.getNave("carguero"); // Inicia con 100 de combustible y 0 desgaste

        // Consumimos el combustible para que no pueda afrontar los 4 que exige la misión
        carguero.consumeCombustible(98);
        System.out.println("Combustible actual: " + carguero.getCombustible());

        Asistente asistente = carguero.getAsistente();

        //Intentar prepararla/ejecutarla y verificar rechazo y registro del motivo
        System.out.println("Intentando ejecutar Mision01...");
        Mision m01 = new M01();
        asistente.ejecutarMision(m01);

        System.out.println("\n--- Bitácora del Rechazo ---");
        carguero.getAsistente().mostrarBitacora();

        System.out.println("\nVerificación: El combustible sigue en " + carguero.getCombustible() + " (No hubo cambios parciales).");
    }
}
