package negocio.hilos;

import controlador.Coordinador;
import modelo.Tramo;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class MasRapidoHilo extends SwingWorker<Void, Void> {
    private Coordinador coordinador;
    private Random random = new Random();
    private List<List<Tramo>> trayecto;
    private int horaLlegada, size;

    public MasRapidoHilo(List<List<Tramo>> trayecto, int horaLlegada, Coordinador coordinador, int size) {
        this.coordinador = coordinador;
        this.trayecto = trayecto;
        this.horaLlegada = horaLlegada;
        this.size = size;
    }

    @Override
    public Void doInBackground() {
        int i = 0;

        while (i < 100 && !this.isCancelled()) {
            try {
                Thread.sleep(random.nextInt(500)+100);
                i+= random.nextInt(50 / size) + 3;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            coordinador.actualizarBarraResultado(i);
        }
        if (!this.isCancelled())
            coordinador.masRapido(trayecto, horaLlegada);
        return null;
    }
}
