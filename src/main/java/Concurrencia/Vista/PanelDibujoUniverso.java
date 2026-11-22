package Concurrencia.Vista;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class PanelDibujoUniverso extends JPanel{

    private static final Color COLOR_ESPACIO = new Color(9, 7, 24);
    private static final Color COLOR_GENERADORA = new Color(75, 220, 140);
    private static final Color COLOR_NAVE = new Color(80, 200, 255);
    private static final Color COLOR_ESPERA = new Color(255, 195, 70);
    private static final Color COLOR_PLATAFORMA = new Color(28, 22, 62);
    private static final Color COLOR_BORDE_PLATAFORMA = new Color(160, 115, 245);
    private static final Color COLOR_ALERTA = new Color(245, 95, 105);

    private final Map<String, String> estadoGeneradoras = new LinkedHashMap<>();
    private final Map<String, String> estadoNaves = new LinkedHashMap<>();

    // Estrellas fijas (en proporciones 0.0 a 1.0) para que no parpadeen al repintar
    private final double[] estrellasX = new double[45];
    private final double[] estrellasY = new double[45];

    private final Timer temporizadorHaz;

    private int stockActual = 0;
    private int capacidadMax = 3;
    private String entidadActiva = null;
    private String tipoAccionActiva = "";

    public PanelDibujoUniverso() {
        this.setBackground(COLOR_ESPACIO);
        this.setPreferredSize(new Dimension(880, 310));

        for (int i = 0; i < estrellasX.length; i++) {
            estrellasX[i] = Math.random();
            estrellasY[i] = Math.random();
        }

        // Apaga el haz de energía apenas concluye la transferencia
        this.temporizadorHaz = new Timer(600, e -> {
            this.entidadActiva = null;
            this.tipoAccionActiva = "";
            repaint();
        });
        this.temporizadorHaz.setRepeats(false);
    }

    /**
     * Interpreta cada mensaje del Observer y actualiza el estado visual.
     * Se ejecuta siempre en el hilo gráfico (EDT) desde VisorConcurrencia.
     */
    public void procesarEvento(String mensaje) {
        if (mensaje.contains("detenida")) {
            this.estadoGeneradoras.clear();
            this.estadoNaves.clear();
            limpiarHaz();
            repaint();
            return;
        }

        if (mensaje.contains("Ingresó una nueva Generadora")) {
            String nombre = extraerEntre(mensaje, "(", ")");
            if (nombre != null) {
                this.estadoGeneradoras.put(nombre, "PRODUCIENDO");
            }
        } else if (mensaje.contains("Ingresó una nueva Nave")) {
            String nombre = extraerEntre(mensaje, "(", ")");
            if (nombre != null) {
                this.estadoNaves.put(nombre, "SOLICITANDO");
            }
        } else {
            String nombre = extraerEntre(mensaje, "[", "]");
            if (nombre != null) {
                if (nombre.startsWith("Generadora")) {
                    if (mensaje.contains("esperando")) {
                        this.estadoGeneradoras.put(nombre, "ESPERANDO");
                        limpiarHazSiPertenece(nombre);
                    } else if (mensaje.contains("depositó")) {
                        this.estadoGeneradoras.put(nombre, "DEPOSITANDO");
                        activarHaz(nombre, "DEPOSITO");
                    } else if (mensaje.contains("solicitando")) {
                        this.estadoGeneradoras.put(nombre, "SOLICITANDO");
                        limpiarHazSiPertenece(nombre);
                    } else if (mensaje.contains("producida") || mensaje.contains("produciendo")) {
                        this.estadoGeneradoras.put(nombre, "PRODUCIENDO");
                        limpiarHazSiPertenece(nombre);
                    }
                } else if (nombre.startsWith("Nave")) {
                    if (mensaje.contains("esperando")) {
                        this.estadoNaves.put(nombre, "ESPERANDO");
                        limpiarHazSiPertenece(nombre);
                    } else if (mensaje.contains("retirada")) {
                        this.estadoNaves.put(nombre, "CONSUMIENDO");
                        activarHaz(nombre, "RETIRO");
                    } else if (mensaje.contains("solicitando")) {
                        this.estadoNaves.put(nombre, "SOLICITANDO");
                        limpiarHazSiPertenece(nombre);
                    }
                }
            }
        }

        actualizarStockDesdeMensaje(mensaje);
        repaint();
    }

    private void activarHaz(String nombre, String tipoAccion) {
        this.entidadActiva = nombre;
        this.tipoAccionActiva = tipoAccion;
        this.temporizadorHaz.restart();
    }

    private void limpiarHaz() {
        this.entidadActiva = null;
        this.tipoAccionActiva = "";
        this.temporizadorHaz.stop();
    }

    private void limpiarHazSiPertenece(String nombre) {
        if (nombre.equals(this.entidadActiva)) {
            limpiarHaz();
        }
    }

    private void actualizarStockDesdeMensaje(String mensaje) {
        int indiceStock = mensaje.indexOf("(Stock: ");
        if (indiceStock == -1) {
            return;
        }
        int inicio = indiceStock + "(Stock: ".length();
        int fin = mensaje.indexOf(")", inicio);
        if (fin == -1) {
            return;
        }
        String[] partes = mensaje.substring(inicio, fin).split("/");
        if (partes.length == 2) {
            try {
                this.stockActual = Integer.parseInt(partes[0].trim());
                this.capacidadMax = Integer.parseInt(partes[1].trim());
            } catch (NumberFormatException ignored) {
            }
        }
    }

    private String extraerEntre(String texto, String apertura, String cierre) {
        int inicio = texto.indexOf(apertura);
        if (inicio == -1) {
            return null;
        }
        int fin = texto.indexOf(cierre, inicio + apertura.length());
        if (fin == -1) {
            return null;
        }
        return texto.substring(inicio + apertura.length(), fin);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        // 1. Fondo espacial con estrellas fijas
        g2.setColor(COLOR_ESPACIO);
        g2.fillRect(0, 0, ancho, alto);
        g2.setColor(new Color(200, 220, 255, 130));
        for (int i = 0; i < estrellasX.length; i++) {
            int sx = (int) (estrellasX[i] * ancho);
            int sy = (int) (estrellasY[i] * alto);
            g2.fillOval(sx, sy, 2, 2);
        }

        // Coordenadas base de las 3 columnas
        int xGeneradoras = ancho / 6;
        int xCentro = ancho / 2;
        int xNaves = (ancho * 5) / 6;
        int yCentro = alto / 2 + 12;

        int anchoPlat = 225;
        int altoPlat = 155;
        int platX = xCentro - anchoPlat / 2;
        int platY = yCentro - altoPlat / 2;

        // Títulos de las columnas
        dibujarTituloColumna(g2, "GENERADORAS", xGeneradoras, 26, COLOR_GENERADORA);
        dibujarTituloColumna(g2, "PLATAFORMA DE CARGA", xCentro, 26, COLOR_BORDE_PLATAFORMA);
        dibujarTituloColumna(g2, "NAVES CONSUMIDORAS", xNaves, 26, COLOR_NAVE);

        // 2. Dibujar Generadoras (Izquierda) y haz si alguna está depositando
        dibujarListaGeneradoras(g2, xGeneradoras, 48, alto - 58, platX, yCentro);

        // 3. Dibujar Naves (Derecha) y haz si alguna está retirando
        dibujarListaNaves(g2, xNaves, 48, alto - 58, platX + anchoPlat, yCentro);

        // 4. Dibujar Plataforma de Carga (Centro)
        dibujarPlataforma(g2, platX, platY, anchoPlat, altoPlat);

        g2.dispose();
    }

    private void dibujarTituloColumna(Graphics2D g2, String texto, int centroX, int y, Color color) {
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.setColor(color);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(texto, centroX - fm.stringWidth(texto) / 2, y);
    }

    private void dibujarPlataforma(Graphics2D g2, int x, int y, int ancho, int alto) {
        Color colorBorde = COLOR_BORDE_PLATAFORMA;
        String textoEstado = "DISPONIBLE";
        if (stockActual >= capacidadMax && capacidadMax > 0) {
            colorBorde = COLOR_ESPERA;
            textoEstado = "PLATAFORMA LLENA";
        } else if (stockActual == 0) {
            colorBorde = COLOR_ALERTA;
            textoEstado = "PLATAFORMA VACÍA";
        }

        g2.setColor(COLOR_PLATAFORMA);
        g2.fillRoundRect(x, y, ancho, alto, 18, 18);
        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(2.2f));
        g2.drawRoundRect(x, y, ancho, alto, 18, 18);

        // Texto de stock arriba
        g2.setFont(new Font("SansSerif", Font.BOLD, 15));
        g2.setColor(Color.WHITE);
        String stockTexto = "Stock: " + stockActual + " / " + capacidadMax;
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(stockTexto, x + (ancho - fm.stringWidth(stockTexto)) / 2, y + 26);

        // Casilleros de baterías adentro de la plataforma
        int totalSlots = Math.max(1, capacidadMax);
        int margen = 16;
        int separacion = 10;
        int anchoDisponible = ancho - (margen * 2) - (separacion * (totalSlots - 1));
        int anchoSlot = Math.min(48, Math.max(20, anchoDisponible / totalSlots));
        int altoSlot = 66;
        int anchoTotalSlots = (anchoSlot * totalSlots) + (separacion * (totalSlots - 1));
        int inicioX = x + (ancho - anchoTotalSlots) / 2;
        int slotY = y + 40;

        for (int i = 0; i < totalSlots; i++) {
            int sx = inicioX + i * (anchoSlot + separacion);
            boolean ocupada = i < stockActual;
            dibujarBateriaSlot(g2, sx, slotY, anchoSlot, altoSlot, ocupada);
        }

        // Etiqueta inferior de estado
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.setColor(colorBorde);
        FontMetrics fmEstado = g2.getFontMetrics();
        g2.drawString(textoEstado, x + (ancho - fmEstado.stringWidth(textoEstado)) / 2, y + alto - 16);
    }

    private void dibujarBateriaSlot(Graphics2D g2, int x, int y, int ancho, int alto, boolean ocupada) {
        int borneAncho = ancho / 2;
        int borneAlto = 6;
        int cuerpoY = y + borneAlto;
        int cuerpoAlto = alto - borneAlto;

        if (ocupada) {
            g2.setColor(new Color(170, 255, 200));
            g2.fillRoundRect(x + (ancho - borneAncho) / 2, y, borneAncho, borneAlto + 1, 3, 3);

            g2.setColor(new Color(30, 175, 95));
            g2.fillRoundRect(x, cuerpoY, ancho, cuerpoAlto, 6, 6);

            g2.setColor(new Color(125, 255, 165));
            int barraAlto = Math.max(5, (cuerpoAlto - 18) / 3);
            for (int b = 0; b < 3; b++) {
                g2.fillRoundRect(x + 4, cuerpoY + 6 + b * (barraAlto + 3), ancho - 8, barraAlto, 3, 3);
            }

            g2.setColor(new Color(180, 255, 210));
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(x, cuerpoY, ancho, cuerpoAlto, 6, 6);
        } else {
            g2.setColor(new Color(90, 110, 145, 140));
            Stroke anterior = g2.getStroke();
            g2.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10f, new float[]{4f, 4f}, 0f));
            g2.drawRoundRect(x, cuerpoY, ancho, cuerpoAlto, 6, 6);
            g2.setStroke(anterior);
        }
    }

    private void dibujarListaGeneradoras(Graphics2D g2, int centroX, int yInicio, int alturaDisp,
                                         int platIzqX, int platCentroY) {
        if (estadoGeneradoras.isEmpty()) {
            dibujarTextoVacio(g2, "(Sin generadoras)", centroX, yInicio + alturaDisp / 2);
            return;
        }

        int cantidad = estadoGeneradoras.size();
        int paso = Math.min(64, alturaDisp / Math.max(1, cantidad));
        int inicioCentrado = yInicio + (alturaDisp - (paso * cantidad)) / 2 + paso / 2;

        int i = 0;
        for (Map.Entry<String, String> entrada : estadoGeneradoras.entrySet()) {
            String nombre = entrada.getKey();
            String estado = entrada.getValue();
            int cy = inicioCentrado + i * paso;

            if ("DEPOSITO".equals(tipoAccionActiva) && nombre.equals(entidadActiva)) {
                int inicioHazX = Math.min(centroX + 82, platIzqX - 18);
                dibujarHazEnergia(g2, inicioHazX, cy, platIzqX - 4, platCentroY, COLOR_GENERADORA);
            }

            Color color = "ESPERANDO".equals(estado) ? COLOR_ESPERA : COLOR_GENERADORA;
            dibujarIconoGeneradora(g2, centroX - 50, cy, color, "DEPOSITANDO".equals(estado));
            dibujarEtiquetaEntidad(g2, nombre, estado, centroX - 24, cy, color);
            i++;
        }
    }

    private void dibujarListaNaves(Graphics2D g2, int centroX, int yInicio, int alturaDisp,
                                   int platDerX, int platCentroY) {
        if (estadoNaves.isEmpty()) {
            dibujarTextoVacio(g2, "(Sin naves)", centroX, yInicio + alturaDisp / 2);
            return;
        }

        int cantidad = estadoNaves.size();
        int paso = Math.min(64, alturaDisp / Math.max(1, cantidad));
        int inicioCentrado = yInicio + (alturaDisp - (paso * cantidad)) / 2 + paso / 2;

        int i = 0;
        for (Map.Entry<String, String> entrada : estadoNaves.entrySet()) {
            String nombre = entrada.getKey();
            String estado = entrada.getValue();
            int cy = inicioCentrado + i * paso;

            if ("RETIRO".equals(tipoAccionActiva) && nombre.equals(entidadActiva)) {
                dibujarHazEnergia(g2, platDerX + 4, platCentroY, centroX - 70, cy, COLOR_NAVE);
            }

            Color color = "ESPERANDO".equals(estado) ? COLOR_ESPERA : COLOR_NAVE;
            dibujarIconoNave(g2, centroX - 50, cy, color);
            dibujarEtiquetaEntidad(g2, nombre, estado, centroX - 24, cy, color);
            i++;
        }
    }

    private void dibujarIconoGeneradora(Graphics2D g2, int cx, int cy, Color color, boolean activa) {
        int r = 15;
        int[] px = new int[6];
        int[] py = new int[6];
        for (int k = 0; k < 6; k++) {
            double angulo = Math.toRadians(60 * k);
            px[k] = cx + (int) (r * Math.cos(angulo));
            py[k] = cy + (int) (r * Math.sin(angulo));
        }

        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), activa ? 95 : 45));
        g2.fillPolygon(px, py, 6);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(activa ? 2.2f : 1.5f));
        g2.drawPolygon(px, py, 6);

        g2.fillOval(cx - 4, cy - 4, 8, 8);
    }

    private void dibujarIconoNave(Graphics2D g2, int cx, int cy, Color color) {
        int[] px = {cx - 16, cx + 13, cx + 7, cx + 13};
        int[] py = {cy, cy - 12, cy, cy + 12};

        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 65));
        g2.fillPolygon(px, py, 4);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1.6f));
        g2.drawPolygon(px, py, 4);

        g2.setColor(Color.WHITE);
        g2.fillOval(cx - 4, cy - 2, 5, 5);
    }

    private void dibujarEtiquetaEntidad(Graphics2D g2, String nombre, String estado, int x, int cy, Color color) {
        g2.setFont(new Font("SansSerif", Font.BOLD, 15));
        g2.setColor(Color.WHITE);
        g2.drawString(nombre, x, cy - 2);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2.setColor(color);
        g2.drawString(estado, x, cy + 14);
    }

    private void dibujarHazEnergia(Graphics2D g2, int x1, int y1, int x2, int y2, Color color) {
        Stroke anterior = g2.getStroke();
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 160));
        g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                10f, new float[]{6f, 5f}, 0f));
        g2.drawLine(x1, y1, x2, y2);

        g2.setColor(color);
        g2.fillOval(x2 - 4, y2 - 4, 8, 8);
        g2.setStroke(anterior);
    }

    private void dibujarTextoVacio(Graphics2D g2, String texto, int centroX, int y) {
        g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
        g2.setColor(new Color(120, 135, 165));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(texto, centroX - fm.stringWidth(texto) / 2, y);
    }

    public void reiniciarDibujo() {
        this.estadoGeneradoras.clear();
        this.estadoNaves.clear();
        this.stockActual = 0;
        limpiarHaz();
        repaint();
    }
}