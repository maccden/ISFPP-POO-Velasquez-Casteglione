package servicio;

import modelo.Tramo;

import java.util.List;

/**
 * Interfaz que define los métodos que debe proporcionar un servicio para la
 * entidad Tramo.
 */
public interface TramoService {

    /**
     * Inserta un nuevo tramo en la base de datos.
     *
     * @param tramo El tramo a insertar.
     */
    void insertar(Tramo tramo);

    /**
     * Actualiza la información de un tramo en la base de datos.
     *
     * @param tramo El tramo a actualizar.
     */
    void actualizar(Tramo tramo);

    /**
     * Elimina un tramo de la base de datos.
     *
     * @param tramo El tramo a eliminar.
     */
    void borrar(Tramo tramo);

    /**
     * Obtiene una lista de todos los tramos almacenados en la base de datos.
     *
     * @return Una lista de tramos.
     */
    List<Tramo> buscarTodos();
}
