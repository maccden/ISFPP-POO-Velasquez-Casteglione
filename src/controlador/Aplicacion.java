package controlador;

import gui.*;
import negocio.Calculo;
import negocio.Empresa;
import negocio.Subject;

public class Aplicacion {

    private Empresa empresa;
    private DesktopFrame desktopFrame;
    private LineaList lineaList;
    private LineaForm lineaForm;
    private ParadaList paradaList;
    private ParadaForm paradaForm;
    private TramoList tramoList;
    private TramoForm tramoForm;
    private ConsultaForm consultaForm;
    private ResultadoForm resultadoForm;
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

        calculo.init(subject);
        empresa.init(subject);

        calculo.cargarDatos(coordinador.listarParadas(), coordinador.listarLineas(), coordinador.listarTramos());
        desktopFrame.setVisible(true);
    }
}
