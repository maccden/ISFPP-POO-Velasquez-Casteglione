package dao;

import datastructures.TreeMap;
import modelo.Linea;

/**
 * Esta interfaz define el contrato para realizar operaciones CRUD (Crear, Leer,
 * Actualizar, Borrar)
 * en objetos Linea (línea de autobús) en una fuente de datos.
 */
public interface LineaDAO {

    /**
     * Inserta una nueva Linea en la fuente de datos.
     *
     * @param linea La instancia de Linea que se va a insertar.
     */
    void insertar(Linea linea);

    /**
     * Actualiza una Linea existente en la fuente de datos.
     *
     * @param linea La instancia de Linea que contiene la información actualizada.
     */
    void actualizar(Linea linea);

    /**
     * Elimina una Linea de la fuente de datos.
     *
     * @param linea La instancia de Linea que se va a eliminar.
     */
    void borrar(Linea linea);

    /**
     * Recupera todas las instancias de Linea desde la fuente de datos.
     *
     * @return Un TreeMap que contiene instancias de Linea, donde las claves son los
     *         códigos de las líneas.
     */
    TreeMap<String, Linea> buscarTodos();
}
