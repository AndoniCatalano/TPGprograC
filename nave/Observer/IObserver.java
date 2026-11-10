package nave.Observer;

import nave.INave;

public interface IObserver {
    /**
     * Se invoca automáticamente cada vez que la nave cambia su estado (recursos, desgaste, etc.).
     * @param nave La instancia de la nave para extraer los datos actualizados (Estilo "pull").
     */
    void actualizarRecursosNave(INave nave);
}