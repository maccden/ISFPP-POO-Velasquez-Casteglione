package servicio;

import dao.secuencial.TramoSecuencialDAO;
import factory.Factory;
import modelo.Tramo;
import dao.TramoDAO;

import java.util.List;

public class TramoServiceImpl implements TramoService {

    private TramoDAO tramoDAO;

    public TramoServiceImpl(){
        tramoDAO = (TramoDAO) Factory.getInstancia("TRAMO");
    }

    @Override
    public void insertar(Tramo tramo) {
        tramoDAO.insertar(tramo);
    }

    @Override
    public void actualizar(Tramo tramo) {
        tramoDAO.actualizar(tramo);
    }

    @Override
    public void borrar(Tramo tramo) {
        tramoDAO.borrar(tramo);

    }

    @Override
    public List<Tramo> buscarTodos() {
        return tramoDAO.buscarTodos();

    }
}
