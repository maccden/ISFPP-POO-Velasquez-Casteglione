package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import datastructures.TreeMap;
import model.*;

public class Datos {

    public static TreeMap<Integer, Parada> cargarParadas(String fileName) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        TreeMap<Integer, Parada> paradas = new TreeMap<>();
        String reader;

        while ((reader = bf.readLine()) != null) {
            String[] partes = reader.split(";");
            String idParada = partes[0];
            String direccion = partes[1];
            paradas.put(Integer.parseInt(idParada), new Parada(Integer.parseInt(idParada), direccion));
        }

        bf.close();
        return paradas;
    }

    public static TreeMap<String, Linea> cargarLineas(String fileName, TreeMap<Integer, Parada> paradas)
            throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        TreeMap<String, Linea> lineas = new TreeMap<>();
        String reader;

        while ((reader = bf.readLine()) != null) {
            String[] partes = reader.split(";");
            String idLinea = partes[0];
            String sentido = partes[1];
            
            if (lineas.get(idLinea) != null) {
                if (sentido.equals("I")) {
                    for (int i = 2; i < partes.length; i++) {
                        if (paradas.get(Integer.parseInt(partes[i])) != null) {
                            lineas.get(idLinea).agregarIda(paradas.get(Integer.parseInt(partes[i])));
                            paradas.get(Integer.parseInt(partes[i])).setLinea(lineas.get(idLinea));
                        }
                    }
                } else if (sentido.equals("R")) {
                    for (int i = 2; i < partes.length; i++) {
                        if (paradas.get(Integer.parseInt(partes[i])) != null) {
                            lineas.get(idLinea).agregarVuelta(paradas.get(Integer.parseInt(partes[i])));
                            paradas.get(Integer.parseInt(partes[i])).setLinea(lineas.get(idLinea));
                        }
                    }
                }
            } else {
                Linea linea = new Linea(idLinea);
                if (sentido.equals("I")) {
                    for (int i = 2; i < partes.length; i++) {
                        if (paradas.get(Integer.parseInt(partes[i])) != null) {
                            linea.agregarIda(paradas.get(Integer.parseInt(partes[i])));
                            paradas.get(Integer.parseInt(partes[i])).setLinea(linea);
                        }
                    }
                } else if (sentido.equals("R")) {
                    for (int i = 2; i < partes.length; i++) {
                        if (paradas.get(Integer.parseInt(partes[i])) != null) {
                            linea.agregarVuelta(paradas.get(Integer.parseInt(partes[i])));
                            paradas.get(Integer.parseInt(partes[i])).setLinea(linea);
                        }
                    }
                }
                lineas.put(idLinea, linea);
            }
        }

        bf.close();
        return lineas;
    }

    public static List<Tramo> cargarTramos(String fileName, TreeMap<Integer, Parada> paradas) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        List<Tramo> tramos = new ArrayList<>();
        String reader;

        while ((reader = bf.readLine()) != null) {
            String[] partes = reader.split(";");
            String parada1 = partes[0];
            String parada2 = partes[1];
            String tiempo = partes[2];
            String tipo = partes[3];
            tramos.add(0, new Tramo(paradas.get(Integer.parseInt(parada1)), paradas.get(Integer.parseInt(parada2)), Integer.parseInt(tiempo),
                    Integer.parseInt(tipo)));
        }

        bf.close();
        return tramos;
    }
}