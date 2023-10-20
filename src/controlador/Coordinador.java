package controlador;

import java.util.List;

import datastructures.TreeMap;
import negocio.Calculo;
import interfaz.Interfaz;
import modelo.*;
import negocio.Empresa;

import java.io.IOException;

public class Coordinador {
    private Empresa empresa;
    private Calculo calculo;
    private Interfaz interfaz;

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

    public Empresa getEmpresa() { return empresa; }

    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public Linea buscarLinea(Linea linea) {
        return empresa.buscarLinea(linea);
    }

    public TreeMap<Integer, Parada> listarParadas() throws IOException {
        return empresa.getParadas();
    }
    
    public TreeMap<String, Linea> listarLineas() throws IOException {
        return empresa.getLineas();
    }

    public List<Tramo> listarTramos() throws IOException {
        return empresa.getTramos();
    }
}