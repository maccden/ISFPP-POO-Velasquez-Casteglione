package model;

import datastructures.ArrayList;

public class Parada {
    private String codigo, direccion;
    private ArrayList<Linea> lineas = new ArrayList<>();

    public Parada(String codigo, String direccion) {
        this.codigo = codigo;
        this.direccion = direccion;
    }
    public String getCodigo() { return codigo; }

    public String getDireccion() {
        return direccion;
    }

    public Linea getLinea() { return null; }

    public void setLinea(Linea linea) {
        for (Linea l : lineas) {
            if (l.equals(linea))
                return;
        }

        lineas.add(lineas.size(), linea);
    }

    @Override
    public String toString() {
        return "Parada [codigo=" + codigo + ", direccion=" + direccion + ", lineas=" + lineas + "]";
    }

    @Override
    public boolean equals(Object obj) {
        Parada other = (Parada) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }
}