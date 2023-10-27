package gui.consulta;

import java.util.List;

import datastructures.TreeMap;
import modelo.Linea;
import modelo.Parada;
import modelo.Tramo;

import javax.swing.*;

import controlador.Constantes;
import controlador.Coordinador;
import util.Time;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class Interfaz extends JFrame {

    private Coordinador coordinador;
    private static JFrame aplicacion;
    private static JButton siguiente, cancelar;
    private static ArrayList<String> dir;
    private static ArrayList<Parada> p;
    private static JComboBox comboBox;
    private static Parada respuesta;

    // Usuario ingresa opcion
    public static int opcion() {
        return Constantes.MAS_RAPIDO;
    }

    // Usuario ingresa estacion origen
    public static Parada ingresarEstacionOrigen(TreeMap<Integer, Parada> paradas) {
        aplicacion = new JFrame("Seleccione la parada inicial");

        JPanel panelNorte = new JPanel();
        JLabel lblNewLabel = new JLabel("Elija la parada inicial de su trayecto");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelNorte.add(lblNewLabel);
        aplicacion.getContentPane().add(panelNorte, BorderLayout.NORTH);

        JPanel panelSur = new JPanel();
        siguiente = new JButton("Siguiente");
        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        JPanel panelCentro = new JPanel();
        dir = new ArrayList<>();
        dir.add("...");
        p = new ArrayList<>();

        for (Parada parada: paradas.values()) {
            p.add(parada);
            dir.add(parada.getCodigo() + " - " + parada.getDireccion());
        }

        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel(dir.toArray()));
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboBox.getSelectedIndex() == 0) {
                    siguiente.setEnabled(false);
                } else {
                    siguiente.setEnabled(true);
                    respuesta = p.get(comboBox.getSelectedIndex() - 1);
                }
            }
        });
        panelCentro.add(comboBox);
        aplicacion.getContentPane().add(panelCentro, BorderLayout.CENTER);

        aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aplicacion.setSize(400, 250);
        aplicacion.setVisible(true);

        return respuesta;
    }

    // Usuario ingresa estacion destino
    public static Parada ingresarEstacionDestino(TreeMap<Integer, Parada> paradas, Parada inicio) {
        aplicacion = new JFrame();

        JPanel panelNorte = new JPanel();
        JLabel lblNewLabel = new JLabel("Elija la parada final de su trayecto");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelNorte.add(lblNewLabel);
        aplicacion.getContentPane().add(panelNorte, BorderLayout.NORTH);

        JPanel panelSur = new JPanel();
        siguiente = new JButton("Siguiente");
        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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


        JPanel panelCentro = new JPanel();
        dir = new ArrayList<>();
        dir.add("...");
        p = new ArrayList<>();

        for (Parada parada: paradas.values()) {
            if (!parada.equals(inicio)) {
                p.add(parada);
                dir.add(parada.getCodigo() + " - " + parada.getDireccion());
            }
        }

        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel(dir.toArray()));
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboBox.getSelectedIndex() == 0) {
                    siguiente.setEnabled(false);
                } else {
                    siguiente.setEnabled(true);
                    respuesta = p.get(comboBox.getSelectedIndex() - 1);
                }
            }
        });
        panelCentro.add(comboBox);
        aplicacion.getContentPane().add(panelCentro, BorderLayout.CENTER);

        aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aplicacion.setSize(400, 250);
        aplicacion.setVisible(true);

        return respuesta;
    }

    public static void resultado(List<List<Tramo>> recorridos, int horario, TreeMap<String, Linea> lineas) {
        Linea linea;
        Tramo tramo;
        String nombreLinea;
        for (List<Tramo> tramos : recorridos) {
            System.out.println(Time.toTime(horario) + " - Llega a la parada");
            for (int i = 0; i < tramos.size() - 1; i++) {
                tramo = tramos.get(i);
                linea = tramo.getInicio().getLineas().get(0);
                nombreLinea = linea.getCodigo();
                if (lineas.get(linea.getCodigo())==null)
                    nombreLinea = "CAMINANDO";
                System.out.println(Time.toTime(tramo.getTiempo()) + " - " + nombreLinea + " ("
                        + tramo.getInicio().getDireccion() + " " + " > " + tramo.getFin().getDireccion() + ")");
            }
            tramo = tramos.get(tramos.size() - 1);
            System.out.println(Time.toTime(tramo.getTiempo()) + " - Fin de recorrido");
            System.out.println("Tiempo Total: " + Time.toTime(tramo.getTiempo() - horario));
            System.out.println("============================================================");
            System.out.println();
        }
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}