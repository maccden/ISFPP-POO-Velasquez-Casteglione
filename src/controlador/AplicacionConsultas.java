package controlador;

import java.io.IOException;
import java.util.List;
import interfaz.Interfaz;
import modelo.Parada;
import modelo.Tramo;
import negocio.Calculo;
import negocio.Empresa;

public class AplicacionConsultas {

    private Empresa empresa;
	private Calculo calculo;
	private Interfaz interfaz;
	private Coordinador coordinador;

    public static void main(String[] args) throws IOException {
        AplicacionConsultas miAplicacion = new AplicacionConsultas();
        miAplicacion.iniciar();
        miAplicacion.consultar();
    }

    private void iniciar() throws IOException {

        empresa = Empresa.getEmpresa();
        calculo = new Calculo();
        coordinador = new Coordinador();
        interfaz = new Interfaz();

        calculo.setCoordinador(coordinador);
        interfaz.setCoordinador(coordinador);

        coordinador.setEmpresa(empresa);
        coordinador.setCalculo(calculo);
        coordinador.setInterfaz(interfaz);

        calculo.cargarDatos(coordinador.listarParadas(), coordinador.listarLineas(), coordinador.listarTramos());
    }

    private void consultar() throws IOException {

        int opcion = Interfaz.opcion();
        Parada origen = Interfaz.ingresarEstacionOrigen(coordinador.listarParadas());
        Parada destino = Interfaz.ingresarEstacionDestino(coordinador.listarParadas(), origen);

        calculo.cargarDatos(coordinador.listarParadas(), coordinador.listarLineas(), coordinador.listarTramos());

        // <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>

        // Lo de abajo es la unica manera de ingresar las paradas, ya que la interfaz aun no funciona completamente

        // <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>  <!>

        origen = coordinador.listarParadas().get(84);
        destino = coordinador.listarParadas().get(85);

		List<Tramo> recorrido = calculo.masRapido(origen, destino);
		Interfaz.resultado(recorrido);
    }
}