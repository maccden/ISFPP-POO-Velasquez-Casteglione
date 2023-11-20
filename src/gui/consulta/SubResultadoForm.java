package gui.consulta;

import controlador.Constantes;
import controlador.Coordinador;
import datastructures.TreeMap;
import modelo.Linea;
import modelo.Tramo;
import org.apache.log4j.Logger;
import util.Time;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubResultadoForm extends JDialog {
    final static Logger logger = Logger.getLogger(ResultadoForm.class);
    private Coordinador coordinador;
    private ResourceBundle resourceBundle;
    private JPanel contentPane;
    private JButton btnCancelar;
    private JTextArea txtResultado;

    public SubResultadoForm() {

    }

    public void init() {
        resourceBundle = coordinador.getResourceBundle();
        setBounds(125, 125, 662, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        Handler handler = new Handler();
        btnCancelar = new JButton(resourceBundle.getString("SubResultadoForm_cancel"));
        btnCancelar.setBounds(249, 205, 155, 32);
        btnCancelar.setFocusable(false);
        contentPane.add(btnCancelar);
        txtResultado = new JTextArea();
        txtResultado.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setBounds(29, 23, 592, 171);
        contentPane.add(scroll);
        btnCancelar.addActionListener(handler);
        setModal(true);
    }

    public String verDatos(int accion, List<List<Tramo>> tramos, int horario, TreeMap<String, Linea> lineas) {
        StringBuilder resultado = new StringBuilder();
        Linea linea;
        Tramo tramo;
        String nombreLinea = "";
        int menorTiempo = 10000;
        int contador = 10000;
        List<List<Tramo>> menosCostosos = new ArrayList<>();

        if (accion == Constantes.MAS_RAPIDO) {
            for (List<Tramo> t: tramos) {
                tramo = t.get(t.size() - 1);
                if (tramo.getTiempo() - horario < menorTiempo)
                    menorTiempo = tramo.getTiempo() - horario;
            }

            for (List<Tramo> t : tramos) {
                if (t.get(t.size() - 1).getTiempo() - horario == menorTiempo) {
                    setTitle(resourceBundle.getString("SubResultadoForm_title_more_faster"));
                    resultado.append(Time.toTime(horario)).append(" - ").append(resourceBundle.getString("SubResultadoForm_arrives")).append("\n");
                    for (int i = 0; i < t.size() - 1; i++) {
                        tramo = t.get(i);
                        linea = tramo.getInicio().getLineas().get(0);
                        nombreLinea = linea.getCodigo();
                        if (lineas.get(linea.getCodigo()) == null)
                            nombreLinea = resourceBundle.getString("SubResultadoForm_walking");
                        resultado.append(Time.toTime(tramo.getTiempo())).append(" - ").append(nombreLinea).append(" (").append(tramo.getInicio().getDireccion()).append(" ").append(" > ").append(tramo.getFin().getDireccion()).append(") \n");
                    }
                    tramo = t.get(t.size() - 1);
                    resultado.append(Time.toTime(tramo.getTiempo())).append(" - ").append(resourceBundle.getString("SubResultadoForm_end")).append("\n");
                    resultado.append(resourceBundle.getString("SubResultadoForm_total_time")).append(Time.toTime(tramo.getTiempo() - horario)).append("\n");
                    resultado.append("================================================================================\n");
                }
            }
        }
        if (accion == Constantes.MENOS_COSTOSO) {
            setTitle(resourceBundle.getString("SubResultadoForm_title_low_cost"));
            for (List<Tramo> t : tramos) {
                int aux = 0;
                for (int i = 0; i < t.size() - 1; i++) {
                    tramo = t.get(i);
                    linea = tramo.getInicio().getLineas().get(0);
                    if (!nombreLinea.equals(linea.getCodigo()))
                        aux++;
                    nombreLinea = linea.getCodigo();
                }

                if (aux <= contador)
                    contador = aux;
            }

            for (List<Tramo> t : tramos) {
                int aux = 0;
                for (int i = 0; i < t.size() - 1; i++) {
                    tramo = t.get(i);
                    linea = tramo.getInicio().getLineas().get(0);
                    if (!nombreLinea.equals(linea.getCodigo()))
                        aux++;
                    nombreLinea = linea.getCodigo();
                }

                if (aux == contador)
                    menosCostosos.add(t);
            }

            for (List<Tramo> t : menosCostosos) {
                resultado.append(Time.toTime(horario)).append(" - ").append(resourceBundle.getString("SubResultadoForm_arrives")).append("\n");
                for (int i = 0; i < t.size() - 1; i++) {
                    tramo = t.get(i);
                    linea = tramo.getInicio().getLineas().get(0);
                    nombreLinea = linea.getCodigo();
                    if (lineas.get(linea.getCodigo()) == null)
                        nombreLinea = resourceBundle.getString("SubResultadoForm_walking");
                    resultado.append(Time.toTime(tramo.getTiempo())).append(" - ").append(nombreLinea).append(" (").append(tramo.getInicio().getDireccion()).append(" ").append(" > ").append(tramo.getFin().getDireccion()).append(") \n");
                }
                tramo = t.get(t.size() - 1);
                resultado.append(Time.toTime(tramo.getTiempo())).append(" - ").append(resourceBundle.getString("SubResultadoForm_end")).append("\n");
                resultado.append(resourceBundle.getString("SubResultadoForm_total_time")).append(Time.toTime(tramo.getTiempo() - horario)).append("\n");
                resultado.append("================================================================================\n");
            }
        }

        return resultado.toString();
    }

    public void accion(String resultado) {
        txtResultado.setText(resultado);
    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == btnCancelar) {
                coordinador.cancelaraSubResultado();
                logger.info("Cancelar subResultadoForm");
                return;
            }
        }
    }

    public void setCoordinador(Coordinador coordinador) { this.coordinador = coordinador; }
}
