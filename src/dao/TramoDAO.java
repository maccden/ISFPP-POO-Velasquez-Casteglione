package dao;

import modelo.Tramo;

import java.util.List;

public interface TramoDAO {
    void insertar(Tramo tramo);

    void actualizar(Tramo tramo);

    void borrar(Tramo tramo);

    List<Tramo> buscarTodos();
}
