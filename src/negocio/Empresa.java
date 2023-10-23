package negocio;

import datastructures.TreeMap;
import modelo.Linea;
import modelo.Parada;
import modelo.Tramo;
import servicio.*;

//import java.util.TreeMap;
import java.io.IOException;
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

    public void agregarLinea(Linea linea) throws LineaExistenteException {
        if (lineas.containsKey(linea.getCodigo()))
            throw new LineaExistenteException();
        lineas.put(linea.getCodigo(), linea);
        lineaService.insertar(linea);
    }

    public void modificarLinea(Linea linea) {
        lineas.put(linea.getCodigo(), linea);
        lineaService.actualizar(linea);
    }

    public void borrarLinea(Linea linea) {
        for (Parada parada : paradas.values())
            if (parada.getLinea(linea))
                throw new LineaReferenciaException();
        Linea l = buscarLinea(linea);
        lineas.remove(l.getCodigo());
        lineaService.borrar(linea);
    }

    public Linea buscarLinea(Linea linea) {
        if (!lineas.containsKey(linea.getCodigo()))
            return null;
        return lineas.get(linea.getCodigo());
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
