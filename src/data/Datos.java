package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import datastructures.ArrayList;
import datastructures.List;
import datastructures.TreeMap;
import model.*;

public class Datos {

    public static TreeMap<String, Parada> cargarParadas(String fileName) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        TreeMap<String, Parada> paradas = new TreeMap<>();
        String reader;

        while ((reader = bf.readLine()) != null) {
            String[] partes = reader.split(";");
            String idParada = partes[0];
            String direccion = partes[1];
            paradas.put(idParada, new Parada(idParada, direccion));
        }

        bf.close();
        return paradas;
    }

    public static TreeMap<String, Linea> cargarLineas(String fileName, TreeMap<String, Parada> paradas)
            throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        TreeMap<String, Linea> lineas = new TreeMap<>();
        String reader;

        while ((reader = bf.readLine()) != null) {
            String[] partes = reader.split(";");
            String idLinea = partes[0];
            String sentido = partes[1];
            Linea linea = new Linea(idLinea);

            for (int i = 2; i < partes.length; i++) {
                Parada parada = paradas.get(partes[i]);

                if (parada != null) {
                    if (sentido.equals("I"))
                        linea.agregarIda(parada);
                    else if (sentido.equals("R"))
                        linea.agregarVuelta(parada);
                    parada.setLinea(idLinea);
                }
            }
            
            lineas.put(idLinea, linea);
        }

        bf.close();
        return lineas;
    }

    public static List<Tramo> cargarTramos(String fileName, TreeMap<String, Parada> paradas) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        List<Tramo> tramos = new ArrayList<>();
        String reader;

        while ((reader = bf.readLine()) != null) {
            String[] partes = reader.split(";");
            String parada1 = partes[0];
            String parada2 = partes[1];
            String tiempo = partes[2];
            String tipo = partes[3];
            tramos.add(0, new Tramo(paradas.get(parada1), paradas.get(parada2), Integer.parseInt(tiempo),
                    Integer.parseInt(tipo)));
        }

        bf.close();
        return tramos;
    }
}