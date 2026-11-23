package Creacion.Vista;

import Creacion.Modelo.ModeloCreacion;
import Creacion.Modelo.ObservadorCreacion;
import Tripulantes.Tripulante;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaCreacion extends JFrame implements ObservadorCreacion {

    // Paleta espacial oscura (unificada con UsuarioVista y PanelControlNave)
    private static final Color COLOR_FONDO = new Color(12, 10, 28);
    private static final Color COLOR_PANEL = new Color(22, 18, 48);
    private static final Color COLOR_CAMPO = new Color(7, 6, 18);
    private static final Color COLOR_BORDE = new Color(145, 95, 235);
    private static final Color COLOR_TITULO = new Color(200, 165, 255);
    private static final Color COLOR_TEXTO = new Color(235, 238, 248);

    // Colores de botones
    private static final Color FONDO_BTN_PRIMARIO = new Color(22, 105, 175);
    private static final Color BORDE_BTN_PRIMARIO = new Color(90, 190, 255);
    private static final Color FONDO_BTN_SECUNDARIO = new Color(105, 60, 185);
    private static final Color BORDE_BTN_SECUNDARIO = new Color(185, 145, 255);
    private static final Color FONDO_BTN_DESHABILITADO = new Color(34, 30, 62);
    private static final Color BORDE_BTN_DESHABILITADO = new Color(80, 68, 125);

    private ModeloCreacion modelo;

    private JComboBox<String> comboTiposNave;
    private JButton btnCrearNave;

    private JTextField txtIdentidad;
    private JTextField txtAntiguedad;
    private JComboBox<String> comboRol;
    private JComboBox<String> comboOrigen;
    private JButton btnAgregarTripulante;

    private JTextArea areaTripulantes;
    private JButton btnIniciarTravesia;

    public VistaCreacion(ModeloCreacion modelo) {
        super("Panel de Creación - Ensamblaje y Reclutamiento");
        this.modelo = modelo;

        this.modelo.agregarObservador(this);

        configurarLayout();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(920, 620));
        setSize(960, 640);
        setLocationRelativeTo(null);
    }

    private void configurarLayout() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(16, 16));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        // --- ENCABEZADO + PANEL NORTE: Nave ---
        JPanel panelNorte = new JPanel(new BorderLayout(0, 12));
        panelNorte.setOpaque(false);

        JLabel lblTituloGeneral = new JLabel("ASTILLERO ESPACIAL — PREPARACIÓN DE MISIÓN", SwingConstants.CENTER);
        lblTituloGeneral.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTituloGeneral.setForeground(COLOR_TITULO);

        JPanel panelNave = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
        panelNave.setBackground(COLOR_PANEL);
        panelNave.setBorder(crearBordeConTitulo("1. Ensamblaje de Nave"));

        comboTiposNave = new JComboBox<>(new String[]{"Exploradora", "Carguero", "Combate"});
        estilizarCombo(comboTiposNave);
        comboTiposNave.setPreferredSize(new Dimension(200, 38));

        btnCrearNave = crearBotonEstilizado("Crear Nave", "CREAR_NAVE", FONDO_BTN_SECUNDARIO, BORDE_BTN_SECUNDARIO);

        panelNave.add(crearEtiqueta("Tipo de Nave:"));
        panelNave.add(comboTiposNave);
        panelNave.add(btnCrearNave);

        panelNorte.add(lblTituloGeneral, BorderLayout.NORTH);
        panelNorte.add(panelNave, BorderLayout.CENTER);
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);

        // --- PANEL OESTE: Tripulantes (Formulario al centro + Botón anclado abajo) ---
        JPanel panelTripulante = new JPanel(new BorderLayout(0, 14));
        panelTripulante.setBackground(COLOR_PANEL);
        panelTripulante.setBorder(crearBordeConTitulo("2. Reclutamiento"));
        panelTripulante.setPreferredSize(new Dimension(350, 420));

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setOpaque(false);

        txtIdentidad = new JTextField();
        estilizarCampoTexto(txtIdentidad);

        txtAntiguedad = new JTextField();
        estilizarCampoTexto(txtAntiguedad);

        comboRol = new JComboBox<>(new String[]{"Capitan", "Teniente", "Consejero", "Alferez"});
        estilizarCombo(comboRol);

        comboOrigen = new JComboBox<>(new String[]{"Terricola", "Marciano", "Vulcano"});
        estilizarCombo(comboOrigen);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        agregarFilaFormulario(panelCampos, gbc, 0, "Identidad:", txtIdentidad);
        agregarFilaFormulario(panelCampos, gbc, 2, "Antigüedad (años):", txtAntiguedad);
        agregarFilaFormulario(panelCampos, gbc, 4, "Rol:", comboRol);
        agregarFilaFormulario(panelCampos, gbc, 6, "Origen:", comboOrigen);

        // Empuja los campos hacia arriba sin aplastar el botón inferior
        gbc.gridy = 8;
        gbc.weighty = 1.0;
        panelCampos.add(Box.createGlue(), gbc);

        btnAgregarTripulante = crearBotonEstilizado(
                "Asignar Tripulante", "AGREGAR_TRIPULANTE",
                FONDO_BTN_SECUNDARIO, BORDE_BTN_SECUNDARIO
        );
        btnAgregarTripulante.setPreferredSize(new Dimension(300, 42));
        btnAgregarTripulante.setEnabled(false);

        panelTripulante.add(panelCampos, BorderLayout.CENTER);
        panelTripulante.add(btnAgregarTripulante, BorderLayout.SOUTH);

        panelPrincipal.add(panelTripulante, BorderLayout.WEST);

        // --- PANEL CENTRO: Lista ---
        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBackground(COLOR_PANEL);
        panelLista.setBorder(crearBordeConTitulo("3. Tripulación Asignada"));

        areaTripulantes = new JTextArea();
        areaTripulantes.setEditable(false);
        areaTripulantes.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaTripulantes.setBackground(COLOR_CAMPO);
        areaTripulantes.setForeground(COLOR_TEXTO);
        areaTripulantes.setCaretColor(COLOR_TITULO);
        areaTripulantes.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        JScrollPane scrollTripulantes = new JScrollPane(areaTripulantes);
        scrollTripulantes.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        scrollTripulantes.getViewport().setBackground(COLOR_CAMPO);

        panelLista.add(scrollTripulantes, BorderLayout.CENTER);
        panelPrincipal.add(panelLista, BorderLayout.CENTER);

        // --- PANEL SUR: Transición ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelSur.setOpaque(false);

        btnIniciarTravesia = crearBotonEstilizado(
                "Terminar la creacion", "INICIAR_TRAVESIA",
                FONDO_BTN_PRIMARIO, BORDE_BTN_PRIMARIO
        );
        btnIniciarTravesia.setEnabled(false);

        panelSur.add(btnIniciarTravesia);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    private void agregarFilaFormulario(JPanel panel, GridBagConstraints gbc, int filaBase,
                                       String textoEtiqueta, JComponent campo) {
        gbc.gridy = filaBase;
        gbc.weighty = 0;
        gbc.insets = new Insets(filaBase == 0 ? 0 : 10, 0, 4, 0);
        panel.add(crearEtiqueta(textoEtiqueta), gbc);

        gbc.gridy = filaBase + 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        campo.setPreferredSize(new Dimension(280, 36));
        panel.add(campo, gbc);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(COLOR_TEXTO);
        return lbl;
    }

    private Border crearBordeConTitulo(String titulo) {
        Border linea = BorderFactory.createLineBorder(COLOR_BORDE, 1);
        TitledBorder bordeConTitulo = BorderFactory.createTitledBorder(linea, " " + titulo + " ");
        bordeConTitulo.setTitleFont(new Font("SansSerif", Font.BOLD, 15));
        bordeConTitulo.setTitleColor(COLOR_TITULO);
        return BorderFactory.createCompoundBorder(
                bordeConTitulo,
                BorderFactory.createEmptyBorder(10, 14, 12, 14)
        );
    }

    private void estilizarCampoTexto(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBackground(COLOR_CAMPO);
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(COLOR_TITULO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
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
                    lbl.setBackground(isSelected ? FONDO_BTN_SECUNDARIO : COLOR_CAMPO);
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

    private JButton crearBotonEstilizado(String texto, String comando, Color fondoActivo, Color bordeActivo) {
        JButton boton = new JButton(texto);
        boton.setActionCommand(comando);
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

        // Actualiza la estética automáticamente cuando cambia setEnabled(...)
        boton.addPropertyChangeListener("enabled", evt -> {
            boolean activo = Boolean.TRUE.equals(evt.getNewValue());
            boton.setBackground(activo ? fondoActivo : FONDO_BTN_DESHABILITADO);
            boton.setForeground(activo ? Color.WHITE : new Color(140, 135, 170));
            boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(activo ? bordeActivo : BORDE_BTN_DESHABILITADO, 1),
                    BorderFactory.createEmptyBorder(9, 18, 9, 18)
            ));
        });

        return boton;
    }

    @Override
    public void actualizarEstado() {
        if (modelo.getNaveConfigurada() != null) {
            btnCrearNave.setEnabled(false);
            comboTiposNave.setEnabled(false);
            btnAgregarTripulante.setEnabled(true);
        }

        areaTripulantes.setText("");
        List<Tripulante> lista = modelo.getTripulacion();

        for (Tripulante t : lista) {
            String info = String.format("ID: %s | Rol/Origen: %s | Antiguedad: %d años | Sueldo: $%.2f\n",
                    t.getIdentidad(),
                    t.getClass().getSimpleName(),
                    t.getAntiguedad(),
                    t.getSueldo());
            areaTripulantes.append(info);
        }

        btnIniciarTravesia.setEnabled(modelo.puedeIniciarTravesia());
    }

    public String getTipoNaveSeleccionado() {
        return (String) comboTiposNave.getSelectedItem();
    }

    public String getIdentidad() {
        return txtIdentidad.getText();
    }

    public String getAntiguedad() {
        return txtAntiguedad.getText();
    }

    public String getRolSeleccionado() {
        return (String) comboRol.getSelectedItem();
    }

    public String getOrigenSeleccionado() {
        return (String) comboOrigen.getSelectedItem();
    }

    public void setControlador(ActionListener c) {
        btnCrearNave.addActionListener(c);
        btnAgregarTripulante.addActionListener(c);
        btnIniciarTravesia.addActionListener(c);
    }

    public void arrancar() {
        setVisible(true);
    }
}