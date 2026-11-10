import nave.INave;
import nave.NaveFactory;
import nave.TipoNaveInvalidoException;
import nave.OperacionRechazadaException;
import nave.IElementosUniverso;
import nave.Asteroide;
import nave.EstacionDeCarga;
import nave.Combate;
import nave.tablero.Tablero;
import nave.tablero.Destino;
import nave.tablero.DimensionesDeTableroInconsistentesException;
import nave.tablero.CantidadDeElementosSuperaCasillaException;
import nave.vista.PanelControlNave;

import javax.swing.*;
import java.util.ArrayList;

public class MainPruebaVistaTablero{

    public static void main(String[] args) {

        // 1. Preparar el Universo (Elementos)
        ArrayList<IElementosUniverso> elementosDelEspacio = new ArrayList<>();

        // Agregamos algunos elementos de prueba para que Destino los distribuya
        for (int i = 0; i < 5; i++) {
            elementosDelEspacio.add(new Asteroide());
            elementosDelEspacio.add(new EstacionDeCarga());
        }
        elementosDelEspacio.add(new Combate()); // Una nave enemiga

        try {
            // 2. Crear el Tablero y pasarle los elementos
            Tablero tablero = new Tablero(10, 10, elementosDelEspacio);
            Destino controladorDestino = new Destino(tablero);

            // 3. Crear Tu Nave Principal (Jugador)
            INave miNave = NaveFactory.getNave("exploradora");

            // 4. Crear la Vista (Monitor) y suscribirla a la nave
            PanelControlNave panelMonitor = new PanelControlNave(tablero);
            miNave.addObservador(panelMonitor);

            // Forzamos un primer aviso para que los Labels se dibujen con el valor inicial
            miNave.notifyObservadores();

            // 5. Armar la Ventana de Java Swing
            SwingUtilities.invokeLater(() -> {
                JFrame ventana = new JFrame("Panel de Control de la Nave (Vista 4)");
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ventana.setContentPane(panelMonitor);
                ventana.pack();
                ventana.setLocationRelativeTo(null); // Centrar en la pantalla
                ventana.setVisible(true);
            });

            // 6. SIMULACIÓN DE LA MISIÓN (Bucle de Turnos en otro hilo)
            new Thread(() -> {
                try {
                    System.out.println("Iniciando viaje simulado...");
                    for (int turno = 1; turno <= 10; turno++) {

                        // Pausa de 1 segundo para ver el avance paso a paso
                        Thread.sleep(1000);

                        // El tablero mueve sus piezas aleatoriamente (avisa a la vista)
                        controladorDestino.turnoDestino();

                        // Simulamos que tu nave avanzó y consumió combustible
                        miNave.consumeCombustible(5);

                        // Ocasionalmente sufre desgaste
                        if (turno % 3 == 0) {
                            miNave.incrementaDesgaste(15);
                        }
                    }
                    System.out.println("Fin de la simulación de viaje.");
                } catch (InterruptedException | OperacionRechazadaException e) {
                    System.err.println("La simulación se interrumpió: " + e.getMessage());
                }
            }).start();

        } catch (DimensionesDeTableroInconsistentesException | CantidadDeElementosSuperaCasillaException | TipoNaveInvalidoException e) {
            e.printStackTrace();
            System.err.println("Error grave en la inicialización: " + e.getMessage());
        }
    }
}