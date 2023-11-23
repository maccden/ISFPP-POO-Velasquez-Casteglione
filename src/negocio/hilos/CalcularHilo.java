package negocio.hilos;

import controlador.Coordinador;
import modelo.Parada;

import javax.swing.*;
import java.util.Random;

/**
 * Clase que representa un hilo para calcular información en segundo plano
 * y actualizar una barra de progreso.
 */
public class CalcularHilo extends SwingWorker<Void, Void> {

    private Coordinador coordinador;
    private Random random = new Random();
    private Parada origen, destino;
    private int horaLlegada, colectivos, size;

    /**
     * Constructor de la clase CalcularHilo.
     *
     * @param origen      La parada de origen para el cálculo.
     * @param destino     La parada de destino para el cálculo.
     * @param horaLlegada La hora de llegada para el cálculo.
     * @param colectivos  La cantidad de colectivos para el cálculo.
     * @param coordinador El coordinador que maneja la lógica de la aplicación.
     * @param size        El tamaño utilizado para el cálculo de la barra de
     *                    progreso.
     */
    public CalcularHilo(Parada origen, Parada destino, int horaLlegada, int colectivos, Coordinador coordinador,
            int size) {
        this.coordinador = coordinador;
        this.origen = origen;
        this.destino = destino;
        this.horaLlegada = horaLlegada;
        this.colectivos = colectivos;
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
                i += random.nextInt(26 / size) + 1;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Actualiza la barra de progreso.
            coordinador.actualizarBarraConsulta(i);
        }

        // Si la tarea no ha sido cancelada, realiza el cálculo final.
        if (!this.isCancelled())
            coordinador.calculo(origen, destino, horaLlegada, colectivos);

        return null;
    }
}
