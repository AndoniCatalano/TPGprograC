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

    /**
     * Constructor del controlador que vincula la vista de control de la nave con el asistente.
     * <b>Pre:</b> asistente no debe ser nulo.
     * <b>Post:</b> Se instancia la vista principal, se registra como observadora del asistente y se vincula este controlador a la vista.
     *
     * @param asistente El asistente encargado de orquestar la nave y las misiones.
     */
    public ControlNaveController(Asistente asistente) {
        assert asistente != null : "Precondición fallida: asistente no puede ser nulo";

        this.vista = new ControlNaveVista();
        this.asistente = asistente;

        // Conectamos la vista principal al modelo
        this.asistente.agregarObservador(this.vista);
        vista.setControlador(this);

        assert invariante() : "Fallo invariante en el constructor de ControlNaveController";
    }

    /**
     * Testea el invariante de clase garantizando que las referencias a la vista y al asistente no sean nulas.
     */
    private boolean invariante() {
        return this.vista != null && this.asistente != null;
    }

    /**
     * Inicia y muestra la ventana principal de control de la nave.
     * <b>Pre:</b> La vista principal debe estar inicializada.
     * <b>Post:</b> La ventana de control de la nave se vuelve visible para el usuario.
     */
    public void iniciarVentana() {
        vista.arrancar();

        assert invariante() : "Fallo invariante en iniciarVentana";
    }

    /**
     * Procesa los eventos de acción disparados por los controles de la vista principal.
     * <b>Pre:</b> El evento e no debe ser nulo.
     * <b>Post:</b> Se ejecuta la acción asociada al comando recibido (iniciar la misión seleccionada o abrir la ventana de concurrencia).
     *
     * @param e El evento de acción generado por un componente de la interfaz.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        assert e != null : "Precondición fallida: el ActionEvent no puede ser nulo";

        switch (e.getActionCommand()) {
            case "INICIAR":
                iniciarMision();
                break;
            case "CONCURRENCIA":
                abrirConcurrencia();
                break;
        }

        assert invariante() : "Fallo invariante en actionPerformed";
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