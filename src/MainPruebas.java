import Tripulantes.Alferez;
import Tripulantes.Capitan;
import Tripulantes.Consejero;
import Tripulantes.Marciano;
import Tripulantes.Teniente;
import Tripulantes.Terricola;
import Tripulantes.Tripulante;
import Tripulantes.Vulcano;
import asistente.Asistente;
import misiones.M01;
import misiones.M02;
import misiones.M03;
import nave.Nave;
import nave.NaveFactory;
import nave.OperacionRechazadaException;
import nave.TipoNaveInvalidoException;

import java.util.ArrayList;

public class MainPruebas {
    public static void main(String[] args)
            throws TipoNaveInvalidoException, OperacionRechazadaException {
            Sistema sistema = Sistema.getSistema();
            probarLimites();

        String[] tipos = {
                "carguero", "combate", "exploradora",
                "carguero", "combate", "exploradora",
                "carguero", "combate", "exploradora",
                "carguero", "combate", "exploradora",
                "carguero", "combate", "exploradora"
        };
        int cantidadInicial = sistema.getNaves().size();

        System.out.println("\n=== Creación e inserción de naves de prueba ===");
        for (String tipo : tipos) {
            sistema.addNave(tipo);
        }

        ArrayList<Nave> naves = sistema.getNaves();
        validar(naves.size() == cantidadInicial + tipos.length,
                "No se insertaron todas las naves en el sistema.");
        validar(tipos.length == 15, "La prueba debe crear 15 naves.");

        for (int i = 0; i < tipos.length; i++) {
            Nave nave = naves.get(cantidadInicial + i);
            System.out.printf("Nave-%02d (%s) agregada: combustible=%d, energía=%d%n",
                    i + 1,
                    nave.getClass().getSimpleName(),
                    nave.getCombustible(),
                    nave.getEnergia());
        }

        for (int i = 0; i < tipos.length; i++) {
            Nave nave = naves.get(cantidadInicial + i);
            System.out.printf("%n--- Tripulación de Nave-%02d (%s) ---%n",
                    i + 1,
                    nave.getClass().getSimpleName());
            cargarTripulacion(nave.getAsistente(), i + 1);
        }

        System.out.println("\n=== Misiones y escenarios por nave ===");
        for (int i = 0; i < tipos.length; i++) {
            Nave nave = naves.get(cantidadInicial + i);
            Asistente asistente = nave.getAsistente();
            String etiqueta = String.format("Nave-%02d", i + 1);
            prepararEscenario(asistente, nave, etiqueta, i % 5);

            misiones.Mision mision = crearMision(i % 3);
            System.out.printf("%n%s (%s) -> %s%n",
                    etiqueta,
                    nave.getClass().getSimpleName(),
                    mision.getClass().getSimpleName());
            mostrarEstado(nave, "Antes de la misión");
            asistente.registrarEventoBitacora("Inicio del ciclo de " + mision.getClass().getSimpleName());
            sistema.ejecutarMision(nave, mision);
            asistente.registrarEventoBitacora("Fin del ciclo de " + mision.getClass().getSimpleName());
            mostrarEstado(nave, "Después de la misión");
        }

        System.out.println("\n=== Bitácora completa, separada por nave ===");
        for (int i = 0; i < tipos.length; i++) {
            Nave nave = naves.get(cantidadInicial + i);
            System.out.printf("%n--- Nave-%02d (%s) --- %n",
                    i + 1,
                    nave.getClass().getSimpleName());
            nave.getAsistente().mostrarBitacora();
        }
    }

    private static void cargarTripulacion(Asistente asistente, int numeroNave) {
        String sufijo = String.format("%02d", numeroNave);
        Tripulante alferez = new Terricola(new Alferez("A-" + sufijo, 5));
        Tripulante teniente = new Marciano(new Teniente("T-" + sufijo, 10));
        Tripulante capitan = new Vulcano(new Capitan("C-" + sufijo, 3));
        Consejero consejero = new Consejero("CO-" + sufijo, 4);
        consejero.addConsejos(2);;
        Tripulante consejeroTerricola = new Terricola(consejero);

        asistente.addTripulante(alferez);
        asistente.addTripulante(teniente);
        asistente.addTripulante(capitan);
        asistente.addTripulante(consejeroTerricola);
        imprimirSueldo(alferez);
        imprimirSueldo(teniente);
        imprimirSueldo(capitan);
        imprimirSueldo(consejeroTerricola);
        System.out.print("Total liquidado: ");
        asistente.mostrarSueldos();
    }

    private static void prepararEscenario(Asistente asistente, Nave nave, String etiqueta, int escenario)
            throws OperacionRechazadaException {
        asistente.registrarEventoBitacora("Prueba " + etiqueta + ": escenario " + (escenario + 1));
        switch (escenario) {
            case 0:
                asistente.registrarEventoBitacora("Escenario normal: preparar recursos para misión.");
                break;
            case 1:
                asistente.registrarEventoBitacora("Escenario sin combustible: consumir reserva completa.");
                nave.consumeCombustible(nave.getCombustible());
                break;
            case 2:
                asistente.registrarEventoBitacora("Escenario de entradas inválidas.");
                asistente.cargaCombustible(-5);
                asistente.consumeEnergia(nave.getEnergia() + 1);
                break;
            case 3:
                asistente.registrarEventoBitacora("Escenario de mantenimiento al alcanzar desgaste 80.");
                nave.incrementaDesgaste(80);
                nave.realizarMantenimiento();
                break;
            case 4:
                asistente.registrarEventoBitacora("Escenario de energía al máximo.");
                if (nave.getEnergia() < 100) {
                    nave.cargaEnergia(100 - nave.getEnergia());
                }
                break;
            default:
                throw new IllegalArgumentException("Escenario desconocido: " + escenario);
        }

        if (escenario != 1) {
            nave.incrementaDesgaste(4);
        }
    }

    private static misiones.Mision crearMision(int indice) {
        switch (indice) {
            case 0:
                return new M01();
            case 1:
                return new M02();
            case 2:
                return new M03();
            default:
                throw new IllegalArgumentException("Índice de misión desconocido: " + indice);
        }
    }

    private static void mostrarEstado(Nave nave, String momento) {
        System.out.printf("%s: combustible=%d, energía=%d, desgaste=%d, mantenimiento=%s%n",
                momento,
                nave.getCombustible(),
                nave.getEnergia(),
                nave.getDesgaste(),
                nave.getNecesitaMantenimiento());
    }

    private static void imprimirSueldo(Tripulante tripulante) {
        System.out.printf("%-20s sueldo=$%.2f%n",
                tripulante.getIdentidad(),
                tripulante.getSueldo());
    }

    private static void validar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static void probarLimites()
            throws TipoNaveInvalidoException, OperacionRechazadaException {
        Nave nave = (Nave) NaveFactory.getNave("carguero");

        System.out.println("=== Pruebas de límites ===");
        debeRechazar("Combustible por encima de 100", () -> nave.cargaCombustible(1));
        debeRechazar("Carga de combustible cero", () -> nave.cargaCombustible(0));
        debeRechazar("Carga de combustible negativa", () -> nave.cargaCombustible(-1));
        validar(nave.getCombustible() == 100, "Una carga inválida modificó el combustible.");

        nave.consumeCombustible(100);
        validar(nave.getCombustible() == 0, "Debe permitirse consumir el combustible exacto disponible.");
        debeRechazar("Consumo de combustible por encima del disponible", () -> nave.consumeCombustible(1));
        debeRechazar("Consumo de combustible cero", () -> nave.consumeCombustible(0));
        debeRechazar("Consumo de combustible negativo", () -> nave.consumeCombustible(-1));
        nave.cargaCombustible(100);
        validar(nave.getCombustible() == 100, "Debe permitirse cargar hasta la capacidad exacta.");

        nave.cargaEnergia(40);
        validar(nave.getEnergia() == 100, "Debe permitirse cargar energía hasta 100.");
        debeRechazar("Energía por encima de 100", () -> nave.cargaEnergia(1));
        nave.consumeEnergia(100);
        validar(nave.getEnergia() == 0, "Debe permitirse consumir toda la energía disponible.");
        debeRechazar("Consumo de energía por encima del disponible", () -> nave.consumeEnergia(1));
        debeRechazar("Carga de energía cero", () -> nave.cargaEnergia(0));
        debeRechazar("Carga de energía negativa", () -> nave.cargaEnergia(-1));

        debeRechazar("Incremento de desgaste cero", () -> nave.incrementaDesgaste(0));
        debeRechazar("Incremento de desgaste negativo", () -> nave.incrementaDesgaste(-1));
        nave.incrementaDesgaste(79);
        validar(nave.getDesgaste() == 79 && !nave.getNecesitaMantenimiento(),
                "El mantenimiento no debe activarse antes de desgaste 80.");
        debeRechazar("Mantenimiento por debajo de 80", nave::realizarMantenimiento);
        nave.incrementaDesgaste(1);
        validar(nave.getDesgaste() == 80 && nave.getNecesitaMantenimiento(),
                "El mantenimiento debe activarse exactamente en desgaste 80.");
        nave.realizarMantenimiento();
        validar(nave.getDesgaste() == 0 && !nave.getNecesitaMantenimiento(),
                "El mantenimiento debe reiniciar el desgaste y su indicador.");

        nave.incrementaDesgaste(101);
        validar(nave.getDesgaste() == 100 && nave.getNecesitaMantenimiento(),
                "El desgaste debe limitarse a 100.");
        nave.realizarMantenimiento();
        nave.incrementaDesgaste(1);
        nave.incrementaDesgaste(Integer.MAX_VALUE);
        validar(nave.getDesgaste() == 100 && nave.getNecesitaMantenimiento(),
                "Un incremento grande no debe desbordar el desgaste.");

        System.out.println("Pruebas de límites completadas correctamente.");
    }

    private static void debeRechazar(String caso, OperacionNave operacion) {
        try {
            operacion.ejecutar();
        } catch (OperacionRechazadaException esperado) {
            System.out.println("OK: " + caso);
            return;
        }
        throw new AssertionError("Se esperaba el rechazo: " + caso);
    }

    @FunctionalInterface
    private interface OperacionNave {
        void ejecutar() throws OperacionRechazadaException;
    }
}
