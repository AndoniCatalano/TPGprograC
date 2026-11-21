package nave.vista;

import asistente.Asistente;
import asistente.vista.AsistenteObserver;
import nave.IElementosUniverso;
import nave.tablero.Observador;
import nave.tablero.Posicion;
import nave.tablero.Tablero;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PanelControlNave extends JPanel implements IVistaPanelControlNave, AsistenteObserver, Observador {

    // --- Etiquetas de Recursos ---
    private final JLabel lblCombustible;
    private final JLabel lblEnergia;
    private final JLabel lblDesgaste;
    private final JLabel lblMantenimiento;

    // --- Referencias al Modelo ---
    private final Asistente asistente;
    private final Tablero tablero;
    private final JPanel panelRadar;

    private Image imgFondo;
    private Image imgNaveJugador;
    private Image imgAsteroide;
    private Image imgEstacion;
    private Image imgNaveEnemiga;
    private Image imgDestino;

    public PanelControlNave(Asistente asistente, Tablero tablero) {
        this.asistente = asistente;
        this.tablero = tablero;

        this.setLayout(new BorderLayout(30, 30));
        this.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        try {
            imgFondo = new ImageIcon("RecursosTablero/fondo.png").getImage();
            imgNaveJugador = new ImageIcon("RecursosTablero/nave.png").getImage();
            imgAsteroide = new ImageIcon("RecursosTablero/asteroides.png").getImage();
            imgEstacion = new ImageIcon("RecursosTablero/estaciondecarga.png").getImage();
            imgNaveEnemiga = new ImageIcon("RecursosTablero/naveenemiga.png").getImage();
            imgDestino = new ImageIcon("RecursosTablero/destino.png").getImage();
        } catch (Exception e) {
            System.err.println("Error al cargar las imágenes: " + e.getMessage());
        }

        // --- PANEL SUPERIOR: Estado de la Nave ---
        JPanel panelRecursos = new JPanel(new GridLayout(1, 4, 10, 0));
        panelRecursos.setBorder(BorderFactory.createTitledBorder("Estado Actual de la Nave"));

        Font fuenteRecursos = new Font("Arial", Font.BOLD, 20);

        lblCombustible = new JLabel("Combustible: --");
        lblEnergia = new JLabel("Energía: --");
        lblDesgaste = new JLabel("Desgaste: --");
        lblMantenimiento = new JLabel("Mantenimiento: --");

        lblCombustible.setFont(fuenteRecursos);
        lblEnergia.setFont(fuenteRecursos);
        lblDesgaste.setFont(fuenteRecursos);
        lblMantenimiento.setFont(fuenteRecursos);

        panelRecursos.add(lblCombustible);
        panelRecursos.add(lblEnergia);
        panelRecursos.add(lblDesgaste);
        panelRecursos.add(lblMantenimiento);

        // --- PANEL CENTRAL: Radar Dinámico ---
        JPanel panelContenedor = new JPanel(new BorderLayout());
        Border bordeExterior = BorderFactory.createLineBorder(Color.WHITE, 2);
        panelContenedor.setBorder(BorderFactory.createTitledBorder(bordeExterior, "Panel de Operaciones en Vivo"));

        panelRadar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarUniverso(g);
            }
        };
        panelRadar.setBackground(Color.BLACK);
        panelRadar.setPreferredSize(new Dimension(1000, 600));

        panelContenedor.add(panelRadar, BorderLayout.CENTER);

        this.add(panelRecursos, BorderLayout.NORTH);
        this.add(panelContenedor, BorderLayout.CENTER);

        // Suscripciones al final del constructor (una vez creados los JLabels)
        if (this.asistente != null) {
            this.asistente.agregarObservador(this);
        }
        if (this.tablero != null) {
            this.tablero.agregarObservador(this);
        }
    }

    // --- IMPLEMENTACIÓN DE AsistenteObserver ---
    @Override
    public void onRecursosActualizados(int combustible, int energia, int desgaste) {
        SwingUtilities.invokeLater(() -> {
            lblCombustible.setText("Combustible: " + combustible);
            lblEnergia.setText("Energía: " + energia);
            lblDesgaste.setText("Desgaste: " + desgaste);
            lblMantenimiento.setText("Mantenimiento: " + (desgaste >= 80 ? "REQUERIDO" : "Óptimo"));
        });
    }

    @Override
    public void onEventoBitacora(String mensaje) {
        // Esta vista no muestra bitácora (la gestiona ControlNaveVista)
    }

    @Override
    public void onInformeGenerado(String informe) {
        // Esta vista no muestra informes (los gestiona ControlNaveVista)
    }

    // --- IMPLEMENTACIÓN DEL Observador DEL TABLERO ---
    @Override
    public void actualizarTablero() {
        SwingUtilities.invokeLater(() -> panelRadar.repaint());
    }

    // =========================================================
    // MOTOR DE DIBUJO DEL RADAR (Versión con Imágenes)
    // =========================================================
    private void dibujarUniverso(Graphics g) {
        if (tablero == null) return;

        int filas = tablero.getX();
        int columnas = tablero.getY();

        int anchoCasilla = panelRadar.getWidth() / columnas;
        int altoCasilla = panelRadar.getHeight() / filas;

        if (imgFondo != null) {
            g.drawImage(imgFondo, 0, 0, panelRadar.getWidth(), panelRadar.getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, panelRadar.getWidth(), panelRadar.getHeight());
        }

        IElementosUniverso[][] matriz = tablero.verTablero();
        Posicion posNave = tablero.getPosicionNave();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int px = j * anchoCasilla;
                int py = i * altoCasilla;

                g.setColor(new Color(84, 191, 240, 30));
                g.drawRect(px, py, anchoCasilla, altoCasilla);

                IElementosUniverso elemento = matriz[i][j];

                if (elemento != null) {
                    if (elemento instanceof nave.Asteroide && imgAsteroide != null) {
                        g.drawImage(imgAsteroide, px, py, anchoCasilla, altoCasilla, this);
                    } else if (elemento instanceof nave.EstacionDeCarga && imgEstacion != null) {
                        g.drawImage(imgEstacion, px, py, anchoCasilla, altoCasilla, this);
                    } else if (elemento instanceof nave.INave && imgNaveEnemiga != null) {
                        g.drawImage(imgNaveEnemiga, px, py, anchoCasilla, altoCasilla, this);
                    } else {
                        g.setColor(Color.ORANGE);
                        g.fillOval(px + 5, py + 5, anchoCasilla - 10, altoCasilla - 10);
                    }
                }

                if (posNave != null && posNave.getX() == i && posNave.getY() == j) {
                    if (imgNaveJugador != null) {
                        g.drawImage(imgNaveJugador, px, py, anchoCasilla, altoCasilla, this);
                    } else {
                        g.setColor(Color.CYAN);
                        g.fillRect(px + 5, py + 5, anchoCasilla - 10, altoCasilla - 10);
                    }
                }
            }
        }
    }

    @Override
    public JPanel getPanelTablero() {
        return this.panelRadar;
    }
}