package dao.secuencial;

import dao.LineaDAO;
import dao.ParadaDAO;
import datastructures.TreeMap;
import factory.Factory;
import modelo.Linea;
import modelo.Parada;
import negocio.Empresa;

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

    private TreeMap<String, Linea> readFromFile(String file) throws IOException {
        BufferedReader bf = null;
        TreeMap<String, Linea> lineas = new TreeMap<>();
        String reader;

        try {
            bf = new BufferedReader(new FileReader(file));
            while ((reader = bf.readLine()) != null) {
                String[] partes = reader.split(";");
                String idLinea = partes[0];
                String sentido = partes[1];

                if (lineas.get(idLinea) != null) {
                    if (sentido.equals("I")) {
                        for (int i = 2; i < partes.length; i++) {
                            if (paradas.get(Integer.valueOf(partes[i])) != null) {
                                lineas.get(idLinea).agregarIda(paradas.get(Integer.valueOf(partes[i])));
                                paradas.get(Integer.valueOf(partes[i])).setLinea(lineas.get(idLinea));
                            }
                        }
                    } else if (sentido.equals("R")) {
                        for (int i = 2; i < partes.length; i++) {
                            if (paradas.get(Integer.valueOf(partes[i])) != null) {
                                lineas.get(idLinea).agregarVuelta(paradas.get(Integer.valueOf(partes[i])));
                                paradas.get(Integer.valueOf(partes[i])).setLinea(lineas.get(idLinea));
                            }
                        }
                    }
                } else {
                    Linea linea = new Linea(idLinea);
                    if (sentido.equals("I")) {
                        for (int i = 2; i < partes.length; i++) {
                            if (paradas.get(Integer.valueOf(partes[i])) != null) {
                                linea.agregarIda(paradas.get(Integer.valueOf(partes[i])));
                                paradas.get(Integer.valueOf(partes[i])).setLinea(linea);
                            }
                        }
                    } else if (sentido.equals("R")) {
                        for (int i = 2; i < partes.length; i++) {
                            if (paradas.get(Integer.valueOf(partes[i])) != null) {
                                linea.agregarVuelta(paradas.get(Integer.valueOf(partes[i])));
                                paradas.get(Integer.valueOf(partes[i])).setLinea(linea);
                            }
                        }
                    }
                    lineas.put(idLinea, linea);
                }
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
        } finally {
            if (bf != null)
                bf.close();
        }

        return lineas;
    }

    private void writeToFile(TreeMap<String, Linea> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Linea e : list.values()) {
                outFile.format("%s;%s;", e.getCodigo(), "I");
                for (Parada parada: e.getParadasIda())
                    outFile.format("%s;",parada.getCodigo());
                outFile.format("\n");
                outFile.format("%s;%s;", e.getCodigo(), "R");
                for (Parada parada: e.getParadasVuelta())
                    outFile.format("%s;",parada.getCodigo());
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
    public TreeMap<String, Linea> buscarTodos() throws IOException {
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
