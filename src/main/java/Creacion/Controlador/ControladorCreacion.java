package Creacion.Controlador;

import Creacion.Modelo.ModeloCreacion;
import Creacion.Vista.VistaCreacion;
import nave.TipoNaveInvalidoException;
import Tripulantes.AntiguedadInvalidaException;
import Tripulantes.OrigenTripulanteException;
import Tripulantes.RolTripulanteInvalidoException;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCreacion implements ActionListener {

    private final VistaCreacion vista;
    private final ModeloCreacion modelo;

    public ControladorCreacion(VistaCreacion vista, ModeloCreacion modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "CREAR_NAVE":
                crearNave();
                break;
            case "AGREGAR_TRIPULANTE":
                agregarTripulante();
                break;
            case "INICIAR_TRAVESIA":
                vista.dispose();
                break;
            default:
                System.out.println("Comando desconocido: " + comando);
                break;
        }
    }

    private void crearNave() {
        try {
            String tipoNave = vista.getTipoNaveSeleccionado();
            modelo.instanciarNave(tipoNave);

            JOptionPane.showMessageDialog(vista,
                    "Nave " + tipoNave + " ensamblada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (TipoNaveInvalidoException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al crear la nave: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarTripulante() {
        try {
            String identidad = vista.getIdentidad();
            if (identidad == null || identidad.trim().isEmpty()) {
                throw new IllegalArgumentException("La identidad no puede estar vacía.");
            }

            int antiguedad = Integer.parseInt(vista.getAntiguedad());
            String rol = vista.getRolSeleccionado();
            String origen = vista.getOrigenSeleccionado();

            modelo.agregarTripulante(rol, origen, identidad, antiguedad);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                    "La antigüedad debe ser un número entero válido.",
                    "Dato Inválido",
                    JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(vista,
                    ex.getMessage(),
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        } catch (AntiguedadInvalidaException | OrigenTripulanteException | RolTripulanteInvalidoException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Fallo en la creación del tripulante: " + ex.getMessage(),
                    "Error de Negocio",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
}