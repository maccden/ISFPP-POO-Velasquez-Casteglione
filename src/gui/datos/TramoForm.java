package gui.datos;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Parada;
import modelo.Tramo;
import negocio.TramoExistenteException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TramoForm extends JDialog {
    private Coordinador coordinador;
    private JTextField jtfParadaInicio, jtfParadaFinal, jtfTiempo, jtfTipo;
    private JLabel tituloInsertar, tituloEliminar, tituloModificar, errorTiempo, errorTipo;
    private JButton btnInsertar, btnModificar, btnEliminar, btnCancelar;
    private JComboBox<Object> comboBoxInicio, comboBoxFinal;
    public TramoForm() {
        getContentPane().setLayout(null);
        //setBounds(100, 100, 470, 250);

        Handler handler = new Handler();

        JLabel textoParadaI = new JLabel("Parada inicio:");
        textoParadaI.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoParadaI.setBounds(10, 39, 70, 14);
        getContentPane().add(textoParadaI);

        jtfParadaInicio = new JTextField();
        jtfParadaInicio.setBounds(90, 37, 200, 20);
        getContentPane().add(jtfParadaInicio);
        jtfParadaInicio.setColumns(10);

        JLabel textoParadaF = new JLabel("Parada final:");
        textoParadaF.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoParadaF.setBounds(10, 69, 65, 14);
        getContentPane().add(textoParadaF);

        jtfParadaFinal = new JTextField();
        jtfParadaFinal.setBounds(90, 67, 200, 20);
        getContentPane().add(jtfParadaFinal);
        jtfParadaFinal.setColumns(10);

        JLabel textoTiempo = new JLabel("Tiempo entre paradas:");
        textoTiempo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoTiempo.setBounds(10, 99, 124, 14);
        getContentPane().add(textoTiempo);

        JLabel textoTipo = new JLabel("Tipo*:");
        textoTipo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoTipo.setBounds(10, 129, 46, 14);
        getContentPane().add(textoTipo);

        JLabel textoAyuda = new JLabel("*1 = colectivo. 2 = caminando.");
        textoAyuda.setBounds(10, 188, 200, 14);
        getContentPane().add(textoAyuda);

        btnEliminar = new JButton("Eliminar");
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

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(106, 159, 89, 23);
        btnCancelar.setFocusable(false);
        getContentPane().add(btnCancelar);
        btnCancelar.addActionListener(handler);

        tituloModificar = new JLabel("Seleccione y modifique los datos del tramo.");
        tituloModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloModificar.setBounds(107, 11, 239, 14);
        getContentPane().add(tituloModificar);

        comboBoxInicio = new JComboBox<>();
        comboBoxInicio.setBounds(90, 36, 350, 22);
        getContentPane().add(comboBoxInicio);
        comboBoxInicio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboBoxInicio.getSelectedIndex() == 0)
                    comboBoxFinal.setEnabled(false);
                else {
                    comboBoxFinal.setEnabled(true);
                    comboBoxFinal.removeAllItems();
                    comboBoxFinal.addItem("Selecionar...");
                    for (Parada parada: coordinador.listarParadas().values()) {
                        if (!parada.equals(coordinador.listarParadas().get(comboBoxInicio.getSelectedIndex())))
                            comboBoxFinal.addItem(parada.getCodigo() + " - " + parada.getDireccion());
                    }
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

        tituloInsertar = new JLabel("Ingrese los nuevos datos del nuevo tramo.");
        tituloInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloInsertar.setBounds(107, 12, 239, 14);
        getContentPane().add(tituloInsertar);

        tituloEliminar = new JLabel("Datos del tramo");
        tituloEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloEliminar.setBounds(107, 12, 239, 14);
        getContentPane().add(tituloEliminar);

        btnInsertar = new JButton("Insertar");
        btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnInsertar.setBounds(10, 160, 89, 23);
        btnInsertar.setFocusable(false);
        getContentPane().add(btnInsertar);
        btnInsertar.addActionListener(handler);

        btnModificar = new JButton("Modificar");
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
            setTitle("Insertar tramo");
            tituloInsertar.setVisible(true);
            comboBoxInicio.setVisible(true);
            comboBoxInicio.addItem("Seleccionar...");
            for (Parada parada: coordinador.listarParadas().values()) {
                comboBoxInicio.addItem(parada.getCodigo() + " - " + parada.getDireccion());
            }
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
            setTitle("Modificar tramo");
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
            setTitle("Borrar tramo");
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

    private void mostrarModificar(Tramo tramo) {
        comboBoxInicio.addItem("Selecionar...");
        for (Parada parada: coordinador.listarParadas().values()) {
            comboBoxInicio.addItem(parada.getCodigo() + " - " + parada.getDireccion());
        }
        comboBoxInicio.setSelectedItem(tramo.getInicio().getCodigo() + " - " + tramo.getInicio().getDireccion());

        comboBoxFinal.addItem("Selecionar...");
        for (Parada parada: coordinador.listarParadas().values()) {
            if (!parada.equals(coordinador.listarParadas().get(comboBoxInicio.getSelectedIndex())))
                comboBoxFinal.addItem(parada.getCodigo() + " - " + parada.getDireccion());
        }
        comboBoxFinal.setSelectedItem(tramo.getFin().getCodigo() + " - " + tramo.getFin().getDireccion());
        jtfTiempo.setText(String.valueOf(tramo.getTiempo()));
        jtfTipo.setText(String.valueOf(tramo.getTipo()));
    }

    private void mostrarEliminar(Tramo tramo) {
        jtfParadaInicio.setText(tramo.getInicio().getCodigo() + " - " + tramo.getInicio().getDireccion());
        jtfParadaFinal.setText(tramo.getFin().getCodigo() + " - " + tramo.getFin().getDireccion());
        jtfTiempo.setText(String.valueOf(tramo.getTiempo()));
        jtfTipo.setText(String.valueOf(tramo.getTipo()));
    }

    private void limpiar() {
        jtfParadaInicio.setText("");
        jtfParadaFinal.setText("");
        jtfTiempo.setText("");
        jtfTipo.setText("");
    }

    private class Handler implements ActionListener {
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

                Tramo tramo = new Tramo(coordinador.listarParadas().get(codigoI), coordinador.listarParadas().get(codigoF), tiempo, tipo);

                int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que borra este registro?", "Confirmar",
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

            Tramo tramo = new Tramo(coordinador.listarParadas().get(codigoI), coordinador.listarParadas().get(codigoF), tiempo, tipo);

            if (event.getSource() == btnInsertar) {
                try {
                    coordinador.insertarTramo(tramo);
                } catch (TramoExistenteException e) {
                    JOptionPane.showMessageDialog(null, "¡Este tramo ya existe!");
                    return;
                }
            }

            if (event.getSource() == btnModificar) {
                coordinador.modificarTramo(tramo);
            }

        }
    }

    public boolean registroValido() {
        errorTiempo.setText("");
        errorTipo.setText("");

        // validar tiempo
        String tiempo = jtfTiempo.getText().trim();
        if (tiempo.isEmpty()) {
            errorTiempo.setText("Campo obligatorio");
            return false;
        }
        try {
            Integer.parseInt(jtfTiempo.getText().trim());
        } catch (NumberFormatException e) {
            errorTiempo.setText("¡Solo numeros!");
            return false;
        }
        if (Integer.parseInt(jtfTiempo.getText().trim()) <= 0) {
            errorTiempo.setText("¡Solo numeros positivos!");
            return false;
        }

        // validar tipo
        String tipo = jtfTipo.getText().trim();
        if (tipo.isEmpty()) {
            errorTipo.setText("Campo obligatorio");
            return false;
        }
        try {
            Integer.parseInt(jtfTipo.getText().trim());
        } catch (NumberFormatException e) {
            errorTipo.setText("¡Solo numeros!");
            return false;
        }
        if (Integer.parseInt(jtfTipo.getText().trim()) != 1 && Integer.parseInt(jtfTipo.getText().trim()) != 2) {
            errorTipo.setText("¡Ingrese numeros solo validos!");
            return false;
        }

        return true;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
