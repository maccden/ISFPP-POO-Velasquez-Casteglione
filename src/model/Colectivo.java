package model;

public class Colectivo {
    private String codigo;
    private Linea linea;

    public Colectivo(String codigo, Linea linea) {
        this.codigo = codigo;
        this.linea = linea;
    }

    public String getCodigo() {
        return codigo;
    }

    public Linea getLinea() {
        return linea;
    }

    @Override
    public String toString() {
        return "Colectivo [id=" + codigo + ", linea=" + linea + "]";
    }

    @Override
    public boolean equals(Object obj) {
        Colectivo other = (Colectivo) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }
}