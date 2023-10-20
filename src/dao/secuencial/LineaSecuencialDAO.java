package dao.secuencial;

import dao.LineaDAO;
import dao.ParadaDAO;
import datastructures.TreeMap;
import modelo.Linea;
import modelo.Parada;
import negocio.Empresa;

import java.io.File;
import java.io.FileNotFoundException;
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

    /* <!>  <!> <!> <!> <!> <!> <!> <!> <!> <!> <!> <!> <!> <!>

    No reescribe el mapa paradas

       <!>  <!> <!> <!> <!> <!> <!> <!> <!> <!> <!> <!> <!> <!>
     */

    private TreeMap<String, Linea> readFromFile(String file) {
        TreeMap<String, Linea> list = new TreeMap<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            String registro;
            Linea linea = null;
            while (inFile.hasNext()) {
                registro = inFile.next();
                String[] campos = registro.split(";");
                for (Linea l: list.values()) {
                    if (l.getCodigo().equals(campos[0]))
                        linea = l;
                }
                if (linea == null) {
                    linea = new Linea(campos[0]);
                    list.put(campos[0], linea);
                }
                if (campos[1].equals("I"))
                    for (int i = 2; i < campos.length; i++) {
                        linea.agregarIda(paradas.get(Integer.valueOf(campos[i])));


                    }
                if (campos[1].equals("R"))
                    for (int i = 2; i < campos.length; i++) {
                        linea.agregarVuelta(paradas.get(Integer.valueOf(campos[i])));


                    }
            }
            inFile.close();
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
            if (inFile != null)
                inFile.close();
        }
        return list;
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
        ParadaDAO ParadaDAO = new ParadaSecuencialDAO();
        TreeMap<Integer, Parada> ds = ParadaDAO.buscarTodos();
        for (Parada d : ds.values())
            parada.put(d.getCodigo(), d);
        return parada;
    }
}
