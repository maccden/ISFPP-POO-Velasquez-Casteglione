package servicio;

import dao.LineaDAO;
import dao.TramoDAO;
import dao.secuencial.LineaSecuencialDAO;
import datastructures.TreeMap;
import factory.Factory;
import modelo.Linea;

import java.io.IOException;

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
