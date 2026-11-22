package nave.vista;

import asistente.Asistente;
import asistente.vista.AsistenteObserver;
import nave.IElementosUniverso;
import nave.tablero.Observador;
import nave.tablero.Posicion;
import nave.tablero.Tablero;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PanelControlNave extends JPanel implements IVistaPanelControlNave, AsistenteObserver, Observador {

    // Paleta espacial oscura (unificada con VisorConcurrencia)
    private static final Color COLOR_FONDO = new Color(12, 10, 28);
    private static final Color COLOR_PANEL = new Color(22, 18, 48);
    private static final Color COLOR_ESPACIO = new Color(9, 7, 24);
    private static final Color COLOR_BORDE = new Color(145, 95, 235);
    private static final Color COLOR_TITULO = new Color(200, 165, 255);
    private static final Color COLOR_TEXTO = new Color(235, 238, 248);

    // Tamaño dinámico de casilla (en píxeles) para soportar de 5x5 hasta 100x100
    private static final int TAM_CASILLA_MIN = 30;
    private static final int TAM_CASILLA_MAX = 64;
    private static final int VIEWPORT_ANCHO = 960;
    private static final int VIEWPORT_ALTO = 560;

    // --- Etiquetas de Recursos ---
    private final JLabel lblCombustible;
    private final JLabel lblEnergia;
    private final JLabel lblDesgaste;
    private final JLabel lblMantenimiento;

    // --- Referencias al Modelo ---
    private final Asistente asistente;
    private final Tablero tablero;
    private final JPanel panelRadar;
    private final JScrollPane scrollRadar;

    private int tamCasillaActual = TAM_CASILLA_MIN;

    private Image imgFondo;
    private Image imgNaveJugador;
    private Image imgAsteroide;
    private Image imgEstacion;
    private Image imgNaveEnemiga;
    private Image imgDestino;

    public PanelControlNave(Asistente asistente, Tablero tablero) {
        this.asistente = asistente;
        this.tablero = tablero;

        this.setLayout(new BorderLayout(16, 16));
        this.setBackground(COLOR_FONDO);
        this.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        cargarImagenes();

        // --- PANEL SUPERIOR: Estado de la Nave ---
        JPanel panelRecursos = new JPanel(new GridLayout(1, 4, 12, 0));
        panelRecursos.setBackground(COLOR_PANEL);
        panelRecursos.setBorder(crearBordeConTitulo("Estado Actual de la Nave"));

        Font fuenteRecursos = new Font("SansSerif", Font.BOLD, 17);

        lblCombustible = crearEtiquetaRecurso("Combustible: --", fuenteRecursos, new Color(100, 235, 150));
        lblEnergia = crearEtiquetaRecurso("Energía: --", fuenteRecursos, new Color(90, 210, 255));
        lblDesgaste = crearEtiquetaRecurso("Desgaste: --", fuenteRecursos, new Color(255, 200, 80));
        lblMantenimiento = crearEtiquetaRecurso("Mantenimiento: --", fuenteRecursos, COLOR_TEXTO);

        panelRecursos.add(lblCombustible);
        panelRecursos.add(lblEnergia);
        panelRecursos.add(lblDesgaste);
        panelRecursos.add(lblMantenimiento);

        // --- PANEL CENTRAL: Radar Dinámico con Scroll ---
        this.tamCasillaActual = calcularTamanoCasilla();

        panelRadar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarUniverso((Graphics2D) g);
            }
        };
        panelRadar.setBackground(COLOR_ESPACIO);
        actualizarDimensionesRadar();

        scrollRadar = new JScrollPane(panelRadar);
        scrollRadar.setPreferredSize(new Dimension(VIEWPORT_ANCHO, VIEWPORT_ALTO));
        scrollRadar.getViewport().setBackground(COLOR_ESPACIO);
        scrollRadar.setBackground(COLOR_PANEL);
        scrollRadar.getVerticalScrollBar().setUnitIncrement(24);
        scrollRadar.getHorizontalScrollBar().setUnitIncrement(24);
        scrollRadar.setBorder(crearBordeConTitulo(obtenerTituloRadar()));

        this.add(panelRecursos, BorderLayout.NORTH);
        this.add(scrollRadar, BorderLayout.CENTER);

        // Suscripciones al final del constructor
        if (this.asistente != null) {
            this.asistente.agregarObservador(this);
        }
        if (this.tablero != null) {
            this.tablero.agregarObservador(this);
        }

        // Centramos la cámara inicialmente sobre la nave
        SwingUtilities.invokeLater(this::centrarCamaraEnNave);
    }

    private void cargarImagenes() {
        imgFondo = cargarImagen("fondo.png");
        imgNaveJugador = cargarImagen("nave.png");
        imgAsteroide = cargarImagen("asteroides.png");
        imgEstacion = cargarImagen("estaciondecarga.png");
        imgNaveEnemiga = cargarImagen("naveenemiga.png");
        imgDestino = cargarImagen("destino.png");
    }

    private Image cargarImagen(String nombreArchivo) {
        // 1. Buscar en el classpath de Maven (src/main/resources)
        String[] rutasClasspath = {
                "/RecursosTablero/" + nombreArchivo,
                "/recursos/" + nombreArchivo,
                "/" + nombreArchivo
        };
        for (String ruta : rutasClasspath) {
            java.net.URL url = getClass().getResource(ruta);
            if (url != null) {
                return new ImageIcon(url).getImage();
            }
        }

        // 2. Buscar como ruta relativa en el proyecto
        String[] rutasArchivo = {
                "RecursosTablero/" + nombreArchivo,
                "src/main/resources/RecursosTablero/" + nombreArchivo,
                "recursos/" + nombreArchivo
        };
        for (String ruta : rutasArchivo) {
            java.io.File archivo = new java.io.File(ruta);
            if (archivo.exists()) {
                return new ImageIcon(archivo.getAbsolutePath()).getImage();
            }
        }

        System.err.println("No se encontró la imagen: " + nombreArchivo);
        return null;
    }

    private boolean imagenLista(Image img) {
        return img != null && img.getWidth(null) > 0 && img.getHeight(null) > 0;
    }

    private JLabel crearEtiquetaRecurso(String texto, Font fuente, Color color) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(fuente);
        lbl.setForeground(color);
        return lbl;
    }

    private Border crearBordeConTitulo(String titulo) {
        Border linea = BorderFactory.createLineBorder(COLOR_BORDE, 1);
        TitledBorder bordeConTitulo = BorderFactory.createTitledBorder(linea, " " + titulo + " ");
        bordeConTitulo.setTitleFont(new Font("SansSerif", Font.BOLD, 15));
        bordeConTitulo.setTitleColor(COLOR_TITULO);
        return BorderFactory.createCompoundBorder(
                bordeConTitulo,
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );
    }

    private String obtenerTituloRadar() {
        if (tablero == null) {
            return "Panel de Operaciones en Vivo";
        }
        return "Panel de Operaciones en Vivo — Sector " + tablero.getX() + "x" + tablero.getY();
    }

    private int calcularTamanoCasilla() {
        if (tablero == null || tablero.getX() <= 0 || tablero.getY() <= 0) {
            return TAM_CASILLA_MIN;
        }
        int filas = tablero.getX();
        int columnas = tablero.getY();

        int idealAncho = VIEWPORT_ANCHO / columnas;
        int idealAlto = VIEWPORT_ALTO / filas;
        int tam = Math.min(idealAncho, idealAlto);

        return Math.max(TAM_CASILLA_MIN, Math.min(TAM_CASILLA_MAX, tam));
    }

    private void actualizarDimensionesRadar() {
        if (tablero == null) {
            panelRadar.setPreferredSize(new Dimension(VIEWPORT_ANCHO, VIEWPORT_ALTO));
            return;
        }
        int anchoTotal = tablero.getY() * tamCasillaActual;
        int altoTotal = tablero.getX() * tamCasillaActual;
        panelRadar.setPreferredSize(new Dimension(
                Math.max(anchoTotal, VIEWPORT_ANCHO - 20),
                Math.max(altoTotal, VIEWPORT_ALTO - 20)
        ));
        panelRadar.revalidate();
    }

    private void centrarCamaraEnNave() {
        if (tablero == null) return;
        Posicion posNave = tablero.getPosicionNave();
        if (posNave == null) return;

        int anchoTablero = tablero.getY() * tamCasillaActual;
        int altoTablero = tablero.getX() * tamCasillaActual;
        int offsetX = Math.max(0, (panelRadar.getWidth() - anchoTablero) / 2);
        int offsetY = Math.max(0, (panelRadar.getHeight() - altoTablero) / 2);

        int naveX = offsetX + posNave.getY() * tamCasillaActual + tamCasillaActual / 2;
        int naveY = offsetY + posNave.getX() * tamCasillaActual + tamCasillaActual / 2;

        Dimension viewSize = scrollRadar.getViewport().getExtentSize();
        int rectX = Math.max(0, naveX - viewSize.width / 2);
        int rectY = Math.max(0, naveY - viewSize.height / 2);

        panelRadar.scrollRectToVisible(new Rectangle(rectX, rectY, viewSize.width, viewSize.height));
    }

    // --- IMPLEMENTACIÓN DE AsistenteObserver ---
    @Override
    public void onRecursosActualizados(int combustible, int energia, int desgaste) {
        SwingUtilities.invokeLater(() -> {
            lblCombustible.setText("Combustible: " + combustible);
            lblEnergia.setText("Energía: " + energia);
            lblDesgaste.setText("Desgaste: " + desgaste);
            boolean requiere = desgaste >= 80;
            lblMantenimiento.setText("Mantenimiento: " + (requiere ? "REQUERIDO" : "Óptimo"));
            lblMantenimiento.setForeground(requiere ? new Color(255, 105, 105) : COLOR_TEXTO);
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
        SwingUtilities.invokeLater(() -> {
            panelRadar.repaint();
            centrarCamaraEnNave();
        });
    }

    // =========================================================
    // MOTOR DE DIBUJO DEL RADAR (Casillas cuadradas + cámara)
    // =========================================================
    private void dibujarUniverso(Graphics2D g2) {
        if (tablero == null) return;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int filas = tablero.getX();
        int columnas = tablero.getY();
        int tam = tamCasillaActual;

        int anchoTablero = columnas * tam;
        int altoTablero = filas * tam;

        // Si el tablero es más chico que la ventana, lo centramos
        int offsetX = Math.max(0, (panelRadar.getWidth() - anchoTablero) / 2);
        int offsetY = Math.max(0, (panelRadar.getHeight() - altoTablero) / 2);

        // Fondo
        g2.setColor(COLOR_ESPACIO);
        g2.fillRect(0, 0, panelRadar.getWidth(), panelRadar.getHeight());
        if (imagenLista(imgFondo)) {
            g2.drawImage(imgFondo, offsetX, offsetY, anchoTablero, altoTablero, this);
        }

        // Optimización: dibujamos solo las casillas visibles en el viewport del scroll
        Rectangle clip = g2.getClipBounds();
        int colInicio = Math.max(0, (clip.x - offsetX) / tam);
        int colFin = Math.min(columnas - 1, (clip.x + clip.width - offsetX) / tam);
        int filaInicio = Math.max(0, (clip.y - offsetY) / tam);
        int filaFin = Math.min(filas - 1, (clip.y + clip.height - offsetY) / tam);

        IElementosUniverso[][] matriz = tablero.verTablero();
        Posicion posNave = tablero.getPosicionNave();
        int margen = Math.max(3, tam / 7);
        int tamIcono = tam - margen * 2;

        for (int i = filaInicio; i <= filaFin; i++) {
            for (int j = colInicio; j <= colFin; j++) {
                int px = offsetX + j * tam;
                int py = offsetY + i * tam;

                // Cuadrícula sutil
                g2.setColor(new Color(145, 95, 235, 38));
                g2.drawRect(px, py, tam, tam);

                IElementosUniverso elemento = matriz[i][j];

                if (elemento != null) {
                    if (elemento instanceof nave.Asteroide) {
                        if (imagenLista(imgAsteroide)) {
                            g2.drawImage(imgAsteroide, px + 2, py + 2, tam - 4, tam - 4, this);
                        } else {
                            g2.setColor(new Color(140, 145, 160));
                            g2.fillOval(px + margen, py + margen, tamIcono, tamIcono);
                        }
                    } else if (elemento instanceof nave.EstacionDeCarga) {
                        if (imagenLista(imgEstacion)) {
                            g2.drawImage(imgEstacion, px + 2, py + 2, tam - 4, tam - 4, this);
                        } else {
                            g2.setColor(new Color(75, 220, 140));
                            g2.fillRoundRect(px + margen, py + margen, tamIcono, tamIcono, 6, 6);
                        }
                    } else if (elemento instanceof nave.INave || elemento instanceof nave.NaveEnemiga) {
                        if (imagenLista(imgNaveEnemiga)) {
                            g2.drawImage(imgNaveEnemiga, px + 2, py + 2, tam - 4, tam - 4, this);
                        } else {
                            g2.setColor(new Color(245, 95, 105));
                            int[] xp = {px + tam / 2, px + margen, px + tam - margen};
                            int[] yp = {py + margen, py + tam - margen, py + tam - margen};
                            g2.fillPolygon(xp, yp, 3);
                        }
                    } else {
                        g2.setColor(new Color(255, 195, 70));
                        g2.fillOval(px + margen, py + margen, tamIcono, tamIcono);
                    }
                }

                // Tu nave (siempre resaltada)
                if (posNave != null && posNave.getX() == i && posNave.getY() == j) {
                    g2.setColor(new Color(80, 200, 255, 65));
                    g2.fillOval(px + 2, py + 2, tam - 4, tam - 4);
                    if (imagenLista(imgNaveJugador)) {
                        g2.drawImage(imgNaveJugador, px + 2, py + 2, tam - 4, tam - 4, this);
                    } else {
                        g2.setColor(new Color(80, 200, 255));
                        int[] xp = {px + tam / 2, px + tam - margen, px + tam / 2, px + margen};
                        int[] yp = {py + margen, py + tam / 2, py + tam - margen, py + tam / 2};
                        g2.fillPolygon(xp, yp, 4);
                    }
                }
            }
        }

        // Borde exterior del tablero real
        g2.setColor(COLOR_BORDE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRect(offsetX, offsetY, anchoTablero, altoTablero);
    }

    @Override
    public JPanel getPanelTablero() {
        return this.panelRadar;
    }
}