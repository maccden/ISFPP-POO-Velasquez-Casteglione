package colectivo.model;

public class Colectivo {
    private String codigo;
    private Parada parada;
    private Linea linea;

    public Colectivo(String codigo, Parada parada, Linea linea) {
        this.codigo = codigo;
        this.parada = parada;
        this.linea = linea;
    }

    public String getCodigo() {
        return codigo;
    }

    public Parada getParada() {
        return parada;
    }

    public Linea getLinea() {
        return linea;
    }

    @Override
    public String toString() {
        return "Colectivo [id=" + codigo + ", parada=" + parada + ", linea=" + linea + "]";
    }
}