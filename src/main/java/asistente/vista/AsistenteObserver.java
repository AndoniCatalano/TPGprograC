package asistente.vista;

public interface AsistenteObserver {
    void onEventoBitacora(String mensaje);
    void onRecursosActualizados(int combustible, int energia, int desgaste);
    void onInformeGenerado(String informe);
}