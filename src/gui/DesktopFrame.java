package gui;

import controlador.Coordinador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * DesktopFrame es la ventana principal de la aplicación que proporciona un menú
 * para acceder a diferentes funcionalidades.
 */
public class DesktopFrame extends JFrame {

    private Coordinador coordinador;
    private ResourceBundle resourceBundle;
    private JPanel aplicacion;

    /**
     * Constructor por defecto para DesktopFrame.
     */
    public DesktopFrame() {
    }

    /**
     * Inicializa DesktopFrame, configurando la interfaz gráfica y agregando
     * elementos de menú con sus respectivos manejadores de eventos.
     */
    public void init() {
        resourceBundle = coordinador.getResourceBundle();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 600);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu1 = new JMenu(resourceBundle.getString("DesktopFrame_application"));
        menuBar.add(menu1);

        JMenuItem salir = new JMenuItem(resourceBundle.getString("DesktopFrame_application_exit"));
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(NORMAL);
            }
        });
        menu1.add(salir);

        JMenu datos = new JMenu(resourceBundle.getString("DesktopFrame_application_data"));
        menuBar.add(datos);

        JMenuItem lineas = new JMenuItem(resourceBundle.getString("DesktopFrame_application_lines"));
        lineas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                coordinador.mostrarLineaList();
            }
        });
        datos.add(lineas);

        JMenuItem paradas = new JMenuItem(resourceBundle.getString("DesktopFrame_application_stops"));
        paradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordinador.mostrarParadaList();
            }
        });
        datos.add(paradas);

        JMenuItem tramos = new JMenuItem(resourceBundle.getString("DesktopFrame_application_sections"));
        tramos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordinador.mostrarTramoList();
            }
        });
        datos.add(tramos);

        JMenu consultas = new JMenu(resourceBundle.getString("DesktopFrame_application_queries"));
        setJMenuBar(menuBar);

        JMenuItem consulta = new JMenuItem(resourceBundle.getString("DesktopFrame_application_querie"));
        consulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                coordinador.mostrarConsulta();
            }
        });

        menuBar.add(consultas);
        consultas.add(consulta);

        JMenu ayuda = new JMenu(resourceBundle.getString("DesktopFrame_application_help"));
        setJMenuBar(menuBar);

        JMenuItem idioma = new JMenuItem(resourceBundle.getString("DesktopFrame_application_language"));
        idioma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                coordinador.mostrarIdioma();
            }
        });

        menuBar.add(ayuda);
        ayuda.add(idioma);

        aplicacion = new JPanel();
        aplicacion.setBorder(new EmptyBorder(5, 5, 5, 5));
        aplicacion.setLayout(new BorderLayout(0, 0));
        setContentPane(aplicacion);

        setSize(600, 480);
        setTitle(resourceBundle.getString("DesktopFrame_company"));
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);
    }

    /**
     * Establece la instancia de Coordinador para DesktopFrame.
     *
     * @param coordinador La instancia de Coordinador que se establecerá.
     */
    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
