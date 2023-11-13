package controlador;

import java.util.List;
import datastructures.TreeMap;
import gui.*;
import negocio.*;
import modelo.*;
import util.Time;

public class Coordinador {
    private Empresa empresa;
    private Calculo calculo;
    private LineaList lineaList;
    private LineaForm lineaForm;
    private ParadaList paradaList;
    private ParadaForm paradaForm;
    private TramoList tramoList;
    private TramoForm tramoForm;
    private ConsultaForm consultaForm;
    private ResultadoForm resultadoForm;
    private SubResultadoForm subResultadoForm;
    private DesktopFrame desktopFrame;
    private String horaLlegada;
    private int limiteColectivos;

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

    public void calculo (Parada parada1, Parada parada2, int horario, int nrolineas) {
        List<List<Tramo>> resultado = calculo.recorridos(parada1, parada2, horario, nrolineas);
        resultadoForm.accion(resultadoForm.verDatos(resultado, horario, listarLineas()));
        resultadoForm.setVisible(true);
    }

    public void masRapido (List<List<Tramo>> trayecto){
        subResultadoForm.accion(subResultadoForm.verDatos(Constantes.MAS_RAPIDO, trayecto, Time.toMins(horaLlegada), listarLineas()));
        subResultadoForm.setVisible(true);
    }

    public void menosCostoso (List<List<Tramo>> trayecto){
        subResultadoForm.accion(subResultadoForm.verDatos(Constantes.MENOS_COSTOSO, trayecto, Time.toMins(horaLlegada), listarLineas()));
        subResultadoForm.setVisible(true);
    }

    // <o> Listar Modelos <o>

    public TreeMap<Integer, Parada> listarParadas() {
        return empresa.getParadas();
    }

    public TreeMap<String, Linea> listarLineas() {
        return empresa.getLineas();
    }

    public List<Tramo> listarTramos() {
        return empresa.getTramos();
    }

    public Linea buscarLinea(Linea linea) {
        return empresa.buscarLinea(linea);
    }

    public Parada buscarParada(Parada parada) {
        return empresa.buscarParada(parada);
    }

    public Tramo buscarTramo(Tramo tramo) {
        return empresa.buscarTramo(tramo);
    }

    // <o> GUI Consulta <o>

    public void cancelarConsulta() {
        consultaForm.setVisible(false);
    }

    public void cancelarResultado() { resultadoForm.setVisible(false); }

    public void cancelaraSubResultado() { subResultadoForm.setVisible(false); }

    public void setNumeroLimiteColectivos(int limite) {
        this.limiteColectivos = limite;
    }

    public int getNumeroLimiteColectivos() {
        return limiteColectivos;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String horaLlegadaParada() {
        return horaLlegada;
    }

    public ConsultaForm getConsultaForm() {
        return consultaForm;
    }

    public void setConsultaForm(ConsultaForm consultaForm) {
        this.consultaForm = consultaForm;
    }

    public ResultadoForm getResultadoForm() {
        return resultadoForm;
    }

    public void setResultadoForm(ResultadoForm resultadoForm) {
        this.resultadoForm = resultadoForm;
    }

    public SubResultadoForm getSubResultadoForm() { return subResultadoForm; }

    public void setSubResultadoForm(SubResultadoForm subResultadoForm) { this.subResultadoForm = subResultadoForm; }

    public DesktopFrame getDesktopFrame() {
        return desktopFrame;
    }

    public void setDesktopFrame(DesktopFrame desktopFrame) {
        this.desktopFrame = desktopFrame;
    }

    // <o> GUI Datos <o>
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

    public ParadaList getParadaList() {
        return paradaList;
    }

    public void setParadaList(ParadaList paradaList) {
        this.paradaList = paradaList;
    }

    public ParadaForm getParadaForm() {
        return paradaForm;
    }

    public void setParadaForm(ParadaForm paradaForm) {
        this.paradaForm = paradaForm;
    }

    public TramoList getTramoList() {
        return tramoList;
    }

    public void setTramoList(TramoList tramoList) {
        this.tramoList = tramoList;
    }

    public TramoForm getTramoForm() {
        return tramoForm;
    }

    public void setTramoForm(TramoForm tramoForm) {
        this.tramoForm = tramoForm;
    }

    public void salirLineaList() {
        lineaList.setVisible(false);
    }

    public void salirParadaList() {
        paradaList.setVisible(false);
    }

    public void salirTramoList() {
        tramoList.setVisible(false);
    }

    // <o> DesktopFrame Consulta <o>

     public void mostrarConsulta() {
         consultaForm.accion();
         consultaForm.setVisible(true);
     }

    // <o> SubConsulta <o> //

    public void mostrarMasRapido(String resultado) {
        subResultadoForm.accion(resultado);
    }

    public void mostrarMenosCostoso(String resultado) {
        subResultadoForm.accion(resultado);
    }

    // <o> DesktopFrame Datos <o>

    public void mostrarLineaList() {
        lineaList.loadTable();
        lineaList.setVisible(true);
    }

    public void mostrarParadaList() {
        paradaList.loadTable();
        paradaList.setVisible(true);
    }

    public void mostrarTramoList() {
        tramoList.loadTable();
        tramoList.setVisible(true);
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

    // <o> ParadaList <o>

    public void insertarParadaForm() {
        paradaForm.accion(Constantes.INSERTAR, null);
        paradaForm.setVisible(true);
    }

    public void modificarParadaForm(Parada parada) {
        paradaForm.accion(Constantes.MODIFICAR, parada);
        paradaForm.setVisible(true);
    }

    public void borrarParadaForm(Parada parada) {
        paradaForm.accion(Constantes.BORRAR, parada);
        paradaForm.setVisible(true);
    }

    // <o> ParadaForm <o>

    public void cancelarParada() {
        paradaForm.setVisible(false);
    }

    public void insertarParada(Parada parada) throws ParadaExistenteException {
        empresa.agregarParada(parada);
        paradaForm.setVisible(false);
        paradaList.addRow(parada);
    }

    public void modificarParada(Parada parada) {
        empresa.modificarParada(parada);
        paradaList.setAccion(Constantes.MODIFICAR);
        paradaList.setParada(parada);
        paradaForm.setVisible(false);
    }

    public void borrarParada(Parada parada) {
        empresa.borrarParada(parada);
        paradaList.setAccion(Constantes.BORRAR);
        paradaForm.setVisible(false);
    }

    // <o> TramoList <o>

    public void insertarTramoForm() {
        tramoForm.accion(Constantes.INSERTAR, null);
        tramoForm.setVisible(true);
    }

    public void modificarTramoForm(Tramo tramo) {
        tramoForm.accion(Constantes.MODIFICAR, tramo);
        tramoForm.setVisible(true);
    }

    public void borrarTramoForm(Tramo tramo) {
        tramoForm.accion(Constantes.BORRAR, tramo);
        tramoForm.setVisible(true);
    }

    // <o> TramoForm <o>

    public void cancelarTramo() {
        tramoForm.setVisible(false);
    }

    public void insertarTramo(Tramo tramo) throws TramoExistenteException {
        empresa.agregarTramo(tramo);
        tramoForm.setVisible(false);
        tramoList.addRow(tramo);
    }

    public void modificarTramo(Tramo tramo) {
        empresa.modificarTramo(tramo);
        tramoList.setAccion(Constantes.MODIFICAR);
        tramoList.setTramo(tramo);
        tramoForm.setVisible(false);
    }

    public void borrarTramo(Tramo tramo) {
        empresa.borrarTramo(tramo);
        tramoList.setAccion(Constantes.BORRAR);
        tramoForm.setVisible(false);
    }
}