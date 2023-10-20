package servicio;

import datastructures.TreeMap;
import modelo.Parada;

public interface ParadaService {
    void insertar(Parada parada);

    void actualizar(Parada parada);

    void borrar(Parada parada);

    TreeMap<Integer, Parada> buscarTodos();
}
