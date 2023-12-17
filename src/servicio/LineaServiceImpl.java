package servicio;

import dao.LineaDAO;
import datastructures.TreeMap;
import factory.Factory;
import modelo.Linea;

/**
 * Implementación concreta de LineaService que utiliza un LineaDAO para realizar
 * operaciones de persistencia.
 */
public class LineaServiceImpl implements LineaService {

    private LineaDAO lineaDAO;

    /**
     * Constructor que inicializa el LineaDAO utilizando la fábrica de conexiones.
     */
    public LineaServiceImpl() {
        lineaDAO = (LineaDAO) Factory.getInstancia("LINEA");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertar(Linea linea) {
        lineaDAO.insertar(linea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actualizar(Linea linea) {
        lineaDAO.actualizar(linea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void borrar(Linea linea) {
        lineaDAO.borrar(linea);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeMap<String, Linea> buscarTodos() {
        return lineaDAO.buscarTodos();
    }
}
