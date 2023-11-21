package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

/**
 * Esta clase proporciona una conexión a la base de datos utilizando los datos
 * de conexión del archivo jdbc.properties.
 */
public class BDConexion {
	
	private static Connection con = null;

	/**
	 * Obtiene una conexión a la base de datos.
	 *
	 * @return La conexión a la base de datos.
	 * @throws RuntimeException Si ocurre un error al crear la conexión.
	 */
	public static Connection getConnection() {
		try {
			if (con == null) {
				// Agregamos un hook para cerrar la conexión al finalizar el programa
				Runtime.getRuntime().addShutdownHook(new MiShDwnHook());
				ResourceBundle rb = ResourceBundle.getBundle("jdbc");
				String driver = rb.getString("driver");
				String url = rb.getString("url");
				String usr = rb.getString("usr");
				String pwd = rb.getString("pwd");
				Class.forName(driver);
				con = DriverManager.getConnection(url, usr, pwd);
			}
			return con;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error al crear la conexión", ex);
		}
	}

	/**
	 * Este hook se ejecuta justo antes de que finalice el programa para cerrar la
	 * conexión.
	 */
	public static class MiShDwnHook extends Thread {
		public void run() {
			try {
				Connection con = BDConexion.getConnection();
				con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}
}
