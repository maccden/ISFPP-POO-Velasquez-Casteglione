package gui.datos;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Parada;
import negocio.LineaReferenciaException;
import negocio.ParadaExistenteException;
import negocio.TramoReferenciaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParadaForm extends JDialog {
    private Coordinador coordinador;
    private JTextField jtfCodigo;
    private JTextField jtfDireccion;
    private JButton btnCancelar, btnInsertar, btnModificar, btnEliminar;
    private JLabel errorCodigo, errorDireccion, tituloModificar, tituloInsertar, tituloEliminar;
    public ParadaForm() {
        getContentPane().setLayout(null);

        Handler handler = new Handler();

        JLabel textoCodigo = new JLabel("Codigo:");
        textoCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoCodigo.setBounds(10, 50, 46, 14);
        getContentPane().add(textoCodigo);

        jtfCodigo = new JTextField();
        jtfCodigo.setBounds(68, 50, 86, 20);
        getContentPane().add(jtfCodigo);
        jtfCodigo.setColumns(10);

        JLabel textoDireccion = new JLabel("Direccion:");
        textoDireccion.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoDireccion.setBounds(10, 80, 53, 14);
        getContentPane().add(textoDireccion);

        jtfDireccion = new JTextField();
        jtfDireccion.setBounds(68, 78, 86, 20);
        getContentPane().add(jtfDireccion);
        jtfDireccion.setColumns(10);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCancelar.setBounds(106, 120, 86, 23);
        getContentPane().add(btnCancelar);
        btnCancelar.addActionListener(handler);

        btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnModificar.setBounds(10, 120, 86, 23);
        getContentPane().add(btnModificar);
        btnModificar.addActionListener(handler);

        tituloModificar = new JLabel("Seleccione y modifique los datos de la parada.");
        tituloModificar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloModificar.setBounds(37, 15, 251, 14);
        getContentPane().add(tituloModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnEliminar.setBounds(10, 121, 86, 23);
        getContentPane().add(btnEliminar);
        btnEliminar.addActionListener(handler);

        btnInsertar = new JButton("Insertar");
        btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnInsertar.setBounds(10, 121, 86, 23);
        getContentPane().add(btnInsertar);
        btnInsertar.addActionListener(handler);

        tituloInsertar = new JLabel("Ingrese los datos para la nueva parada.");
        tituloInsertar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloInsertar.setBounds(37, 16, 251, 14);
        getContentPane().add(tituloInsertar);

        tituloEliminar = new JLabel("Datos de la parada.");
        tituloEliminar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloEliminar.setBounds(37, 16, 251, 14);
        getContentPane().add(tituloEliminar);

        errorCodigo = new JLabel("Solo numeros, no letras.");
        errorCodigo.setForeground(Color.RED);
        errorCodigo.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorCodigo.setBounds(162, 50, 150, 14);
        getContentPane().add(errorCodigo);

        errorDireccion  = new JLabel("Campo obligatorio");
        errorDireccion.setForeground(Color.RED);
        errorDireccion.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorDireccion.setBounds(162, 80, 150, 14);
        getContentPane().add(errorDireccion);
    }

    public void accion(int accion, Parada parada) {
        btnInsertar.setVisible(false);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        jtfCodigo.setEditable(true);
        jtfDireccion.setEditable(true);

        if (accion == Constantes.INSERTAR) {
            tituloInsertar.setVisible(true);
            btnInsertar.setVisible(true);
            limpiar();
        }

        if (accion == Constantes.MODIFICAR) {
            tituloModificar.setVisible(true);
            btnModificar.setVisible(true);
            jtfCodigo.setEditable(false);
            mostrar(parada);
        }

        if (accion == Constantes.BORRAR) {
            tituloEliminar.setVisible(true);
            btnEliminar.setVisible(true);
            jtfCodigo.setEditable(false);
            jtfDireccion.setEditable(false);
            mostrar(parada);
        }
    }

    private void mostrar(Parada parada) {
        jtfCodigo.setText(String.valueOf(parada.getCodigo()));
        jtfDireccion.setText(parada.getDireccion());
    }

    private void limpiar() {
        jtfCodigo.setText("");
        jtfDireccion.setText("");
    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            int codigo = Integer.parseInt(jtfCodigo.getText().trim());
            String direccion = jtfDireccion.getText().trim();
            Parada parada = new Parada(codigo, direccion);

            if (event.getSource() == btnInsertar) {
                if (!registroValido())
                    return;
                try {
                    coordinador.insertarParada(parada);
                } catch (ParadaExistenteException e) {
                    JOptionPane.showMessageDialog(null, "Esta l�nea ya existe!");
                    return;
                }
            }

            if (event.getSource() == btnModificar) {
                if (!registroValido())
                    return;
                coordinador.modificarParada(parada);
            }

            if (event.getSource() == btnEliminar) {
                int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que borra este registro?", "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (JOptionPane.OK_OPTION == resp)
                    try {
                        coordinador.borrarParada(parada);
                    } catch (LineaReferenciaException e) {
                        JOptionPane.showMessageDialog(null, "¡Hay lineas que hacen referencia a esta parada!");
                        return;
                    } catch (TramoReferenciaException e) {
                        JOptionPane.showMessageDialog(null, "¡Hay tramo que hacen referencia a esta parada!");
                        return;
                    }
                return;
            }

            if (event.getSource() == btnCancelar) {
                coordinador.cancelarParada();
                return;
            }
        }
    }

    public boolean registroValido() {
        errorCodigo.setText("");
        errorDireccion.setText("");

        // validar codigo
        String codigo = jtfCodigo.getText().trim();
        if (codigo.isEmpty()) {
            errorCodigo.setText("Campo obligatorio");
            return false;
        }
        if (codigo.matches("[A-Z][a-zA-Z]*") != true) {
            errorCodigo.setText("Solo letras. Primera con may�scula");
            return false;
        }

        // validar direccion
        String direccion = jtfDireccion.getText().trim();
        if (direccion.isEmpty()) {
            errorDireccion.setText("Campo obligatorio");
            return false;
        }

        return true;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

}
