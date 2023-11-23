package factory;

import java.util.Hashtable;
import java.util.ResourceBundle;

/**
 * La clase Factory proporciona un mecanismo para obtener instancias de objetos
 * según su nombre (objName) utilizando una Hashtable para almacenar y gestionar
 * las instancias creadas.
 */
public class Factory {

    // Hashtable para almacenar las instancias creadas
    private static Hashtable<String, Object> instancias = new Hashtable<>();

    /**
     * Obtiene una instancia del objeto asociado al nombre proporcionado.
     *
     * @param objName El nombre del objeto que se desea obtener.
     * @return Una instancia del objeto asociado al nombre.
     * @throws RuntimeException Si ocurre algún error durante la creación de la
     *                          instancia.
     */
    public static Object getInstancia(String objName) {
        try {
            // Verifica si existe un objeto relacionado a objName en la Hashtable
            Object obj = instancias.get(objName);

            // Si no existe, lo instancia y lo agrega a la Hashtable
            if (obj == null) {
                ResourceBundle rb = ResourceBundle.getBundle("factory");
                String sClassname = rb.getString(objName);
                obj = Class.forName(sClassname).getDeclaredConstructor().newInstance();

                // Agrega el objeto a la Hashtable
                instancias.put(objName, obj);
            }

            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
