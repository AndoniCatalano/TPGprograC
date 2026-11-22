package asistente.controlador;

import Concurrencia.Controlador.ConcurrenciaControlador;
import asistente.Asistente;
import asistente.vista.ControlNaveVista;
import misiones.*;
import nave.tablero.Tablero;
import nave.vista.PanelControlNave;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlNaveController implements ActionListener {

    private final ControlNaveVista vista;
    private final Asistente asistente;
    private ConcurrenciaControlador controladorConcurrencia;

    private JFrame ventanaTablero;
    private Tablero tableroActual;

    public ControlNaveController(Asistente asistente) {
        this.vista = new ControlNaveVista();
        this.asistente = asistente;

        // Conectamos la vista principal al modelo
        this.asistente.agregarObservador(this.vista);
        vista.setControlador(this);
    }

    public void iniciarVentana() {
        vista.arrancar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "INICIAR":
                iniciarMision();
                break;
            case "CONCURRENCIA":
                abrirConcurrencia();
                break;
        }
    }

    private void abrirConcurrencia() {
        if (controladorConcurrencia == null) {
            controladorConcurrencia = new ConcurrenciaControlador();
        }
        controladorConcurrencia.iniciar();
    }

    private JFrame abrirVentanaTablero() {
        Tablero tablero = asistente.getTablero();

        // Creamos la ventana del tablero la primera vez (o si el asistente cambió de tablero)
        if (ventanaTablero == null || tableroActual != tablero) {
            tableroActual = tablero;
            PanelControlNave panelTablero = new PanelControlNave(asistente, tablero);

            ventanaTablero = new JFrame("Radar del Universo — Recorrido de la Misión");
            // DISPOSE_ON_CLOSE cierra solo el tablero y deja abierto ControlNaveVista
            ventanaTablero.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventanaTablero.setContentPane(panelTablero);
            ventanaTablero.pack();
            ventanaTablero.setLocationRelativeTo(vista);
        }

        ventanaTablero.setVisible(true);
        ventanaTablero.toFront();

        return ventanaTablero;
    }

    private void iniciarMision() {
        String seleccion = vista.getMisionSeleccionada();
        vista.deshabilitarBoton();

        // Abrimos la vista del tablero antes de lanzar el hilo de la misión
        ventanaTablero = abrirVentanaTablero();

        // Hilo separado para no congelar la interfaz gráfica (Swing)
        new Thread(() -> {
            try {
                Mision mision;
                if (seleccion.contains("M-01")) {
                    mision = new M01();
                } else if (seleccion.contains("M-02")) {
                    mision = new M02();
                } else {
                    mision = new M03();
                }

                asistente.ejecutarMision(mision);
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                        vista.onEventoBitacora("Error crítico: " + ex.getMessage()));
            } finally {
                SwingUtilities.invokeLater(() -> {
                    if (ventanaTablero != null) {
                        ventanaTablero.dispose();
                    }
                    vista.habilitarBoton();
                    vista.toFront();
                });
            }
        }).start();
    }
}