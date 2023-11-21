package dao;

import datastructures.TreeMap;
import modelo.Parada;

/**
 * Esta interfaz define el contrato para realizar operaciones CRUD (Crear, Leer,
 * Actualizar, Borrar)
 * en objetos Parada en una fuente de datos.
 */
public interface ParadaDAO {

    /**
     * Inserta una nueva Parada en la fuente de datos.
     *
     * @param parada La instancia de Parada que se va a insertar.
     */
    void insertar(Parada parada);

    /**
     * Actualiza una Parada existente en la fuente de datos.
     *
     * @param parada La instancia de Parada que contiene la información actualizada.
     */
    void actualizar(Parada parada);

    /**
     * Elimina una Parada de la fuente de datos.
     *
     * @param parada La instancia de Parada que se va a eliminar.
     */
    void borrar(Parada parada);

    /**
     * Recupera todas las instancias de Parada desde la fuente de datos.
     *
     * @return Un TreeMap que contiene instancias de Parada, donde las claves son
     *         los códigos de las paradas.
     */
    TreeMap<Integer, Parada> buscarTodos();
}
