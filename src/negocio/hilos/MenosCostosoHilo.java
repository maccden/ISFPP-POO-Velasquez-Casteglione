package negocio.hilos;

import controlador.Coordinador;
import modelo.Tramo;

import javax.swing.*;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa un hilo para calcular la ruta menos costosa en segundo
 * plano
 * y actualizar una barra de progreso.
 */
public class MenosCostosoHilo extends SwingWorker<Void, Void> {
    
    private Coordinador coordinador;
    private Random random = new Random();
    private List<List<Tramo>> trayecto;
    private int horaLlegada, size;

    /**
     * Constructor de la clase MenosCostosoHilo.
     *
     * @param trayecto    El trayecto para el cálculo de la ruta menos costosa.
     * @param horaLlegada La hora de llegada para el cálculo.
     * @param coordinador El coordinador que maneja la lógica de la aplicación.
     * @param size        El tamaño utilizado para el cálculo de la barra de
     *                    progreso.
     */
    public MenosCostosoHilo(List<List<Tramo>> trayecto, int horaLlegada, Coordinador coordinador, int size) {
        this.coordinador = coordinador;
        this.trayecto = trayecto;
        this.horaLlegada = horaLlegada;
        this.size = size;
    }

    /**
     * Método principal que se ejecuta en segundo plano para realizar cálculos y
     * actualizar la barra de progreso.
     *
     * @return null.
     */
    @Override
    public Void doInBackground() {
        int i = 0;

        while (i < 100 && !this.isCancelled()) {
            try {
                // Simula el proceso de cálculo con una pausa aleatoria.
                Thread.sleep(random.nextInt(500) + 100);
                i += random.nextInt(50 / size) + 3;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Actualiza la barra de progreso.
            coordinador.actualizarBarraResultado(i);
        }

        // Si la tarea no ha sido cancelada, realiza el cálculo de la ruta menos
        // costosa.
        if (!this.isCancelled())
            coordinador.menosCostoso(trayecto, horaLlegada);

        return null;
    }
}
