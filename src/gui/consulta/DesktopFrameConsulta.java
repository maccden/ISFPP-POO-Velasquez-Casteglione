package gui.consulta;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controlador.Coordinador;

public class DesktopFrameConsulta extends JFrame {
	private Coordinador coordinador;
	private JPanel contentPane;

	public DesktopFrameConsulta() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 600);
		JMenuBar menuBar = new JMenuBar();
		JMenu mnNewMenu_1 = new JMenu("Aplicacion");
		JMenu mnNewMenu_2 = new JMenu("Consultas");
		setJMenuBar(menuBar);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Salir");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(NORMAL);
			}
		});

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Consultas");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador.mostrarConsulta();
			}
		});
		
		menuBar.add(mnNewMenu_1);
		mnNewMenu_1.add(mntmNewMenuItem_1);
		menuBar.add(mnNewMenu_2);
		mnNewMenu_2.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setSize(600, 480);
		setTitle("Empresa: MVC");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}