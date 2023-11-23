package gui.consulta;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controlador.Constantes;
import controlador.Coordinador;
import datastructures.TreeMap;
import modelo.Linea;
import modelo.Tramo;
import negocio.hilos.MasRapidoHilo;
import negocio.hilos.MenosCostosoHilo;
import org.apache.log4j.Logger;
import util.Time;

/**
 * ResultadoForm es una ventana de diálogo que muestra los resultados de una
 * consulta de transporte, incluyendo las rutas sugeridas
 * e información de viaje. Los usuarios pueden elegir ver la ruta más rápida o
 * menos costosa, y el cálculo se realiza en segundo
 * plano mediante hilos.
 */
public class ResultadoForm extends JDialog {

	/**
	 * Registro para registrar mensajes y eventos en ResultadoForm.
	 */
	final static Logger logger = Logger.getLogger(ResultadoForm.class);

	private ResourceBundle resourceBundle;
	private Coordinador coordinador;
	private JPanel contentPane;
	private JButton btnCancelar, btnMasRapido, btnMenosCostoso, btnCancelarSub;
	private JTextArea txtResultado;
	private JProgressBar progressBar;
	private int horario;
	private List<List<Tramo>> trayecto;

	/**
	 * Constructor por defecto para ResultadoForm.
	 */
	public ResultadoForm() {
	}

	/**
	 * Inicializa ResultadoForm, configurando los componentes de la interfaz gráfica
	 * y agregando escuchadores de eventos.
	 */
	public void init() {
		resourceBundle = coordinador.getResourceBundle();
		setBounds(100, 100, 662, 300);
		setTitle(resourceBundle.getString("ResultadoForm_title_window"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Handler handler = new Handler();
		btnCancelar = new JButton(resourceBundle.getString("ResultadoForm_cancel"));
		btnCancelar.setBounds(249, 205, 155, 32);
		btnCancelar.setFocusable(false);
		contentPane.add(btnCancelar);
		txtResultado = new JTextArea();
		txtResultado.setFont(new Font("Arial", Font.PLAIN, 13));
		JScrollPane scroll = new JScrollPane(txtResultado);
		scroll.setBounds(29, 23, 592, 171);
		contentPane.add(scroll);
		btnCancelar.addActionListener(handler);

		btnMasRapido = new JButton(resourceBundle.getString("ResultadoForm_more_faster"));
		btnMasRapido.setBounds(84, 205, 155, 32);
		btnMasRapido.setFocusable(false);
		contentPane.add(btnMasRapido);
		btnMasRapido.addActionListener(handler);

		btnMenosCostoso = new JButton(resourceBundle.getString("ResultadoForm_low_cost"));
		btnMenosCostoso.setBounds(414, 205, 155, 32);
		btnMenosCostoso.setFocusable(false);
		contentPane.add(btnMenosCostoso);
		btnMenosCostoso.addActionListener(handler);

		progressBar = new JProgressBar();
		progressBar.setBounds(249, 205, 155, 32);
		contentPane.add(progressBar);
		progressBar.setVisible(false);

		btnCancelarSub = new JButton(resourceBundle.getString("ResultadoForm_cancel"));
		btnCancelarSub.setFocusable(false);
		contentPane.add(btnCancelarSub);
		btnCancelarSub.addActionListener(handler);

		setModal(true);
	}

	/**
	 * Muestra la información de viaje calculada en la ventana ResultadoForm.
	 *
	 * @param tramos  La lista de tramos que representa las rutas sugeridas.
	 * @param horario El tiempo de viaje especificado.
	 * @param lineas  El TreeMap de líneas para referencia.
	 * @return Una cadena formateada que contiene la información de viaje.
	 */
	public String verDatos(List<List<Tramo>> tramos, int horario, TreeMap<String, Linea> lineas) {
		StringBuilder resultado = new StringBuilder();
		if (tramos.isEmpty()) {
			resultado.append(resourceBundle.getString("ResultadoForm_no_travel"));
			btnMasRapido.setEnabled(false);
			btnMenosCostoso.setEnabled(false);
			return resultado.toString();
		}

		trayecto = tramos;
		this.horario = horario;
		Linea linea;
		Tramo tramo;
		String nombreLinea;
		for (List<Tramo> t : tramos) {
			resultado.append(Time.toTime(horario)).append(" - ")
					.append(resourceBundle.getString("ResultadoForm_arrives")).append("\n");
			for (int i = 0; i < t.size() - 1; i++) {
				tramo = t.get(i);
				linea = tramo.getInicio().getLineas().get(0);
				nombreLinea = linea.getCodigo();
				if (lineas.get(linea.getCodigo()) == null)
					nombreLinea = resourceBundle.getString("ResultadoForm_walking");
				resultado.append(Time.toTime(tramo.getTiempo())).append(" - ").append(nombreLinea).append(" (")
						.append(tramo.getInicio().getDireccion()).append(" ").append(" > ")
						.append(tramo.getFin().getDireccion()).append(") \n");
			}
			tramo = t.get(t.size() - 1);
			resultado.append(Time.toTime(tramo.getTiempo())).append(" - ")
					.append(resourceBundle.getString("ResultadoForm_end")).append("\n");
			resultado.append(resourceBundle.getString("ResultadoForm_total_time"))
					.append(Time.toTime(tramo.getTiempo() - horario)).append("\n");
			resultado.append("=======================================================================\n");
		}
		btnMasRapido.setEnabled(true);
		btnMenosCostoso.setEnabled(true);
		return resultado.toString();
	}

	/**
	 * Actualiza el contenido del JTextArea con la cadena de resultado
	 * proporcionada.
	 *
	 * @param resultado La cadena de resultado que se mostrará.
	 */
	public void accion(String resultado) {
		txtResultado.setText(resultado);
	}

	/**
	 * Clase de escuchador de acciones para manejar clics de botones e interacciones
	 * del usuario.
	 */
	private class Handler implements ActionListener {

		/**
		 * Maneja eventos de acción desencadenados por botones en ResultadoForm.
		 *
		 * @param event El evento de acción.
		 */
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnCancelar) {
				coordinador.cancelarResultado();
				logger.info("Cancelar resultadoForm");
				return;
			}

			if (event.getSource() == btnCancelarSub) {
				coordinador.cancelarHilo();
				progressBar.setVisible(false);
				progressBar.setValue(0);
				terminar();
				logger.info("Cancelar subcalculo");
				return;
			}

			if (event.getSource() == btnMasRapido) {
				// coordinador.masRapido(trayecto, horario);
				btnCancelarSub.setBounds(84, 205, 155, 32);
				coordinador.ejecutarHilo(new MasRapidoHilo(trayecto, horario, coordinador, trayecto.size()));
				calculando(Constantes.MAS_RAPIDO);
				logger.info("Calcula rapido");
			}

			if (event.getSource() == btnMenosCostoso) {
				btnCancelarSub.setBounds(414, 205, 155, 32);
				coordinador.ejecutarHilo(new MenosCostosoHilo(trayecto, horario, coordinador, trayecto.size()));
				calculando(Constantes.MENOS_COSTOSO);
				logger.info("Calcula menos costoso");
			}
		}
	}

	/**
	 * Establece la instancia de Coordinador para ResultadoForm.
	 *
	 * @param coordinador La instancia de Coordinador que se establecerá.
	 */
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	/**
	 * Actualiza la barra de progreso con el valor proporcionado.
	 *
	 * @param i El valor para establecer en la barra de progreso.
	 */
	public void actualizarBarra(int i) {
		progressBar.setValue(i);
		progressBar.repaint();
	}

	/**
	 * Inicia la indicación visual de cálculos en curso mostrando la barra de
	 * progreso y ajustando la visibilidad de los botones.
	 *
	 * @param consulta El tipo de consulta que se está realizando.
	 */
	public void calculando(int consulta) {
		progressBar.setValue(0);
		progressBar.setVisible(true);
		btnCancelar.setVisible(false);
		btnCancelar.setEnabled(false);
		if (consulta == Constantes.MAS_RAPIDO) {
			btnMasRapido.setVisible(false);
			btnMasRapido.setEnabled(false);
			btnMenosCostoso.setVisible(true);
			btnMenosCostoso.setEnabled(false);
		}
		if (consulta == Constantes.MENOS_COSTOSO) {
			btnMasRapido.setVisible(true);
			btnMasRapido.setEnabled(false);
			btnMenosCostoso.setVisible(false);
			btnMenosCostoso.setEnabled(false);
		}
		btnCancelarSub.setVisible(true);
	}

	/**
	 * Completa el proceso de cálculo restableciendo la barra de progreso y
	 * volviendo a habilitar las interacciones del usuario.
	 */
	public void terminar() {
		progressBar.setValue(0);
		progressBar.setVisible(false);
		btnCancelar.setVisible(true);
		btnCancelar.setEnabled(true);
		btnMasRapido.setVisible(true);
		btnMasRapido.setEnabled(true);
		btnMenosCostoso.setVisible(true);
		btnMenosCostoso.setEnabled(true);
		btnCancelarSub.setVisible(false);
	}
}
