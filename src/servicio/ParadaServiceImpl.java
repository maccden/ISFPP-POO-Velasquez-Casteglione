package servicio;

import dao.ParadaDAO;
import datastructures.TreeMap;
import modelo.Parada;
import dao.secuencial.ParadaSecuencialDAO;

public class ParadaServiceImpl implements ParadaService {
    private ParadaDAO paradaDAO;

    public ParadaServiceImpl(){
        paradaDAO = new ParadaSecuencialDAO();
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
