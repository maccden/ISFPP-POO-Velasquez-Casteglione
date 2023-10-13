package app;

import java.io.IOException;

import datastructures.List;
import datastructures.TreeMap;
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
        TreeMap<String, Parada> paradas;
        TreeMap<String, Linea> lineas;
        List<Tramo> tramos;

        try {
            paradas = Datos.cargarParadas(CargarParametros.getArchivoParada());
            lineas = Datos.cargarLineas(CargarParametros.getArchivoLinea(), paradas);
            tramos = Datos.cargarTramos(CargarParametros.getArchivoTramo(), paradas);
        } catch (IOException e) {
            System.out.println("Error al cargar archivos de datos");
            System.exit(-1);
        }
    }
}
