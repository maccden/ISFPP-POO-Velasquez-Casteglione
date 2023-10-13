package interfaz;

import app.Constante;
import datastructures.List;
import datastructures.TreeMap;
import model.Parada;
import model.Tramo;

public class Interfaz {
    // Usuario ingresa opcion
    public static int opcion() {
        return Constante.MAS_RAPIDO;
    }

    // Usuario ingresa estacion origen
    public static Parada ingresarEstacionOrigen(TreeMap<String, Parada> paradas) {
        return paradas.get("a2");
    }

    // Usuario ingresa estacion destino
    public static Parada ingresarEstacionDestino(TreeMap<String, Parada> paradas) {
        return paradas.get("c6");
    }

    /*
    public static void resultado(List<Tramo> recorrido) {
        int tiempoTotal = 0;
        int tiempoSubte = 0;
        int tiempoCaminando = 0;
        for (Tramo t : recorrido) {
            System.out.println(t.getInicio().getLinea().getCodigo() + "-" + t.getInicio().getNombre() + " > " + t.getFin().getLinea().getCodigo() + "-" + t.getFin().getNombre() + " :" + t.getTiempo());
            tiempoTotal += t.getTiempo();
            if (t.getInicio().getLinea().equals(t.getFin().getLinea()))
                tiempoSubte += t.getTiempo();
            else
                tiempoCaminando += t.getTiempo();
        }
        System.out.println("Tiempo Total: " + tiempoTotal);
        System.out.println("Tiempo Subte: " + tiempoSubte);
        System.out.println("Tiempo Caminando: " + tiempoCaminando);
    }
     */
}