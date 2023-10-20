package servicio;

import datastructures.TreeMap;
import modelo.Linea;

public interface LineaService {
    void insertar(Linea linea);

    void actualizar(Linea linea);

    void borrar(Linea linea);

    TreeMap<String, Linea> buscarTodos();
}
