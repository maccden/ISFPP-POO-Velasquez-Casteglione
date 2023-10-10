package model;

import datastructures.List;

public class Linea {
    private String codigo;
    private char sentido;
    private List<Parada> paradasVuelta, paradasIda;

    public Linea(String codigo, char sentido, List<Parada> paradasIda, List<Parada> paradasVuelta) {
        this.codigo = codigo;
        this.sentido = sentido;
        this.paradasIda = paradasIda;
        this.paradasVuelta = paradasVuelta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public char getSentido() {
        return sentido;
    }

    public void setSentido(char sentido) {
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