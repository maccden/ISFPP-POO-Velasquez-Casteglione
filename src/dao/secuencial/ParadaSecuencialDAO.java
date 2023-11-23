package dao.secuencial;

import dao.ParadaDAO;
import datastructures.TreeMap;
import modelo.Parada;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Implementación de la interfaz ParadaDAO para el acceso secuencial a datos.
 */
public class ParadaSecuencialDAO implements ParadaDAO {
    
    private TreeMap<Integer, Parada> list;
    private String name;
    private boolean actualizar;

    /**
     * Constructor que inicializa el DAO.
     */
    public ParadaSecuencialDAO() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("parada");
        actualizar = true;
    }

    /**
     * Lee las paradas desde un archivo y las retorna en un TreeMap.
     *
     * @param file El nombre del archivo.
     * @return Un TreeMap con las paradas, donde la clave es el código de la parada
     *         y el valor es la parada.
     */
    private TreeMap<Integer, Parada> readFromFile(String file) {
        TreeMap<Integer, Parada> list = new TreeMap<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                Parada p = new Parada();
                p.setCodigo(Integer.parseInt(inFile.next()));
                p.setDireccion(inFile.next());
                list.put(p.getCodigo(), p);
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
     * Escribe las paradas en un archivo.
     *
     * @param list El TreeMap con las paradas.
     * @param file El nombre del archivo.
     */
    private void writeToFile(TreeMap<Integer, Parada> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Parada p : list.values()) {
                outFile.format("%s;%s;\n", p.getCodigo(), p.getDireccion());
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
    public TreeMap<Integer, Parada> buscarTodos() {
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
    public void insertar(Parada parada) {
        list.put(parada.getCodigo(), parada);
        writeToFile(list, name);
        actualizar = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actualizar(Parada parada) {
        list.put(parada.getCodigo(), parada);
        writeToFile(list, name);
        actualizar = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void borrar(Parada parada) {
        list.remove(parada.getCodigo());
        writeToFile(list, name);
        actualizar = true;
    }
}
