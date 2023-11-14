package negocio.hilos;

import controlador.Coordinador;
import modelo.Parada;

import javax.swing.*;
import java.util.Random;

public class CalcularHilo extends SwingWorker<Void, Void> {
    private Coordinador coordinador;
    private Random random = new Random();
    private Parada origen, destino;
    private int horaLlegada, colectivos, size;

    public CalcularHilo (Parada origen, Parada destino, int horaLlegada, int colectivos, Coordinador coordinador, int size) {
        this.coordinador = coordinador;
        this.origen = origen;
        this.destino = destino;
        this.horaLlegada = horaLlegada;
        this.colectivos = colectivos;
        this.size = size;
    }

    @Override
    public Void doInBackground() {
        int i = 0;

        while (i < 100 && !this.isCancelled()) {
            try {
                Thread.sleep(random.nextInt(500)+100);
                i+= random.nextInt(26 / size ) + 1;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            coordinador.actualizarBarraConsulta(i);
        }
        if (!this.isCancelled())
            coordinador.calculo(origen, destino, horaLlegada, colectivos);
        return null;
    }
}
