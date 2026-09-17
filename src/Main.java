import Tripulantes.Alferez;
import Tripulantes.Capitan;
import Tripulantes.Consejero;
import Tripulantes.Marciano;
import Tripulantes.Teniente;
import Tripulantes.Terricola;
import Tripulantes.Tripulante;
import Tripulantes.Vulcano;

public class Main {
    public static void main(String[] args) {
        Tripulante alferez = new Alferez("A-001", 5);
        verificarSueldo("Alferez terricola", new Terricola(alferez), 225.0);

        Tripulante teniente = new Teniente("T-001", 10);
        verificarSueldo("Teniente marciano", new Marciano(teniente), 538.0);

        Tripulante capitan = new Capitan("C-001", 3);
        verificarSueldo("Capitan vulcano", new Vulcano(capitan), 1630.0);

        Consejero consejero = new Consejero("CO-001", 4);
        consejero.addConsejo("Consejo 1");
        consejero.addConsejo("Consejo 2");
        verificarSueldo("Consejero terricola", new Terricola(consejero), 744.0);

        System.out.println("Todos los tests de sueldos pasaron correctamente.");
    }

    private static void verificarSueldo(String nombre, Tripulante tripulante, double esperado) {
        double obtenido = tripulante.getSueldo();

        if (Double.compare(obtenido, esperado) != 0) {
            throw new AssertionError(
                    nombre + ": se esperaba " + esperado + ", pero se obtuvo " + obtenido
            );
        }

        System.out.printf("%-35s sueldo = %.2f%n", nombre, obtenido);
    }
}
