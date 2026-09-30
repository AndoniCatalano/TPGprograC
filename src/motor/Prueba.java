package motor;

public class Prueba {
    public static void main(String[] args){
        MotorWarp motor = new MotorWarp();
        System.out.println(motor.getEstado());

        // Prueba 1: Transición válida
        try {
            motor.iniciarSalto();
            System.out.println("Estado tras iniciar: " + motor.getEstado());
        } catch (TransicionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        // Prueba 2: Transición inválida (intentar iniciar de nuevo)
        try {
            motor.iniciarSalto();
        } catch (TransicionInvalidaException e) {
            System.out.println("Fallo esperado: " + e.getMessage());
        }

        // Prueba 3: Transición inválida (finalizar antes de ejecutar)
        try {
            motor.finalizarSalto();
        } catch (TransicionInvalidaException e) {
            System.out.println("Fallo esperado: " + e.getMessage());
        }

        // Prueba 4: Transición válida
        try {
            motor.ejecutarSalto();
            System.out.println("Estado tras ejecutar: " + motor.getEstado());
        } catch (TransicionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        // Prueba 5: Transiciones finales
        try {
            motor.finalizarSalto();
            System.out.println("Estado tras finalizar: " + motor.getEstado());

            motor.terminarEnfriamiento();
            System.out.println("Estado final: " + motor.getEstado());
        } catch (TransicionInvalidaException e) {
            System.out.println(e.getMessage());
        }


    }
}
