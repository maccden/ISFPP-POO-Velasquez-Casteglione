package dao;

import datastructures.TreeMap;
import modelo.Linea;

public interface LineaDAO {
    void insertar(Linea linea);

    void actualizar(Linea linea);

    void borrar(Linea linea);

    TreeMap<String, Linea> buscarTodos();
}
