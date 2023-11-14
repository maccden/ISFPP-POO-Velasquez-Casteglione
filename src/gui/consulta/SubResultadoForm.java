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

public class SubResultadoForm extends JDialog {
    final static Logger logger = Logger.getLogger(ResultadoForm.class);
    private Coordinador coordinador;
    private JPanel contentPane;
    private JButton btnCancelar;
    private JTextArea txtResultado;

    public SubResultadoForm() {
        setBounds(125, 125, 662, 300);
        setTitle("Trayectos");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        Handler handler = new Handler();
        btnCancelar = new JButton("Cancelar");
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
                    setTitle("Mas rapido");
                    resultado.append(Time.toTime(horario)).append(" - Llega a la parada").append("\n");
                    for (int i = 0; i < t.size() - 1; i++) {
                        tramo = t.get(i);
                        linea = tramo.getInicio().getLineas().get(0);
                        nombreLinea = linea.getCodigo();
                        if (lineas.get(linea.getCodigo()) == null)
                            nombreLinea = "CAMINANDO";
                        resultado.append(Time.toTime(tramo.getTiempo())).append(" - ").append(nombreLinea).append(" (").append(tramo.getInicio().getDireccion()).append(" ").append(" > ").append(tramo.getFin().getDireccion()).append(") \n");
                    }
                    tramo = t.get(t.size() - 1);
                    resultado.append(Time.toTime(tramo.getTiempo())).append(" - Fin de recorrido \n");
                    resultado.append("Tiempo Total: ").append(Time.toTime(tramo.getTiempo() - horario)).append("\n");
                    resultado.append("================================================================================\n");
                }
            }
        }
        if (accion == Constantes.MENOS_COSTOSO) {
            setTitle("Menos costoso");
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
                resultado.append(Time.toTime(horario)).append(" - Llega a la parada").append("\n");
                for (int i = 0; i < t.size() - 1; i++) {
                    tramo = t.get(i);
                    linea = tramo.getInicio().getLineas().get(0);
                    nombreLinea = linea.getCodigo();
                    if (lineas.get(linea.getCodigo())==null)
                        nombreLinea = "CAMINANDO";
                    resultado.append(Time.toTime(tramo.getTiempo())).append(" - ").append(nombreLinea).append(" (").append(tramo.getInicio().getDireccion()).append(" ").append(" > ").append(tramo.getFin().getDireccion()).append(") \n");
                }
                tramo = t.get(t.size() - 1);
                resultado.append(Time.toTime(tramo.getTiempo())).append(" - Fin de recorrido \n");
                resultado.append("Tiempo Total: ").append(Time.toTime(tramo.getTiempo() - horario)).append("\n");
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
