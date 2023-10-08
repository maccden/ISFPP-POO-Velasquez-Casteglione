package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CargarParametros {
    private static String archivoLinea;
    private static String archivoParada;
    private static String archivoColectivo;
    private static String archivoTramo;

    public static void parametros() throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");

        prop.load(input);

        archivoLinea = prop.getProperty("linea");
        archivoParada = prop.getProperty("parada");
        archivoColectivo = prop.getProperty("colectivo");
        archivoTramo = prop.getProperty("tramo");
    }

    public static String getArchivoLinea() {
        return archivoLinea;
    }

    public static String getArchivoParada() {
        return archivoParada;
    }

    public static String getArchivoColectivo() {
        return archivoColectivo;
    }

    public static String getArchivoTramo() {
        return archivoTramo;
    }
}
