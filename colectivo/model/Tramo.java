package colectivo.model;

public class Tramo{
    private Parada parada1;
    private Parada parada2;
    private int tiempo;

    public Tramo() {

    }

    public Parada getParada1() {
        return parada1;
    }

    public Parada getParada2() {
        return parada2;
    }

    public int getTiempo() {
        return tiempo;
    }

}