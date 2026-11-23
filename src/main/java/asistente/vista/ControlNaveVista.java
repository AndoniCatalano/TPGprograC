package asistente.vista;

import asistente.controlador.ControlNaveController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class ControlNaveVista extends JFrame implements AsistenteObserver {

    // Paleta espacial oscura (unificada con UsuarioVista, VistaCreacion y PanelControlNave)
    private static final Color COLOR_FONDO = new Color(12, 10, 28);
    private static final Color COLOR_PANEL = new Color(22, 18, 48);
    private static final Color COLOR_CAMPO = new Color(7, 6, 18);
    private static final Color COLOR_BORDE = new Color(145, 95, 235);
    private static final Color COLOR_TITULO = new Color(200, 165, 255);
    private static final Color COLOR_TEXTO = new Color(235, 238, 248);

    // Colores de recursos y botones
    private static final Color COLOR_COMBUSTIBLE = new Color(100, 235, 150);
    private static final Color COLOR_ENERGIA = new Color(90, 210, 255);
    private static final Color COLOR_DESGASTE = new Color(255, 105, 105);

    private static final Color FONDO_BTN_INICIAR = new Color(22, 105, 175);
    private static final Color BORDE_BTN_INICIAR = new Color(90, 190, 255);
    private static final Color FONDO_BTN_CONCURRENCIA = new Color(105, 60, 185);
    private static final Color BORDE_BTN_CONCURRENCIA = new Color(185, 145, 255);
    private static final Color FONDO_BTN_DESHABILITADO = new Color(34, 30, 62);
    private static final Color BORDE_BTN_DESHABILITADO = new Color(80, 68, 125);

    private JProgressBar barraCombustible = new JProgressBar(0, 100);
    private JProgressBar barraEnergia = new JProgressBar(0, 100);
    private JProgressBar barraDesgaste = new JProgressBar(0, 100);

    private JTextArea areaBitacora = new JTextArea(12, 32);
    private JTextArea areaInformes = new JTextArea(12, 32);

    private JComboBox<String> comboMisiones = new JComboBox<>(
            new String[]{"M-01: Asistencia", "M-02: Recolección", "M-03: Retorno"}
    );
    private JButton btnIniciar = new JButton("Iniciar Misión");
    private JButton btnConcurrencia = new JButton("Concurrencia");

    public ControlNaveVista() {
        setTitle("Control de Nave");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(16, 16));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        panelPrincipal.setPreferredSize(new Dimension(960, 600));

        // --- ENCABEZADO + PANEL NORTE: Estado de Recursos ---
        JPanel panelNorte = new JPanel(new BorderLayout(0, 12));
        panelNorte.setOpaque(false);

        JLabel lblTituloGeneral = new JLabel("CENTRO DE MANDO — CONTROL DE NAVE", SwingConstants.CENTER);
        lblTituloGeneral.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTituloGeneral.setForeground(COLOR_TITULO);

        JPanel panelRecursos = new JPanel(new GridLayout(1, 3, 16, 0));
        panelRecursos.setBackground(COLOR_PANEL);
        panelRecursos.setBorder(crearBordeConTitulo("Estado de Recursos"));

        panelRecursos.add(crearPanelBarra("Combustible", barraCombustible, COLOR_COMBUSTIBLE));
        panelRecursos.add(crearPanelBarra("Energía", barraEnergia, COLOR_ENERGIA));
        panelRecursos.add(crearPanelBarra("Desgaste", barraDesgaste, COLOR_DESGASTE));

        panelNorte.add(lblTituloGeneral, BorderLayout.NORTH);
        panelNorte.add(panelRecursos, BorderLayout.CENTER);
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);

        // --- PANEL CENTRO: Bitácora e Informes ---
        estilizarAreaTexto(areaBitacora);
        estilizarAreaTexto(areaInformes);

        JPanel panelBitacora = envolverEnTarjetaConScroll("Bitácora de Eventos", areaBitacora);
        JPanel panelInformes = envolverEnTarjetaConScroll("Informes de Misión", areaInformes);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelBitacora, panelInformes);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(10);
        splitPane.setBorder(null);
        splitPane.setOpaque(true);
        splitPane.setBackground(COLOR_FONDO);
        estilizarSplitPane(splitPane);

        panelPrincipal.add(splitPane, BorderLayout.CENTER);

        // --- PANEL SUR: Controles ---
        JPanel panelControles = new JPanel(new BorderLayout(12, 0));
        panelControles.setBackground(COLOR_PANEL);
        panelControles.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        estilizarBoton(btnConcurrencia, FONDO_BTN_CONCURRENCIA, BORDE_BTN_CONCURRENCIA);
        btnConcurrencia.setActionCommand("CONCURRENCIA");

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panelDerecho.setOpaque(false);

        JLabel lblSeleccion = new JLabel("Seleccionar Misión:");
        lblSeleccion.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSeleccion.setForeground(COLOR_TEXTO);

        estilizarCombo(comboMisiones);
        comboMisiones.setPreferredSize(new Dimension(210, 38));

        estilizarBoton(btnIniciar, FONDO_BTN_INICIAR, BORDE_BTN_INICIAR);
        btnIniciar.setActionCommand("INICIAR");

        panelDerecho.add(lblSeleccion);
        panelDerecho.add(comboMisiones);
        panelDerecho.add(btnIniciar);

        panelControles.add(btnConcurrencia, BorderLayout.WEST);
        panelControles.add(panelDerecho, BorderLayout.EAST);

        panelPrincipal.add(panelControles, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        setMinimumSize(new Dimension(880, 540));
    }

    private JPanel crearPanelBarra(String titulo, JProgressBar barra, Color color) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setOpaque(false);

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(COLOR_TEXTO);

        barra.setStringPainted(true);
        barra.setOpaque(true);
        barra.setFont(new Font("SansSerif", Font.BOLD, 13));
        barra.setBackground(COLOR_CAMPO);
        barra.setForeground(color);
        barra.setPreferredSize(new Dimension(200, 26));
        barra.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        barra.setUI(new BasicProgressBarUI() {
            @Override
            protected Color getSelectionBackground() {
                // Color del texto cuando está sobre el relleno brillante de la barra
                return COLOR_CAMPO;
            }

            @Override
            protected Color getSelectionForeground() {
                // Color del texto cuando está sobre el fondo oscuro vacío
                return COLOR_TEXTO;
            }
        });

        p.add(lbl, BorderLayout.NORTH);
        p.add(barra, BorderLayout.CENTER);
        return p;
    }

    private JPanel envolverEnTarjetaConScroll(String titulo, JTextArea area) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(COLOR_PANEL);
        contenedor.setBorder(crearBordeConTitulo(titulo));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        scroll.getViewport().setBackground(COLOR_CAMPO);

        contenedor.add(scroll, BorderLayout.CENTER);
        return contenedor;
    }

    private void estilizarAreaTexto(JTextArea area) {
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBackground(COLOR_CAMPO);
        area.setForeground(COLOR_TEXTO);
        area.setCaretColor(COLOR_TITULO);
        area.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
    }

    private Border crearBordeConTitulo(String titulo) {
        Border linea = BorderFactory.createLineBorder(COLOR_BORDE, 1);
        TitledBorder bordeConTitulo = BorderFactory.createTitledBorder(linea, " " + titulo + " ");
        bordeConTitulo.setTitleFont(new Font("SansSerif", Font.BOLD, 15));
        bordeConTitulo.setTitleColor(COLOR_TITULO);
        return BorderFactory.createCompoundBorder(
                bordeConTitulo,
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        );
    }

    private void estilizarSplitPane(JSplitPane splitPane) {
        splitPane.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(COLOR_FONDO);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        g.setColor(new Color(145, 95, 235, 90));
                        int x = getWidth() / 2;
                        g.drawLine(x, 12, x, getHeight() - 12);
                    }
                };
            }
        });
    }

    private void estilizarCombo(JComboBox<String> combo) {
        combo.setFont(new Font("SansSerif", Font.BOLD, 14));
        combo.setBackground(COLOR_CAMPO);
        combo.setForeground(Color.WHITE);
        combo.setOpaque(true);
        combo.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        combo.setFocusable(false);

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setOpaque(true);
                lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
                lbl.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

                if (index == -1) {
                    lbl.setBackground(COLOR_CAMPO);
                    lbl.setForeground(combo.isEnabled() ? Color.WHITE : new Color(175, 165, 210));
                } else {
                    lbl.setBackground(isSelected ? FONDO_BTN_CONCURRENCIA : COLOR_CAMPO);
                    lbl.setForeground(Color.WHITE);
                }
                return lbl;
            }
        });

        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton btn = new JButton("▼");
                btn.setFont(new Font("SansSerif", Font.PLAIN, 11));
                btn.setForeground(COLOR_TITULO);
                btn.setBackground(COLOR_PANEL);
                btn.setOpaque(true);
                btn.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, COLOR_BORDE));
                btn.setFocusPainted(false);
                return btn;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(COLOR_CAMPO);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }

            @Override
            public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
                ListCellRenderer<Object> renderer = comboBox.getRenderer();
                Component c = renderer.getListCellRendererComponent(
                        listBox, comboBox.getSelectedItem(), -1, false, false);
                c.setFont(comboBox.getFont());
                c.setBackground(COLOR_CAMPO);
                c.setForeground(comboBox.isEnabled() ? Color.WHITE : new Color(175, 165, 210));
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(true);
                }
                currentValuePane.paintComponent(g, c, comboBox, bounds.x, bounds.y, bounds.width, bounds.height, true);
            }
        });
    }

    private void estilizarBoton(JButton boton, Color fondoActivo, Color bordeActivo) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(fondoActivo);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bordeActivo, 1),
                BorderFactory.createEmptyBorder(9, 18, 9, 18)
        ));

        boton.addPropertyChangeListener("enabled", evt -> {
            boolean activo = Boolean.TRUE.equals(evt.getNewValue());
            boton.setBackground(activo ? fondoActivo : FONDO_BTN_DESHABILITADO);
            boton.setForeground(activo ? Color.WHITE : new Color(140, 135, 170));
            boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(activo ? bordeActivo : BORDE_BTN_DESHABILITADO, 1),
                    BorderFactory.createEmptyBorder(9, 18, 9, 18)
            ));
        });
    }

    public void arrancar() {
        System.out.println("arrancar");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void habilitarBoton() {
        btnIniciar.setEnabled(true);
    }

    public void deshabilitarBoton() {
        btnIniciar.setEnabled(false);
    }

    public void setControlador(ControlNaveController c) {
        btnIniciar.addActionListener(c);
        btnConcurrencia.addActionListener(c);
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public String getMisionSeleccionada() {
        return (String) comboMisiones.getSelectedItem();
    }

    // --- MÉTODOS SOBRESCRITOS DEL OBSERVER ---
    @Override
    public void onRecursosActualizados(int combustible, int energia, int desgaste) {
        SwingUtilities.invokeLater(() -> {
            barraCombustible.setValue(combustible);
            barraEnergia.setValue(energia);
            barraDesgaste.setValue(desgaste);
        });
    }

    @Override
    public void onEventoBitacora(String evento) {
        SwingUtilities.invokeLater(() -> {
            areaBitacora.append(evento + "\n");
            areaBitacora.setCaretPosition(areaBitacora.getDocument().getLength());
        });
    }

    @Override
    public void onInformeGenerado(String informe) {
        SwingUtilities.invokeLater(() -> {
            areaInformes.append(informe + "\n\n");
            areaInformes.setCaretPosition(areaInformes.getDocument().getLength());
        });
    }
}