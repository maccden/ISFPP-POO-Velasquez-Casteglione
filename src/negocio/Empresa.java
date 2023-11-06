package negocio;

import datastructures.TreeMap;
import modelo.Linea;
import modelo.Parada;
import modelo.Tramo;
import servicio.*;

//import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private static Empresa empresa = null;
    private String nombre;
    private TreeMap<String, Linea> lineas;
    private LineaService lineaService;
    private TreeMap<Integer, Parada> paradas;
    private ParadaService paradaService;
    private List<Tramo> tramos;
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

    // <o> Linea <o>

    public void agregarLinea(Linea linea) throws LineaExistenteException {
        for (Linea l: lineas.values())
            if (l.equals(linea))
                throw new LineaExistenteException();
        lineas.put(linea.getCodigo(), linea);
        lineaService.insertar(linea);
    }

    public void modificarLinea(Linea linea) {
        lineas.remove(linea.getCodigo());
        lineas.put(linea.getCodigo(), linea);
        lineaService.actualizar(linea);
    }

    public void borrarLinea(Linea linea) {
        Linea l = buscarLinea(linea);
        for (Parada parada: l.getParadas())
            parada.removeLinea(buscarLinea(linea));
        lineas.remove(l.getCodigo());
        lineaService.borrar(linea);
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
    }

    public void modificarParada(Parada parada) {
        paradas.put(parada.getCodigo(), parada);
        paradaService.actualizar(parada);
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
    }

    public void modificarTramo(Tramo tramo) {
        tramos.remove(tramo);
        tramos.add(tramo);
        tramoService.insertar(tramo);
    }

    public void borrarTramo(Tramo tramo) {
        tramos.remove(tramo);
        tramoService.borrar(tramo);
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
