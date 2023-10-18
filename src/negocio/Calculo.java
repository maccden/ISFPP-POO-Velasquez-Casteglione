package negocio;

import controlador.Coordinador;
import datastructures.*;
import modelo.*;

public class Calculo {
    private Graph<Parada, Tramo> grafo;
    private Coordinador coordinador;
    private TreeMap<String, Vertex<Parada>> vertices;

    public Calculo() {

    }

    public void cargarDatos(TreeMap<String, Parada> paradas, List<Tramo> tramos) {
        grafo = new AdjacencyMapGraph<>(true);
        vertices = new TreeMap<String, Vertex<Parada>>();

        for (Entry<String, Parada> parada : paradas.entrySet())
            vertices.put(parada.getKey(), grafo.insertVertex(parada.getValue()));

        for (Tramo tramo : tramos)
            if (grafo.getEdge(vertices.get(tramo.getInicio().getCodigo()),
                    vertices.get(tramo.getFin().getCodigo())) == null)
                grafo.insertEdge(vertices.get(tramo.getInicio().getCodigo()), vertices.get(tramo.getFin().getCodigo()),
                        tramo);
    }

    public List<Tramo> rapido(Parada inicio, Parada fin) {
        // copia grafo
        Graph<Parada, Integer> rapido = new AdjacencyMapGraph<>(true);
        Map<Parada, Vertex<Parada>> res = new ProbeHashMap<>();
        Vertex<Parada>[] vert;

        for (Vertex<Parada> vertex : grafo.vertices())
            res.put(vertex.getElement(), rapido.insertVertex(vertex.getElement()));

        for (Edge<Tramo> peso : grafo.edges()) {
            vert = grafo.endVertices(peso);
            rapido.insertEdge(res.get(vert[0].getElement()), res.get(vert[1].getElement()),
                    peso.getElement().getTiempo());
        }

        PositionalList<Vertex<Parada>> lista = GraphAlgorithms.shortestPathList(rapido, res.get(inicio), res.get(fin));
        List<Tramo> tramos = new ArrayList<Tramo>();
        Position<Vertex<Parada>> v1, v2;

        for (Position<Vertex<Parada>> position : lista.positions())
            if (lista.after(position) != null) {
                v1 = position;
                v2 = lista.after(v1);
                tramos.add(tramos.size(), grafo.getEdge(vertices.get(v1.getElement().getElement().getCodigo()),
                        vertices.get(v2.getElement().getElement().getCodigo())).getElement());
            }
        return tramos;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}

/*
 * Cosas para anotar:
 * 
 * el peso de cada tramo
 * 
 * Armar un grafo por cada linea. Si el resultado es null, la linea no pasa por
 * la parada <!>
 * Si hay 2 lineas que pasan por la parada, comparar cual es el mas rapido <!>
 * 
 * 2 Aplicaciones, 1 para modificar, sacar e ingresar paradas, y la otra para
 * consultar <!>
 */