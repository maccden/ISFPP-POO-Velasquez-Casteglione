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
    
    /*
    public static TreeMap<String, Linea> cargarLineas(String fileName, TreeMap<String, Parada> paradas) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        TreeMap<String, Linea> lineas = new TreeMap<>();
        String reader;
        
        while ((reader = bf.readLine()) != null) {
            String[] partes = reader.split(";");
            String idLinea = partes[0];
            
            // idLinea = partes[0]
            // ArrayList = partes[1] .. partes[n]

            lineas.put(idLinea, new Linea(idLinea, paradas.get(idParada)));
        }

        bf.close();
        return lineas;
    }
    */
    
    public static TreeMap<String, Colectivo> cargarColectivos(String fileName, TreeMap<String, Linea> lineas) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        TreeMap<String, Colectivo> colectivos = new TreeMap<>();
        String reader;
        
        while ((reader = bf.readLine()) != null) {
            String[] partes = reader.split(";");
            String idColectivo = partes[0];
            String idLinea = partes[1];
            colectivos.put(idColectivo, new Colectivo(idColectivo, lineas.get(idLinea)));
        }

        bf.close();
        return colectivos;
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
            tramos.add(0, new Tramo(paradas.get(parada1), paradas.get(parada2), Integer.parseInt(tiempo)));
        }

        bf.close();
        return tramos;
    }
}
