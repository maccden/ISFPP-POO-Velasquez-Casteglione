package controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Configuracion {
    private static Configuracion configuracion = null;
    private Coordinador coordinador;
    private ResourceBundle resourceBundle;

    public static Configuracion getConfiguracion() {
        if (configuracion == null) {
            configuracion = new Configuracion();
        }
        return configuracion;
    }

    private Configuracion() {
        Properties prop = new Properties();
        InputStream input;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);

            Locale.setDefault(new Locale(prop.getProperty("language"), prop.getProperty("country")));
            resourceBundle = ResourceBundle.getBundle(prop.getProperty("labels"));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}
