package dao.secuencial;

import datastructures.TreeMap;
import dao.ParadaDAO;
import dao.TramoDAO;
import factory.Factory;
import modelo.Parada;
import modelo.Tramo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Implementación de la interfaz TramoDAO para el acceso secuencial a datos.
 */
public class TramoSecuencialDAO implements TramoDAO {

    private List<Tramo> list;
    private String name;
    private Hashtable<Integer, Parada> paradas;
    private boolean actualizar;

    /**
     * Constructor que inicializa el DAO.
     */
    public TramoSecuencialDAO() {
        paradas = cargarParadas();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("tramo");
        actualizar = true;
    }

    /**
     * Lee los tramos desde un archivo y los retorna en una lista.
     *
     * @param file El nombre del archivo.
     * @return Una lista de tramos.
     */
    private List<Tramo> readFromFile(String file) {
        List<Tramo> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                Tramo e = new Tramo();
                e.setInicio(paradas.get(Integer.parseInt(inFile.next())));
                e.setFin(paradas.get(Integer.parseInt(inFile.next())));
                e.setTiempo(inFile.nextInt());
                e.setTipo(inFile.nextInt());
                list.add(e);
            }
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

    /**
     * Escribe los tramos en un archivo.
     *
     * @param list La lista de tramos.
     * @param file El nombre del archivo.
     */
    private void writeToFile(List<Tramo> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Tramo t : list) {
                outFile.format("%s;%s;%s;%s;\n", t.getInicio().getCodigo(), t.getFin().getCodigo(),
                        t.getTiempo(), t.getTipo());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tramo> buscarTodos() {
        if (actualizar) {
            list = readFromFile(name);
            actualizar = false;
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertar(Tramo tramo) {
        list.add(tramo);
        writeToFile(list, name);
        actualizar = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actualizar(Tramo tramo) {
        int pos = list.indexOf(tramo);
        list.set(pos, tramo);
        writeToFile(list, name);
        actualizar = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void borrar(Tramo tramo) {
        list.remove(tramo);
        writeToFile(list, name);
        actualizar = true;
    }

    /**
     * Carga las paradas desde el archivo.
     *
     * @return Un Hashtable con las paradas, donde la clave es el código de la
     *         parada y el valor es la parada.
     */
    private Hashtable<Integer, Parada> cargarParadas() {
        Hashtable<Integer, Parada> paradas = new Hashtable<>();
        ParadaDAO paradaDAO = (ParadaDAO) Factory.getInstancia("PARADA");
        TreeMap<Integer, Parada> ds = paradaDAO.buscarTodos();
        for (Parada d : ds.values())
            paradas.put(d.getCodigo(), d);
        return paradas;
    }
}
