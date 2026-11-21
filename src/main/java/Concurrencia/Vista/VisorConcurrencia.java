package Concurrencia.Vista;

import Concurrencia.Modelo.IObserverPlataforma;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VisorConcurrencia extends JPanel implements IVista, IObserverPlataforma {

    // Paleta espacial oscura
    private static final Color COLOR_FONDO = new Color(12, 10, 28);
    private static final Color COLOR_PANEL = new Color(22, 18, 48);
    private static final Color COLOR_CONSOLA = new Color(7, 6, 18);
    private static final Color COLOR_BORDE = new Color(145, 95, 235);
    private static final Color COLOR_TITULO = new Color(200, 165, 255);

    // Colores para diferenciar eventos en la consola
    private static final Color COLOR_HORA = new Color(120, 135, 160);
    private static final Color COLOR_NAVE = new Color(90, 210, 255);
    private static final Color COLOR_GENERADORA = new Color(100, 235, 150);
    private static final Color COLOR_ESPERA = new Color(255, 200, 80);
    private static final Color COLOR_INGRESO = new Color(190, 140, 255);
    private static final Color COLOR_DETENER = new Color(255, 105, 105);
    private static final Color COLOR_TEXTO_BASE = new Color(220, 225, 235);

    // Colores de los botones (para habilitar y deshabilitar limpiamente)
    private static final Color FONDO_BTN_NAVE = new Color(22, 105, 175);
    private static final Color BORDE_BTN_NAVE = new Color(90, 190, 255);
    private static final Color FONDO_BTN_GENERADORA = new Color(26, 130, 78);
    private static final Color BORDE_BTN_GENERADORA = new Color(95, 220, 150);
    private static final Color FONDO_BTN_DETENER = new Color(165, 42, 52);
    private static final Color BORDE_BTN_DETENER = new Color(245, 110, 120);
    private static final Color FONDO_BTN_REINICIAR = new Color(105, 60, 185);
    private static final Color BORDE_BTN_REINICIAR = new Color(185, 145, 255);

    private final JButton botonReiniciar;

    private static final int TAMANO_FUENTE_CONSOLA = 16;
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final JButton botonCrearNave;
    private final JButton botonCrearGeneradora;
    private final JButton botonDetener;
    private final JTextPane areaConsola;
    private final PanelDibujoUniverso panelDibujo;

    public VisorConcurrencia() {
        this.setLayout(new BorderLayout(14, 14));
        this.setBackground(COLOR_FONDO);
        this.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        // --- ENCABEZADO + PANEL DE CONTROLES (NORTE) ---
        JPanel panelSuperior = new JPanel(new BorderLayout(0, 10));
        panelSuperior.setOpaque(false);

        JLabel etiquetaTitulo = new JLabel("VISOR DEL UNIVERSO PARALELO", SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        etiquetaTitulo.setForeground(COLOR_TITULO);

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 8));
        panelControles.setBackground(COLOR_PANEL);
        panelControles.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(COLOR_BORDE, 1),
        BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        this.botonCrearNave = crearBotonEstilizado(
                "Crear Nave Consumidora",
                IVista.CREAR_NAVE,
                new Color(22, 105, 175),
                new Color(90, 190, 255)
        );
        this.botonCrearGeneradora = crearBotonEstilizado(
                "Crear Generadora de Energía",
                IVista.CREAR_GENERADORA,
                new Color(26, 130, 78),
                new Color(95, 220, 150)
        );
        this.botonDetener = crearBotonEstilizado(
                "Detener Simulación",
                IVista.DETENER_SIMULACION,
                new Color(165, 42, 52),
                new Color(245, 110, 120)
        );

        this.botonReiniciar = crearBotonEstilizado(
                "Reiniciar Simulación",
                IVista.REINICIAR_SIMULACION,
                FONDO_BTN_REINICIAR,
                BORDE_BTN_REINICIAR
        );
        deshabilitarBoton(this.botonReiniciar);

        panelControles.add(this.botonCrearNave);
        panelControles.add(this.botonCrearGeneradora);
        panelControles.add(this.botonDetener);
        panelControles.add(this.botonReiniciar);

        panelControles.add(this.botonCrearNave);
        panelControles.add(this.botonCrearGeneradora);
        panelControles.add(this.botonDetener);

        panelSuperior.add(etiquetaTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelControles, BorderLayout.CENTER);

        // --- PANEL DE DIBUJO EN VIVO (ARRIBA DEL CENTRO) ---
        this.panelDibujo = new PanelDibujoUniverso();
        JPanel contenedorDibujo = new JPanel(new BorderLayout());
        contenedorDibujo.setBackground(COLOR_PANEL);
        contenedorDibujo.setBorder(crearBordeConTitulo("Simulación Visual en Tiempo Real"));
        contenedorDibujo.add(this.panelDibujo, BorderLayout.CENTER);

        // --- CONSOLA DE EVENTOS (ABAJO DEL CENTRO) ---
        this.areaConsola = new JTextPane();
        this.areaConsola.setEditable(false);
        this.areaConsola.setBackground(COLOR_CONSOLA);
        this.areaConsola.setFont(new Font("Consolas", Font.PLAIN, TAMANO_FUENTE_CONSOLA));
        this.areaConsola.setMargin(new Insets(12, 14, 12, 14));

        JScrollPane scrollConsola = new JScrollPane(this.areaConsola);
        scrollConsola.setPreferredSize(new Dimension(880, 240));
        scrollConsola.getViewport().setBackground(COLOR_CONSOLA);
        scrollConsola.setBackground(COLOR_PANEL);
        scrollConsola.setBorder(crearBordeConTitulo("Registro de Eventos — Plataforma de Carga"));

        JSplitPane panelDividido = new JSplitPane(JSplitPane.VERTICAL_SPLIT, contenedorDibujo, scrollConsola);
        panelDividido.setResizeWeight(0.58);
        panelDividido.setBorder(null);
        panelDividido.setOpaque(false);

        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(panelDividido, BorderLayout.CENTER);
    }

    private JButton crearBotonEstilizado(String texto, String comando, Color fondo, Color borde) {
        JButton boton = new JButton(texto);
        boton.setActionCommand(comando);
        boton.setFont(new Font("SansSerif", Font.BOLD, 15));
        boton.setForeground(Color.WHITE);
        boton.setBackground(fondo);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde, 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
        return boton;
    }

    private Border crearBordeConTitulo(String titulo) {
        Border linea = BorderFactory.createLineBorder(COLOR_BORDE, 1);
        TitledBorder bordeConTitulo = BorderFactory.createTitledBorder(linea, " " + titulo + " ");
        bordeConTitulo.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
        bordeConTitulo.setTitleColor(COLOR_TITULO);
        return BorderFactory.createCompoundBorder(
                bordeConTitulo,
                BorderFactory.createEmptyBorder(6, 8, 8, 8)
        );
    }

    // --- MVC: Conexión con el Controlador ---
    @Override
    public void setActionListener(ActionListener controlador) {
        this.botonCrearNave.addActionListener(controlador);
        this.botonCrearGeneradora.addActionListener(controlador);
        this.botonDetener.addActionListener(controlador);
        this.botonReiniciar.addActionListener(controlador);
    }

    @Override
    public void agregarMensajeConsola(String mensaje) {
        StyledDocument documento = this.areaConsola.getStyledDocument();
        String marcaHora = "[" + LocalTime.now().format(FORMATO_HORA) + "] ";
        Color colorEvento = determinarColorEvento(mensaje);

        try {
            agregarTextoConEstilo(documento, marcaHora, COLOR_HORA, false);
            agregarTextoConEstilo(documento, mensaje + "\n", colorEvento, mensaje.contains("Ingresó"));
            this.areaConsola.setCaretPosition(documento.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        this.panelDibujo.procesarEvento(mensaje);
    }

    @Override
    public void deshabilitarControles() {
        deshabilitarBoton(this.botonCrearNave);
        deshabilitarBoton(this.botonCrearGeneradora);
        deshabilitarBoton(this.botonDetener);
        habilitarBoton(this.botonReiniciar, FONDO_BTN_REINICIAR, BORDE_BTN_REINICIAR);
    }

    @Override
    public void habilitarControles() {
        habilitarBoton(this.botonCrearNave, FONDO_BTN_NAVE, BORDE_BTN_NAVE);
        habilitarBoton(this.botonCrearGeneradora, FONDO_BTN_GENERADORA, BORDE_BTN_GENERADORA);
        habilitarBoton(this.botonDetener, FONDO_BTN_DETENER, BORDE_BTN_DETENER);
        deshabilitarBoton(this.botonReiniciar);

        this.areaConsola.setText("");
        this.panelDibujo.reiniciarDibujo();
    }

    private void habilitarBoton(JButton boton, Color fondo, Color borde) {
        boton.setEnabled(true);
        boton.setBackground(fondo);
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde, 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
    }

    private void deshabilitarBoton(JButton boton) {
        boton.setEnabled(false);
        boton.setBackground(new Color(45, 42, 68));
        boton.setForeground(new Color(125, 125, 150));
        boton.setCursor(Cursor.getDefaultCursor());
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(75, 70, 105), 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
    }

    private void agregarTextoConEstilo(StyledDocument documento, String texto, Color color, boolean negrita)
            throws BadLocationException {
        SimpleAttributeSet atributos = new SimpleAttributeSet();
        StyleConstants.setForeground(atributos, color);
        StyleConstants.setBold(atributos, negrita);
        StyleConstants.setFontSize(atributos, TAMANO_FUENTE_CONSOLA);
        documento.insertString(documento.getLength(), texto, atributos);
    }

    private Color determinarColorEvento(String mensaje) {
        if (mensaje.contains("reiniciada")) {
            return COLOR_TITULO;
        }
        if (mensaje.contains("detenida")) {
            return COLOR_DETENER;
        }
        if (mensaje.contains("Ingresó")) {
            return COLOR_INGRESO;
        }
        if (mensaje.contains("esperando")) {
            return COLOR_ESPERA;
        }
        if (mensaje.contains("[Generadora")) {
            return COLOR_GENERADORA;
        }
        if (mensaje.contains("[Nave")) {
            return COLOR_NAVE;
        }
        return COLOR_TEXTO_BASE;
    }

    // --- OBSERVER: Actualización automática desde los hilos del Modelo ---
    @Override
    public void actualizarEvento(String mensaje) {
        SwingUtilities.invokeLater(() -> agregarMensajeConsola(mensaje));
    }
}