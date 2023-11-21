package servicio;

import datastructures.TreeMap;
import modelo.Linea;

/**
 * Interfaz que define los métodos de servicio para la entidad Linea.
 */
public interface LineaService {

    /**
     * Inserta una nueva línea en el servicio.
     *
     * @param linea La línea a insertar.
     */
    void insertar(Linea linea);

    /**
     * Actualiza la información de una línea en el servicio.
     *
     * @param linea La línea a actualizar.
     */
    void actualizar(Linea linea);

    /**
     * Elimina una línea del servicio.
     *
     * @param linea La línea a eliminar.
     */
    void borrar(Linea linea);

    /**
     * Busca y devuelve todas las líneas en el servicio.
     *
     * @return Un TreeMap que contiene todas las líneas.
     */
    TreeMap<String, Linea> buscarTodos();
}
