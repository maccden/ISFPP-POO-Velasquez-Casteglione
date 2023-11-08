package gui.consulta;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controlador.Coordinador;
import modelo.Parada;
import org.apache.log4j.Logger;

public class ConsultaForm extends JDialog {
	final static Logger logger = Logger.getLogger(ConsultaForm.class);
	private Coordinador coordinador;
	private JPanel contentPane;
	private JButton btnCalcular;
	private JButton btnCancelar;
	private JLabel lblParada1;
	private JLabel lblParada2;
	private JComboBox<Object> cbxParada1;
	private JComboBox<Object> cbxParada2;
	private JLabel lblHora;
	private JLabel lblColectivos;
	private JTextField jtfHora, jtfNumeroLineas;

	public ConsultaForm() {
		setBounds(100, 100, 530, 270);
		setTitle("Consulta");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Handler handler = new Handler();

		btnCalcular = new JButton("Calcular");
		btnCalcular.setBounds(65, 185, 156, 32);
		btnCalcular.setFocusable(false);
		contentPane.add(btnCalcular);
		btnCalcular.addActionListener(handler);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(288, 185, 156, 32);
		btnCancelar.setFocusable(false);
		contentPane.add(btnCancelar);

		lblParada1 = new JLabel("Parada Origen:");
		lblParada1.setBounds(20, 41, 118, 14);
		contentPane.add(lblParada1);

		lblParada2 = new JLabel("Parada Destino:");
		lblParada2.setBounds(20, 77, 118, 14);
		contentPane.add(lblParada2);

		cbxParada1 = new JComboBox<>();
		cbxParada1.setBounds(115, 37, 375, 22);
		contentPane.add(cbxParada1);
		cbxParada1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbxParada1.getSelectedIndex() == 0) {
					cbxParada2.setEnabled(false);
					btnCalcular.setEnabled(false);
					cbxParada2.removeAllItems();
				} else {
					cbxParada2.setEnabled(true);
					cbxParada2.removeAllItems();
					cbxParada2.addItem("Selecionar...");
					for (Parada parada: coordinador.listarParadas().values()) {
						if (!parada.equals(coordinador.listarParadas().get(cbxParada1.getSelectedIndex())))
							cbxParada2.addItem(parada.getCodigo() + " - " + parada.getDireccion());
					}
				}
			}
		});

		cbxParada2 = new JComboBox<>();
		cbxParada2.setBounds(115, 73, 375, 22);
		contentPane.add(cbxParada2);
		cbxParada2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbxParada2.getSelectedIndex() == 0)
					btnCalcular.setEnabled(false);
				else
					btnCalcular.setEnabled(true);
			}
		});

		JLabel titulo = new JLabel("Ingrese los datos para calcular el trayecto mas corto:");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 12));
		titulo.setBounds(94, 11, 325, 14);
		contentPane.add(titulo);

		lblHora = new JLabel("Hora de la llegada:");
		lblHora.setBounds(20, 112, 150, 14);
		contentPane.add(lblHora);

		lblColectivos = new JLabel("Limite de numeros de colectivos:");
		lblColectivos.setBounds(20, 147, 200, 14);
		contentPane.add(lblColectivos);

		jtfHora = new JTextField();
		jtfHora.setBounds(130, 109, 86, 20);
		contentPane.add(jtfHora);
		jtfHora.setColumns(10);

		jtfNumeroLineas = new JTextField();
		jtfNumeroLineas.setBounds(213, 144, 86, 20);
		contentPane.add(jtfNumeroLineas);
		jtfNumeroLineas.setColumns(10);

		btnCancelar.addActionListener(handler);
		setModal(true);
	}

	public void accion() {
		cbxParada1.removeAllItems();
		cbxParada1.addItem("Seleccionar...");
		for (Parada parada : coordinador.listarParadas().values())
			cbxParada1.addItem(parada.getCodigo() + " - " + parada.getDireccion());
		cbxParada2.setEnabled(false);
		btnCalcular.setEnabled(false);
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnCancelar) {
				coordinador.cancelarConsulta();
				logger.info("Cancelar consultaForm");
				return;
			}
			if (event.getSource() == btnCalcular) {
				Parada parada1 = new Parada(Integer.parseInt(((String) cbxParada1.getSelectedItem()).split(" - ")[0]),
						null);
				Parada parada2 = new Parada(Integer.parseInt(((String) cbxParada2.getSelectedItem()).split(" - ")[0]),
						null);
				coordinador.masRapido(coordinador.buscarParada(parada1),
						coordinador.buscarParada(parada2), coordinador.horaLlegadaParada(),
						coordinador.numeroLineas());
				logger.info("Consulta masRapido");
			}
		}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}