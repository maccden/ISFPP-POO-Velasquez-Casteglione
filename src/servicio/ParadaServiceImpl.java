package servicio;

import dao.ParadaDAO;
import datastructures.TreeMap;
import factory.Factory;
import modelo.Parada;

/**
 * Implementación del servicio para la entidad Parada que utiliza un objeto
 * ParadaDAO para realizar operaciones en la base de datos.
 */
public class ParadaServiceImpl implements ParadaService {

    private ParadaDAO paradaDAO;

    /**
     * Constructor que inicializa el ParadaDAO utilizando la fábrica de conexiones.
     */
    public ParadaServiceImpl() {
        paradaDAO = (ParadaDAO) Factory.getInstancia("PARADA");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertar(Parada parada) {
        paradaDAO.insertar(parada);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actualizar(Parada parada) {
        paradaDAO.actualizar(parada);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void borrar(Parada parada) {
        paradaDAO.borrar(parada);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeMap<Integer, Parada> buscarTodos() {
        return paradaDAO.buscarTodos();
    }
}
