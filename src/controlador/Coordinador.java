package controlador;

import java.util.List;

import datastructures.TreeMap;
import gui.datos.*;
import negocio.Calculo;
import gui.consulta.Interfaz;
import modelo.*;
import negocio.Empresa;
import negocio.LineaExistenteException;

import java.io.IOException;

public class Coordinador {
    private Empresa empresa;
    private Calculo calculo;
    private Interfaz interfaz;
//  private DesktopFrameConsulta desktopFrameConsulta;
    private DesktopFrameDatos desktopFrameDatos;
    private LineaList lineaList;
    private LineaForm lineaForm;
    private ParadaList paradaList;
    private ParadaForm paradaForm;
    private TramoList tramoList;
    private TramoForm tramoForm;

    // <o> Getters y Setters de Empresa, Calculo e Interfaz <o>

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Calculo getCalculo() {
        return calculo;
    }

    public void setCalculo(Calculo calculo) {
        this.calculo = calculo;
    }

    public Interfaz getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(Interfaz interfaz) {
        this.interfaz = interfaz;
    }

    // <o> Listar Modelos <o>

    public TreeMap<Integer, Parada> listarParadas() {
        return empresa.getParadas();
    }
    
    public TreeMap<String, Linea> listarLineas()  {
        return empresa.getLineas();
    }

    public List<Tramo> listarTramos() {
        return empresa.getTramos();
    }

    public Linea buscarLinea(Linea linea) {
        return empresa.buscarLinea(linea);
    }

    // <o> GUI Consulta <o>

    // <o> GUI Datos <o>

    public DesktopFrameDatos getDesktopFrameDatos() {
        return desktopFrameDatos;
    }

    public void setDesktopFrameDatos(DesktopFrameDatos desktopFrameDatos) {
        this.desktopFrameDatos = desktopFrameDatos;
    }

    public LineaList getLineaList() {
        return lineaList;
    }

    public void setLineaList(LineaList lineaList) {
        this.lineaList = lineaList;
    }

    public LineaForm getLineaForm() {
        return lineaForm;
    }

    public void setLineaForm(LineaForm lineaForm) {
        this.lineaForm = lineaForm;
    }

    public void salirLineaList() {
        lineaList.setVisible(false);
    }

    // <o> DesktopFrame Consulta <o>

    /*
    public void mostrarConsulta() {
        consultaForm.accion();
        consultaForm.setVisible(true);
    }
     */

    // <o> DesktopFrame Datos <o>

    public void mostrarLineaList() {
        lineaList.loadTable();
        lineaList.setVisible(true);
    }

    // <o> LineaList <o>

    public void insertarLineaForm() {
        lineaForm.accion(Constantes.INSERTAR, null);
        lineaForm.setVisible(true);
    }

    public void modificarLineaForm(Linea linea) {
        lineaForm.accion(Constantes.MODIFICAR, linea);
        lineaForm.setVisible(true);
    }

    public void borrarLineaForm(Linea linea) {
        lineaForm.accion(Constantes.BORRAR, linea);
        lineaForm.setVisible(true);
    }

    // <o> LineaForm <o>

    public void cancelarLinea() {
        lineaForm.setVisible(false);
    }

    public void insertarLinea(Linea linea) throws LineaExistenteException {
        empresa.agregarLinea(linea);
        lineaForm.setVisible(false);
        lineaList.addRow(linea);
    }

    public void modificarLinea(Linea linea) {
        empresa.modificarLinea(linea);
        lineaList.setAccion(Constantes.MODIFICAR);
        lineaList.setLinea(linea);
        lineaForm.setVisible(false);
    }

    public void borrarLinea(Linea linea) {
        empresa.borrarLinea(linea);
        lineaList.setAccion(Constantes.BORRAR);
        lineaForm.setVisible(false);
    }
}