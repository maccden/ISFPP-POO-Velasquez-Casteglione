package model;

import datastructures.ArrayList;

public class Linea {
    private String codigo;
    private char sentido;
    private ArrayList<Parada> paradasIda;
    private ArrayList<Parada> paradasRegreso;

    public Linea(String codigo, char sentido, ArrayList<Parada> paradasIda, ArrayList<Parada> paradasRegreso) {
        this.codigo = codigo;
        this.sentido = sentido;
        this.paradasIda = paradasIda;
        this.paradasRegreso = paradasRegreso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public char getSentido() { return sentido; }

    public void setSentido(char sentido) { this.sentido = sentido; }

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