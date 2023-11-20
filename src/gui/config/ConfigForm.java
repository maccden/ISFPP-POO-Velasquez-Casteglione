package gui.config;

import controlador.Coordinador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConfigForm extends JDialog {
    private Coordinador coordinador;
    private ResourceBundle resourceBundle;
    private JComboBox comboBox;
    private JButton btnConfirmar, btnSalir;
    public ConfigForm() {

    }

    public void init() {
        resourceBundle = coordinador.getResourceBundle();
        setBounds(100, 100, 250, 225);
        setTitle("Configuracion");
        getContentPane().setLayout(null);

        JLabel titulo = new JLabel(resourceBundle.getString("ConfigForm_title"));
        titulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        titulo.setBounds(10, 11, 207, 14);
        getContentPane().add(titulo);

        comboBox = new JComboBox();
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

                }
                if (Objects.equals(comboBox.getSelectedItem(), resourceBundle.getString("ConfigForm_US"))) {

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

    public void setCoordinador(Coordinador coordinador) { this.coordinador = coordinador; }
}
