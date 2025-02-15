package gui.consulta;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;
import java.util.regex.PatternSyntaxException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controlador.Coordinador;
import modelo.Parada;
import negocio.hilos.CalcularHilo;
import util.Time;

import org.apache.log4j.Logger;

/**
 * La clase ConsultaForm representa una ventana de consulta en la interfaz
 * gráfica de usuario.
 * Permite al usuario realizar consultas de colectivos entre dos paradas en un
 * determinado horario.
 */
public class ConsultaForm extends JDialog {

	/**
	 * Registro para registrar mensajes y eventos en ConsultaForm.
	 */
	final static Logger logger = Logger.getLogger(ConsultaForm.class);

	private Coordinador coordinador;
	private ResourceBundle resourceBundle;
	private JPanel contentPane;
	private JButton btnCalcular, btnCancelar, btnCancelar2;
	private JLabel lblParada1, lblParada2, lblHora, lblColectivos, errorHora, errorNumeroLineas;
	private JComboBox<Object> cbxParada1, cbxParada2;
	private JTextField jtfHora, jtfLimiteColectivos;
	private JProgressBar progressBar;

	/**
	 * Constructor de la clase ConsultaForm.
	 */
	public ConsultaForm() {
	}

	/**
	 * Inicializa la interfaz gráfica de usuario y configura los componentes.
	 */
	public void init() {
		resourceBundle = coordinador.getResourceBundle();
		setBounds(100, 100, 530, 270);
		setTitle(resourceBundle.getString("ConsultaForm_title_window"));
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

		btnCalcular = new JButton(resourceBundle.getString("ConsultaForm_calculate"));
		btnCalcular.setBounds(50, 185, 104, 32);
		btnCalcular.setFocusable(false);
		contentPane.add(btnCalcular);
		btnCalcular.addActionListener(handler);

		btnCancelar = new JButton(resourceBundle.getString("ConsultaForm_cancel"));
		btnCancelar.setBounds(358, 185, 104, 32);
		btnCancelar.setFocusable(false);
		contentPane.add(btnCancelar);

		lblParada1 = new JLabel(resourceBundle.getString("ConsultaForm_origin_station"));
		lblParada1.setBounds(20, 41, 118, 14);
		contentPane.add(lblParada1);

		lblParada2 = new JLabel(resourceBundle.getString("ConsultaForm_destination_station"));
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
					cbxParada2.addItem(resourceBundle.getString("ConsultaForm_select"));
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

		JLabel titulo = new JLabel(resourceBundle.getString("ConsultaForm_title"));
		titulo.setFont(new Font("Tahoma", Font.BOLD, 12));
		titulo.setBounds(94, 11, 325, 14);
		contentPane.add(titulo);

		lblHora = new JLabel(resourceBundle.getString("ConsultaForm_hour"));
		lblHora.setBounds(20, 112, 150, 14);
		contentPane.add(lblHora);

		lblColectivos = new JLabel(resourceBundle.getString("ConsultaForm_bus"));
		lblColectivos.setBounds(20, 147, 200, 14);
		contentPane.add(lblColectivos);

		jtfHora = new JTextField();
		jtfHora.setBounds(130, 109, 86, 20);
		contentPane.add(jtfHora);

		jtfLimiteColectivos = new JTextField();
		jtfLimiteColectivos.setBounds(213, 144, 86, 20);
		contentPane.add(jtfLimiteColectivos);

		progressBar = new JProgressBar();
		progressBar.setBounds(164, 185, 184, 32);
		progressBar.setVisible(false);
		contentPane.add(progressBar);

		btnCancelar2 = new JButton(resourceBundle.getString("ConsultaForm_cancel"));
		btnCancelar2.setBounds(50, 185, 104, 32);
		btnCancelar2.setFocusable(false);
		btnCancelar2.setVisible(false);
		contentPane.add(btnCancelar2);
		btnCancelar2.addActionListener(handler);

		btnCancelar.addActionListener(handler);
		setModal(true);
	}

	/**
	 * Acción que se ejecuta al abrir la ventana de consulta.
	 */
	public void accion() {
		cbxParada1.removeAllItems();
		cbxParada1.addItem(resourceBundle.getString("ConsultaForm_select"));
		for (Parada parada : coordinador.listarParadas().values())
			cbxParada1.addItem(parada.getCodigo() + " - " + parada.getDireccion());
		cbxParada2.setEnabled(false);
		btnCalcular.setEnabled(false);
		jtfHora.setText("");
		jtfLimiteColectivos.setText("");
	}

	/**
	 * Clase interna para gestionar eventos de acción.
	 */
	private class Handler implements ActionListener {
		/**
		 * Método que se ejecuta al realizar una acción.
		 *
		 * @param event El evento de acción.
		 */
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == btnCancelar) {
				coordinador.cancelarConsulta();
				logger.info("Cancelar consultaForm");
				return;
			}

			if (event.getSource() == btnCancelar2) {
				coordinador.cancelarHilo();
				progressBar.setVisible(false);
				progressBar.setValue(0);
				terminar();
				logger.info("Cancelar calculo");
				return;
			}

			if (!registroValido())
				return;

			if (event.getSource() == btnCalcular) {

				Parada parada1 = new Parada(Integer.parseInt(((String) cbxParada1.getSelectedItem()).split(" - ")[0]),
						null);
				Parada parada2 = new Parada(Integer.parseInt(((String) cbxParada2.getSelectedItem()).split(" - ")[0]),
						null);

				coordinador.ejecutarHilo(new CalcularHilo(parada1, parada2, Time.toMins(jtfHora.getText()),
						Integer.parseInt(jtfLimiteColectivos.getText()), coordinador,
						Integer.parseInt(jtfLimiteColectivos.getText())));
				calculando();

				logger.info("Consulta calcula");
			}
		}
	}

	/**
	 * Establece el coordinador para la ventana de consulta.
	 *
	 * @param coordinador El coordinador que gestionará las acciones de consulta.
	 */
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	/**
	 * Verifica si los datos ingresados en la ventana son válidos.
	 *
	 * @return true si los datos son válidos, false de lo contrario.
	 */
	public boolean registroValido() {
		errorHora.setText("");
		errorNumeroLineas.setText("");

		// validar la hora
		if (jtfHora.getText().isEmpty()) {
			errorHora.setText(resourceBundle.getString("ConsultaForm_error_emptyspace"));
			return false;
		}
		try {
			String[] horario = jtfHora.getText().trim().split(":");
			if (horario.length < 2) {
				errorHora.setText(resourceBundle.getString("ConsultaForm_error_hour1"));
				return false;
			}
			Integer.parseInt(horario[0]);
			Integer.parseInt(horario[1]);
			if (Integer.parseInt(horario[0]) > 24 || Integer.parseInt(horario[1]) > 60
					|| Integer.parseInt(horario[0]) < 0 || Integer.parseInt(horario[1]) < 0) {
				errorHora.setText(resourceBundle.getString("ConsultaForm_error_hour2"));
				return false;
			}
			if (Integer.parseInt(horario[0]) == 24 && Integer.parseInt(horario[1]) > 0) {
				errorHora.setText(resourceBundle.getString("ConsultaForm_error_hour2"));
				return false;
			}
		} catch (PatternSyntaxException e) {
			errorHora.setText(resourceBundle.getString("ConsultaForm_error_hour1"));
			return false;
		} catch (NumberFormatException e) {
			errorHora.setText(resourceBundle.getString("ConsultaForm_error_hour2"));
			return false;
		}

		// validar limite colectivos
		String limiteColectivos = jtfLimiteColectivos.getText().trim();
		if (limiteColectivos.isEmpty()) {
			errorNumeroLineas.setText(resourceBundle.getString("ConsultaForm_error_emptyspace"));
			return false;
		}
		try {
			Integer.parseInt(jtfLimiteColectivos.getText().trim());
		} catch (NumberFormatException e) {
			errorNumeroLineas.setText(resourceBundle.getString("ConsultaForm_error_number1"));
			return false;
		}
		if (Integer.parseInt(jtfLimiteColectivos.getText().trim()) <= 0) {
			errorNumeroLineas.setText(resourceBundle.getString("ConsultaForm_error_number2"));
			return false;
		}
		return true;
	}

	/**
	 * Actualiza el valor de la barra de progreso.
	 *
	 * @param i El nuevo valor de la barra de progreso.
	 */
	public void actualizarBarra(int i) {
		progressBar.setValue(i);
		progressBar.repaint();
	}

	/**
	 * Configura la interfaz para indicar que se está realizando un cálculo.
	 */
	public void calculando() {
		progressBar.setValue(0);
		btnCancelar.setEnabled(false);
		btnCalcular.setEnabled(false);
		btnCalcular.setVisible(false);
		progressBar.setVisible(true);
		btnCancelar2.setVisible(true);
		cbxParada1.setEnabled(false);
		cbxParada2.setEnabled(false);
		jtfHora.setEnabled(false);
		jtfLimiteColectivos.setEnabled(false);
	}

	/**
	 * Configura la interfaz para indicar que el cálculo ha terminado.
	 */
	public void terminar() {
		progressBar.setValue(0);
		cbxParada1.setEnabled(true);
		cbxParada2.setEnabled(true);
		jtfHora.setEnabled(true);
		jtfLimiteColectivos.setEnabled(true);
		btnCalcular.setEnabled(true);
		btnCalcular.setVisible(true);
		btnCancelar.setEnabled(true);
		btnCancelar2.setVisible(false);
		progressBar.setVisible(false);
	}
}
