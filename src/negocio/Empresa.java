package negocio;

import datastructures.TreeMap;
import modelo.Linea;
import modelo.Parada;
import modelo.Tramo;
import servicio.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class Empresa {

    private static Empresa empresa = null;
    private String nombre;
    private Subject subject;
    private TreeMap<String, Linea> lineas;
    private LineaService lineaService;
    private TreeMap<Integer, Parada> paradas;
    private ParadaService paradaService;
    private List<Tramo> tramos;
    private Logger logger;
    private TramoService tramoService;

    public static Empresa getEmpresa() {
        if (empresa == null) {
            empresa = new Empresa();
        }
        return empresa;
    }

    private Empresa() {
        super();
        paradas = new TreeMap<>();
        paradaService = new ParadaServiceImpl();
        paradas = paradaService.buscarTodos();
        lineas = new TreeMap<>();
        lineaService = new LineaServiceImpl();
        lineas = lineaService.buscarTodos();
        tramos = new ArrayList<Tramo>();
        tramoService = new TramoServiceImpl();
        tramos.addAll(tramoService.buscarTodos());
    }

    public void init(Subject subject) {
        this.subject = subject;
    }

    // <o> Linea <o>

    public void agregarLinea(Linea linea) throws LineaExistenteException {
        for (Linea l: lineas.values())
            if (l.equals(linea))
                throw new LineaExistenteException();
        lineas.put(linea.getCodigo(), linea);
        lineaService.insertar(linea);
        subject.refresh();
        logger.info("Se agrega una linea");
    }

    public void modificarLinea(Linea linea) {
        lineas.remove(linea.getCodigo());
        lineas.put(linea.getCodigo(), linea);
        lineaService.actualizar(linea);
        subject.refresh();
        logger.info("Se modifica una linea");
    }

    public void borrarLinea(Linea linea) {
        Linea l = buscarLinea(linea);
        for (Parada parada: l.getParadas())
            parada.removeLinea(buscarLinea(linea));
        lineas.remove(l.getCodigo());
        lineaService.borrar(linea);
        subject.refresh();
        logger.info("Se borra una linea");
    }

    public Linea buscarLinea(Linea linea) {
        for (Linea l: lineas.values())
            if (l.equals(linea))
                return lineas.get(linea.getCodigo());
        return null;
    }

    // <o> Parada <o>

    public void agregarParada(Parada parada) throws ParadaExistenteException {
        for (Parada p: paradas.values())
            if (p.equals(parada))
                throw new ParadaExistenteException();
        paradas.put(parada.getCodigo(), parada);
        paradaService.insertar(parada);
        subject.refresh();
        logger.info("Se agrega una parada");
    }

    public void modificarParada(Parada parada) {
        paradas.put(parada.getCodigo(), parada);
        paradaService.actualizar(parada);
        subject.refresh();
        logger.info("Se modifica una parada");
    }

    public void borrarParada(Parada parada) {
        for (Linea linea : lineas.values())
            if (linea.contains(parada))
                throw new LineaReferenciaException();
        for (Tramo tramo: tramos)
            if (tramo.getInicio().equals(parada) || tramo.getFin().equals(parada))
                throw new TramoReferenciaException();
        Parada p = buscarParada(parada);
        paradas.remove(p.getCodigo());
        paradaService.borrar(parada);
        subject.refresh();
        logger.info("Se borra una parada");
    }

    public Parada buscarParada(Parada parada) {
        if (!paradas.containsKey(parada.getCodigo()))
            return null;
        return paradas.get(parada.getCodigo());
    }

    // <o> Tramo <o>

    public void agregarTramo(Tramo tramo) {
        for (Tramo t: tramos)
            if (t.equals(tramo))
                throw new TramoExistenteException();
        tramos.add(tramo);
        tramoService.insertar(tramo);
        subject.refresh();
        logger.info("Se agrega un tramo");
    }

    public void modificarTramo(Tramo tramo) {
        tramos.remove(tramo);
        tramos.add(tramo);
        tramoService.insertar(tramo);
        subject.refresh();
        logger.info("Se modifica un tramo");
    }

    public void borrarTramo(Tramo tramo) {
        tramos.remove(tramo);
        tramoService.borrar(tramo);
        subject.refresh();
        logger.info("Se borra un tramo");
    }

    public Tramo buscarTramo(Tramo tramo) {
        if (!tramos.contains(tramo))
            return null;
        return tramos.get(tramos.indexOf(tramo));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TreeMap<String, Linea> getLineas() {
        return lineas;
    }

    public TreeMap<Integer, Parada> getParadas() {
        return paradas;
    }

    public List<Tramo> getTramos() {
        return tramos;
    }
}
