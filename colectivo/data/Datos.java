package colectivo.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import colectivo.model.*;

public class Datos {

    public List<Parada> cargarParadas(String rutaArchivo) {
        try (BufferedReader bf = new BufferedReader(new FileReader(rutaArchivo))) {
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
