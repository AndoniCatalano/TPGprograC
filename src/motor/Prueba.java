package motor;

public class Prueba {
    public static void main(String[] args){
        MotorWarp motor = new MotorWarp();
        System.out.println(motor.getEstado());
        motor.iniciarSalto();
        System.out.println(motor.getEstado());
        motor.iniciarSalto();
        motor.finalizarSalto();
        motor.ejecutarSalto();
        System.out.println(motor.getEstado());
        motor.finalizarSalto();
        System.out.println(motor.getEstado());
        motor.terminarEnfriamiento();
        System.out.println(motor.getEstado());


    }
}
