package servicio;

import datastructures.TreeMap;
import modelo.Linea;

import java.io.IOException;

public interface LineaService {
    void insertar(Linea linea);

    void actualizar(Linea linea);

    void borrar(Linea linea);

    TreeMap<String, Linea> buscarTodos() throws IOException;
}
