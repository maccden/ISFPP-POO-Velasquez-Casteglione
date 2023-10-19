package modelo;

import datastructures.ArrayList;
import datastructures.List;

public class Parada {
    private String codigo, direccion;
    private List<Linea> lineas;

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

    public void setLinea(Linea linea) {
        if (!lineas.isEmpty())
            for (Linea l : lineas)
                if (l.equals(linea))
                    return;
        lineas.add(lineas.size(), linea);
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