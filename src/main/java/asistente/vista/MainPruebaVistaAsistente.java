package asistente.vista;

import asistente.Asistente;
import asistente.controlador.ControlNaveController;
import nave.Exploradora;
import nave.INave;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainPruebaVistaAsistente {
    public static void main(String[] args) {

        // Las interfaces gráficas en Java siempre deben arrancarse en su propio hilo de eventos
        SwingUtilities.invokeLater(() -> {

            // 1. Inicializamos el Modelo (Nave y Asistente)
            // Asumo que tu Exploradora recibe (combustible, energia) en el constructor
            INave nave = new Exploradora();
            Asistente asistente = new Asistente(nave);

            // 2. Inicializamos la Vista
            ControlNaveVista vista = new ControlNaveVista();

            // 3. Inicializamos el Controlador (esto conecta automáticamente a la vista como Observer)
            ControlNaveController controlador = new ControlNaveController(vista, asistente);

            // 4. Creamos la ventana principal para mostrar tu JPanel
            JFrame frame = new JFrame("Panel de Control - Nave Interestelar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra el programa al darle a la X
            frame.setContentPane(vista); // Cargamos tu vista adentro de la ventana
            frame.setSize(800, 600); // Tamaño inicial de la ventana
            frame.setLocationRelativeTo(null); // Esto centra la ventana en la pantalla
            frame.setVisible(true); // La mostramos

        });
    }
}