package nave.vista;

import nave.INave;
import nave.Observer.IObserver;
import nave.IElementosUniverso;

import nave.tablero.Observador;
import nave.tablero.Tablero;
import nave.tablero.Posicion;

import javax.swing.*;
import java.awt.*;

public class PanelControlNave extends JPanel implements IVistaPanelControlNave, IObserver, Observador {

    // --- Etiquetas de Recursos ---
    private final JLabel lblCombustible;
    private final JLabel lblEnergia;
    private final JLabel lblDesgaste;
    private final JLabel lblMantenimiento;

    // --- Componentes del Radar ---
    private final Tablero tablero;
    private final JPanel panelRadar;

    public PanelControlNave(Tablero tablero) {
        this.tablero = tablero;

        // 1. Suscripción automática al Tablero de la cátedra
        if (this.tablero != null) {
            this.tablero.agregarObservador(this);
        }

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- PANEL SUPERIOR: Estado de la Nave ---
        JPanel panelRecursos = new JPanel(new GridLayout(1, 4, 10, 0));
        panelRecursos.setBorder(BorderFactory.createTitledBorder("Estado Actual de la Nave"));

        Font fuenteRecursos = new Font("Arial", Font.BOLD, 14);

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
        panelRadar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarUniverso(g);
            }
        };
        panelRadar.setBorder(BorderFactory.createTitledBorder("Radar del Universo (En vivo)"));
        panelRadar.setBackground(Color.BLACK); // Fondo espacial
        panelRadar.setPreferredSize(new Dimension(800, 600));

        this.add(panelRecursos, BorderLayout.NORTH);
        this.add(panelRadar, BorderLayout.CENTER);
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
    // MOTOR DE DIBUJO DEL RADAR
    // =========================================================
    private void dibujarUniverso(Graphics g) {
        if (tablero == null) return;

        int filas = tablero.getX();
        int columnas = tablero.getY();

        int anchoCasilla = panelRadar.getWidth() / columnas;
        int altoCasilla = panelRadar.getHeight() / filas;

        IElementosUniverso[][] matriz = tablero.verTablero();
        Posicion posNave = tablero.getPosicionNave();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int px = j * anchoCasilla;
                int py = i * altoCasilla;

                // 1. Dibujar la cuadrícula de fondo
                g.setColor(Color.DARK_GRAY);
                g.drawRect(px, py, anchoCasilla, altoCasilla);

                // 2. Extraer el elemento actual
                IElementosUniverso elemento = matriz[i][j];

                if (elemento != null) {
                    // Diferenciación visual usando 'instanceof'
                    if (elemento instanceof nave.Asteroide) {
                        // Asteroides: Círculo gris
                        g.setColor(Color.GRAY);
                        g.fillOval(px + 5, py + 5, anchoCasilla - 10, altoCasilla - 10);

                    } else if (elemento instanceof nave.EstacionDeCarga) {
                        // Estación de Carga: Cuadrado verde claro
                        g.setColor(new Color(144, 238, 144)); // Verde claro (LightGreen)
                        g.fillRect(px + 8, py + 8, anchoCasilla - 16, altoCasilla - 16);

                    } else if (elemento instanceof nave.INave) {
                        // Naves enemigas (Combate, Carguero, Exploradora): Triángulo rojo
                        g.setColor(Color.RED);
                        int[] xPoints = {px + anchoCasilla / 2, px + 5, px + anchoCasilla - 5};
                        int[] yPoints = {py + 5, py + altoCasilla - 5, py + altoCasilla - 5};
                        g.fillPolygon(xPoints, yPoints, 3);
                    } else {
                        // Fallback (cualquier otro elemento no clasificado): Círculo naranja
                        g.setColor(Color.ORANGE);
                        g.fillOval(px + 5, py + 5, anchoCasilla - 10, altoCasilla - 10);
                    }
                }

                // 3. Dibujar TU NAVE (Jugador) si la posición coincide
                if (posNave != null && posNave.getX() == i && posNave.getY() == j) {
                    g.setColor(Color.CYAN);
                    // Tu nave se dibuja más grande que las estaciones
                    g.fillRect(px + 2, py + 2, anchoCasilla - 4, altoCasilla - 4);
                }
            }
        }
    }

    @Override
    public JPanel getPanelTablero() {
        return this.panelRadar;
    }
}