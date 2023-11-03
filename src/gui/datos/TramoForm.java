package gui.datos;

import javax.swing.*;
import java.awt.*;

public class TramoForm extends JDialog {
    private JTextField paradaInicio;
    private JTextField paradaFinal;
    private JTextField tiempo;
    private JTextField tipo;

    public TramoForm() {
        getContentPane().setLayout(null);

        JLabel textoParadaI = new JLabel("Parada inicio:");
        textoParadaI.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoParadaI.setBounds(10, 39, 70, 14);
        getContentPane().add(textoParadaI);

        paradaInicio = new JTextField();
        paradaInicio.setBounds(90, 37, 86, 20);
        getContentPane().add(paradaInicio);
        paradaInicio.setColumns(10);

        JLabel textoParadaF = new JLabel("Parada final:");
        textoParadaF.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoParadaF.setBounds(10, 69, 65, 14);
        getContentPane().add(textoParadaF);

        paradaFinal = new JTextField();
        paradaFinal.setBounds(90, 67, 86, 20);
        getContentPane().add(paradaFinal);
        paradaFinal.setColumns(10);

        JLabel textoTiempo = new JLabel("Tiempo entre paradas:");
        textoTiempo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoTiempo.setBounds(10, 99, 124, 14);
        getContentPane().add(textoTiempo);

        JLabel textoTipo = new JLabel("Tipo*:");
        textoTipo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textoTipo.setBounds(10, 129, 46, 14);
        getContentPane().add(textoTipo);

        JLabel textoAyuda = new JLabel("*1 = colectivo. 2 = caminando.");
        textoAyuda.setBounds(10, 188, 150, 14);
        getContentPane().add(textoAyuda);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnEliminar.setBounds(10, 159, 89, 23);
        getContentPane().add(btnEliminar);

        tiempo = new JTextField();
        tiempo.setBounds(144, 97, 86, 20);
        getContentPane().add(tiempo);
        tiempo.setColumns(10);

        tipo = new JTextField();
        tipo.setBounds(50, 127, 86, 20);
        getContentPane().add(tipo);
        tipo.setColumns(10);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(106, 159, 89, 23);
        getContentPane().add(btnCancelar);

        JLabel tituloModificar = new JLabel("Seleccione y modifique los datos del tramo.");
        tituloModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloModificar.setBounds(247, 11, 239, 14);
        getContentPane().add(tituloModificar);

        JComboBox comboBoxInicio = new JComboBox();
        comboBoxInicio.setBounds(90, 36, 350, 22);
        getContentPane().add(comboBoxInicio);

        JComboBox comboBoxFinal = new JComboBox();
        comboBoxFinal.setBounds(90, 66, 350, 22);
        getContentPane().add(comboBoxFinal);

        JLabel tituloInsertar = new JLabel("Ingrese los nuevos datos del nuevo tramo.");
        tituloInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloInsertar.setBounds(247, 12, 239, 14);
        getContentPane().add(tituloInsertar);

        JLabel tituloEliminar = new JLabel("Datos del tramo");
        tituloEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tituloEliminar.setBounds(247, 12, 239, 14);
        getContentPane().add(tituloEliminar);

        JButton btnInsertar = new JButton("Insertar");
        btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnInsertar.setBounds(10, 160, 89, 23);
        getContentPane().add(btnInsertar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnModificar.setBounds(10, 160, 89, 23);
        getContentPane().add(btnModificar);

        JLabel errorParadaI = new JLabel("Campo obligatorio.");
        errorParadaI.setForeground(Color.RED);
        errorParadaI.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorParadaI.setBounds(450, 40, 274, 14);
        getContentPane().add(errorParadaI);

        JLabel errorParadaF = new JLabel("Campo obligatorio.");
        errorParadaF.setForeground(Color.RED);
        errorParadaF.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorParadaF.setBounds(450, 70, 274, 14);
        getContentPane().add(errorParadaF);

        JLabel errorTiempo = new JLabel("Ingrese un tiempo valido.");
        errorTiempo.setForeground(Color.RED);
        errorTiempo.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorTiempo.setBounds(240, 100, 274, 14);
        getContentPane().add(errorTiempo);

        JLabel errorTipo = new JLabel("¡Tipo de recorrido invalido!");
        errorTipo.setForeground(Color.RED);
        errorTipo.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorTipo.setBounds(144, 130, 274, 14);
        getContentPane().add(errorTipo);
    }
}
