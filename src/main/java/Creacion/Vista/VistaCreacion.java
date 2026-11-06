package Creacion.Vista;

import Creacion.Modelo.ModeloCreacion;
import Creacion.Modelo.ObservadorCreacion;
import Tripulantes.Tripulante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaCreacion extends JFrame implements ObservadorCreacion {
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
        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        // Panel Norte: Nave
        JPanel panelNave = new JPanel(new FlowLayout());
        panelNave.setBorder(BorderFactory.createTitledBorder("1. Ensamblaje de Nave"));
        comboTiposNave = new JComboBox<>(new String[]{"Exploradora", "Carguero", "Combate"});
        btnCrearNave = new JButton("Instanciar Nave");
        btnCrearNave.setActionCommand("CREAR_NAVE");

        panelNave.add(new JLabel("Tipo de Nave:"));
        panelNave.add(comboTiposNave);
        panelNave.add(btnCrearNave);
        add(panelNave, BorderLayout.NORTH);

        // Panel Oeste: Tripulantes
        JPanel panelTripulante = new JPanel(new GridLayout(5, 2, 5, 5));
        panelTripulante.setBorder(BorderFactory.createTitledBorder("2. Reclutamiento"));

        txtIdentidad = new JTextField();
        txtAntiguedad = new JTextField();
        comboRol = new JComboBox<>(new String[]{"Capitan", "Teniente", "Consejero", "Alferez"});
        comboOrigen = new JComboBox<>(new String[]{"Terricola", "Marciano", "Vulcano"});

        btnAgregarTripulante = new JButton("Asignar Tripulante");
        btnAgregarTripulante.setActionCommand("AGREGAR_TRIPULANTE");
        btnAgregarTripulante.setEnabled(false);

        panelTripulante.add(new JLabel("Identidad:"));
        panelTripulante.add(txtIdentidad);
        panelTripulante.add(new JLabel("Antigüedad (años):"));
        panelTripulante.add(txtAntiguedad);
        panelTripulante.add(new JLabel("Rol:"));
        panelTripulante.add(comboRol);
        panelTripulante.add(new JLabel("Origen:"));
        panelTripulante.add(comboOrigen);
        panelTripulante.add(new JLabel(""));
        panelTripulante.add(btnAgregarTripulante);

        add(panelTripulante, BorderLayout.WEST);

        // Panel Centro: Lista
        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBorder(BorderFactory.createTitledBorder("3. Tripulación Asignada"));
        areaTripulantes = new JTextArea();
        areaTripulantes.setEditable(false);
        panelLista.add(new JScrollPane(areaTripulantes), BorderLayout.CENTER);
        add(panelLista, BorderLayout.CENTER);

        // Panel Sur: Transición
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnIniciarTravesia = new JButton("Ir al Centro de Control (Tablero)");
        btnIniciarTravesia.setActionCommand("INICIAR_TRAVESIA");
        btnIniciarTravesia.setEnabled(false);
        panelSur.add(btnIniciarTravesia);
        add(panelSur, BorderLayout.SOUTH);
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
            String info = String.format("ID: %s | Rol/Origen: %s | Sueldo: $%.2f\n",
                    t.getIdentidad(),
                    t.getClass().getSimpleName(),
                    t.getSueldo());
            areaTripulantes.append(info);
        }

        btnIniciarTravesia.setEnabled(modelo.puedeIniciarTravesia());
    }

    public String getTipoNaveSeleccionado() { return (String) comboTiposNave.getSelectedItem(); }
    public String getIdentidad() { return txtIdentidad.getText(); }
    public String getAntiguedad() { return txtAntiguedad.getText(); }
    public String getRolSeleccionado() { return (String) comboRol.getSelectedItem(); }
    public String getOrigenSeleccionado() { return (String) comboOrigen.getSelectedItem(); }

    public void setControlador(ActionListener c) {
        btnCrearNave.addActionListener(c);
        btnAgregarTripulante.addActionListener(c);
        btnIniciarTravesia.addActionListener(c);
    }

    public void arrancar() {
        setVisible(true);
    }
}