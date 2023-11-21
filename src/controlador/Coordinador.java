package controlador;

import java.util.List;
import java.util.ResourceBundle;

import datastructures.TreeMap;
import gui.*;
import gui.config.ConfigForm;
import gui.consulta.ConsultaForm;
import gui.consulta.ResultadoForm;
import gui.consulta.SubResultadoForm;
import gui.datos.*;
import negocio.*;
import modelo.*;
import negocio.exceptions.LineaExistenteException;
import negocio.exceptions.ParadaExistenteException;
import negocio.exceptions.TramoExistenteException;

import javax.swing.*;

/**
 * Clase que coordina las interacciones entre las distintas partes del sistema.
 * Gestiona la lógica de negocio, la interfaz gráfica y la configuración.
 */
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
    private ConfigForm configForm;
    private Configuracion configuracion;
    private DesktopFrame desktopFrame;
    private SwingWorker<Void, Void> hilo;

    // <o> Getters y Setters de Empresa, Calculo e Interfaz <o>

    /**
     * Devuelve la instancia única de la empresa.
     *
     * @return La instancia de la empresa.
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Establece la instancia de la empresa.
     *
     * @param empresa Instancia de la empresa a establecer.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Devuelve la instancia del objeto de cálculo.
     *
     * @return La instancia del objeto de cálculo.
     */
    public Calculo getCalculo() {
        return calculo;
    }

    /**
     * Establece la instancia del objeto de cálculo.
     *
     * @param calculo Instancia del objeto de cálculo a establecer.
     */
    public void setCalculo(Calculo calculo) {
        this.calculo = calculo;
    }

    /**
     * Devuelve la configuración del sistema.
     *
     * @return Configuración del sistema.
     */
    public Configuracion getConfiguracion() {
        return configuracion;
    }

    /**
     * Establece la configuración del sistema.
     *
     * @param configuracion Configuración del sistema a establecer.
     */
    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    // <o> Calculo <o>

    /**
     * Realiza cálculos de recorridos entre dos paradas y muestra los resultados en
     * la interfaz.
     *
     * @param parada1   La primera parada.
     * @param parada2   La segunda parada.
     * @param horario   El horario del recorrido.
     * @param nrolineas Número de líneas a considerar.
     */
    public void calculo(Parada parada1, Parada parada2, int horario, int nrolineas) {
        List<List<Tramo>> resultado = calculo.recorridos(buscarParada(parada1), buscarParada(parada2), horario,
                nrolineas);
        consultaForm.terminar();
        resultadoForm.accion(resultadoForm.verDatos(resultado, horario, listarLineas()));
        resultadoForm.setVisible(true);
    }

    /**
     * Muestra los resultados de los recorridos más rápidos en la interfaz.
     *
     * @param trayecto    Lista de listas de tramos que representan los recorridos.
     * @param horaLlegada Hora de llegada.
     */
    public void masRapido(List<List<Tramo>> trayecto, int horaLlegada) {
        resultadoForm.terminar();
        subResultadoForm
                .accion(subResultadoForm.verDatos(Constantes.MAS_RAPIDO, trayecto, horaLlegada, listarLineas()));
        subResultadoForm.setVisible(true);
    }

    /**
     * Muestra los resultados de los recorridos menos costosos en la interfaz.
     *
     * @param trayecto    Lista de listas de tramos que representan los recorridos.
     * @param horaLlegada Hora de llegada.
     */
    public void menosCostoso(List<List<Tramo>> trayecto, int horaLlegada) {
        resultadoForm.terminar();
        subResultadoForm
                .accion(subResultadoForm.verDatos(Constantes.MENOS_COSTOSO, trayecto, horaLlegada, listarLineas()));
        subResultadoForm.setVisible(true);
    }

    // <o> Configuracion <o>

    /**
     * Devuelve el ResourceBundle utilizado para la internacionalización.
     *
     * @return ResourceBundle para la internacionalización.
     */
    public ResourceBundle getResourceBundle() {
        return configuracion.getResourceBundle();
    }

    // <o> Listar Modelos <o>

    /**
     * Devuelve un TreeMap que contiene todas las paradas de la empresa.
     *
     * @return TreeMap con claves enteras representando IDs de paradas y valores
     *         como instancias de Parada.
     */
    public TreeMap<Integer, Parada> listarParadas() {
        return empresa.getParadas();
    }

    /**
     * Devuelve un TreeMap que contiene todas las líneas de la empresa.
     *
     * @return TreeMap con claves de cadena representando nombres de líneas y
     *         valores como instancias de Linea.
     */
    public TreeMap<String, Linea> listarLineas() {
        return empresa.getLineas();
    }

    /**
     * Devuelve una lista que contiene todos los tramos de la empresa.
     *
     * @return Lista de instancias de Tramo.
     */
    public List<Tramo> listarTramos() {
        return empresa.getTramos();
    }

    /**
     * Busca una línea específica en la empresa.
     *
     * @param linea La línea a buscar.
     * @return La instancia de Linea correspondiente si se encuentra; de lo
     *         contrario, null.
     */
    public Linea buscarLinea(Linea linea) {
        return empresa.buscarLinea(linea);
    }

    /**
     * Busca una parada específica en la empresa.
     *
     * @param parada La parada a buscar.
     * @return La instancia de Parada correspondiente si se encuentra; de lo
     *         contrario, null.
     */
    public Parada buscarParada(Parada parada) {
        return empresa.buscarParada(parada);
    }

    /**
     * Busca un tramo específico en la empresa.
     *
     * @param tramo El tramo a buscar.
     * @return La instancia de Tramo correspondiente si se encuentra; de lo
     *         contrario, null.
     */
    public Tramo buscarTramo(Tramo tramo) {
        return empresa.buscarTramo(tramo);
    }

    /**
     * Cancela la operación de consulta y oculta el formulario de consulta.
     */
    public void cancelarConsulta() {
        consultaForm.setVisible(false);
    }

    /**
     * Cancela la operación de visualización de resultados y oculta el formulario de
     * resultados.
     */
    public void cancelarResultado() {
        resultadoForm.setVisible(false);
    }

    /**
     * Cancela la operación de visualización de resultados secundarios y oculta el
     * formulario correspondiente.
     */
    public void cancelaraSubResultado() {
        subResultadoForm.setVisible(false);
    }

    /**
     * Obtiene el formulario de consulta actualmente asociado.
     *
     * @return El formulario de consulta actual.
     */
    public ConsultaForm getConsultaForm() {
        return consultaForm;
    }

    /**
     * Establece el formulario de consulta.
     *
     * @param consultaForm El nuevo formulario de consulta a establecer.
     */
    public void setConsultaForm(ConsultaForm consultaForm) {
        this.consultaForm = consultaForm;
    }

    /**
     * Obtiene el formulario de resultados actualmente asociado.
     *
     * @return El formulario de resultados actual.
     */
    public ResultadoForm getResultadoForm() {
        return resultadoForm;
    }

    /**
     * Establece el formulario de resultados.
     *
     * @param resultadoForm El nuevo formulario de resultados a establecer.
     */
    public void setResultadoForm(ResultadoForm resultadoForm) {
        this.resultadoForm = resultadoForm;
    }

    /**
     * Obtiene el formulario de resultados secundarios actualmente asociado.
     *
     * @return El formulario de resultados secundarios actual.
     */
    public SubResultadoForm getSubResultadoForm() {
        return subResultadoForm;
    }

    /**
     * Establece el formulario de resultados secundarios.
     *
     * @param subResultadoForm El nuevo formulario de resultados secundarios a
     *                         establecer.
     */
    public void setSubResultadoForm(SubResultadoForm subResultadoForm) {
        this.subResultadoForm = subResultadoForm;
    }

    /**
     * Obtiene el marco de escritorio actualmente asociado.
     *
     * @return El marco de escritorio actual.
     */
    public DesktopFrame getDesktopFrame() {
        return desktopFrame;
    }

    /**
     * Establece el marco de escritorio.
     *
     * @param desktopFrame El nuevo marco de escritorio a establecer.
     */
    public void setDesktopFrame(DesktopFrame desktopFrame) {
        this.desktopFrame = desktopFrame;
    }

    /**
     * Obtiene la lista de líneas actualmente asociada.
     *
     * @return La lista de líneas actual.
     */
    public LineaList getLineaList() {
        return lineaList;
    }

    /**
     * Establece la lista de líneas.
     *
     * @param lineaList La nueva lista de líneas a establecer.
     */
    public void setLineaList(LineaList lineaList) {
        this.lineaList = lineaList;
    }

    /**
     * Obtiene el formulario de línea actualmente asociado.
     *
     * @return El formulario de línea actual.
     */
    public LineaForm getLineaForm() {
        return lineaForm;
    }

    /**
     * Establece el formulario de línea.
     *
     * @param lineaForm El nuevo formulario de línea a establecer.
     */
    public void setLineaForm(LineaForm lineaForm) {
        this.lineaForm = lineaForm;
    }

    /**
     * Obtiene la lista de paradas actualmente asociada.
     *
     * @return La lista de paradas actual.
     */
    public ParadaList getParadaList() {
        return paradaList;
    }

    /**
     * Establece la lista de paradas.
     *
     * @param paradaList La nueva lista de paradas a establecer.
     */
    public void setParadaList(ParadaList paradaList) {
        this.paradaList = paradaList;
    }

    /**
     * Obtiene el formulario de parada actualmente asociado.
     *
     * @return El formulario de parada actual.
     */
    public ParadaForm getParadaForm() {
        return paradaForm;
    }

    /**
     * Establece el formulario de parada.
     *
     * @param paradaForm El nuevo formulario de parada a establecer.
     */
    public void setParadaForm(ParadaForm paradaForm) {
        this.paradaForm = paradaForm;
    }

    /**
     * Obtiene la lista de tramos actualmente asociada.
     *
     * @return La lista de tramos actual.
     */
    public TramoList getTramoList() {
        return tramoList;
    }

    /**
     * Establece la lista de tramos.
     *
     * @param tramoList La nueva lista de tramos a establecer.
     */
    public void setTramoList(TramoList tramoList) {
        this.tramoList = tramoList;
    }

    /**
     * Obtiene el formulario de tramo actualmente asociado.
     *
     * @return El formulario de tramo actual.
     */
    public TramoForm getTramoForm() {
        return tramoForm;
    }

    /**
     * Establece el formulario de tramo.
     *
     * @param tramoForm El nuevo formulario de tramo a establecer.
     */
    public void setTramoForm(TramoForm tramoForm) {
        this.tramoForm = tramoForm;
    }

    /**
     * Oculta la lista de líneas.
     */
    public void salirLineaList() {
        lineaList.setVisible(false);
    }

    /**
     * Oculta la lista de paradas.
     */
    public void salirParadaList() {
        paradaList.setVisible(false);
    }

    /**
     * Oculta la lista de tramos.
     */
    public void salirTramoList() {
        tramoList.setVisible(false);
    }

    /**
     * Muestra el formulario de consulta y realiza la acción asociada.
     */
    public void mostrarConsulta() {
        consultaForm.accion();
        consultaForm.setVisible(true);
    }

    /**
     * Muestra la lista de líneas cargando los datos y haciéndola visible.
     */
    public void mostrarLineaList() {
        lineaList.loadTable();
        lineaList.setVisible(true);
    }

    /**
     * Muestra la lista de paradas cargando los datos y haciéndola visible.
     */
    public void mostrarParadaList() {
        paradaList.loadTable();
        paradaList.setVisible(true);
    }

    /**
     * Muestra la lista de tramos cargando los datos y haciéndola visible.
     */
    public void mostrarTramoList() {
        tramoList.loadTable();
        tramoList.setVisible(true);
    }

    // <o> Configuracion <o>

    /**
     * Obtiene el formulario de configuración actualmente asociado.
     *
     * @return El formulario de configuración actual.
     */
    public ConfigForm getConfigForm() {
        return configForm;
    }

    /**
     * Establece el formulario de configuración.
     *
     * @param configForm El nuevo formulario de configuración a establecer.
     */
    public void setConfigForm(ConfigForm configForm) {
        this.configForm = configForm;
    }

    /**
     * Muestra el formulario de configuración.
     */
    public void mostrarIdioma() {
        configForm.setVisible(true);
    }

    /**
     * Oculta el formulario de configuración.
     */
    public void salirConfiguracion() {
        configForm.setVisible(false);
    }

    // <o> LineaList <o>

    /**
     * Muestra el formulario para insertar una línea.
     */
    public void insertarLineaForm() {
        lineaForm.accion(Constantes.INSERTAR, null);
        lineaForm.setVisible(true);
    }

    /**
     * Muestra el formulario para modificar una línea.
     *
     * @param linea La línea a modificar.
     */
    public void modificarLineaForm(Linea linea) {
        lineaForm.accion(Constantes.MODIFICAR, linea);
        lineaForm.setVisible(true);
    }

    /**
     * Muestra el formulario para borrar una línea.
     *
     * @param linea La línea a borrar.
     */
    public void borrarLineaForm(Linea linea) {
        lineaForm.accion(Constantes.BORRAR, linea);
        lineaForm.setVisible(true);
    }

    // <o> LineaForm <o>

    /**
     * Cancela la operación en el formulario de línea.
     */
    public void cancelarLinea() {
        lineaForm.setVisible(false);
    }

    /**
     * Inserta una nueva línea en la empresa y actualiza la lista de líneas.
     *
     * @param linea La línea a insertar.
     * @throws LineaExistenteException Si la línea ya existe.
     */
    public void insertarLinea(Linea linea) throws LineaExistenteException {
        empresa.agregarLinea(linea);
        lineaForm.setVisible(false);
        lineaList.addRow(linea);
    }

    /**
     * Modifica una línea existente en la empresa y actualiza la lista de líneas.
     *
     * @param linea La línea a modificar.
     */
    public void modificarLinea(Linea linea) {
        empresa.modificarLinea(linea);
        lineaList.setAccion(Constantes.MODIFICAR);
        lineaList.setLinea(linea);
        lineaForm.setVisible(false);
    }

    /**
     * Borra una línea existente en la empresa y actualiza la lista de líneas.
     *
     * @param linea La línea a borrar.
     */
    public void borrarLinea(Linea linea) {
        empresa.borrarLinea(linea);
        lineaList.setAccion(Constantes.BORRAR);
        lineaForm.setVisible(false);
    }

    // <o> ParadaList <o>

    /**
     * Muestra el formulario para insertar una parada.
     */
    public void insertarParadaForm() {
        paradaForm.accion(Constantes.INSERTAR, null);
        paradaForm.setVisible(true);
    }

    /**
     * Muestra el formulario para modificar una parada.
     *
     * @param parada La parada a modificar.
     */
    public void modificarParadaForm(Parada parada) {
        paradaForm.accion(Constantes.MODIFICAR, parada);
        paradaForm.setVisible(true);
    }

    /**
     * Muestra el formulario para borrar una parada.
     *
     * @param parada La parada a borrar.
     */
    public void borrarParadaForm(Parada parada) {
        paradaForm.accion(Constantes.BORRAR, parada);
        paradaForm.setVisible(true);
    }

    // <o> ParadaForm <o>

    /**
     * Cancela la operación en el formulario de parada.
     */
    public void cancelarParada() {
        paradaForm.setVisible(false);
    }

    /**
     * Inserta una nueva parada en la empresa y actualiza la lista de paradas.
     *
     * @param parada La parada a insertar.
     * @throws ParadaExistenteException Si la parada ya existe.
     */
    public void insertarParada(Parada parada) throws ParadaExistenteException {
        empresa.agregarParada(parada);
        paradaForm.setVisible(false);
        paradaList.addRow(parada);
    }

    /**
     * Modifica una parada existente en la empresa y actualiza la lista de paradas.
     *
     * @param parada La parada a modificar.
     */
    public void modificarParada(Parada parada) {
        empresa.modificarParada(parada);
        paradaList.setAccion(Constantes.MODIFICAR);
        paradaList.setParada(parada);
        paradaForm.setVisible(false);
    }

    /**
     * Borra una parada existente en la empresa y actualiza la lista de paradas.
     *
     * @param parada La parada a borrar.
     */
    public void borrarParada(Parada parada) {
        empresa.borrarParada(parada);
        paradaList.setAccion(Constantes.BORRAR);
        paradaForm.setVisible(false);
    }

    // <o> TramoList <o>

    // <o> TramoList <o>

    /**
     * Muestra el formulario para insertar un tramo.
     */
    public void insertarTramoForm() {
        tramoForm.accion(Constantes.INSERTAR, null);
        tramoForm.setVisible(true);
    }

    /**
     * Muestra el formulario para modificar un tramo.
     *
     * @param tramo El tramo a modificar.
     */
    public void modificarTramoForm(Tramo tramo) {
        tramoForm.accion(Constantes.MODIFICAR, tramo);
        tramoForm.setVisible(true);
    }

    /**
     * Muestra el formulario para borrar un tramo.
     *
     * @param tramo El tramo a borrar.
     */
    public void borrarTramoForm(Tramo tramo) {
        tramoForm.accion(Constantes.BORRAR, tramo);
        tramoForm.setVisible(true);
    }

    // <o> TramoForm <o>

    /**
     * Cancela la operación en el formulario de tramo.
     */
    public void cancelarTramo() {
        tramoForm.setVisible(false);
    }

    /**
     * Inserta un nuevo tramo en la empresa y actualiza la lista de tramos.
     *
     * @param tramo El tramo a insertar.
     * @throws TramoExistenteException Si el tramo ya existe.
     */
    public void insertarTramo(Tramo tramo) throws TramoExistenteException {
        empresa.agregarTramo(tramo);
        tramoForm.setVisible(false);
        tramoList.addRow(tramo);
    }

    /**
     * Modifica un tramo existente en la empresa y actualiza la lista de tramos.
     *
     * @param tramo El tramo a modificar.
     */
    public void modificarTramo(Tramo tramo) {
        empresa.modificarTramo(tramo);
        tramoList.setAccion(Constantes.MODIFICAR);
        tramoList.setTramo(tramo);
        tramoForm.setVisible(false);
    }

    /**
     * Borra un tramo existente en la empresa y actualiza la lista de tramos.
     *
     * @param tramo El tramo a borrar.
     */
    public void borrarTramo(Tramo tramo) {
        empresa.borrarTramo(tramo);
        tramoList.setAccion(Constantes.BORRAR);
        tramoForm.setVisible(false);
    }

    // <o> Hilos <o>

    /**
     * Ejecuta un hilo SwingWorker.
     *
     * @param sw El SwingWorker a ejecutar.
     */
    public void ejecutarHilo(SwingWorker<Void, Void> sw) {
        this.hilo = sw;
        hilo.execute();
    }

    /**
     * Cancela la ejecución del hilo actual.
     */
    public void cancelarHilo() {
        this.hilo.cancel(true);
    }

    /**
     * Actualiza la barra de progreso en el formulario de consulta.
     *
     * @param i El valor de progreso.
     */
    public void actualizarBarraConsulta(int i) {
        consultaForm.actualizarBarra(i);
    }

    /**
     * Actualiza la barra de progreso en el formulario de resultado.
     *
     * @param i El valor de progreso.
     */
    public void actualizarBarraResultado(int i) {
        resultadoForm.actualizarBarra(i);
    }
}