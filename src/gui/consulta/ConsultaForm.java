package gui.consulta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import datastructures.TreeMap;
import controlador.Coordinador;
import modelo.Parada;
import org.apache.log4j.Logger;

public class ConsultaForm extends JDialog {
	final static Logger logger = Logger.getLogger(ConsultaForm.class);
	private Coordinador coordinador;
	private JPanel contentPane;
	private JButton btnRapido;
	private JButton btnCancelar;
	private JLabel lblParada1;
	private JLabel lblParada2;
	private JComboBox<Parada> cbxParada1;
	private JComboBox<Parada> cbxParada2;
	private TreeMap<Integer, Parada> paradas;

	public ConsultaForm() {
		setBounds(100, 100, 662, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Handler handler = new Handler();

		btnRapido = new JButton("Mas Rapido");
		btnRapido.setBounds(65, 150, 156, 32);
		contentPane.add(btnRapido);
		btnRapido.addActionListener(handler);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(231, 206, 155, 32);
		contentPane.add(btnCancelar);

		lblParada1 = new JLabel("Parada Origen:");
		lblParada1.setBounds(34, 35, 118, 14);
		contentPane.add(lblParada1);

		lblParada2 = new JLabel("Parada Destino:");
		lblParada2.setBounds(34, 71, 118, 14);
		contentPane.add(lblParada2);

		cbxParada1 = new JComboBox<Parada>();
		cbxParada1.setBounds(162, 31, 390, 22);
		contentPane.add(cbxParada1);

		cbxParada2 = new JComboBox<Parada>();
		cbxParada2.setBounds(162, 67, 390, 22);
		contentPane.add(cbxParada2);

		btnCancelar.addActionListener(handler);
		setModal(true);
	}

	public void accion() {
		if (paradas != null)
			return;
		paradas = coordinador.listarParadas();
		for (int i = 0; i < paradas.size(); i++) {
			cbxParada1.addItem(paradas.get(i));
			cbxParada2.addItem(paradas.get(i));
		}
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnCancelar) {
				coordinador.cancelarConsulta();
				logger.info("Cancelar consultaForm");
				return;
			}

			if (event.getSource() == btnRapido) {
				coordinador.masRapido((Parada) cbxParada1.getSelectedItem(),
						(Parada) cbxParada2.getSelectedItem(), coordinador.horaLlegadaParada(),
						coordinador.numeroLineas());
				logger.info("Consulta masRapido");

			}
		}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
