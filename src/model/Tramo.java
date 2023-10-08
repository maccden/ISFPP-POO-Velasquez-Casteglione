package model;

public class Tramo {
    private Parada parada1;
    private Parada parada2;
    private int tiempo;

    public Tramo(Parada parada1, Parada parada2, int tiempo) {
        this.parada1 = parada1;
        this.parada2 = parada2;
        this.tiempo = tiempo;
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

    @Override
    public boolean equals(Object obj) {
        Tramo other = (Tramo) obj;
        if (parada1 == null) {
            if (other.parada1 != null)
                return false;
        } else if (!parada1.equals(other.parada1))
            return false;
        if (parada2 == null) {
            if (other.parada2 != null)
                return false;
        } else if (!parada2.equals(other.parada2))
            return false;
        return true;
    }
}