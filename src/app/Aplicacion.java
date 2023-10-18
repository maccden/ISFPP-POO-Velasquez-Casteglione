package app;

import java.io.IOException;

import datastructures.List;
import datastructures.TreeMap;
import interfaz.Interfaz;
import logica.Calculo;
import model.*;
import data.*;

public class Aplicacion {
    public static void main(String[] args) {
        // Cargar parametros
        try {
            CargarParametros.parametros();
        } catch (IOException e) {
            System.out.println("Error al cargar parametros");
            System.exit(-1);
        }

        // Cargar datos
        TreeMap<String, Parada> paradas = null;
        TreeMap<String, Linea> lineas = null;
        List<Tramo> tramos = null;

        try {
            paradas = Datos.cargarParadas(CargarParametros.getArchivoParada());
            lineas = Datos.cargarLineas(CargarParametros.getArchivoLinea(), paradas);
            tramos = Datos.cargarTramos(CargarParametros.getArchivoTramo(), paradas);
        } catch (IOException e) {
            System.out.println("Error al cargar archivos de datos");
            System.exit(-1);
        }

        // Ingreso datos usuario
        int opcion = Interfaz.opcion();
        Parada origen = Interfaz.ingresarEstacionOrigen(paradas);
        Parada destino = Interfaz.ingresarEstacionDestino(paradas, origen);

        origen = paradas.get("25");
        destino = paradas.get("1");

        // Realizar calculo
        Calculo c = new Calculo(paradas, tramos);

        List<Tramo> recorrido = c.rapido(origen, destino);

        // Mostrar resultado
        Interfaz.resultado(recorrido);

    }
}