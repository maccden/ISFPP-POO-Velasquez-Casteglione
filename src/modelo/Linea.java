package modelo;

import java.util.ArrayList;
import java.util.List;

public class Linea {
    private String codigo;
    private int comienza;
    private int finaliza;
    private int frecuencia;
    private List<Parada> paradas;

    public Linea(String codigo, int comienza, int finaliza, int frecuencia) {
        super();
        this.codigo = codigo;
        this.comienza = comienza;
        this.finaliza = finaliza;
        this.frecuencia = frecuencia;
        this.paradas = new ArrayList<Parada>();
    }

    public void agregarParada(Parada parada) {
        paradas.add(parada);
        parada.setLinea(this);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getComienza() {
        return comienza;
    }

    public void setComienza(int comienza) {
        this.comienza = comienza;
    }

    public int getFinaliza() {
        return finaliza;
    }

    public void setFinaliza(int finaliza) {
        this.finaliza = finaliza;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public boolean contains(Parada parada) {
        return paradas.contains(parada);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Linea other = (Linea) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return codigo;
    }
}
