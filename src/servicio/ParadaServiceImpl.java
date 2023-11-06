package servicio;

import dao.ParadaDAO;
import datastructures.TreeMap;
import conexion.Factory;
import modelo.Parada;

public class ParadaServiceImpl implements ParadaService {
    private ParadaDAO paradaDAO;

    public ParadaServiceImpl(){
        paradaDAO = (ParadaDAO) Factory.getInstancia("PARADA");
    }

    @Override
    public void insertar(Parada parada) {
        paradaDAO.insertar(parada);
    }

    @Override
    public void actualizar(Parada parada) {
        paradaDAO.actualizar(parada);
    }

    @Override
    public void borrar(Parada parada) {
        paradaDAO.borrar(parada);
    }

    @Override
    public TreeMap<Integer, Parada> buscarTodos() {
        return paradaDAO.buscarTodos();
    }
}
