package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.PatternSyntaxException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controlador.Coordinador;
import modelo.Parada;
import util.Time;

import org.apache.log4j.Logger;

public class ConsultaForm extends JDialog {
	final static Logger logger = Logger.getLogger(ConsultaForm.class);
	private Coordinador coordinador;
	private JPanel contentPane;
	private JButton btnCalcular, btnCancelar;
	private JLabel lblParada1, lblParada2, lblHora, lblColectivos, errorHora, errorNumeroLineas;
	private JComboBox<Object> cbxParada1, cbxParada2;
	private JTextField jtfHora, jtfLimiteColectivos;

	public ConsultaForm() {
		setBounds(100, 100, 530, 270);
		setTitle("Consulta");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Handler handler = new Handler();

		errorHora = new JLabel("");
		errorHora.setForeground(Color.RED);
		errorHora.setFont(new Font("Tahoma", Font.BOLD, 12));
		errorHora.setBounds(231, 110, 200, 14);
		getContentPane().add(errorHora);

		errorNumeroLineas = new JLabel("");
		errorNumeroLineas.setForeground(Color.RED);
		errorNumeroLineas.setFont(new Font("Tahoma", Font.BOLD, 12));
		errorNumeroLineas.setBounds(310, 146, 200, 14);
		getContentPane().add(errorNumeroLineas);

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
					for (Parada parada : coordinador.listarParadas().values()) {
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

		jtfLimiteColectivos = new JTextField();
		jtfLimiteColectivos.setBounds(213, 144, 86, 20);
		contentPane.add(jtfLimiteColectivos);
		jtfLimiteColectivos.setColumns(10);

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
		jtfHora.setText("");
		jtfLimiteColectivos.setText("");
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnCancelar) {
				coordinador.cancelarConsulta();
				logger.info("Cancelar consultaForm");
				return;
			}
			if (!registroValido())
				return;

			coordinador.setHoraLlegada(jtfHora.getText());
			coordinador.setNumeroLimiteColectivos(Integer.parseInt(jtfLimiteColectivos.getText()));

			if (event.getSource() == btnCalcular) {
				Parada parada1 = new Parada(Integer.parseInt(((String) cbxParada1.getSelectedItem()).split(" - ")[0]),
						null);
				Parada parada2 = new Parada(Integer.parseInt(((String) cbxParada2.getSelectedItem()).split(" - ")[0]),
						null);
				coordinador.calculo(coordinador.buscarParada(parada1),
						coordinador.buscarParada(parada2), Time.toMins(coordinador.horaLlegadaParada()),
						coordinador.getNumeroLimiteColectivos());
				logger.info("Consulta masRapido");
			}
		}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	public boolean registroValido() {
		errorHora.setText("");
		errorNumeroLineas.setText("");

		// validar la hora
		if (jtfHora.getText().isEmpty()) {
			errorHora.setText("Campo obligatorio");
			return false;
		}
		try {
			String[] horario = jtfHora.getText().trim().split(":");
			if (horario.length < 2) {
				errorHora.setText("¡Escriba bien la hora! (XX:XX)");
				return false;
			}
			Integer.parseInt(horario[0]);
			Integer.parseInt(horario[1]);
			if (Integer.parseInt(horario[0]) > 24 || Integer.parseInt(horario[1]) > 60
			|| Integer.parseInt(horario[0]) < 0 || Integer.parseInt(horario[1]) < 0) {
				errorHora.setText("¡Ingrese una hora valida!");
				return false;
			}
			if (Integer.parseInt(horario[0]) == 24 && Integer.parseInt(horario[1]) > 0) {
				errorHora.setText("¡Ingrese una hora valida!");
				return false;
			}
		} catch (PatternSyntaxException e) {
			errorHora.setText("¡Escriba bien la hora! (XX:XX)");
			return false;
		} catch (NumberFormatException e) {
			errorHora.setText("¡Ingrese una hora valida!");
			return false;
		}
		
		// validar limite colectivos
		String limiteColectivos = jtfLimiteColectivos.getText().trim();
		if (limiteColectivos.isEmpty()) {
			errorNumeroLineas.setText("Campo obligatorio");
			return false;
		}
		try {
			Integer.parseInt(jtfLimiteColectivos.getText().trim());
		} catch (NumberFormatException e) {
			errorNumeroLineas.setText("¡Solo numeros!");
			return false;
		}
		if (Integer.parseInt(jtfLimiteColectivos.getText().trim()) <= 0) {
			errorNumeroLineas.setText("¡Solo numeros positivos!");
			return false;
		}
		coordinador.setNumeroLimiteColectivos(Integer.parseInt(limiteColectivos));
		return true;
	}
}