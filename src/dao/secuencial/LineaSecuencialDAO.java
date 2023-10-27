package dao.secuencial;

import dao.LineaDAO;
import dao.ParadaDAO;
import datastructures.TreeMap;
import factory.Factory;
import modelo.Linea;
import modelo.Parada;
import negocio.Empresa;
import util.Time;

import java.io.*;
import java.util.*;

public class LineaSecuencialDAO implements LineaDAO {
    private TreeMap<String, Linea> list;
    private String name;
    private Hashtable<Integer, Parada> paradas;
    private boolean actualizar;

    public LineaSecuencialDAO() {
        paradas = cargarParada();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("linea");
        actualizar = true;
    }

    private TreeMap<String, Linea> readFromFile(String file) {
        BufferedReader bf = null;
        TreeMap<String, Linea> lineas = new TreeMap<>();
        String reader;

        try {
            bf = new BufferedReader(new FileReader(file));
            while ((reader = bf.readLine()) != null) {
                String[] partes = reader.split(";");
                String idLinea = partes[0];

                Linea linea = new Linea(partes[0], Time.toMins(partes[1]), Time.toMins(partes[2]), Integer.parseInt(partes[3]));
                for (int i = 4; i < partes.length; i++) {
                    if (paradas.get(Integer.valueOf(partes[i])) != null) {
                            linea.agregarParada(paradas.get(Integer.valueOf(partes[i])));
                            paradas.get(Integer.valueOf(partes[i])).setLinea(linea);
                    }
                }
                lineas.put(idLinea, linea);
            }
            bf.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error opening file.");
            fileNotFoundException.printStackTrace();
        } catch (NoSuchElementException noSuchElementException) {
            System.err.println("Error in file record structure");
            noSuchElementException.printStackTrace();
        } catch (IllegalStateException illegalStateException) {
            System.err.println("Error reading from file.");
            illegalStateException.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error: an I/O error occur");
            throw new RuntimeException(e);
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    System.err.println("Error: an I/O error occur");
                    throw new RuntimeException(e);
                }
            }
        }

        return lineas;
    }

    private void writeToFile(TreeMap<String, Linea> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Linea l : list.values()) {
                outFile.format("%s;%s;%s;%s;", l.getCodigo(), l.getComienza(), l.getFinaliza(), l.getFrecuencia());
                for (Parada parada: l.getParadas()) {
                    if (l.getParadas().get(l.getParadas().size() - 1).equals(parada))
                        outFile.format("", parada.getCodigo());
                    else
                        outFile.format("%s;", parada.getCodigo());
                }
                outFile.format("\n");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error creating file.");
        } catch (FormatterClosedException formatterClosedException) {
            System.err.println("Error writing to file.");
        } finally {
            if (outFile != null)
                outFile.close();
        }
    }

    @Override
    public TreeMap<String, Linea> buscarTodos() {
        if (actualizar) {
            list = readFromFile(name);
            actualizar = false;
        }
        return list;
    }

    @Override
    public void insertar(Linea linea) {
        list.put(linea.getCodigo(), linea);
        writeToFile(list, name);
        actualizar = true;
    }

    @Override
    public void actualizar(Linea linea) {
        list.put(linea.getCodigo(), linea);
        writeToFile(list, name);
        actualizar = true;
    }

    @Override
    public void borrar(Linea linea) {
        list.remove(linea.getCodigo());
        writeToFile(list, name);
        actualizar = true;
    }

    private Hashtable<Integer, Parada> cargarParada() {
        Hashtable<Integer, Parada> parada = new Hashtable<Integer, Parada>();
        ParadaDAO ParadaDAO = (ParadaDAO) Factory.getInstancia("PARADA");
        TreeMap<Integer, Parada> ds = ParadaDAO.buscarTodos();
        for (Parada d : ds.values())
            parada.put(d.getCodigo(), d);
        return parada;
    }
}
