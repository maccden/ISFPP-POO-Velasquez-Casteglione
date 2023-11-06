package controlador;

import gui.datos.*;

import negocio.Empresa;

public class AplicacionDatos {
    private Empresa empresa;
    private DesktopFrameDatos desktopFrameDatos;
    private LineaList lineaList;
    private LineaForm lineaForm;
    private ParadaList paradaList;
    private ParadaForm paradaForm;
    private TramoList tramoList;
    private TramoForm tramoForm;
    private Coordinador coordinador;

    public static void main(String[] args) {
        AplicacionDatos miAplicacion = new AplicacionDatos();
        miAplicacion.iniciar();
    }

    private void iniciar() {
        /* Se instancian las clases */
        empresa = Empresa.getEmpresa();
        coordinador = new Coordinador();
        desktopFrameDatos = new DesktopFrameDatos();
        lineaList = new LineaList();
        lineaForm = new LineaForm();
        paradaList = new ParadaList();
        paradaForm = new ParadaForm();
        tramoList = new TramoList();
        tramoForm = new TramoForm();

        /* Se establecen las relaciones entre clases */
        desktopFrameDatos.setCoordinador(coordinador);
        lineaList.setCoordinador(coordinador);
        lineaForm.setCoordinador(coordinador);
        paradaList.setCoordinador(coordinador);
        paradaForm.setCoordinador(coordinador);
        tramoList.setCoordinador(coordinador);
        tramoForm.setCoordinador(coordinador);

        /* Se establecen relaciones con la clase coordinador */
        coordinador.setEmpresa(empresa);
        coordinador.setDesktopFrameDatos(desktopFrameDatos);
        coordinador.setLineaList(lineaList);
        coordinador.setLineaForm(lineaForm);
        coordinador.setParadaList(paradaList);
        coordinador.setParadaForm(paradaForm);
        coordinador.setTramoList(tramoList);
        coordinador.setTramoForm(tramoForm);

        desktopFrameDatos.setVisible(true);
    }
}
