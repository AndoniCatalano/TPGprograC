import motor.*;
import nave.*;

public class MainC_MotorWarp {
    public static void main(String[] args) throws Exception{
        System.out.println("--- ESCENARIO C: Motor Warp ---");

        INave exploradora = NaveFactory.getNave("exploradora");
        MotorWarp motor = exploradora.getMotorWarp();

        //Recorrer secuencia válida (por ahora no existe el paso del tiempo)
        System.out.println("Estado inicial: " + motor.getEstado());
        exploradora.prepararSalto();
        System.out.println("Tras preparar: " + motor.getEstado());
        exploradora.ejecutarSalto();
        System.out.println("Tras ejecutar: " + motor.getEstado());
        exploradora.finalizarSalto();
        System.out.println("Final: " + motor.getEstado());

        //Intentar transición inválida y verificar rechazo
        try{
            System.out.println("\nIntentando ejecutar salto desde estado Disponible...");
            exploradora.ejecutarSalto();
        }
        catch (TransicionInvalidaException e) {
            System.out.println("Rechazo registrado con éxito: " + e.getMessage());
        }
    }
}