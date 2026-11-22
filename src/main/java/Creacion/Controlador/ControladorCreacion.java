package Creacion.Controlador;

import Creacion.Modelo.CapitanAsignadoException;
import Creacion.Modelo.ModeloCreacion;
import Creacion.Vista.VistaCreacion;
import asistente.controlador.ControlNaveController;
import nave.INave;
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


    public ControladorCreacion(){
        modelo = new ModeloCreacion();
        vista = new VistaCreacion(modelo);
    }

    public void iniciarVentana(){
        vista.arrancar();
        vista.setControlador(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "CREAR_NAVE":
                crearNave();
                break;
            case "AGREGAR_TRIPULANTE":
                agregarTripulante();
                break;
            case "INICIAR_TRAVESIA":
                INave nave = modelo.getNaveConfigurada();


                ControlNaveController controlNaveController = new ControlNaveController(nave.getAsistente());
                controlNaveController.iniciarVentana();
                vista.dispose();
                break;
            default:
                System.out.println("Comando desconocido: " + e.getActionCommand());
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
            int antiguedad = Integer.parseInt(vista.getAntiguedad());
            String identidad = vista.getIdentidad();
            String rol = vista.getRolSeleccionado();
            String origen = vista.getOrigenSeleccionado();

            if (modelo.getTieneCapitan() && rol.equals("Capitan")) {
                throw new CapitanAsignadoException();
            }

            if (identidad.trim().isEmpty()) {
                throw new IllegalArgumentException("La identidad no puede estar vacía.");
            }

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
        catch (CapitanAsignadoException ex){
            JOptionPane.showMessageDialog(vista,
                    "Ya hay un capitan asignado",
                    "Capitan ya asignado",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
}