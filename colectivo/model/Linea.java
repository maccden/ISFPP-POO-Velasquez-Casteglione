package colectivo.model;

import colectivo.net.datastructures.ArrayList;

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
}