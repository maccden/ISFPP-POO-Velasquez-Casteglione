package dao;

import datastructures.TreeMap;
import modelo.Linea;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface LineaDAO {
    void insertar(Linea linea);

    void actualizar(Linea linea);

    void borrar(Linea linea);

    TreeMap<String, Linea> buscarTodos();
}
