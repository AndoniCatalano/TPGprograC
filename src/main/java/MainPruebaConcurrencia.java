//import Concurrencia.Controlador.ConcurrenciaControlador;
//import Concurrencia.Modelo.UniversoConcurrente;
//import Concurrencia.Vista.VisorConcurrencia;
//
//import javax.swing.*;
//
//public class MainPruebaConcurrencia {
//    public static void main(String[] args) {
//        UniversoConcurrente universoConcurrente = new UniversoConcurrente(3);
//
//        VisorConcurrencia panelVisor = new VisorConcurrencia();
//        universoConcurrente.getPlataforma().agregarObservador(panelVisor);
//
//        new ConcurrenciaControlador(universoConcurrente, panelVisor);
//
//        SwingUtilities.invokeLater(() -> {
//            JFrame ventana = new JFrame("Visor del Universo Paralelo");
//            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            ventana.setContentPane(panelVisor);
//            ventana.pack();
//            ventana.setLocationRelativeTo(null);
//            ventana.setVisible(true);
//        });
//    }
//}