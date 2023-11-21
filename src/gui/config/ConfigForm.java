package gui.config;

import controlador.Coordinador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * La clase ConfigForm representa una ventana de configuración en la interfaz
 * gráfica de usuario.
 * Permite al usuario seleccionar el idioma de la aplicación.
 */
public class ConfigForm extends JDialog {
    
    private Coordinador coordinador;
    private ResourceBundle resourceBundle;
    private JComboBox<Object> comboBox;
    private JButton btnConfirmar, btnSalir;

    /**
     * Constructor de la clase ConfigForm.
     */
    public ConfigForm() {
    }

    /**
     * Inicializa la interfaz gráfica de usuario y configura los componentes.
     */
    public void init() {
        resourceBundle = coordinador.getResourceBundle();
        setBounds(100, 100, 250, 225);
        setTitle("Configuración");
        getContentPane().setLayout(null);

        JLabel titulo = new JLabel(resourceBundle.getString("ConfigForm_title"));
        titulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        titulo.setBounds(10, 11, 207, 14);
        getContentPane().add(titulo);

        comboBox = new JComboBox<>();
        comboBox.addItem(resourceBundle.getString("ConfigForm_select"));
        comboBox.addItem(resourceBundle.getString("ConfigForm_ES"));
        comboBox.addItem(resourceBundle.getString("ConfigForm_US"));
        comboBox.setBounds(20, 36, 197, 22);
        getContentPane().add(comboBox);
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboBox.getSelectedIndex() == 0)
                    btnConfirmar.setEnabled(false);
                else
                    btnConfirmar.setEnabled(true);
            }
        });

        btnConfirmar = new JButton(resourceBundle.getString("ConfigForm_confirm"));
        btnConfirmar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnConfirmar.setBounds(20, 150, 89, 23);
        btnConfirmar.setFocusable(false);
        getContentPane().add(btnConfirmar);
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(comboBox.getSelectedItem(), resourceBundle.getString("ConfigForm_ES"))) {
                    // Realizar acciones para el idioma español
                }
                if (Objects.equals(comboBox.getSelectedItem(), resourceBundle.getString("ConfigForm_US"))) {
                    // Realizar acciones para el idioma inglés
                }
            }
        });

        btnSalir = new JButton(resourceBundle.getString("ConfigForm_exit"));
        btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnSalir.setBounds(128, 150, 89, 23);
        btnSalir.setFocusable(false);
        getContentPane().add(btnSalir);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordinador.salirConfiguracion();
            }
        });

        setModal(true);
    }

    /**
     * Establece el coordinador para la ventana de configuración.
     *
     * @param coordinador El coordinador que gestionará las acciones de
     *                    configuración.
     */
    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
