package app;

import java.io.IOException;

import java.util.List;
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
        TreeMap<Integer, Parada> paradas = null;
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

        origen = paradas.get(1);

        Parada destino = Interfaz.ingresarEstacionDestino(paradas, origen);

        destino = paradas.get(2);

        // Realizar calculo
        Calculo c = new Calculo(paradas, lineas, tramos);

        List<Tramo> recorrido = c.masRapido(origen, destino);

        // Mostrar resultado
        Interfaz.resultado(recorrido);

    }
}

/*
Expandir el grafo para que represente a cada Linea

Los vertices representaran Parada y Linea

Cuando devuelvan se devuelvan listas en los modelos, devolver un clone
 */