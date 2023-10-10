package model;

import datastructures.List;

public class Parada {
    private String codigo, direccion;
    private List<Linea> lineas;

    public Parada(String codigo, String direccion, List<Linea> lineas) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.lineas = lineas;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDireccion() {
        return direccion;
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