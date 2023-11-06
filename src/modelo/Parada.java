package modelo;

import datastructures.ArrayList;
import datastructures.List;

public class Parada {
    private int codigo;
    private String direccion;
    private List<Linea> lineas = new ArrayList<>();

    public Parada() {

    }

    public Parada(int codigo, String direccion) {
        this.codigo = codigo;
        this.direccion = direccion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Linea> getLineas() { return lineas; }

    public boolean isLinea(Linea linea) {
        for (Linea l: lineas)
            if (l.equals(linea))
                return true;
        return false;
    }

    public void setLinea(Linea linea) {
        if (!lineas.isEmpty())
            for (Linea l : lineas)
                if (l.equals(linea))
                    return;
        lineas.add(lineas.size(), linea);
    }

    public void removeLinea(Linea linea) {
        for (int i = 0; i < lineas.size(); i++) {
            if (lineas.get(i).equals(linea)) {
                lineas.remove(i);
                return;
            }
        }
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Parada{");
        sb.append("codigo='").append(codigo).append('\'');
        sb.append(", direccion='").append(direccion).append('\'');
        sb.append(", lineas=");
        for (Linea linea : lineas)
            sb.append(linea.getCodigo());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parada parada)) return false;
        return codigo == parada.codigo;
    }
}