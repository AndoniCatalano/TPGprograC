package nave.vista;

import nave.INave;
import nave.Observer.IObserver;
import nave.IElementosUniverso;

import nave.tablero.Observador;
import nave.tablero.Tablero;
import nave.tablero.Posicion;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class PanelControlNave extends JPanel implements IVistaPanelControlNave, IObserver, Observador {

    // --- Etiquetas de Recursos ---
    private final JLabel lblCombustible;
    private final JLabel lblEnergia;
    private final JLabel lblDesgaste;
    private final JLabel lblMantenimiento;

    // --- Componentes del Radar ---
    private final Tablero tablero;
    private final JPanel panelRadar;

    private Image imgFondo;
    private Image imgNaveJugador;
    private Image imgAsteroide;
    private Image imgEstacion;
    private Image imgNaveEnemiga;
    private Image imgDestino;

    public PanelControlNave(Tablero tablero) {
        this.tablero = tablero;

        if (this.tablero != null) {
            this.tablero.agregarObservador(this);
        }

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
        // Sobrescribimos paintComponent para dibujar la grilla y los elementos
        JPanel panelContenedor = new JPanel(new BorderLayout());
        Border bordeExterior = BorderFactory.createLineBorder(Color.WHITE, 2);
        panelContenedor.setBorder(BorderFactory.createTitledBorder(bordeExterior, "Panel de Operaciones en Vivo"));

        // 2. Tu panelRadar se mantiene igual, pero le QUITAMOS el setBorder
        panelRadar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarUniverso(g);
            }
        };
        // Ya no le ponemos borde al panelRadar, solo el color de fondo y tamaño
        panelRadar.setBackground(Color.BLACK);
        panelRadar.setPreferredSize(new Dimension(1000, 600));

        // 3. Metemos el radar adentro del contenedor
        panelContenedor.add(panelRadar, BorderLayout.CENTER);

        // 4. Agregamos el contenedor al panel principal (en vez del radar suelto)
        this.add(panelRecursos, BorderLayout.NORTH);
        this.add(panelContenedor, BorderLayout.CENTER);
    }

    //Implementación del IObserver de la nave
    @Override
    public void actualizarRecursosNave(INave nave) {
        lblCombustible.setText("Combustible: " + nave.getCombustible());
        lblEnergia.setText("Energía: " + nave.getEnergia());
        lblDesgaste.setText("Desgaste: " + nave.getDesgaste());
        lblMantenimiento.setText("Mantenimiento: " + (nave.getNecesitaMantenimiento() ? "REQUERIDO" : "Óptimo"));
    }

    //Implementación del observador del tablero
    @Override
    public void actualizarTablero() {  // Cuando Destino mueve una pieza, el tablero avisa y forzamos el redibujado visual
        panelRadar.repaint();
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

        // 1. Dibujar imagen de fondo (Cubre todo el panelRadar)
        if (imgFondo != null) {
            g.drawImage(imgFondo, 0, 0, panelRadar.getWidth(), panelRadar.getHeight(), this);
        } else {
            // Fallback: Si falla la imagen, pintamos de negro
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, panelRadar.getWidth(), panelRadar.getHeight());
        }

        IElementosUniverso[][] matriz = tablero.verTablero();
        Posicion posNave = tablero.getPosicionNave();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int px = j * anchoCasilla;
                int py = i * altoCasilla;

                // Dibujar la cuadrícula (Opcional, podrías quitarla si el fondo ya es bueno)
                g.setColor(new Color(84, 191, 240, 30)); // Celeste muy transparente
                g.drawRect(px, py, anchoCasilla, altoCasilla);

                // 2. Extraer el elemento actual
                IElementosUniverso elemento = matriz[i][j];

                if (elemento != null) {
                    if (elemento instanceof nave.Asteroide && imgAsteroide != null) {
                        // Dibujar Asteroide
                        g.drawImage(imgAsteroide, px, py, anchoCasilla, altoCasilla, this);

                    } else if (elemento instanceof nave.EstacionDeCarga && imgEstacion != null) {
                        // Dibujar Estación
                        g.drawImage(imgEstacion, px, py, anchoCasilla, altoCasilla, this);

                    } else if (elemento instanceof nave.INave && imgNaveEnemiga != null) {
                        // Dibujar Naves enemigas
                        g.drawImage(imgNaveEnemiga, px, py, anchoCasilla, altoCasilla, this);

                    } else {
                        // Fallback de seguridad (si falta la imagen, dibuja un círculo naranja)
                        g.setColor(Color.ORANGE);
                        g.fillOval(px + 5, py + 5, anchoCasilla - 10, altoCasilla - 10);
                    }
                }

                // 3. Dibujar TU NAVE (Jugador)
                if (posNave != null && posNave.getX() == i && posNave.getY() == j) {
                    if (imgNaveJugador != null) {
                        g.drawImage(imgNaveJugador, px, py, anchoCasilla, altoCasilla, this);
                    } else {
                        // Fallback de seguridad
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