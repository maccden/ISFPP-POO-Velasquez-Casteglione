package negocio;

import datastructures.TreeMap;
import modelo.Linea;
import modelo.Parada;
import modelo.Tramo;
import negocio.exceptions.*;
import servicio.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Clase que representa la empresa de transporte.
 */
public class Empresa {

    private static Empresa empresa = null;
    private String nombre;
    private Subject subject;
    private TreeMap<String, Linea> lineas;
    private LineaService lineaService;
    private TreeMap<Integer, Parada> paradas;
    private ParadaService paradaService;
    private List<Tramo> tramos;
    private Logger logger = Logger.getLogger(Empresa.class);
    private TramoService tramoService;

    /**
     * Método estático que devuelve la instancia única de la empresa (patrón
     * Singleton).
     *
     * @return La instancia única de la empresa.
     */
    public static Empresa getEmpresa() {
        if (empresa == null)
            empresa = new Empresa();
        return empresa;
    }

    /**
     * Constructor de la clase Empresa.
     */
    private Empresa() {
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

    /**
     * Inicializa la empresa con un sujeto (patrón Observer).
     *
     * @param subject El sujeto que notificará a los observadores.
     */
    public void init(Subject subject) {
        this.subject = subject;
    }

    // <o> Linea <o>

    /**
     * Agrega una nueva línea a la empresa.
     *
     * @param linea La línea a agregar.
     * @throws LineaExistenteException Si la línea ya existe.
     */
    public void agregarLinea(Linea linea) throws LineaExistenteException {
        for (Linea l : lineas.values())
            if (l.equals(linea))
                throw new LineaExistenteException();
        lineas.put(linea.getCodigo(), linea);
        lineaService.insertar(linea);
        subject.refresh();
        logger.info("Se agrega una linea");
    }

    /**
     * Modifica una línea existente en la empresa.
     *
     * @param linea La línea a modificar.
     */
    public void modificarLinea(Linea linea) {
        lineas.remove(linea.getCodigo());
        lineas.put(linea.getCodigo(), linea);
        lineaService.actualizar(linea);
        subject.refresh();
        logger.info("Se modifica una linea");
    }

    /**
     * Borra una línea de la empresa.
     *
     * @param linea La línea a borrar.
     */
    public void borrarLinea(Linea linea) {
        Linea l = buscarLinea(linea);
        for (Parada parada : l.getParadas())
            parada.removeLinea(buscarLinea(linea));
        lineas.remove(l.getCodigo());
        lineaService.borrar(linea);
        subject.refresh();
        logger.info("Se borra una linea");
    }

    /**
     * Busca una línea en la empresa.
     *
     * @param linea La línea a buscar.
     * @return La línea encontrada o null si no se encuentra.
     */
    public Linea buscarLinea(Linea linea) {
        for (Linea l : lineas.values())
            if (l.equals(linea))
                return lineas.get(linea.getCodigo());
        return null;
    }

    // <o> Parada <o>

    /**
     * Agrega una nueva parada a la empresa.
     *
     * @param parada La parada a agregar.
     * @throws ParadaExistenteException Si la parada ya existe.
     */
    public void agregarParada(Parada parada) throws ParadaExistenteException {
        for (Parada p : paradas.values())
            if (p.equals(parada))
                throw new ParadaExistenteException();
        paradas.put(parada.getCodigo(), parada);
        paradaService.insertar(parada);
        subject.refresh();
        logger.info("Se agrega una parada");
    }

    /**
     * Modifica una parada existente en la empresa.
     *
     * @param parada La parada a modificar.
     */
    public void modificarParada(Parada parada) {
        paradas.put(parada.getCodigo(), parada);
        paradaService.actualizar(parada);
        subject.refresh();
        logger.info("Se modifica una parada");
    }

    /**
     * Borra una parada de la empresa.
     *
     * @param parada La parada a borrar.
     * @throws LineaReferenciaException Si hay líneas de la empresa que hacen
     *                                  referencia a la parada.
     * @throws TramoReferenciaException Si hay tramos que hacen referencia a la
     *                                  parada.
     */
    public void borrarParada(Parada parada) {
        for (Linea linea : lineas.values())
            if (linea.contains(parada))
                throw new LineaReferenciaException();
        for (Tramo tramo : tramos)
            if (tramo.getInicio().equals(parada) || tramo.getFin().equals(parada))
                throw new TramoReferenciaException();
        Parada p = buscarParada(parada);
        paradas.remove(p.getCodigo());
        paradaService.borrar(parada);
        subject.refresh();
        logger.info("Se borra una parada");
    }

    /**
     * Busca una parada en la empresa.
     *
     * @param parada La parada a buscar.
     * @return La parada encontrada o null si no se encuentra.
     */
    public Parada buscarParada(Parada parada) {
        if (!paradas.containsKey(parada.getCodigo()))
            return null;
        return paradas.get(parada.getCodigo());
    }

    // <o> Tramo <o>

    /**
     * Agrega un nuevo tramo a la empresa.
     *
     * @param tramo El tramo a agregar.
     * @throws TramoExistenteException Si el tramo ya existe.
     */
    public void agregarTramo(Tramo tramo) {
        for (Tramo t : tramos)
            if (t.equals(tramo))
                throw new TramoExistenteException();
        tramos.add(tramo);
        tramoService.insertar(tramo);
        subject.refresh();
        logger.info("Se agrega un tramo");
    }

    /**
     * Modifica un tramo existente en la empresa.
     *
     * @param tramo El tramo a modificar.
     */
    public void modificarTramo(Tramo tramo) {
        tramos.remove(tramo);
        tramos.add(tramo);
        tramoService.insertar(tramo);
        subject.refresh();
        logger.info("Se modifica un tramo");
    }

    /**
     * Borra un tramo de la empresa.
     *
     * @param tramo El tramo a borrar.
     */
    public void borrarTramo(Tramo tramo) {
        tramos.remove(tramo);
        tramoService.borrar(tramo);
        subject.refresh();
        logger.info("Se borra un tramo");
    }

    /**
     * Busca un tramo en la empresa.
     *
     * @param tramo El tramo a buscar.
     * @return El tramo encontrado o null si no se encuentra.
     */
    public Tramo buscarTramo(Tramo tramo) {
        if (!tramos.contains(tramo))
            return null;
        return tramos.get(tramos.indexOf(tramo));
    }

    /**
     * Obtiene el nombre de la empresa.
     *
     * @return El nombre de la empresa.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el mapa de líneas de la empresa.
     *
     * @return El mapa de líneas de la empresa.
     */
    public TreeMap<String, Linea> getLineas() {
        return lineas;
    }

    /**
     * Obtiene el mapa de paradas de la empresa.
     *
     * @return El mapa de paradas de la empresa.
     */
    public TreeMap<Integer, Parada> getParadas() {
        return paradas;
    }

    /**
     * Obtiene la lista de tramos de la empresa.
     *
     * @return La lista de tramos de la empresa.
     */
    public List<Tramo> getTramos() {
        return tramos;
    }
}
