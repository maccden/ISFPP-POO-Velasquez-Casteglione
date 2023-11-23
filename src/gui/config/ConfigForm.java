package gui.config;

import controlador.Coordinador;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                if (Objects.equals(comboBox.getSelectedItem(), resourceBundle.getString("ConfigForm_ES")))
                    cambiarIdioma("es", "ES");
                if (Objects.equals(comboBox.getSelectedItem(), resourceBundle.getString("ConfigForm_US")))
                    cambiarIdioma("en", "US");
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
     * Cambia la configuración de idioma y país en un archivo de propiedades.
     *
     * @param language Nuevo valor para la configuración de idioma.
     * @param country  Nuevo valor para la configuración de país.
     */
    private void cambiarIdioma(String language, String country) {
        try {
            Path path = Paths.get("./config.properties");
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).matches("\\s*language\\s*=\\s*(es|en)"))
                    lines.set(i, "language = " + language);
                else if (lines.get(i).matches("\\s*country\\s*=\\s*(ES|US)"))
                    lines.set(i, "country = " + country);
            }
            Files.write(path, lines);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        coordinador.salirConfiguracion();
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
