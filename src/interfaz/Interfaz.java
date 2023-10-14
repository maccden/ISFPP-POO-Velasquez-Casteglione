package interfaz;

import app.Constante;

import datastructures.TreeMap;
import model.Linea;
import model.Parada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class Interfaz extends JFrame {

    private static JFrame aplicacion;
    private static JButton siguiente, cancelar;
    private static ArrayList<String> nombresLineas, nombresParadas1, nombresParadas2;
    private static JComboBox comboBox1, comboBox2;
    private static String respuesta1, respuesta2;
    private static Parada respuestaParada;

    // Usuario ingresa opcion
    public static int opcion() {
        return Constante.MAS_RAPIDO;
    }

    // Usuario ingresa estacion origen
    public static Parada ingresarEstacionOrigen(TreeMap<String, Linea> lineas, TreeMap<String, Parada> paradas) {
        aplicacion = new JFrame();

        JPanel panelNorte = new JPanel();
        JLabel lblNewLabel = new JLabel("Elija la linea la cual va a utilizar y la parada de inicio");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelNorte.add(lblNewLabel);
        aplicacion.getContentPane().add(panelNorte, BorderLayout.NORTH);

        JPanel panelSur = new JPanel();
        siguiente = new JButton("Siguiente");
        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respuestaParada = paradas.get(respuesta2);
                aplicacion.dispose();
            }
        });
        panelSur.add(siguiente);
        cancelar = new JButton("Cancelar");
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicacion.dispose();
            }
        });
        panelSur.add(cancelar);
        aplicacion.getContentPane().add(panelSur, BorderLayout.SOUTH);

        siguiente.setFocusable(false);
        siguiente.setEnabled(false);
        cancelar.setFocusable(false);

        nombresLineas = new ArrayList<>();
        nombresLineas.add("...");

        for (String linea: lineas.keySet())
            nombresLineas.add(linea);

        JPanel panelIzquier = new JPanel();
        JPanel panelDerecho = new JPanel();
        panelDerecho.setVisible(false);
        comboBox1 = new JComboBox();
        comboBox2 = new JComboBox();
        comboBox1.setModel(new DefaultComboBoxModel(nombresLineas.toArray()));
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                respuesta1 = nombresLineas.get(comboBox1.getSelectedIndex());
                panelDerecho.setVisible(true);

                nombresParadas1 = new ArrayList<>();
                nombresParadas1.add("...");
                nombresParadas2 = new ArrayList<>();

                for (Parada parada: paradas.values())
                    if (parada.getLinea(respuesta1)) {
                        nombresParadas1.add(parada.getCodigo() + " - " + parada.getDireccion());
                        nombresParadas2.add(parada.getCodigo());
                    }

                comboBox2.setModel(new DefaultComboBoxModel(nombresParadas1.toArray()));
            }
        });
        panelIzquier.add(comboBox1);
        aplicacion.getContentPane().add(panelIzquier, BorderLayout.WEST);

        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                respuesta2 = nombresParadas2.get(comboBox2.getSelectedIndex());
                siguiente.setEnabled(true);
            }
        });
        panelDerecho.add(comboBox2);
        aplicacion.getContentPane().add(panelDerecho, BorderLayout.EAST);

        aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aplicacion.setSize(400, 250);
        aplicacion.setVisible(true);

        return respuestaParada;
    }

    // Usuario ingresa estacion destino
    public static Parada ingresarEstacionDestino(TreeMap<String, Parada> paradas) {

        return paradas.get("c6");
    }

    /*
    public static void resultado(List<Tramo> recorrido) {
        int tiempoTotal = 0;
        int tiempoSubte = 0;
        int tiempoCaminando = 0;
        for (Tramo t : recorrido) {
            System.out.println(t.getInicio().getLinea().getCodigo() + "-" + t.getInicio().getNombre() + " > " + t.getFin().getLinea().getCodigo() + "-" + t.getFin().getNombre() + " :" + t.getTiempo());
            tiempoTotal += t.getTiempo();
            if (t.getInicio().getLinea().equals(t.getFin().getLinea()))
                tiempoSubte += t.getTiempo();
            else
                tiempoCaminando += t.getTiempo();
        }
        System.out.println("Tiempo Total: " + tiempoTotal);
        System.out.println("Tiempo Subte: " + tiempoSubte);
        System.out.println("Tiempo Caminando: " + tiempoCaminando);
    }
     */
}