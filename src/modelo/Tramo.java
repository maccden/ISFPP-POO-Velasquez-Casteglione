package modelo;

public class Tramo {
    private Parada inicio, fin;
    private int tiempo, tipo;

    public Tramo() {
    }

    public Tramo(Parada inicio, Parada fin, int tiempo, int tipo) {
        this.inicio = inicio;
        this.fin = fin;
        this.tiempo = tiempo;
        this.tipo = tipo;
    }

    public int getCongestion() {
        int promedioCongestion = 0;
        for (Linea linea : inicio.getLineas())
            promedioCongestion += linea.getFrecuencia();
        for (Linea linea : fin.getLineas())
            promedioCongestion += linea.getFrecuencia();
        return promedioCongestion / (inicio.getLineas().size() + fin.getLineas().size());
    }

    public Parada getInicio() {
        return inicio;
    }

    public Parada getFin() {
        return fin;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setInicio(Parada inicio) {
        this.inicio = inicio;
    }

    public void setFin(Parada fin) {
        this.fin = fin;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Tramo [inicio=" + inicio + ", fin=" + fin + ", tiempo=" + tiempo + ", tipo=" + tipo + "]";
    }

    @Override
    public boolean equals(Object obj) {
        Tramo other = (Tramo) obj;
        if (inicio == null) {
            if (other.inicio != null)
                return false;
        } else if (!inicio.equals(other.inicio))
            return false;
        if (fin == null) {
            if (other.fin != null)
                return false;
        } else if (!fin.equals(other.fin))
            return false;
        return true;
    }
}