package controlador;

import java.util.List;
import datastructures.TreeMap;
import datos.Datos;
import negocio.Calculo;
import interfaz.Interfaz;
import modelo.*;

import java.io.IOException;

public class Coordinador {
    private Interfaz interfaz;
    private Calculo calculo;

    public Interfaz getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(Interfaz interfaz) {
        this.interfaz = interfaz;
    }

    public Calculo getCalculo() {
        return calculo;
    }

    public void setCalculo(Calculo calculo) {
        this.calculo = calculo;
    }

    public Linea buscarLinea(Linea linea) throws IOException {
        return listarLineas().get(linea.getCodigo());
    }

    public TreeMap<Integer, Parada> listarParadas() throws IOException {
        return Datos.getParadas();
    }
    
    public TreeMap<String, Linea> listarLineas() throws IOException {
        return Datos.getLineas();
    }

    public List<Tramo> listarTramos() throws IOException {
        return Datos.getTramos();
    }
}