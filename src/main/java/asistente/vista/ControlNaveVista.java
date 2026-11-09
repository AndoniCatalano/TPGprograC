package asistente.vista;


import javax.swing.*;
import java.awt.*;

public class ControlNaveVista extends JPanel implements AsistenteObserver{
    private JProgressBar barraCombustible = new JProgressBar(0, 100);
    private JProgressBar barraEnergia = new JProgressBar(0, 100);
    private JProgressBar barraDesgaste = new JProgressBar(0, 100);

    private JTextArea areaBitacora = new JTextArea(10, 30);
    private JTextArea areaInformes = new JTextArea(10, 30);

    private JComboBox<String> comboMisiones = new JComboBox<>(new String[]{"M-01: Asistencia", "M-02: Recolección", "M-03: Retorno"});
    private JButton btnIniciar = new JButton("Iniciar Misión");

    public ControlNaveVista() {
        setLayout(new BorderLayout());

        JPanel panelRecursos = new JPanel(new GridLayout(1, 3, 10, 10));
        panelRecursos.setBorder(BorderFactory.createTitledBorder("Estado de Recursos"));
        panelRecursos.add(crearPanelBarra("Combustible", barraCombustible));
        panelRecursos.add(crearPanelBarra("Energía", barraEnergia));
        panelRecursos.add(crearPanelBarra("Desgaste", barraDesgaste));
        add(panelRecursos, BorderLayout.NORTH);

        areaBitacora.setEditable(false);
        areaInformes.setEditable(false);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(areaBitacora), new JScrollPane(areaInformes));
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        JPanel panelControles = new JPanel();
        panelControles.add(new JLabel("Seleccionar Misión:"));
        panelControles.add(comboMisiones);
        panelControles.add(btnIniciar);
        add(panelControles, BorderLayout.SOUTH);
    }

    private JPanel crearPanelBarra(String titulo, JProgressBar barra) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(titulo), BorderLayout.NORTH);
        barra.setStringPainted(true);
        p.add(barra, BorderLayout.CENTER);
        return p;
    }

    public JButton getBtnIniciar() { return btnIniciar; }
    public String getMisionSeleccionada() { return (String) comboMisiones.getSelectedItem(); }

    // --- MÉTODOS SOBRESCRITOS DEL OBSERVER ---
    @Override
    public void onRecursosActualizados(int combustible, int energia, int desgaste) {
        barraCombustible.setValue(combustible);
        barraEnergia.setValue(energia);
        barraDesgaste.setValue(desgaste);
    }

    @Override
    public void onEventoBitacora(String evento) {
        areaBitacora.append(evento + "\n");
        areaBitacora.setCaretPosition(areaBitacora.getDocument().getLength());
    }

    @Override
    public void onInformeGenerado(String informe) {
        areaInformes.append(informe + "\n\n");
        areaInformes.setCaretPosition(areaInformes.getDocument().getLength());
    }
}