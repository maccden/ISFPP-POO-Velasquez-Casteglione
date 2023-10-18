package model;

import datastructures.ArrayList;
import datastructures.List;

public class Linea {
    private String codigo;
    private List<Parada> paradasVuelta, paradasIda;

    public Linea(String codigo) {
        this.codigo = codigo;
        paradasVuelta = new ArrayList<>();
        paradasIda = new ArrayList<>();
    }

    public void agregarIda(Parada parada) {
        paradasIda.add(paradasIda.size(), parada);
    }

    public void agregarVuelta(Parada parada) {
        paradasVuelta.add(paradasVuelta.size(), parada);
    }

    public String getCodigo() {
        return codigo;
    }

    public List<Parada> getParadasIda() { return paradasIda; }

    public List<Parada> getParadasVuelta() { return paradasVuelta; }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Linea [codigo=" + codigo + ", paradasVuelta=" + paradasVuelta + ", paradasIda="
                + paradasIda + "]";
    }

    @Override
    public boolean equals(Object obj) {
        Linea other = (Linea) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }
}