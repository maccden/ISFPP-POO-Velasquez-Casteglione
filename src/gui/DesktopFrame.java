package gui;

import controlador.Coordinador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesktopFrame extends JFrame {
    private Coordinador coordinador;
    private JPanel aplicacion;

    public DesktopFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 600);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu1 = new JMenu("Aplicacion");
        menuBar.add(menu1);

        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(NORMAL);
            }
        });
        menu1.add(salir);

        JMenu datos = new JMenu("Datos");
        menuBar.add(datos);

        JMenuItem lineas = new JMenuItem("Lineas");
        lineas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                coordinador.mostrarLineaList();
            }
        });
        datos.add(lineas);

        JMenuItem paradas = new JMenuItem("Paradas");
        paradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordinador.mostrarParadaList();
            }
        });
        datos.add(paradas);

        JMenuItem tramos = new JMenuItem("Tramos");
        tramos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { coordinador.mostrarTramoList(); }
        });
        datos.add(tramos);

        JMenu consultas = new JMenu("Consultas");
        setJMenuBar(menuBar);

        JMenuItem consulta = new JMenuItem("Consultas");
        consulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                coordinador.mostrarConsulta();
            }
        });

        menuBar.add(consultas);
        consultas.add(consulta);

        JMenu ayuda = new JMenu("Ayuda");
        setJMenuBar(menuBar);

        JMenuItem idioma = new JMenuItem("Idioma");
        consulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //coordinador.mostrarConsulta();
            }
        });

        menuBar.add(ayuda);
        ayuda.add(idioma);

        aplicacion = new JPanel();
        aplicacion.setBorder(new EmptyBorder(5, 5, 5, 5));
        aplicacion.setLayout(new BorderLayout(0, 0));
        setContentPane(aplicacion);

        setSize(600, 480);
        setTitle("Empresa: MVC");
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
