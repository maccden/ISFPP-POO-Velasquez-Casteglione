package model;

import datastructures.ArrayList;
import datastructures.List;

public class Parada {
    private String codigo, direccion;
    private List<String> lineas;

    public Parada(String codigo, String direccion) {
        this.codigo = codigo;
        this.direccion = direccion;
        lineas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getLinea() {
        return null;
    }

    public void setLinea(String linea) {
        for (String l : lineas)
            if (l.equals(linea))
                return;
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