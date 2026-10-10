import motor.*;
import nave.*;

public class MainC_MotorWarp {
    public static void main(String[] args) throws Exception{
        System.out.println("--- ESCENARIO C: Motor Warp ---");

        NaveFactory factory = new NaveFactory();
        Nave exploradora = (Nave) factory.getNave("exploradora");
        MotorWarp motor = exploradora.getMotorWarp();

        //Recorrer secuencia válida (por ahora no existe el paso del tiempo)
        System.out.println("Estado inicial: " + motor.getEstado());
        motor.prepararSalto();
        System.out.println("Tras preparar: " + motor.getEstado());
        motor.ejecutarSalto();
        System.out.println("Tras ejecutar: " + motor.getEstado());
        motor.finalizarSalto();
        System.out.println("Enfriando: " + motor.getEstado());
        motor.terminarEnfriamiento();
        System.out.println("Final: " + motor.getEstado());

        //Intentar transición inválida y verificar rechazo
        try{
            System.out.println("\nIntentando ejecutar salto desde estado Disponible...");
            motor.ejecutarSalto();
        }
        catch (TransicionInvalidaException e) {
            System.out.println("Rechazo registrado con éxito: " + e.getMessage());
        }
    }
}