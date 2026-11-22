package Usuarios.Vista;

import Usuarios.Controlador.UsuarioControlador;

import javax.swing.*;
import java.awt.*;

public class UsuarioVista extends JFrame implements IUsuarios {

    // Paleta espacial oscura (unificada con VisorConcurrencia)
    private static final Color COLOR_FONDO = new Color(12, 10, 28);
    private static final Color COLOR_TARJETA_TRANSLUCIDA = new Color(255, 255, 255, 22);
    private static final Color COLOR_BORDE_TARJETA = new Color(145, 95, 235, 120);
    private static final Color COLOR_CAMPO = new Color(7, 6, 18);
    private static final Color COLOR_BORDE = new Color(145, 95, 235);
    private static final Color COLOR_TITULO = new Color(200, 165, 255);
    private static final Color COLOR_TEXTO_BASE = new Color(235, 238, 248);
    private static final Color COLOR_ERROR = new Color(255, 105, 105);
    private static final Color COLOR_EXITO = new Color(100, 235, 150);

    // Colores de los botones (mismos que VisorConcurrencia)
    private static final Color FONDO_BTN_LOGIN = new Color(22, 105, 175);
    private static final Color BORDE_BTN_LOGIN = new Color(90, 190, 255);
    private static final Color FONDO_BTN_REGISTRAR = new Color(105, 60, 185);
    private static final Color BORDE_BTN_REGISTRAR = new Color(185, 145, 255);

    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final JButton btnLogin;
    private final JButton btnRegistrar;
    private final JLabel lblError;

    public UsuarioVista() {
        super("Usuarios");

        // --- FONDO PRINCIPAL OSCURO SÓLIDO ---
        JPanel panelFondo = new JPanel(new GridBagLayout());
        panelFondo.setBackground(COLOR_FONDO);
        panelFondo.setPreferredSize(new Dimension(540, 490));
        panelFondo.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // --- RECUADRO TRANSLÚCIDO REDONDEADO DETRÁS DE TODO EL LOGIN ---
        JPanel panelTarjeta = new JPanel(new BorderLayout(0, 28)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_TARJETA_TRANSLUCIDA);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                g2.setColor(COLOR_BORDE_TARJETA);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panelTarjeta.setOpaque(false);
        panelTarjeta.setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));
        panelTarjeta.setPreferredSize(new Dimension(420, 405));

        // --- TÍTULO (NORTE) ---
        JLabel lblTitulo = new JLabel("ACCESO TRIPULACIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TITULO);

        // --- FORMULARIO (Usuario + Contraseña + Error) ---
        JPanel panelFormulario = new JPanel();
        panelFormulario.setOpaque(false);
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        JLabel lblUsername = crearEtiquetaConIcono("Usuario:", crearIconoUsuario());
        txtUsername = new JTextField(20);
        estilizarCampo(txtUsername);
        lblUsername.setLabelFor(txtUsername);

        JLabel lblPassword = crearEtiquetaConIcono("Contraseña:", crearIconoCandado());
        txtPassword = new JPasswordField(20);
        JPanel panelPassword = crearCampoPasswordConOjo(txtPassword);
        lblPassword.setLabelFor(txtPassword);

        lblError = new JLabel(" ");
        lblError.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblError.setForeground(COLOR_ERROR);
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelFormulario.add(lblUsername);
        panelFormulario.add(Box.createVerticalStrut(8));
        panelFormulario.add(txtUsername);
        panelFormulario.add(Box.createVerticalStrut(18));
        panelFormulario.add(lblPassword);
        panelFormulario.add(Box.createVerticalStrut(8));
        panelFormulario.add(panelPassword);
        panelFormulario.add(Box.createVerticalStrut(12));
        panelFormulario.add(lblError);

        // --- BOTONES SEPARADOS (Login en azul | Registrar en violeta) ---
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 16, 0));
        panelBotones.setOpaque(false);
        panelBotones.setPreferredSize(new Dimension(360, 48));

        btnLogin = crearBotonEstilizado("Login", LOGIN, FONDO_BTN_LOGIN, BORDE_BTN_LOGIN);
        btnRegistrar = crearBotonEstilizado("Registrar", REGISTRAR, FONDO_BTN_REGISTRAR, BORDE_BTN_REGISTRAR);

        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistrar);

        panelTarjeta.add(lblTitulo, BorderLayout.NORTH);
        panelTarjeta.add(panelFormulario, BorderLayout.CENTER);
        panelTarjeta.add(panelBotones, BorderLayout.SOUTH);

        panelFondo.add(panelTarjeta);

        setContentPane(panelFondo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private JPanel crearCampoPasswordConOjo(JPasswordField campo) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(COLOR_CAMPO);
        contenedor.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        contenedor.setPreferredSize(new Dimension(340, 42));
        contenedor.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));

        campo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        campo.setBackground(COLOR_CAMPO);
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(COLOR_TITULO);
        campo.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 6));

        char echoCharPorDefecto = campo.getEchoChar();

        JToggleButton btnOjo = new JToggleButton(crearIconoOjo(false));
        btnOjo.setSelectedIcon(crearIconoOjo(true));
        btnOjo.setToolTipText("Mostrar contraseña");
        btnOjo.setOpaque(false);
        btnOjo.setContentAreaFilled(false);
        btnOjo.setBorderPainted(false);
        btnOjo.setFocusPainted(false);
        btnOjo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOjo.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 10));

        btnOjo.addActionListener(e -> {
            if (btnOjo.isSelected()) {
                campo.setEchoChar((char) 0);
                btnOjo.setToolTipText("Ocultar contraseña");
            } else {
                campo.setEchoChar(echoCharPorDefecto);
                btnOjo.setToolTipText("Mostrar contraseña");
            }
        });

        contenedor.add(campo, BorderLayout.CENTER);
        contenedor.add(btnOjo, BorderLayout.EAST);
        return contenedor;
    }

    private JLabel crearEtiquetaConIcono(String texto, Icon icono) {
        JLabel etiqueta = new JLabel(texto, icono, SwingConstants.LEFT);
        etiqueta.setIconTextGap(8);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 15));
        etiqueta.setForeground(COLOR_TEXTO_BASE);
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        return etiqueta;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        campo.setBackground(COLOR_CAMPO);
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(COLOR_TITULO);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        campo.setPreferredSize(new Dimension(340, 42));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
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

    private Icon crearIconoUsuario() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_TEXTO_BASE);
                g2.fillRoundRect(x, y, 16, 16, 4, 4);
                g2.setColor(COLOR_FONDO);
                g2.fillOval(x + 5, y + 3, 6, 6);
                g2.fillArc(x + 3, y + 10, 10, 7, 0, 180);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 16;
            }

            @Override
            public int getIconHeight() {
                return 16;
            }
        };
    }

    private Icon crearIconoCandado() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_TEXTO_BASE);
                g2.setStroke(new BasicStroke(2f));
                g2.drawArc(x + 4, y + 1, 8, 8, 0, 180);
                g2.fillRoundRect(x + 2, y + 6, 12, 10, 3, 3);
                g2.setColor(COLOR_FONDO);
                g2.fillOval(x + 6, y + 9, 4, 4);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 16;
            }

            @Override
            public int getIconHeight() {
                return 16;
            }
        };
    }

    private Icon crearIconoOjo(boolean tachado) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(tachado ? COLOR_TITULO : COLOR_TEXTO_BASE);
                g2.setStroke(new BasicStroke(1.6f));

                // Contorno del ojo
                g2.drawArc(x + 1, y + 3, 18, 12, 0, 180);
                g2.drawArc(x + 1, y + 1, 18, 12, 180, 180);

                // Pupila
                g2.fillOval(x + 7, y + 5, 6, 6);

                // Línea diagonal si está visible (para indicar "ocultar")
                if (tachado) {
                    g2.setStroke(new BasicStroke(1.8f));
                    g2.drawLine(x + 3, y + 14, x + 17, y + 2);
                }
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 20;
            }

            @Override
            public int getIconHeight() {
                return 16;
            }
        };
    }

    @Override
    public void setMensaje(String mensaje) {
        if (lblError != null) {
            if (mensaje != null && mensaje.toLowerCase().contains("exitosamente")) {
                lblError.setForeground(COLOR_EXITO);
            } else {
                lblError.setForeground(COLOR_ERROR);
            }
            lblError.setText(mensaje);
        }
    }

    @Override
    public void arrancar() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void setController(UsuarioControlador controller) {
        btnLogin.addActionListener(controller);
        btnRegistrar.addActionListener(controller);
    }

    @Override
    public String getUsername() {
        return txtUsername.getText();
    }

    @Override
    public String getPassword() {
        return new String(txtPassword.getPassword());
    }
}
