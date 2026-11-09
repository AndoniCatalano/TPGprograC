package asistente.controlador;

import asistente.Asistente;
import misiones.*;
import asistente.vista.ControlNaveVista;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlNaveController {

    private ControlNaveVista vista;
    private Asistente asistente;

    public ControlNaveController(ControlNaveVista vista, Asistente asistente) {
        this.vista = vista;
        this.asistente = asistente;

        // CRÍTICO: Conectamos la vista al modelo para que reciba las actualizaciones automáticas
        this.asistente.agregarObservador(this.vista);

        this.vista.getBtnIniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarMision();
            }
        });
    }

    private void iniciarMision() {
        String seleccion = vista.getMisionSeleccionada();
        vista.getBtnIniciar().setEnabled(false);

        // CRÍTICO: Hilo separado para no congelar la interfaz gráfica (Swing)
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
                vista.onEventoBitacora("Error crítico: " + ex.getMessage());
            } finally {
                SwingUtilities.invokeLater(() -> vista.getBtnIniciar().setEnabled(true));
            }
        }).start();
    }
}