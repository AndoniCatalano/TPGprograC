package nave.vista;
import javax.swing.JPanel;


public interface IVistaPanelControlNave {

    /**
     * Devuelve el panel central donde se dibuja el radar dinámicamente (en tiempo real).
     * @return El JPanel reservado para el tablero.
     */
    JPanel getPanelTablero();
}