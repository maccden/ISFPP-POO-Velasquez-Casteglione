package servicio;

import dao.LineaDAO;
import datastructures.TreeMap;
import conexion.Factory;
import modelo.Linea;

public class LineaServiceImpl implements LineaService {

    private LineaDAO lineaDAO;

    public LineaServiceImpl(){
        lineaDAO = (LineaDAO) Factory.getInstancia("LINEA");
    }

    @Override
    public void insertar(Linea linea) {
        lineaDAO.insertar(linea);
    }

    @Override
    public void actualizar(Linea linea) {
        lineaDAO.actualizar(linea);
    }

    @Override
    public void borrar(Linea linea) {
        lineaDAO.borrar(linea);
    }

    @Override
    public TreeMap<String, Linea> buscarTodos() {
        return lineaDAO.buscarTodos();
    }
}
