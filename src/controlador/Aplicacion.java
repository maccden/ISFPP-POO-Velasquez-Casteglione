package controlador;

import gui.*;
import gui.config.ConfigForm;
import gui.consulta.ConsultaForm;
import gui.consulta.ResultadoForm;
import gui.consulta.SubResultadoForm;
import gui.datos.*;
import negocio.Calculo;
import negocio.Empresa;
import negocio.Subject;

public class Aplicacion {

    private Empresa empresa;
    private Configuracion configuracion;
    private DesktopFrame desktopFrame;
    private LineaList lineaList;
    private LineaForm lineaForm;
    private ParadaList paradaList;
    private ParadaForm paradaForm;
    private TramoList tramoList;
    private TramoForm tramoForm;
    private ConsultaForm consultaForm;
    private ResultadoForm resultadoForm;
    private ConfigForm configForm;
    private SubResultadoForm subResultadoForm;
    private Calculo calculo;
    private Subject subject;
    private Coordinador coordinador;
    public static void main(String[] args) {
        Aplicacion miAplicacion = new Aplicacion();
        miAplicacion.iniciar();
    }

    private void iniciar() {
        empresa = Empresa.getEmpresa();
        subject = new Subject();
        coordinador = new Coordinador();
        desktopFrame = new DesktopFrame();
        lineaList = new LineaList();
        lineaForm = new LineaForm();
        paradaList = new ParadaList();
        paradaForm = new ParadaForm();
        tramoList = new TramoList();
        tramoForm = new TramoForm();
        consultaForm = new ConsultaForm();
        resultadoForm = new ResultadoForm();
        subResultadoForm = new SubResultadoForm();
        calculo = new Calculo();
        configuracion = Configuracion.getConfiguracion();
        configForm = new ConfigForm();

        desktopFrame.setCoordinador(coordinador);
        lineaList.setCoordinador(coordinador);
        lineaForm.setCoordinador(coordinador);
        paradaList.setCoordinador(coordinador);
        paradaForm.setCoordinador(coordinador);
        tramoList.setCoordinador(coordinador);
        tramoForm.setCoordinador(coordinador);
        consultaForm.setCoordinador(coordinador);
        resultadoForm.setCoordinador(coordinador);
        subResultadoForm.setCoordinador(coordinador);
        calculo.setCoordinador(coordinador);
        configuracion.setCoordinador(coordinador);
        configForm.setCoordinador(coordinador);

        coordinador.setEmpresa(empresa);
        coordinador.setLineaList(lineaList);
        coordinador.setLineaForm(lineaForm);
        coordinador.setParadaList(paradaList);
        coordinador.setParadaForm(paradaForm);
        coordinador.setTramoList(tramoList);
        coordinador.setTramoForm(tramoForm);
        coordinador.setConsultaForm(consultaForm);
        coordinador.setResultadoForm(resultadoForm);
        coordinador.setSubResultadoForm(subResultadoForm);
        coordinador.setCalculo(calculo);
        coordinador.setConfiguracion(configuracion);
        coordinador.setConfigForm(configForm);

        calculo.init(subject);
        empresa.init(subject);
        configForm.init();
        consultaForm.init();
        resultadoForm.init();
        subResultadoForm.init();
        lineaForm.init();
        lineaList.init();
        paradaForm.init();
        paradaList.init();
        tramoForm.init();
        tramoList.init();
        desktopFrame.init();

        calculo.cargarDatos(coordinador.listarParadas(), coordinador.listarLineas(), coordinador.listarTramos());
        desktopFrame.setVisible(true);
    }
}
