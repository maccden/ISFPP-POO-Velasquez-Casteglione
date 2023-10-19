package controlador;

import java.io.IOException;
import datastructures.List;
import datastructures.TreeMap;
import datos.CargarParametros;
import interfaz.Interfaz;
import modelo.Linea;
import modelo.Parada;
import modelo.Tramo;
import negocio.Calculo;

public class AplicacionConsultas {

	private Calculo calculo;
	private Interfaz interfaz;
	private Coordinador coordinador;

    public static void main(String[] args) throws IOException {
        AplicacionConsultas miAplicacion = new AplicacionConsultas();
        miAplicacion.iniciar();
        miAplicacion.consultar();
    }

    private void iniciar() throws IOException {
        coordinador = new Coordinador();
        calculo = new Calculo();
        interfaz = new Interfaz();
        calculo.setCoordinador(coordinador);
        interfaz.setCoordinador(coordinador);
        coordinador.setCalculo(calculo);
        coordinador.setInterfaz(interfaz);
    }

    private void consultar() throws IOException {
        CargarParametros.parametros();
        TreeMap<String, Parada> paradas = coordinador.listarParadas();
        //TreeMap<String, Linea> linea = coordinador.listarLineas();
        List<Tramo> tramos = coordinador.listarTramos();
        int opcion = Interfaz.opcion();
		calculo.cargarDatos(paradas, tramos);
        Parada origen = Interfaz.ingresarEstacionOrigen(paradas);
        Parada destino = Interfaz.ingresarEstacionDestino(paradas, origen);
        origen = paradas.get("1");
        destino = paradas.get("2");
		List<Tramo> recorrido1 = calculo.rapido(origen, destino);
		Interfaz.resultado(recorrido1);
    }
}