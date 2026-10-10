import nave.*;
import misiones.*;

public class MainB_RecursosInsuficientes{

    public static void main(String[] args) throws Exception {
        System.out.println("--- ESCENARIO B: Recursos Insuficientes ---");
        NaveFactory factory = new NaveFactory();
        Nave carguero = (Nave) factory.getNave("carguero"); // Inicia con 100 de combustible y 0 desgaste
        // Consumimos el combustible para que no pueda afrontar los 4 que exige la misión
        carguero.consumeCombustible(98);
        System.out.println("Combustible actual: " + carguero.getCombustible());

        //Intentar prepararla/ejecutarla y verificar rechazo y registro del motivo
        Mision m01 = new M01();
        carguero.getAsistente().ejecutarMision(m01);

        System.out.println("\n--- Bitácora del Rechazo ---");
        carguero.getAsistente().mostrarBitacora();
    }
}
