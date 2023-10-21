package negocio;

import controlador.Constantes;
import controlador.Coordinador;
import datastructures.*;
import modelo.Linea;
import modelo.Parada;
import modelo.Tramo;

import java.util.List;
import java.util.ArrayList;

public class Calculo {
    private static final int CAMBIO_LINEA = 10000;
    private Graph<ParadaLinea, Integer> red;
    private TreeMap<String, Vertex<ParadaLinea>> vertices;
    private TreeMap<String, Linea> lineaMap;
    private TreeMap<Integer, Parada> paradaMap;
    private TreeMap<String, Tramo> tramoMap;
    private Coordinador coordinador;

    public Calculo() {

    }

    public void cargarDatos (TreeMap<Integer, Parada> paradaMap, TreeMap<String, Linea> lineaMap, List<Tramo> tramos) {

        // Map parada
        this.paradaMap = paradaMap;

        // Map Linea
        this.lineaMap = lineaMap;

        // Map tramo
        tramoMap = new TreeMap<String, Tramo>();
        for (Tramo t : tramos)
            tramoMap.put(t.getInicio().getCodigo() + "-" + t.getFin().getCodigo(), t);

        // Map paradaLinea
        TreeMap<String, ParadaLinea> paradaLinea = new TreeMap<String, ParadaLinea>();
        for (Parada p : paradaMap.values())
            for (Linea l : p.getLineas())
                paradaLinea.put(p.getCodigo() + l.getCodigo(), new ParadaLinea(p.getCodigo(), l.getCodigo()));

        // Cargar paradas caminando
        for (Tramo t : tramos)
            if (t.getTipo() == Constantes.TRAMO_CAMINANDO) {
                paradaLinea.put(t.getInicio().getCodigo() + "", new ParadaLinea(t.getInicio().getCodigo(), ""));
                paradaLinea.put(t.getFin().getCodigo() + "", new ParadaLinea(t.getFin().getCodigo(), ""));
            }

        red = new AdjacencyMapGraph<>(true);

        // Cargar paradas
        vertices = new TreeMap<String, Vertex<ParadaLinea>>();
        for (Entry<String, ParadaLinea> pl : paradaLinea.entrySet())
            vertices.put(pl.getKey(), red.insertVertex(pl.getValue()));

        // Cargar tramos
        Parada origen, destino;
        for (Linea l : lineaMap.values()) {
            for (int i = 0; i < l.getParadasIda().size() - 1; i++) {
                origen = l.getParadasIda().get(i);
                destino = l.getParadasIda().get(i + 1);
                red.insertEdge(vertices.get(origen.getCodigo() + l.getCodigo()), vertices.get(destino.getCodigo() + l.getCodigo()), tramoMap.get(origen.getCodigo() + "-" + destino.getCodigo()).getTiempo());
            }
            for (int i = 0; i < l.getParadasVuelta().size() - 1; i++) {
                origen = l.getParadasVuelta().get(i);
                destino = l.getParadasVuelta().get(i + 1);
                red.insertEdge(vertices.get(origen.getCodigo() + l.getCodigo()),
                        vertices.get(destino.getCodigo() + l.getCodigo()),
                        tramoMap.get(origen.getCodigo() + "-" + destino.getCodigo()).getTiempo());
            }
        }

        // Cargar cambio de linea
        for (Parada p : paradaMap.values())
            for (Linea ori : p.getLineas())
                for (Linea des : p.getLineas())
                    if (!ori.equals(des))
                        if (red.getEdge(vertices.get(p.getCodigo() + ori.getCodigo()),
                                vertices.get(p.getCodigo() + des.getCodigo())) == null)
                            red.insertEdge(vertices.get(p.getCodigo() + ori.getCodigo()),
                                    vertices.get(p.getCodigo() + des.getCodigo()), CAMBIO_LINEA);

        // Cargar tramos caminando
        for (Tramo t : tramos)
            if (t.getTipo() == Constantes.TRAMO_CAMINANDO) {
                red.insertEdge(vertices.get(t.getInicio().getCodigo() + ""), vertices.get(t.getFin().getCodigo() + ""),
                        t.getTiempo());
                for (Linea ori : t.getInicio().getLineas()) {
                    red.insertEdge(vertices.get(t.getInicio().getCodigo() + ""),
                            vertices.get(t.getInicio().getCodigo() + ori.getCodigo()), CAMBIO_LINEA);
                    red.insertEdge(vertices.get(t.getInicio().getCodigo() + ori.getCodigo()),
                            vertices.get(t.getInicio().getCodigo() + ""), 0);
                }
                for (Linea des : t.getFin().getLineas()) {
                    red.insertEdge(vertices.get(t.getFin().getCodigo() + ""),
                            vertices.get(t.getFin().getCodigo() + des.getCodigo()), CAMBIO_LINEA);
                    red.insertEdge(vertices.get(t.getFin().getCodigo() + des.getCodigo()),
                            vertices.get(t.getFin().getCodigo() + ""), 0);
                }
            }
    }

    public List<Tramo> masRapido(Parada paradaOrigen, Parada paradaDestino) {
        // copia grafo
        Graph<ParadaLinea, Integer> copia = new AdjacencyMapGraph<>(true);
        Map<ParadaLinea, Vertex<ParadaLinea>> res = new ProbeHashMap<>();

        for (Vertex<ParadaLinea> result : red.vertices())
            res.put(result.getElement(), copia.insertVertex(result.getElement()));

        Vertex<ParadaLinea>[] vert;

        for (Edge<Integer> result : red.edges()) {
            vert = red.endVertices(result);
            copia.insertEdge(res.get(vert[0].getElement()), res.get(vert[1].getElement()), result.getElement());
        }

        // Agregar vertice inicio y fin
        ParadaLinea plOrigen = new ParadaLinea(0, "Inicio");
        Vertex<ParadaLinea> origen = copia.insertVertex(plOrigen);
        res.put(plOrigen, origen);
        for (Linea l : paradaOrigen.getLineas())
            if (copia.getEdge(origen, res.get(vertices.get(paradaOrigen.getCodigo() + l.getCodigo()).getElement())) == null)
                copia.insertEdge(origen, res.get(vertices.get(paradaOrigen.getCodigo() + l.getCodigo()).getElement()), 0);

        ParadaLinea plDestino = new ParadaLinea(0, "Fin");
        Vertex<ParadaLinea> destino = copia.insertVertex(plDestino);
        res.put(plDestino, destino);
        for (Linea l : paradaDestino.getLineas())
            if (copia.getEdge(res.get(vertices.get(paradaDestino.getCodigo() + l.getCodigo()).getElement()), destino) == null)
                copia.insertEdge(res.get(vertices.get(paradaDestino.getCodigo() + l.getCodigo()).getElement()), destino, 0);

        // Calcular camino mas corto
        PositionalList<Vertex<ParadaLinea>> lista = GraphAlgorithms.shortestPathList(copia, origen, destino);

        List<Tramo> tramos = new ArrayList<Tramo>();
        List<Integer> tiempos = new ArrayList<Integer>();
        List<Integer> paradas = new ArrayList<Integer>();
        List<String> lineas = new ArrayList<String>();

        Vertex<ParadaLinea> v1, v2 = null;
        Position<Vertex<ParadaLinea>> aux = lista.first();
        while (lista.after(aux) != null) {
            v1 = aux.getElement();
            aux = lista.after(aux);
            v2 = aux.getElement();
            tiempos.add(copia.getEdge(res.get(v1.getElement()), res.get(v2.getElement())).getElement());
            paradas.add(v1.getElement().getParada());
            lineas.add(v1.getElement().getLinea());

        }

        // System.out.println(copia);
        // System.out.println(tiempos);
        // System.out.println(paradas);
        // System.out.println(lineas);

        Tramo t;
        TreeMap<Integer, Parada> pMap = new TreeMap<Integer, Parada>();
        for (int i = 1; i < paradas.size(); i++) {
            if (pMap.get(paradas.get(i)) == null) {
                Parada p = paradaMap.get(paradas.get(i));
                pMap.put(p.getCodigo(), new Parada(p.getCodigo(), p.getDireccion()));
            }
            pMap.get(paradas.get(i)).setLinea(lineaMap.get((lineas.get(i))));
        }
        for (int i = 1; i < paradas.size() - 1; i++)
            if ((t = tramoMap.get(paradas.get(i) + "-" + paradas.get(i + 1))) != null)
                tramos.add(new Tramo(pMap.get(paradas.get(i)), pMap.get(paradas.get(i + 1)), t.getTipo(), t.getTiempo()));

        return tramos;
    }

    private class ParadaLinea {
        private Integer parada;
        private String linea;

        public ParadaLinea(Integer parada, String linea) {
            this.parada = parada;
            this.linea = linea;
        }

        public Integer getParada() {
            return parada;
        }

        public String getLinea() {
            return linea;
        }

        @Override
        public String toString() {
            return parada + linea;
        }

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