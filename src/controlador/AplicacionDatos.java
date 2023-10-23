package controlador;

import gui.datos.DesktopFrameDatos;
import gui.datos.LineaForm;
import gui.datos.LineaList;

import negocio.Empresa;
import negocio.LineaExistenteException;

import java.io.IOException;

public class AplicacionDatos {
    private Empresa empresa;
    private DesktopFrameDatos desktopFrameDatos;
    private LineaList lineaList;
    private LineaForm lineaForm;
    private Coordinador coordinador;

    public static void main(String[] args) {
        AplicacionDatos miAplicacion = new AplicacionDatos();
        miAplicacion.iniciar();
    }

    private void iniciar() throws LineaExistenteException {
        /* Se instancian las clases */
        empresa = Empresa.getEmpresa();
        coordinador = new Coordinador();
        desktopFrameDatos = new DesktopFrameDatos();
        lineaList = new LineaList();
        lineaForm = new LineaForm();

        /* Se establecen las relaciones entre clases */
        desktopFrameDatos.setCoordinador(coordinador);
        lineaList.setCoordinador(coordinador);
        lineaForm.setCoordinador(coordinador);

        /* Se establecen relaciones con la clase coordinador */
        coordinador.setEmpresa(empresa);
        coordinador.setDesktopFrameDatos(desktopFrameDatos);
        coordinador.setLineaList(lineaList);
        coordinador.setLineaForm(lineaForm);

        desktopFrameDatos.setVisible(true);
    }
}
