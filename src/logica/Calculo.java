package logica;

import datastructures.*;
import model.*;

public class Calculo {

    private Graph<Parada, Tramo> colectivo;
    private TreeMap<String, Vertex<Parada>> vertices;

    public Calculo(TreeMap<String, Parada> estaciones, List<Tramo> tramos) {

        colectivo = new AdjacencyMapGraph<>(false);

        // Cargar paradas
        vertices = new TreeMap<String, Vertex<Parada>>();
        for (Entry<String, Parada> estacion : estaciones.entrySet())
            vertices.put(estacion.getKey(),
                    colectivo.insertVertex(estacion.getValue()));

        // Cargar tramos
        for (Tramo tramo : tramos)
            colectivo.insertEdge(vertices.get(tramo.getInicio().getCodigo()),
                    vertices.get(tramo.getFin().getCodigo()), tramo);
    }

    public List<Tramo> rapido(Parada inicio, Parada fin) {

        // copia grafo
        Graph<Parada, Integer> rapido = new AdjacencyMapGraph<>(false);
        Map<Parada, Vertex<Parada>> res = new ProbeHashMap<>();

        for (Vertex<Parada> result : colectivo.vertices())
            res.put(result.getElement(),
                    rapido.insertVertex(result.getElement()));

        Vertex<Parada>[] vert;

        for (Edge<Tramo> result : colectivo.edges()) {
            vert = colectivo.endVertices(result);
            rapido.insertEdge(res.get(vert[0].getElement()), res.get(vert[1].getElement()), result.getElement().getTiempo());
        }
        PositionalList<Vertex<Parada>> lista = GraphAlgorithms.shortestPathList(rapido, res.get(inicio), res.get(fin));

        List<Tramo> tramos = new ArrayList<Tramo>();

        /*
        Vertex<Parada> v1, v2;
        Position<Vertex<Parada>> aux = lista.first();
        while (lista.after(aux) != null) {
            v1 = aux.getElement();
            aux = lista.after(aux);
            v2 = aux.getElement();
            tramos.add(colectivo.getEdge(vertices.get(v1.getElement().getCodigo()), vertices.get(v2.getElement().getCodigo())).getElement());
        }
        */

        return tramos;


    }

}

/*
Cosas para anotar:

el peso de cada tramo


Armar un grafo por cada linea. Si el resultado es null, la linea no pasa por la parada <!>
Si hay 2 lineas que pasan por la parada, comparar cual es el mas rapido <!>

2 Aplicaciones, 1 para modificar, sacar e ingresar paradas, y la otra para consultar <!>






 */
