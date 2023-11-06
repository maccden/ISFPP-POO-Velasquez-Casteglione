package gui.datos;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Parada;
import negocio.LineaReferenciaException;
import negocio.ParadaExistenteException;
import negocio.TramoReferenciaException;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParadaForm extends JDialog {
    final static Logger logger = Logger.getLogger(ParadaForm.class);
    private Coordinador coordinador;
    private JTextField jtfCodigo;
    private JTextField jtfDireccion;
    private JButton btnCancelar, btnInsertar, btnModificar, btnEliminar;
    private JLabel errorCodigo, errorDireccion, tituloModificar, tituloInsertar, tituloEliminar;

    public ParadaForm() {
        setBounds(100, 100, 550, 190);

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
        jtfDireccion.setBounds(68, 78, 220, 20);
        getContentPane().add(jtfDireccion);
        jtfDireccion.setColumns(10);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCancelar.setBounds(106, 120, 86, 23);
        btnCancelar.setFocusable(false);
        getContentPane().add(btnCancelar);
        btnCancelar.addActionListener(handler);

        btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnModificar.setBounds(10, 120, 86, 23);
        btnModificar.setFocusable(false);
        getContentPane().add(btnModificar);
        btnModificar.addActionListener(handler);

        tituloModificar = new JLabel("Seleccione y modifique los datos de la parada.");
        tituloModificar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloModificar.setBounds(141, 15, 251, 14);
        getContentPane().add(tituloModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnEliminar.setBounds(10, 121, 86, 23);
        btnEliminar.setFocusable(false);
        getContentPane().add(btnEliminar);
        btnEliminar.addActionListener(handler);

        btnInsertar = new JButton("Insertar");
        btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnInsertar.setBounds(10, 121, 86, 23);
        btnInsertar.setFocusable(false);
        getContentPane().add(btnInsertar);
        btnInsertar.addActionListener(handler);

        tituloInsertar = new JLabel("Ingrese los datos para la nueva parada.");
        tituloInsertar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloInsertar.setBounds(141, 16, 251, 14);
        getContentPane().add(tituloInsertar);

        tituloEliminar = new JLabel("Datos de la parada.");
        tituloEliminar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloEliminar.setBounds(141, 16, 251, 14);
        getContentPane().add(tituloEliminar);

        errorCodigo = new JLabel("");
        errorCodigo.setForeground(Color.RED);
        errorCodigo.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorCodigo.setBounds(162, 50, 150, 14);
        getContentPane().add(errorCodigo);

        errorDireccion = new JLabel("");
        errorDireccion.setForeground(Color.RED);
        errorDireccion.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorDireccion.setBounds(295, 80, 150, 14);
        getContentPane().add(errorDireccion);

        setModal(true);
    }

    public void accion(int accion, Parada parada) {
        tituloEliminar.setVisible(false);
        tituloModificar.setVisible(false);
        tituloInsertar.setVisible(false);
        btnInsertar.setVisible(false);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        jtfCodigo.setEditable(true);
        jtfDireccion.setEditable(true);
        errorCodigo.setText("");
        errorDireccion.setText("");

        if (accion == Constantes.INSERTAR) {
            setTitle("Insertar parada");
            tituloInsertar.setVisible(true);
            btnInsertar.setVisible(true);
            limpiar();
        }

        if (accion == Constantes.MODIFICAR) {
            setTitle("Modificar parada");
            tituloModificar.setVisible(true);
            btnModificar.setVisible(true);
            mostrar(parada);
        }

        if (accion == Constantes.BORRAR) {
            setTitle("Borrar parada");
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

            if (event.getSource() == btnCancelar) {
                coordinador.cancelarParada();
                logger.info("cancelar paradaForm");
                return;
            }

            String codigo = jtfCodigo.getText().trim();
            String direccion = jtfDireccion.getText().trim();

            if (!registroValido()) {
                logger.error("registro no valido parada form");
                return;
            }

            Parada parada = new Parada(Integer.parseInt(codigo), direccion);

            if (event.getSource() == btnInsertar) {
                try {
                    coordinador.insertarParada(parada);
                    logger.info("insertar paradaForm");

                } catch (ParadaExistenteException e) {
                    JOptionPane.showMessageDialog(null, "¡Esta parada ya existe!");
                    logger.error("error insertar paradaForm");
                    return;
                }
            }

            if (event.getSource() == btnModificar) {
                coordinador.modificarParada(parada);
                logger.info("modificar paradaForm");
            }

            if (event.getSource() == btnEliminar) {
                int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro que borra este registro?", "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (JOptionPane.OK_OPTION == resp)
                    try {
                        coordinador.borrarParada(parada);
                        logger.info("borrar paradaForm");

                    } catch (LineaReferenciaException e) {
                        JOptionPane.showMessageDialog(null, "¡Hay lineas que hacen referencia a esta parada!");
                        logger.error("error lineaReferencia paradaForm");

                        return;
                    } catch (TramoReferenciaException e) {
                        JOptionPane.showMessageDialog(null, "¡Hay tramo que hacen referencia a esta parada!");
                        logger.error("error tramoReferencia paradaForm");
                        return;
                    }
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
        try {
            Integer.parseInt(jtfCodigo.getText().trim());
        } catch (NumberFormatException e) {
            errorCodigo.setText("¡Solo numeros!");
            return false;
        }
        if (Integer.parseInt(jtfCodigo.getText().trim()) <= 0) {
            errorCodigo.setText("¡Solo numeros positivos!");
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
