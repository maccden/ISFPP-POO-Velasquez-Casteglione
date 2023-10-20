package dao;

import datastructures.TreeMap;
import modelo.Parada;

public interface ParadaDAO {
    void insertar(Parada parada);

    void actualizar(Parada parada);

    void borrar(Parada parada);

    TreeMap<Integer, Parada> buscarTodos();
}
