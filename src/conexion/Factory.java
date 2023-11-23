package conexion;

import java.util.Hashtable;
import java.util.ResourceBundle;

/**
 * Esta clase proporciona un patrón de diseño Factory para obtener instancias de
 * objetos.
 */
public class Factory {

	private static Hashtable<String, Object> instancias = new Hashtable<>();

	/**
	 * Obtiene una instancia del objeto especificado por su nombre.
	 *
	 * @param objName El nombre del objeto que se desea obtener.
	 * @return La instancia del objeto.
	 * @throws RuntimeException Si ocurre un error al crear la instancia del objeto.
	 */
	public static Object getInstancia(String objName) {
		try {
			// Verificamos si existe un objeto relacionado a objName en la hashtable
			Object obj = instancias.get(objName);

			// Si no existe, lo instanciamos y lo agregamos
			if (obj == null) {
				ResourceBundle rb = ResourceBundle.getBundle("factory");
				String sClassname = rb.getString(objName);
				obj = Class.forName(sClassname).getDeclaredConstructor().newInstance();

				// Agregamos el objeto a la hashtable
				instancias.put(objName, obj);
			}

			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}