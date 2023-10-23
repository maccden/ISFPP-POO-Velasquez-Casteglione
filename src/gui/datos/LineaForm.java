package gui.datos;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Linea;
import negocio.LineaExistenteException;
import negocio.LineaReferenciaException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LineaForm extends JDialog {
    private Coordinador coordinador;

    private JPanel contentPane;
    private JTextField jtfCodigo;
    private JTextField jtfNombre;

    private JLabel lblErrorCodigo;
    private JLabel lblErrorNombre;

    private JButton btnInsertar;
    private JButton btnModificar;
    private JButton btnBorrar;
    private JButton btnCancelar;

    /**
     * Create the frame.
     */
    public LineaForm() {
        setBounds(100, 100, 662, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCodigo = new JLabel("C�digo:");
        lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblCodigo.setBounds(42, 24, 107, 14);
        contentPane.add(lblCodigo);

        jtfCodigo = new JTextField();
        jtfCodigo.setBounds(159, 24, 86, 20);
        contentPane.add(jtfCodigo);
        jtfCodigo.setColumns(10);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNombre.setBounds(42, 55, 107, 14);
        contentPane.add(lblNombre);

        jtfNombre = new JTextField();
        jtfNombre.setBounds(159, 55, 86, 20);
        contentPane.add(jtfNombre);
        jtfNombre.setColumns(10);

        lblErrorCodigo = new JLabel("");
        lblErrorCodigo.setForeground(Color.RED);
        lblErrorCodigo.setBounds(255, 24, 300, 14);
        contentPane.add(lblErrorCodigo);

        lblErrorNombre = new JLabel("");
        lblErrorNombre.setForeground(Color.RED);
        lblErrorNombre.setBounds(255, 55, 300, 14);
        contentPane.add(lblErrorNombre);

        Handler handler = new Handler();

        btnInsertar = new JButton("Insertar");
        btnInsertar.setBounds(85, 202, 114, 32);
        contentPane.add(btnInsertar);
        btnInsertar.addActionListener(handler);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(85, 202, 114, 32);
        contentPane.add(btnModificar);
        btnModificar.addActionListener(handler);

        btnBorrar = new JButton("Borrar");
        btnBorrar.setBounds(85, 202, 114, 32);
        contentPane.add(btnBorrar);
        btnBorrar.addActionListener(handler);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(225, 202, 114, 32);
        contentPane.add(btnCancelar);
        btnCancelar.addActionListener(handler);

        setModal(true);
    }

    public void accion(int accion, Linea linea) {
        btnInsertar.setVisible(false);
        btnModificar.setVisible(false);
        btnBorrar.setVisible(false);
        jtfCodigo.setEditable(true);
        jtfNombre.setEditable(true);

        if (accion == Constantes.INSERTAR) {
            btnInsertar.setVisible(true);
            limpiar();
        }

        if (accion == Constantes.MODIFICAR) {
            btnModificar.setVisible(true);
            jtfCodigo.setEditable(false);
            mostrar(linea);
        }

        if (accion == Constantes.BORRAR) {
            btnBorrar.setVisible(true);
            jtfCodigo.setEditable(false);
            jtfNombre.setEditable(false);
            mostrar(linea);
        }
    }

    private void mostrar(Linea linea) {
        jtfCodigo.setText(linea.getCodigo());
    }

    private void limpiar() {
        jtfCodigo.setText("");
    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            String codigo = jtfCodigo.getText().trim();
            String nombre = jtfNombre.getText().trim();
            Linea linea = new Linea(codigo);

            if (event.getSource() == btnInsertar) {
                if (!registroValido())
                    return;
                try {
                    coordinador.insertarLinea(linea);
                } catch (LineaExistenteException e) {
                    JOptionPane.showMessageDialog(null, "¡Esta linea ya existe!");
                    return;
                }
            }

            if (event.getSource() == btnModificar) {
                if (!registroValido())
                    return;
                coordinador.modificarLinea(linea);
            }

            if (event.getSource() == btnBorrar) {
                int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que quiere borrar este registro?", "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (JOptionPane.OK_OPTION == resp)
                    try {
                        coordinador.borrarLinea(linea);
                    } catch (LineaReferenciaException e) {
                        JOptionPane.showMessageDialog(null, "¡Hay estaciones que hacen referencia a esta linea!");
                        return;
                    }
                return;
            }

            if (event.getSource() == btnCancelar) {
                coordinador.cancelarLinea();
                return;
            }
        }
    }

    public boolean registroValido() {
        lblErrorCodigo.setText("");
        lblErrorNombre.setText("");

        // validar codigo
        String codigo = jtfCodigo.getText().trim();
        if (codigo.isEmpty()) {
            lblErrorCodigo.setText("Campo obligatorio");
            return false;
        }
        if (codigo.matches("[A-Z][a-zA-Z]*") != true) {
            lblErrorCodigo.setText("Solo letras. Primera con mayuscula");
            return false;
        }

        // validar nombre
        String nombre = jtfNombre.getText().trim();
        if (nombre.isEmpty()) {
            lblErrorNombre.setText("Campo obligatorio");
            return false;
        }

        return true;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
