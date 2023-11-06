package negocio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import controlador.Coordinador;
import org.apache.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.YenKShortestPath;
import org.jgrapht.graph.DirectedMultigraph;
import controlador.Constantes;
import modelo.Linea;
import modelo.Parada;
import modelo.Tramo;
import util.Time;
import datastructures.TreeMap;

public class Calculo implements Observer {
    private Coordinador coordinador;
    private final static Logger logger = Logger.getLogger(Calculo.class);
    private Subject subject;
    private Graph<Parada, ParadaLinea> red;
    private TreeMap<Integer, Parada> paradaMap;
    private TreeMap<String, Tramo> tramoMap;
    private boolean actualizar;

    public Calculo() {
    }

    public void init(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
        this.actualizar = true;
    }

    public void cargarDatos(TreeMap<Integer, Parada> paradaMap, TreeMap<String, Linea> lineaMap, List<Tramo> tramos) {
        if (actualizar) {
            // Map paradas
            this.paradaMap = paradaMap;

            // Map tramo
            tramoMap = new TreeMap<String, Tramo>();
            for (Tramo t : tramos)
                tramoMap.put(t.getInicio().getCodigo() + "-" + t.getFin().getCodigo(), t);

            red = new DirectedMultigraph<>(null, null, false);

            // Cargar paradas
            for (Parada p : paradaMap.values())
                red.addVertex(p);

            // Cargar tramos lineas
            Parada origen, destino;
            for (Linea l : lineaMap.values())
                for (int i = 0; i < l.getParadas().size() - 1; i++) {
                    origen = l.getParadas().get(i);
                    destino = l.getParadas().get(i + 1);
                    red.addEdge(origen, destino, new ParadaLinea(origen, l));
                }

            // Cargar tramos caminando
            Linea linea;
            for (Tramo t : tramos)
                if (t.getTipo() == Constantes.TRAMO_CAMINANDO) {
                    linea = new Linea(t.getInicio().getCodigo() + "-" + t.getFin().getCodigo(), Time.toMins("00:00"),
                            Time.toMins("24:00"), 0);
                    red.addEdge(t.getInicio(), t.getFin(), new ParadaLinea(t.getInicio(), linea));
                }
            actualizar = false;
            logger.info("Se actualizaron los datos para realizar calculos");
        }
        logger.info("No se actualizaron los datos");
    }

    public List<List<Tramo>> recorridos(Parada paradaOrigen, Parada paradaDestino, int horario, int nroLineas) {

        // Crear grafo
        Graph<Parada, ParadaLinea> redConsulta = grafoRecorrido(paradaOrigen, paradaDestino);

        // Todos los recorridos
        YenKShortestPath<Parada, ParadaLinea> yksp = new YenKShortestPath<Parada, ParadaLinea>(redConsulta);
        List<GraphPath<Parada, ParadaLinea>> caminos = yksp.getPaths(paradaOrigen, paradaDestino, Integer.MAX_VALUE);

        // Eliminar recorridos superan cambioLineas
        List<Linea> lineas;
        Iterator<GraphPath<Parada, ParadaLinea>> r = caminos.iterator();
        while (r.hasNext()) {
            lineas = new ArrayList<Linea>();
            int cambioLineas = 0;
            for (ParadaLinea pl : r.next().getEdgeList())
                if (lineas.isEmpty())
                    lineas.add(pl.getLinea());
                else if (!lineas.get(lineas.size() - 1).equals(pl.getLinea()))
                    lineas.add(pl.getLinea());
            for (Linea l : lineas)
                if (l.getFrecuencia() != 0)
                    cambioLineas++;
            if (cambioLineas > nroLineas)
                r.remove();
        }

        // Realizar c�lculo de tiempo y preparar resultados
        List<List<Tramo>> listaTramos = new ArrayList<List<Tramo>>();
        Tramo t = null;
        int proximoColectivo;
        int tiempo = 0;
        List<Tramo> tramos;
        List<ParadaLinea> paradalineas;
        List<Parada> paradas;
        Parada origen = null;
        Parada destino = null;
        TreeMap<Integer, Parada> pMap;
        for (GraphPath<Parada, ParadaLinea> gp : caminos) {
            pMap = new TreeMap<Integer, Parada>();
            paradas = gp.getVertexList();
            for (Parada p : paradas)
                pMap.put(p.getCodigo(), new Parada(p.getCodigo(), paradaMap.get(p.getCodigo()).getDireccion()));
            proximoColectivo = horario;
            tramos = new ArrayList<Tramo>();
            paradalineas = gp.getEdgeList();
            for (int i = 0; i < paradalineas.size(); i++) {
                t = tramoMap.get(paradas.get(i).getCodigo() + "-" + paradas.get(i + 1).getCodigo());
                origen = pMap.get(paradas.get(i).getCodigo());
                origen.setLinea(paradalineas.get(i).getLinea());
                destino = pMap.get(paradas.get(i + 1).getCodigo());
                proximoColectivo = proximoColectivo(paradalineas.get(i).getLinea(), paradas.get(i),
                        proximoColectivo + tiempo);
                tramos.add(new Tramo(origen, destino, t.getTipo(), proximoColectivo));
                tiempo = t.getTiempo();
            }
            destino.setLinea(origen.getLineas().get(0));
            tramos.add(new Tramo(destino, destino, t.getTipo(), proximoColectivo + t.getTiempo()));
            listaTramos.add(tramos);
        }
        return listaTramos;
    }

    public List<List<Tramo>> recorridos1(Parada paradaOrigen, Parada paradaDestino, int horario, int nroLineas) {

        // Todos los recorridos
        YenKShortestPath<Parada, ParadaLinea> yksp = new YenKShortestPath<Parada, ParadaLinea>(red);
        List<GraphPath<Parada, ParadaLinea>> caminos = yksp.getPaths(paradaOrigen, paradaDestino, Integer.MAX_VALUE);

        // Eliminar recorridos superan cambioLineas
        List<Linea> lineas;
        Iterator<GraphPath<Parada, ParadaLinea>> r = caminos.iterator();
        while (r.hasNext()) {
            lineas = new ArrayList<Linea>();
            int cambioLineas = 0;
            for (ParadaLinea pl : r.next().getEdgeList())
                if (lineas.isEmpty())
                    lineas.add(pl.getLinea());
                else if (!lineas.get(lineas.size() - 1).equals(pl.getLinea()))
                    lineas.add(pl.getLinea());
            for (Linea l : lineas)
                if (l.getFrecuencia() != 0)
                    cambioLineas++;
            if (cambioLineas > nroLineas)
                r.remove();
        }

        // Realizar c�lculo de tiempo y preparar resultados
        List<List<Tramo>> listaTramos = new ArrayList<List<Tramo>>();
        Tramo t = null;
        int proximoColectivo;
        int tiempo = 0;
        List<Tramo> tramos;
        List<ParadaLinea> paradalineas;
        List<Parada> paradas;
        Parada origen = null;
        Parada destino = null;
        TreeMap<Integer, Parada> pMap;
        for (GraphPath<Parada, ParadaLinea> gp : caminos) {
            pMap = new TreeMap<Integer, Parada>();
            paradas = gp.getVertexList();
            for (Parada p : paradas)
                pMap.put(p.getCodigo(), new Parada(p.getCodigo(), paradaMap.get(p.getCodigo()).getDireccion()));
            proximoColectivo = horario;
            tramos = new ArrayList<Tramo>();
            paradalineas = gp.getEdgeList();
            for (int i = 0; i < paradalineas.size(); i++) {
                t = tramoMap.get(paradas.get(i).getCodigo() + "-" + paradas.get(i + 1).getCodigo());
                origen = pMap.get(paradas.get(i).getCodigo());
                origen.setLinea(paradalineas.get(i).getLinea());
                destino = pMap.get(paradas.get(i + 1).getCodigo());
                proximoColectivo = proximoColectivo(paradalineas.get(i).getLinea(), paradas.get(i),
                        proximoColectivo + tiempo);
                tramos.add(new Tramo(origen, destino, t.getTipo(), proximoColectivo));
                tiempo = t.getTiempo();
            }
            destino.setLinea(origen.getLineas().get(0));
            tramos.add(new Tramo(destino, destino, t.getTipo(), proximoColectivo + t.getTiempo()));
            listaTramos.add(tramos);
        }
        return listaTramos;
    }

    private Graph<Parada, ParadaLinea> grafoRecorrido(Parada paradaOrigen, Parada paradaDestino) {

        Set<ParadaLinea> paradaLineas = new HashSet<ParadaLinea>();
        paradaLineas.addAll(red.outgoingEdgesOf(paradaOrigen));
        paradaLineas.addAll(red.incomingEdgesOf(paradaDestino));

        Set<Linea> lineas = new HashSet<Linea>();
        for (ParadaLinea p : paradaLineas)
            lineas.add(p.getLinea());

        Graph<Parada, ParadaLinea> recorrido = new DirectedMultigraph<>(null, null, false);

        // Cargar paradas
        for (Parada p : paradaMap.values())
            recorrido.addVertex(p);

        // Cargar tramos lineas
        Parada origen, destino;
        for (Linea l : lineas)
            for (int i = 0; i < l.getParadas().size() - 1; i++) {
                origen = l.getParadas().get(i);
                destino = l.getParadas().get(i + 1);
                recorrido.addEdge(origen, destino, new ParadaLinea(origen, l));
            }

        // Cargar tramos caminando
        Linea linea;
        for (Tramo t : tramoMap.values())
            if (t.getTipo() == Constantes.TRAMO_CAMINANDO) {
                linea = new Linea(t.getInicio().getCodigo() + "-" + t.getFin().getCodigo(), Time.toMins("00:00"),
                        Time.toMins("24:00"), 0);
                recorrido.addEdge(t.getInicio(), t.getFin(), new ParadaLinea(t.getInicio(), linea));
            }
        return recorrido;
    }

    private int proximoColectivo(Linea linea, Parada parada, int horario) {
        int nroParada = linea.getParadas().indexOf(parada);
        // Tramo caminando
        if (nroParada == -1)
            return horario;

        // Calcular el tiempo desde el inicio del recorrido a la parada
        Parada origen, destino;
        int tiempo = 0;
        for (int i = 0; i < nroParada; i++) {
            origen = linea.getParadas().get(i);
            destino = linea.getParadas().get(i + 1);
            tiempo += tramoMap.get(origen.getCodigo() + "-" + destino.getCodigo()).getTiempo();
        }

        // Ya pas� el �ltimo colectivo
        if (linea.getFinaliza() + tiempo < horario)
            return -1;

        // Tiempo del pr�ximo colectivo
        for (int j = linea.getComienza(); j <= linea.getFinaliza(); j += linea.getFrecuencia())
            if (j + tiempo >= horario)
                return j + tiempo;
        return -1;
    }

    private class ParadaLinea {
        private Parada parada;
        private Linea linea;

        public ParadaLinea(Parada parada, Linea linea) {
            this.parada = parada;
            this.linea = linea;
        }

        public Parada getParada() {
            return parada;
        }

        public void setParada(Parada parada) {
            this.parada = parada;
        }

        public Linea getLinea() {
            return linea;
        }

        public void setLinea(Linea linea) {
            this.linea = linea;
        }

        @Override
        public String toString() {
            return parada.getCodigo() + " " + linea.getCodigo();
        }
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    @Override
    public void update() {
        actualizar = true;
    }
}