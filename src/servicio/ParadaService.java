package servicio;

import datastructures.TreeMap;
import modelo.Parada;

/**
 * Servicio para la entidad Parada que proporciona métodos para operaciones
 * CRUD.
 */
public interface ParadaService {

    /**
     * Inserta una nueva parada en el sistema.
     *
     * @param parada La parada a insertar.
     */
    void insertar(Parada parada);

    /**
     * Actualiza la información de una parada existente en el sistema.
     *
     * @param parada La parada con la información actualizada.
     */
    void actualizar(Parada parada);

    /**
     * Borra una parada del sistema.
     *
     * @param parada La parada a borrar.
     */
    void borrar(Parada parada);

    /**
     * Busca y devuelve todas las paradas existentes en el sistema.
     *
     * @return Un TreeMap que contiene todas las paradas, donde la clave es el
     *         código de la parada.
     */
    TreeMap<Integer, Parada> buscarTodos();
}
