package controlador;

import gui.datos.DesktopFrameDatos;
//import gui.datos.LineaForm;
//import gui.datos.LineaList;

import gui.datos.ParadaForm;
import gui.datos.ParadaList;
import negocio.Empresa;


public class AplicacionDatos {
    private Empresa empresa;
    private DesktopFrameDatos desktopFrameDatos;
    /*
    private LineaList lineaList;
    private LineaForm lineaForm;
     */
    private ParadaList paradaList;
    private ParadaForm paradaForm;
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
        //lineaList = new LineaList();
        //lineaForm = new LineaForm();
        paradaList = new ParadaList();
        paradaForm = new ParadaForm();

        /* Se establecen las relaciones entre clases */
        desktopFrameDatos.setCoordinador(coordinador);
        //lineaList.setCoordinador(coordinador);
        //lineaForm.setCoordinador(coordinador);
        paradaList.setCoordinador(coordinador);
        paradaForm.setCoordinador(coordinador);

        /* Se establecen relaciones con la clase coordinador */
        coordinador.setEmpresa(empresa);
        coordinador.setDesktopFrameDatos(desktopFrameDatos);
        //coordinador.setLineaList(lineaList);
        //coordinador.setLineaForm(lineaForm);
        coordinador.setParadaList(paradaList);
        coordinador.setParadaForm(paradaForm);

        desktopFrameDatos.setVisible(true);
    }
}
