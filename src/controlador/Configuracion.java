package controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Clase que gestiona la configuración de la aplicación.
 */
public class Configuracion {

    private static Configuracion configuracion = null;
    private Coordinador coordinador;
    private ResourceBundle resourceBundle;

    /**
     * Obtiene una instancia de la configuración (patrón Singleton).
     *
     * @return Instancia única de la configuración.
     */
    public static Configuracion getConfiguracion() {
        if (configuracion == null) {
            configuracion = new Configuracion();
        }
        return configuracion;
    }

    /**
     * Constructor privado para asegurar el patrón Singleton.
     */
    private Configuracion() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            Locale.setDefault(new Locale(prop.getProperty("language"), prop.getProperty("country")));
            resourceBundle = ResourceBundle.getBundle(prop.getProperty("labels"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ResourceBundle asociado a la configuración.
     *
     * @return ResourceBundle de la configuración.
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Establece el coordinador asociado a la configuración.
     *
     * @param coordinador Coordinador a establecer.
     */
    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
