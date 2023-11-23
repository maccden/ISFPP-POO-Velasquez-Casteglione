package gui.datos;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Parada;
import modelo.Tramo;
import negocio.exceptions.TramoExistenteException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;

/**
 * Clase TramoForm que representa la interfaz gráfica para el formulario de
 * tramos.
 */
public class TramoForm extends JDialog {

    private Coordinador coordinador;
    private ResourceBundle resourceBundle;
    private JTextField jtfParadaInicio, jtfParadaFinal, jtfTiempo, jtfTipo;
    private JLabel tituloInsertar, tituloEliminar, tituloModificar, errorTiempo, errorTipo;
    private JButton btnInsertar, btnModificar, btnEliminar, btnCancelar;
    private JComboBox<Object> comboBoxInicio, comboBoxFinal;

    /**
     * Constructor de la clase TramoForm.
     */
    public TramoForm() {
    }

    /**
     * Inicializa la interfaz gráfica y configura los componentes necesarios.
     */
    public void init() {
        resourceBundle = coordinador.getResourceBundle();
        getContentPane().setLayout(null);

        Handler handler = new Handler();

        JLabel textoParadaI = new JLabel(resourceBundle.getString("TramoForm_stop_start"));
        textoParadaI.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoParadaI.setBounds(10, 39, 70, 14);
        getContentPane().add(textoParadaI);

        jtfParadaInicio = new JTextField();
        jtfParadaInicio.setBounds(90, 37, 200, 20);
        getContentPane().add(jtfParadaInicio);
        jtfParadaInicio.setColumns(10);

        JLabel textoParadaF = new JLabel(resourceBundle.getString("TramoForm_stop_end"));
        textoParadaF.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoParadaF.setBounds(10, 69, 65, 14);
        getContentPane().add(textoParadaF);

        jtfParadaFinal = new JTextField();
        jtfParadaFinal.setBounds(90, 67, 200, 20);
        getContentPane().add(jtfParadaFinal);
        jtfParadaFinal.setColumns(10);

        JLabel textoTiempo = new JLabel(resourceBundle.getString("TramoForm_time"));
        textoTiempo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoTiempo.setBounds(10, 99, 124, 14);
        getContentPane().add(textoTiempo);

        JLabel textoTipo = new JLabel(resourceBundle.getString("TramoForm_type"));
        textoTipo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoTipo.setBounds(10, 129, 46, 14);
        getContentPane().add(textoTipo);

        JLabel textoAyuda = new JLabel(resourceBundle.getString("TramoForm_help"));
        textoAyuda.setBounds(10, 188, 200, 14);
        getContentPane().add(textoAyuda);

        btnEliminar = new JButton(resourceBundle.getString("TramoForm_delete"));
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnEliminar.setBounds(10, 159, 89, 23);
        btnEliminar.setFocusable(false);
        getContentPane().add(btnEliminar);
        btnEliminar.addActionListener(handler);

        jtfTiempo = new JTextField();
        jtfTiempo.setBounds(144, 97, 86, 20);
        getContentPane().add(jtfTiempo);
        jtfTiempo.setColumns(10);

        jtfTipo = new JTextField();
        jtfTipo.setBounds(50, 127, 86, 20);
        getContentPane().add(jtfTipo);
        jtfTipo.setColumns(10);

        btnCancelar = new JButton(resourceBundle.getString("TramoForm_cancel"));
        btnCancelar.setBounds(106, 159, 89, 23);
        btnCancelar.setFocusable(false);
        getContentPane().add(btnCancelar);
        btnCancelar.addActionListener(handler);

        tituloModificar = new JLabel(resourceBundle.getString("TramoForm_title_modify"));
        tituloModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloModificar.setBounds(107, 11, 239, 14);
        getContentPane().add(tituloModificar);

        comboBoxInicio = new JComboBox<>();
        comboBoxInicio.setBounds(90, 36, 350, 22);
        getContentPane().add(comboBoxInicio);
        comboBoxInicio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboBoxInicio.getSelectedIndex() == 0) {
                    comboBoxFinal.setEnabled(false);
                    comboBoxFinal.removeAllItems();
                    btnInsertar.setEnabled(false);
                    btnModificar.setEnabled(false);
                } else {
                    comboBoxFinal.setEnabled(true);
                    comboBoxFinal.removeAllItems();
                    comboBoxFinal.addItem(resourceBundle.getString("TramoForm_select"));
                    for (Parada parada : coordinador.listarParadas().values())
                        if (!parada.equals(coordinador.listarParadas().get(comboBoxInicio.getSelectedIndex())))
                            comboBoxFinal.addItem(parada.getCodigo() + " - " + parada.getDireccion());
                }
            }
        });

        comboBoxFinal = new JComboBox<>();
        comboBoxFinal.setBounds(90, 66, 350, 22);
        getContentPane().add(comboBoxFinal);
        comboBoxFinal.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboBoxFinal.getSelectedIndex() == 0) {
                    btnInsertar.setEnabled(false);
                    btnModificar.setEnabled(false);
                } else {
                    btnInsertar.setEnabled(true);
                    btnModificar.setEnabled(true);
                }
            }
        });

        tituloInsertar = new JLabel(resourceBundle.getString("TramoForm_title_insert"));
        tituloInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloInsertar.setBounds(107, 12, 239, 14);
        getContentPane().add(tituloInsertar);

        tituloEliminar = new JLabel(resourceBundle.getString("TramoForm_title_remove"));
        tituloEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloEliminar.setBounds(107, 12, 239, 14);
        getContentPane().add(tituloEliminar);

        btnInsertar = new JButton(resourceBundle.getString("TramoForm_insert"));
        btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnInsertar.setBounds(10, 160, 89, 23);
        btnInsertar.setFocusable(false);
        getContentPane().add(btnInsertar);
        btnInsertar.addActionListener(handler);

        btnModificar = new JButton(resourceBundle.getString("TramoForm_update"));
        btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnModificar.setBounds(10, 160, 89, 23);
        btnModificar.setFocusable(false);
        getContentPane().add(btnModificar);
        btnModificar.addActionListener(handler);

        errorTiempo = new JLabel("");
        errorTiempo.setForeground(Color.RED);
        errorTiempo.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorTiempo.setBounds(240, 100, 274, 14);
        getContentPane().add(errorTiempo);

        errorTipo = new JLabel("");
        errorTipo.setForeground(Color.RED);
        errorTipo.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorTipo.setBounds(144, 130, 274, 14);
        getContentPane().add(errorTipo);

        setModal(true);
    }

    /**
     * Configura la acción y muestra el formulario según la acción especificada.
     *
     * @param accion Acción a realizar (Constantes.INSERTAR, Constantes.MODIFICAR,
     *               Constantes.BORRAR).
     * @param tramo  Tramo asociado a la acción.
     */
    public void accion(int accion, Tramo tramo) {
        setBounds(100, 100, 470, 250);
        tituloEliminar.setVisible(false);
        tituloModificar.setVisible(false);
        tituloInsertar.setVisible(false);
        btnInsertar.setVisible(false);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        jtfParadaInicio.setEditable(true);
        jtfParadaFinal.setEditable(true);
        jtfTiempo.setEditable(true);
        jtfTipo.setEditable(true);
        comboBoxInicio.setVisible(false);
        comboBoxFinal.setVisible(false);
        errorTiempo.setText("");
        errorTipo.setText("");

        if (accion == Constantes.INSERTAR) {
            setTitle(resourceBundle.getString("TramoForm_title_window_insert"));
            tituloInsertar.setVisible(true);
            comboBoxInicio.setVisible(true);
            comboBoxInicio.addItem(resourceBundle.getString("TramoForm_select"));
            for (Parada parada : coordinador.listarParadas().values())
                comboBoxInicio.addItem(parada.getCodigo() + " - " + parada.getDireccion());
            jtfParadaInicio.setVisible(false);
            comboBoxFinal.setVisible(true);
            comboBoxFinal.setEnabled(false);
            comboBoxFinal.removeAllItems();
            jtfParadaFinal.setVisible(false);
            btnInsertar.setVisible(true);
            btnInsertar.setEnabled(false);
            btnModificar.setEnabled(false);
            limpiar();
        }

        if (accion == Constantes.MODIFICAR) {
            setTitle(resourceBundle.getString("TramoForm_title_window_modify"));
            tituloModificar.setVisible(true);
            comboBoxInicio.setVisible(true);
            jtfParadaInicio.setVisible(false);
            comboBoxFinal.setVisible(true);
            jtfParadaFinal.setVisible(false);
            btnModificar.setVisible(true);
            btnInsertar.setEnabled(false);
            btnModificar.setEnabled(false);
            mostrarModificar(tramo);
        }

        if (accion == Constantes.BORRAR) {
            setBounds(100, 100, 320, 250);
            setTitle(resourceBundle.getString("TramoForm_title_window_remove"));
            jtfParadaInicio.setVisible(true);
            jtfParadaFinal.setVisible(true);
            tituloEliminar.setVisible(true);
            btnEliminar.setVisible(true);
            jtfParadaInicio.setEditable(false);
            jtfParadaFinal.setEditable(false);
            jtfTiempo.setEditable(false);
            jtfTipo.setEditable(false);
            mostrarEliminar(tramo);
        }
    }

    /**
     * Muestra los detalles del tramo en el formulario de modificación.
     *
     * @param tramo Tramo cuyos detalles se mostrarán.
     */
    private void mostrarModificar(Tramo tramo) {
        comboBoxInicio.addItem(resourceBundle.getString("TramoForm_select"));
        for (Parada parada : coordinador.listarParadas().values())
            comboBoxInicio.addItem(parada.getCodigo() + " - " + parada.getDireccion());
        comboBoxInicio.setSelectedItem(tramo.getInicio().getCodigo() + " - " + tramo.getInicio().getDireccion());

        comboBoxFinal.addItem(resourceBundle.getString("TramoForm_select"));
        for (Parada parada : coordinador.listarParadas().values())
            if (!parada.equals(coordinador.listarParadas().get(comboBoxInicio.getSelectedIndex())))
                comboBoxFinal.addItem(parada.getCodigo() + " - " + parada.getDireccion());
        comboBoxFinal.setSelectedItem(tramo.getFin().getCodigo() + " - " + tramo.getFin().getDireccion());
        jtfTiempo.setText(String.valueOf(tramo.getTiempo()));
        jtfTipo.setText(String.valueOf(tramo.getTipo()));
    }

    /**
     * Muestra los detalles del tramo en el formulario de eliminación.
     *
     * @param tramo Tramo cuyos detalles se mostrarán.
     */
    private void mostrarEliminar(Tramo tramo) {
        jtfParadaInicio.setText(tramo.getInicio().getCodigo() + " - " + tramo.getInicio().getDireccion());
        jtfParadaFinal.setText(tramo.getFin().getCodigo() + " - " + tramo.getFin().getDireccion());
        jtfTiempo.setText(String.valueOf(tramo.getTiempo()));
        jtfTipo.setText(String.valueOf(tramo.getTipo()));
    }

    /**
     * Limpia los campos de texto del formulario.
     */
    private void limpiar() {
        jtfParadaInicio.setText("");
        jtfParadaFinal.setText("");
        jtfTiempo.setText("");
        jtfTipo.setText("");
    }

    /**
     * Clase interna Handler que implementa ActionListener para manejar eventos de
     * los botones.
     */
    private class Handler implements ActionListener {

        /**
         * Maneja los eventos de los botones.
         *
         * @param event Evento de acción.
         */
        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == btnCancelar) {
                coordinador.cancelarTramo();
                return;
            }

            if (event.getSource() == btnEliminar) {
                int codigoI = Integer.parseInt(jtfParadaInicio.getText().trim().split(" - ")[0]);
                int codigoF = Integer.parseInt(jtfParadaFinal.getText().trim().split(" - ")[0]);
                int tiempo = Integer.parseInt(jtfTiempo.getText().trim());
                int tipo = Integer.parseInt(jtfTipo.getText().trim());

                Tramo tramo = new Tramo(coordinador.listarParadas().get(codigoI),
                        coordinador.listarParadas().get(codigoF), tiempo, tipo);

                int resp = JOptionPane.showConfirmDialog(null, resourceBundle.getString("TramoForm_confirm_1"),
                        resourceBundle.getString("TramoForm_confirm_2"),
                        JOptionPane.YES_NO_OPTION);
                if (JOptionPane.OK_OPTION == resp)
                    coordinador.borrarTramo(tramo);
                return;
            }

            if (!registroValido())
                return;

            int codigoI = Integer.parseInt(((String) comboBoxInicio.getSelectedItem()).toString().split(" - ")[0]);
            int codigoF = Integer.parseInt(((String) comboBoxFinal.getSelectedItem()).toString().split(" - ")[0]);
            int tiempo = Integer.parseInt(jtfTiempo.getText().trim());
            int tipo = Integer.parseInt(jtfTipo.getText().trim());

            Tramo tramo = new Tramo(coordinador.listarParadas().get(codigoI), coordinador.listarParadas().get(codigoF),
                    tiempo, tipo);

            if (event.getSource() == btnInsertar)
                try {
                    coordinador.insertarTramo(tramo);
                } catch (TramoExistenteException e) {
                    JOptionPane.showMessageDialog(null, resourceBundle.getString("TramoForm_error_1"));
                    return;
                }

            if (event.getSource() == btnModificar)
                coordinador.modificarTramo(tramo);
        }
    }

    /**
     * Verifica si el registro en el formulario es válido.
     *
     * @return `true` si el registro es válido, `false` en caso contrario.
     */
    public boolean registroValido() {
        errorTiempo.setText("");
        errorTipo.setText("");

        // validar tiempo
        String tiempo = jtfTiempo.getText().trim();
        if (tiempo.isEmpty()) {
            errorTiempo.setText(resourceBundle.getString("TramoForm_valid_1"));
            return false;
        }
        try {
            Integer.parseInt(jtfTiempo.getText().trim());
        } catch (NumberFormatException e) {
            errorTiempo.setText(resourceBundle.getString("TramoForm_error_2"));
            return false;
        }
        if (Integer.parseInt(jtfTiempo.getText().trim()) <= 0) {
            errorTiempo.setText(resourceBundle.getString("TramoForm_error_3"));
            return false;
        }

        // validar tipo
        String tipo = jtfTipo.getText().trim();
        if (tipo.isEmpty()) {
            errorTipo.setText(resourceBundle.getString("TramoForm_valid_1"));
            return false;
        }
        try {
            Integer.parseInt(jtfTipo.getText().trim());
        } catch (NumberFormatException e) {
            errorTipo.setText(resourceBundle.getString("TramoForm_error_2"));
            return false;
        }
        if (Integer.parseInt(jtfTipo.getText().trim()) != 1 && Integer.parseInt(jtfTipo.getText().trim()) != 2) {
            errorTipo.setText(resourceBundle.getString("TramoForm_error_4"));
            return false;
        }
        return true;
    }

    /**
     * Establece el coordinador para la clase TramoForm.
     *
     * @param coordinador Coordinador a establecer.
     */
    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
