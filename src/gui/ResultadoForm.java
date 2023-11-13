package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import controlador.Coordinador;
import datastructures.TreeMap;
import modelo.Linea;
import modelo.Tramo;
import org.apache.log4j.Logger;
import util.Time;

public class ResultadoForm extends JDialog {
	final static Logger logger = Logger.getLogger(ResultadoForm.class);
	private Coordinador coordinador;
	private JPanel contentPane;
	private JButton btnCancelar, btnMasRapido, btnMenosCostoso;
	private JTextArea txtResultado;
	private List<List<Tramo>> trayecto;

	public ResultadoForm() {
		setBounds(100, 100, 662, 300);
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

		btnMasRapido = new JButton("Mas rapido");
		btnMasRapido.setBounds(84, 205, 155, 32);
		btnMasRapido.setFocusable(false);
		contentPane.add(btnMasRapido);
		btnMasRapido.addActionListener(handler);

		btnMenosCostoso = new JButton("Menos costoso");
		btnMenosCostoso.setBounds(414, 205, 155, 32);
		btnMenosCostoso.setFocusable(false);
		contentPane.add(btnMenosCostoso);
		btnMenosCostoso.addActionListener(handler);

		setModal(true);
	}

	public String verDatos(List<List<Tramo>> tramos, int horario, TreeMap<String, Linea> lineas) {
		StringBuilder resultado = new StringBuilder();
		if (tramos.isEmpty()) {
			resultado.append("No hay recorrido entre las paradas.");
			btnMasRapido.setEnabled(false);
			btnMenosCostoso.setEnabled(false);
			return resultado.toString();
		}

		trayecto = tramos;
		Linea linea;
		Tramo tramo;
		String nombreLinea;
		for (List<Tramo> t : tramos) {
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
			resultado.append("=======================================================================\n");
		}
		btnMasRapido.setEnabled(true);
		btnMenosCostoso.setEnabled(true);
		return resultado.toString();
	}

	public void accion(String resultado) {
		txtResultado.setText(resultado);
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnCancelar) {
				coordinador.cancelarResultado();
				logger.info("Cancelar resultadoForm");
				return;
			}

			if (event.getSource() == btnMasRapido) {
				coordinador.masRapido(trayecto);
			}

			if (event.getSource() == btnMenosCostoso) {
				coordinador.menosCostoso(trayecto);
			}
		}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
