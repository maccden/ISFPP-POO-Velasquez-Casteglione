package gui.consulta;

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
import modelo.Linea;
import modelo.Tramo;
import org.apache.log4j.Logger;

public class ResultadoForm extends JDialog {
	final static Logger logger = Logger.getLogger(ResultadoForm.class);
	private Coordinador coordinador;
	private JPanel contentPane;
	private JButton btnCancelar;
	private JTextArea txtResultado;

	public ResultadoForm() {
		setBounds(100, 100, 662, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Handler handler = new Handler();
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(249, 205, 155, 32);
		contentPane.add(btnCancelar);
		txtResultado = new JTextArea();
		JScrollPane scroll = new JScrollPane(txtResultado);
		scroll.setBounds(29, 23, 592, 171);
		contentPane.add(scroll);
		btnCancelar.addActionListener(handler);
		setModal(true);
	}

	public String verDatos(List<List<Tramo>> resultado2) {
		String resultado = "";
		int tiempoTotal = 0;
		int tiempoSubte = 0;
		int tiempoCaminando = 0;
		for (List<Tramo> tramos : resultado2)
			for (Tramo t : tramos) {
				resultado += t.getInicio().getCodigo() + "-" + t.getInicio().getDireccion() + " > "
						+ t.getFin().getCodigo() + "-" + t.getFin().getDireccion() + " :"
						+ t.getTiempo() + "\n";
				tiempoTotal += t.getTiempo();
				for (Linea linea : t.getFin().getLineas())
					if (t.getInicio().getLineas().contains(linea))
						tiempoSubte += t.getTiempo();
					else
						tiempoCaminando += t.getTiempo();
			}
		resultado += "Tiempo Total: " + tiempoTotal + "\n";
		resultado += "Tiempo Subte: " + tiempoSubte + "\n";
		resultado += "Tiempo Caminando: " + tiempoCaminando + "\n";
		return resultado;
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
		}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
