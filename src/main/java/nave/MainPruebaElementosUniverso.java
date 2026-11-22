package nave;

import asistente.Asistente;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainPruebaElementosUniverso {
    private static final String[] TIPOS_NAVE = {"CARGUERO", "EXPLORADORA", "COMBATE"};
    private static final List<String> fallas = new ArrayList<>();

    public static void main(String[] args) throws TipoNaveInvalidoException {
        probarAsteroideYEstacion();
        probarDobleDespacho();
        if (fallas.isEmpty()) {
            System.out.println("Todas las pruebas de elementos del universo finalizaron correctamente.");
        } else {
            throw new IllegalStateException("Pruebas fallidas (" + fallas.size() + "):\n- " +
                    String.join("\n- ", fallas));
        }
    }

    private static void probarAsteroideYEstacion() throws TipoNaveInvalidoException {
        for (String tipoNave : TIPOS_NAVE) {
            INave nave = NaveFactory.getNave(tipoNave);
            Asistente asistente = nave.getAsistente();
            int energiaInicial = asistente.consultarEnergia();

            Asteroide asteroide = new Asteroide();
            verificar(asteroide.esAmenaza(asistente), "El asteroide debe ser una amenaza para " + tipoNave);
            asteroide.interactuarCon(asistente);
            verificar(asistente.consultarEnergia() == energiaInicial - 30,
                    "El asteroide no descontó 30 de energía a " + tipoNave);

            nave = NaveFactory.getNave(tipoNave);
            asistente = nave.getAsistente();
            int combustibleInicial = asistente.consultarCombustible();
            energiaInicial = asistente.consultarEnergia();

            EstacionDeCarga estacion = new EstacionDeCarga();
            verificar(!estacion.esAmenaza(asistente), "La estación no debe ser una amenaza para " + tipoNave);
            estacion.interactuarCon(asistente);
            verificar(asistente.consultarCombustible() == Math.min(100, combustibleInicial + (int) (combustibleInicial * 0.3)),
                    "La estación recargó incorrectamente el combustible de " + tipoNave);
            verificar(asistente.consultarEnergia() == Math.min(100, energiaInicial + (int) (energiaInicial * 0.3)),
                    "La estación recargó incorrectamente la energía de " + tipoNave);

            System.out.println("Asteroide y estación: OK para " + tipoNave);
        }
    }

    private static void probarDobleDespacho() throws TipoNaveInvalidoException {
        for (String tipoNave : TIPOS_NAVE) {
            for (String tipoEnemigo : TIPOS_NAVE) {
                INave nave = NaveFactory.getNave(tipoNave);
                Asistente asistente = nave.getAsistente();
                boolean amenazaEsperada = esDerrota(tipoNave, tipoEnemigo);
                int energiaInicial = asistente.consultarEnergia();
                int energiaEsperada = amenazaEsperada ? 0 : energiaInicial;

                NaveEnemiga enemiga = crearNaveEnemiga(tipoEnemigo);
                boolean amenazaObtenida = enemiga.esAmenaza(asistente);
                verificar(amenazaObtenida == amenazaEsperada,
                        "Amenaza " + tipoNave + " vs. " + tipoEnemigo +
                                ": esperada=" + amenazaEsperada + ", obtenida=" + amenazaObtenida);
                enemiga.interactuarCon(asistente);
                int energiaObtenida = asistente.consultarEnergia();
                boolean dobleDespachoCorrecto = energiaObtenida == energiaEsperada;
                verificar(dobleDespachoCorrecto,
                        "Doble despacho " + tipoNave + " vs. " + tipoEnemigo +
                                ": energía esperada=" + energiaEsperada + ", obtenida=" + energiaObtenida);

                System.out.println(tipoNave + " vs. " + tipoEnemigo +
                        " | amenaza esperada=" + amenazaEsperada +
                        ", obtenida=" + amenazaObtenida +
                        " | doble despacho=" + (dobleDespachoCorrecto ? "OK" : "FALLÓ"));
            }
        }
    }

    private static NaveEnemiga crearNaveEnemiga(String tipo) {
        NaveEnemiga enemiga = new NaveEnemiga();
        try {
            Field campoTipo = NaveEnemiga.class.getDeclaredField("tipo");
            campoTipo.setAccessible(true);
            campoTipo.set(enemiga, tipo);
            return enemiga;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("No se pudo preparar una nave enemiga de tipo " + tipo, e);
        }
    }

    private static boolean esDerrota(String tipoNave, String tipoEnemigo) {
        return ("CARGUERO".equals(tipoNave) && "EXPLORADORA".equals(tipoEnemigo)) ||
                ("EXPLORADORA".equals(tipoNave) && "COMBATE".equals(tipoEnemigo)) ||
                ("COMBATE".equals(tipoNave) && "CARGUERO".equals(tipoEnemigo));
    }

    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) {
            fallas.add(mensaje);
            System.out.println("FALLÓ: " + mensaje);
        }
    }
}
