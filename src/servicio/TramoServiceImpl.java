package servicio;

import conexion.Factory;
import modelo.Tramo;
import dao.TramoDAO;

import java.util.List;

/**
 * Implementación del servicio TramoService que interactúa con la capa de acceso
 * a datos (DAO) para realizar
 * operaciones relacionadas con la entidad Tramo.
 */
public class TramoServiceImpl implements TramoService {

    private TramoDAO tramoDAO;

    /**
     * Constructor que inicializa la instancia de TramoDAO.
     */
    public TramoServiceImpl() {
        tramoDAO = (TramoDAO) Factory.getInstancia("TRAMO");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertar(Tramo tramo) {
        tramoDAO.insertar(tramo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actualizar(Tramo tramo) {
        tramoDAO.actualizar(tramo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void borrar(Tramo tramo) {
        tramoDAO.borrar(tramo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tramo> buscarTodos() {
        return tramoDAO.buscarTodos();
    }
}
