package controlador;

import gui.consulta.ConsultaForm;
import gui.consulta.DesktopFrameConsulta;
import gui.consulta.ResultadoForm;
import negocio.Calculo;
import negocio.Empresa;

public class AplicacionConsultas {

    private Empresa empresa;
    private DesktopFrameConsulta desktopFrameConsulta;
    private ConsultaForm consultaForm;
    private ResultadoForm resultadoForm;
	private Calculo calculo;
	private Coordinador coordinador;

    public static void main(String[] args) {
        AplicacionConsultas miAplicacion = new AplicacionConsultas();
        miAplicacion.iniciar();
    }

    private void iniciar() {
        empresa = Empresa.getEmpresa();
        coordinador = new Coordinador();
        desktopFrameConsulta = new DesktopFrameConsulta();
        consultaForm = new ConsultaForm();
        resultadoForm = new ResultadoForm();
        calculo = new Calculo();

        desktopFrameConsulta.setCoordinador(coordinador);
        consultaForm.setCoordinador(coordinador);
        resultadoForm.setCoordinador(coordinador);
        calculo.setCoordinador(coordinador);

        coordinador.setEmpresa(empresa);
        coordinador.setDesktopFrameConsulta(desktopFrameConsulta);
        coordinador.setConsultaForm(consultaForm);
        coordinador.setResultadoForm(resultadoForm);
        coordinador.setCalculo(calculo);

        calculo.cargarDatos(coordinador.listarParadas(), coordinador.listarLineas(), coordinador.listarTramos());
        desktopFrameConsulta.setVisible(true);
    }
}