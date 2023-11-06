package gui.datos;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Linea;
import modelo.Parada;
import negocio.LineaExistenteException;
import util.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class LineaForm extends JDialog {
    private Coordinador coordinador;
    private JLabel tituloModificar, tituloInsertar, tituloEliminar, errorCodigo, errorComienzo, errorFinaliza, errorFrecuencia;
    private JTextField jtfCodigo, jtfComienzo, jtfFinaliza, jtfFrecuencia;
    private JButton btnInsertar, btnModificar, btnCancelar, btnEliminar, btnAgregar, btnQuitar;
    private JComboBox comboBoxParadas, comboBoxParadasN;

    public LineaForm() {
        setBounds(100, 100, 750, 375);

        getContentPane().setLayout(null);

        Handler handler = new Handler();

        tituloModificar = new JLabel("Seleccione y modifique los datos de la linea.");
        tituloModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloModificar.setBounds(245, 11, 239, 14);
        getContentPane().add(tituloModificar);

        tituloInsertar = new JLabel("Ingrese los nuevos datos de la nueva linea.");
        tituloInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloInsertar.setBounds(245, 12, 239, 14);
        getContentPane().add(tituloInsertar);

        tituloEliminar = new JLabel("Datos del linea");
        tituloEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloEliminar.setBounds(120, 12, 239, 14);
        getContentPane().add(tituloEliminar);

        JLabel textoCodigo = new JLabel("Codigo:");
        textoCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoCodigo.setBounds(10, 40, 46, 14);
        getContentPane().add(textoCodigo);

        jtfCodigo = new JTextField();
        jtfCodigo.setBounds(57, 37, 86, 20);
        getContentPane().add(jtfCodigo);

        JLabel textoHoraInicio = new JLabel("Comienzo de la linea:");
        textoHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoHoraInicio.setBounds(10, 65, 126, 14);
        getContentPane().add(textoHoraInicio);

        JLabel textoHoraFinal = new JLabel("Finalizacion de la linea:");
        textoHoraFinal.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoHoraFinal.setBounds(10, 90, 120, 14);
        getContentPane().add(textoHoraFinal);

        jtfComienzo = new JTextField();
        jtfComienzo.setBounds(135, 63, 86, 20);
        getContentPane().add(jtfComienzo);

        jtfFinaliza = new JTextField();
        jtfFinaliza.setBounds(135, 88, 86, 20);
        getContentPane().add(jtfFinaliza);

        JLabel textoFrecuencia = new JLabel("Frecuencia de la linea:");
        textoFrecuencia.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoFrecuencia.setBounds(10, 115, 120, 14);
        getContentPane().add(textoFrecuencia);

        jtfFrecuencia = new JTextField();
        jtfFrecuencia.setBounds(135, 113, 86, 20);
        getContentPane().add(jtfFrecuencia);

        JLabel textoParadas = new JLabel("Paradas:");
        textoParadas.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoParadas.setBounds(10, 140, 46, 14);
        getContentPane().add(textoParadas);

        btnInsertar = new JButton("Insertar");
        btnInsertar.setBounds(10, 302, 89, 23);
        btnInsertar.setFocusable(false);
        getContentPane().add(btnInsertar);
        btnInsertar.addActionListener(handler);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(105, 302, 89, 23);
        btnCancelar.setFocusable(false);
        getContentPane().add(btnCancelar);
        btnCancelar.addActionListener(handler);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(10, 302, 89, 23);
        btnModificar.setFocusable(false);
        getContentPane().add(btnModificar);
        btnModificar.addActionListener(handler);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(10, 302, 89, 23);
        btnEliminar.setFocusable(false);
        getContentPane().add(btnEliminar);
        btnEliminar.addActionListener(handler);

        comboBoxParadas = new JComboBox();
        comboBoxParadas.setMaximumRowCount(6);
        comboBoxParadas.setBounds(10, 161, 300, 20);
        getContentPane().add(comboBoxParadas);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(320, 198, 89, 23);
        btnAgregar.setFocusable(false);
        getContentPane().add(btnAgregar);
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxParadas.getSelectedIndex() != 0) {
                    comboBoxParadasN.addItem(comboBoxParadas.getSelectedItem());
                    comboBoxParadas.removeItem(comboBoxParadas.getSelectedItem());

                    if (comboBoxParadasN.getItemCount() >= 2) {
                        btnInsertar.setEnabled(true);
                        btnModificar.setEnabled(true);
                    }
                }
            }
        });

        btnQuitar = new JButton("Quitar");
        btnQuitar.setBounds(320, 232, 89, 23);
        btnQuitar.setFocusable(false);
        getContentPane().add(btnQuitar);
        btnQuitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxParadasN.getItemCount() > 0) {
                    comboBoxParadas.addItem(comboBoxParadasN.getSelectedItem());
                    comboBoxParadasN.removeItem(comboBoxParadasN.getSelectedItem());

                    if (comboBoxParadasN.getItemCount() < 2) {
                        btnInsertar.setEnabled(false);
                        btnModificar.setEnabled(false);
                    }
                }
            }
        });

        comboBoxParadasN = new JComboBox();
        comboBoxParadasN.setMaximumRowCount(6);
        comboBoxParadasN.setBounds(419, 161, 304, 20);
        getContentPane().add(comboBoxParadasN);

        errorCodigo = new JLabel("");
        errorCodigo.setForeground(Color.RED);
        errorCodigo.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorCodigo.setBounds(148, 40, 700, 14);
        getContentPane().add(errorCodigo);

        errorComienzo = new JLabel("");
        errorComienzo.setForeground(Color.RED);
        errorComienzo.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorComienzo.setBounds(231, 65, 200, 14);
        getContentPane().add(errorComienzo);

        errorFinaliza = new JLabel("");
        errorFinaliza.setForeground(Color.RED);
        errorFinaliza.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorFinaliza.setBounds(231, 91, 700, 14);
        getContentPane().add(errorFinaliza);

        errorFrecuencia = new JLabel("");
        errorFrecuencia.setForeground(Color.RED);
        errorFrecuencia.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorFrecuencia.setBounds(231, 116, 200, 14);
        getContentPane().add(errorFrecuencia);

        setModal(true);
    }

    public void accion(int accion, Linea linea) {
        setBounds(100, 100, 750, 375);
        tituloEliminar.setVisible(false);
        tituloModificar.setVisible(false);
        tituloInsertar.setVisible(false);
        btnInsertar.setVisible(false);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        btnAgregar.setVisible(false);
        btnQuitar.setVisible(false);
        jtfCodigo.setEditable(true);
        jtfComienzo.setEditable(true);
        jtfFinaliza.setEditable(true);
        jtfFrecuencia.setEditable(true);
        comboBoxParadasN.setVisible(false);
        errorCodigo.setText("");
        errorComienzo.setText("");
        errorFinaliza.setText("");
        errorFrecuencia.setText("");

        if (accion == Constantes.INSERTAR) {
            setTitle("Insertar linea");
            tituloInsertar.setVisible(true);
            comboBoxParadas.removeAllItems();
            comboBoxParadas.addItem("Seleccionar...");
            for (Parada parada: coordinador.listarParadas().values())
                comboBoxParadas.addItem(parada.getCodigo() + " - " + parada.getDireccion());
            btnAgregar.setVisible(true);
            btnQuitar.setVisible(true);
            comboBoxParadasN.setVisible(true);
            comboBoxParadasN.removeAllItems();
            btnInsertar.setVisible(true);
            btnInsertar.setEnabled(false);
            limpiar();
        }

        if (accion == Constantes.MODIFICAR) {
            setTitle("Modificar linea");
            tituloModificar.setVisible(true);
            comboBoxParadasN.setVisible(true);
            btnAgregar.setVisible(true);
            btnQuitar.setVisible(true);
            btnModificar.setVisible(true);
            mostrarModificar(linea);
        }

        if (accion == Constantes.BORRAR) {
            setBounds(100, 100, 335, 375);
            setTitle("Borrar linea");
            tituloEliminar.setVisible(true);
            btnEliminar.setVisible(true);
            jtfCodigo.setEditable(false);
            jtfComienzo.setEditable(false);
            jtfFinaliza.setEditable(false);
            jtfFrecuencia.setEditable(false);
            mostrarEliminar(linea);
        }
    }

    private void mostrarModificar(Linea linea) {
        jtfCodigo.setText(linea.getCodigo());
        jtfComienzo.setText(Time.toTime(linea.getComienza()));
        jtfFinaliza.setText(Time.toTime(linea.getFinaliza()));
        jtfFrecuencia.setText(String.valueOf(linea.getFrecuencia()));
        comboBoxParadas.removeAllItems();
        comboBoxParadas.addItem("Seleccionar...");
        for (Parada parada: coordinador.listarParadas().values()) {
            if (!linea.contains(parada))
                comboBoxParadas.addItem(parada.getCodigo() + " - " + parada.getDireccion());
        }
        comboBoxParadasN.removeAllItems();
        for (Parada parada: linea.getParadas())
            comboBoxParadasN.addItem(parada.getCodigo() + " - " + parada.getDireccion());
    }

    private void mostrarEliminar(Linea linea) {
        jtfCodigo.setText(linea.getCodigo());
        jtfComienzo.setText(Time.toTime(linea.getComienza()));
        jtfFinaliza.setText(Time.toTime(linea.getFinaliza()));
        jtfFrecuencia.setText(String.valueOf(linea.getFrecuencia()));
        comboBoxParadas.removeAllItems();
        comboBoxParadas.setEditable(false);
        for (Parada parada: linea.getParadas())
            comboBoxParadas.addItem(parada.getCodigo() + " - " + parada.getDireccion());
        comboBoxParadas.setEditable(false);
    }

    private void limpiar() {
        jtfCodigo.setText("");
        jtfComienzo.setText("");
        jtfFinaliza.setText("");
        jtfFrecuencia.setText("");
    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == btnCancelar) {
                coordinador.cancelarLinea();
                return;
            }

            if (event.getSource() == btnEliminar) {
                String codigo = jtfCodigo.getText().trim();
                Linea linea = new Linea(codigo, 0, 0, 0);

                int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que borra este registro?", "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (JOptionPane.OK_OPTION == resp)
                    coordinador.borrarLinea(linea);
                return;
            }

            if (!registroValido())
                return;

            String codigo = jtfCodigo.getText().trim();
            int comienzo = Time.toMins(jtfComienzo.getText().trim());
            int finaliza = Time.toMins(jtfFinaliza.getText().trim());
            int frecuencia = Integer.parseInt(jtfFrecuencia.getText().trim());

            Linea linea = new Linea(codigo, comienzo, finaliza, frecuencia);

            if (event.getSource() == btnInsertar) {
                for (int i = 0; i < comboBoxParadasN.getItemCount(); i++) {
                    Parada parada = new Parada(Integer.parseInt(((String) comboBoxParadasN.getItemAt(i)).split(" - ")[0]), null);
                    linea.agregarParada(coordinador.buscarParada(parada));
                }
                try {
                    coordinador.insertarLinea(linea);
                } catch (LineaExistenteException e) {
                    JOptionPane.showMessageDialog(null, "¡Esta linea ya existe!");
                    return;
                }
            }

            if (event.getSource() == btnModificar) {
                List<Parada> paradas = new ArrayList<>();
                for (int i = 0; i < comboBoxParadasN.getItemCount(); i++) {
                    Parada parada = new Parada(Integer.parseInt(((String) comboBoxParadasN.getItemAt(i)).split(" - ")[0]), null);
                    paradas.add(coordinador.buscarParada(parada));
                    parada.setLinea(linea);
                }
                for (Parada parada: linea.getParadas()) {
                    if (!paradas.contains(parada))
                        parada.removeLinea(linea);
                }
                linea.setParadas(paradas);
                coordinador.modificarLinea(linea);
            }

        }
    }

    public boolean registroValido() {
        errorCodigo.setText("");
        errorComienzo.setText("");
        errorFinaliza.setText("");
        errorFrecuencia.setText("");

        // validar codigo
        String codigo = jtfCodigo.getText().trim();
        if (codigo.isEmpty()) {
            errorCodigo.setText("Campo obligatorio");
            return false;
        }
        if (codigo.matches("[A-Z][1-9][IR]") != true) {
            errorCodigo.setText("Codigo invalido: Primera letra mayuscula, segundo caracter numerico, y tercer letra I o R.");
            return false;
        }

        // validar hora que comienza
        if (jtfComienzo.getText().isEmpty()) {
            errorComienzo.setText("Campo obligatorio");
            return false;
        }
        try {
            String[] comienzo = jtfComienzo.getText().trim().split(":");
            if (comienzo.length < 2) {
                errorComienzo.setText("¡Escriba bien la hora! (XX:XX)");
                return false;
            }
            Integer.parseInt(comienzo[0]);
            Integer.parseInt(comienzo[1]);
            if (Integer.parseInt(comienzo[0]) > 24 || Integer.parseInt(comienzo[1]) > 60 || Integer.parseInt(comienzo[0]) < 0 || Integer.parseInt(comienzo[0]) < 0) {
                errorComienzo.setText("¡Ingrese una hora valida!");
                return false;
            }
            if (Integer.parseInt(comienzo[0]) == 24 && Integer.parseInt(comienzo[1]) > 0) {
                errorComienzo.setText("¡Ingrese una hora valida!");
                return false;
            }
        } catch (PatternSyntaxException e) {
            errorComienzo.setText("¡Escriba bien la hora! (XX:XX)");
            return false;
        } catch (NumberFormatException e) {
            errorComienzo.setText("¡Ingrese una hora valida!");
            return false;
        }

        // validar hora que finaliza
        if (jtfFinaliza.getText().isEmpty()) {
            errorFinaliza.setText("Campo obligatorio");
            return false;
        }
        try {
            String[] finaliza = jtfFinaliza.getText().trim().split(":");
            if (finaliza.length < 2) {
                errorFinaliza.setText("¡Escriba bien la hora! (XX:XX)");
                return false;
            }
            Integer.parseInt(finaliza[0]);
            Integer.parseInt(finaliza[1]);
            if (Integer.parseInt(finaliza[0]) > 24 || Integer.parseInt(finaliza[1]) > 60 || Integer.parseInt(finaliza[0]) < 0 || Integer.parseInt(finaliza[0]) < 0) {
                errorFinaliza.setText("¡Ingrese una hora valida!");
                return false;
            }
            if (Integer.parseInt(finaliza[0]) == 24 && Integer.parseInt(finaliza[1]) > 0) {
                errorFinaliza.setText("¡Ingrese una hora valida!");
                return false;
            }
        } catch (PatternSyntaxException e) {
            errorFinaliza.setText("¡Escriba bien la hora! (XX:XX)");
            return false;
        } catch (NumberFormatException e) {
            errorFinaliza.setText("¡Ingrese una hora valida!");
            return false;
        }

        // validar entre 2 horas

        int comienzo = Time.toMins(jtfComienzo.getText().trim());
        int finaliza = Time.toMins(jtfFinaliza.getText().trim());

        if (comienzo >= finaliza) {
            errorFinaliza.setText("¡No ingrese la hora de comienzo mayor a la de la finalizacion!");
            return false;
        }

        // validar la frecuencia
        String frecuencia = jtfFrecuencia.getText().trim();
        if (frecuencia.isEmpty()) {
            errorFrecuencia.setText("Campo obligatorio");
            return false;
        }
        try {
            Integer.parseInt(jtfFrecuencia.getText().trim());
        } catch (NumberFormatException e) {
            errorFrecuencia.setText("¡Solo numeros!");
            return false;
        }
        if (Integer.parseInt(jtfFrecuencia.getText().trim()) <= 0) {
            errorFrecuencia.setText("¡Solo numeros positivos!");
            return false;
        }

        return true;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
