package dao;

import modelo.Tramo;

import java.util.List;

/**
 * Esta interfaz define el contrato para realizar operaciones CRUD (Crear, Leer,
 * Actualizar, Borrar)
 * en objetos Tramo en una fuente de datos.
 */
public interface TramoDAO {

    /**
     * Inserta un nuevo Tramo en la fuente de datos.
     *
     * @param tramo La instancia de Tramo que se va a insertar.
     */
    void insertar(Tramo tramo);

    /**
     * Actualiza un Tramo existente en la fuente de datos.
     *
     * @param tramo La instancia de Tramo que contiene la información actualizada.
     */
    void actualizar(Tramo tramo);

    /**
     * Elimina un Tramo de la fuente de datos.
     *
     * @param tramo La instancia de Tramo que se va a eliminar.
     */
    void borrar(Tramo tramo);

    /**
     * Recupera todas las instancias de Tramo desde la fuente de datos.
     *
     * @return Una lista que contiene instancias de Tramo.
     */
    List<Tramo> buscarTodos();
}
