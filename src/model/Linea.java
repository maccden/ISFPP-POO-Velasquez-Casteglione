package model;

import datastructures.ArrayList;
import datastructures.List;

public class Linea {
    private String codigo;
    private String sentido;
    private List<Parada> paradasVuelta, paradasIda;

    public Linea(String codigo, String sentido) {
        this.codigo = codigo;
        this.sentido = sentido;
        paradasVuelta = paradasIda = new ArrayList<>();
    }

    public void agregarIda(Parada parada) {
        paradasIda.add(paradasIda.size() ,parada);
    }

    public void agregarVuelta(Parada parada){
        paradasVuelta.add(paradasVuelta.size(), parada);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }
    
    @Override
    public String toString() {
        return "Linea [codigo=" + codigo + ", sentido=" + sentido + ", paradasRegreso=" + paradasVuelta
                + ", paradasIda=" + paradasIda + "]";
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