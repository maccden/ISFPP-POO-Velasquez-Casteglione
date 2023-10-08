package model;

import datastructures.ArrayList;

public class Linea {
    private String codigo;
    private String nombre;
    private ArrayList<Parada> paradas;
    
    public Linea(String codigo, String nombre, ArrayList<Parada> paradas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.paradas = paradas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Parada getParada(int i) {
        return paradas.get(i);
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