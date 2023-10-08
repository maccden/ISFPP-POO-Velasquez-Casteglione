package model;

public class Parada {
    private String codigo, direccion;

    public Parada(String codigo, String direccion) {
        this.codigo = codigo;
        this.direccion = direccion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Parada [codigo=" + codigo + ", direccion=" + direccion + "]";
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