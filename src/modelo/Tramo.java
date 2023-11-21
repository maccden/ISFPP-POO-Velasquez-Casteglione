package modelo;

import java.util.Objects;

/**
 * La clase Tramo representa un tramo entre dos paradas en una red de transporte
 * público.
 */
public class Tramo {

    private Parada inicio, fin;
    private int tiempo, tipo;

    /**
     * Constructor por defecto de la clase Tramo.
     */
    public Tramo() {
    }

    /**
     * Constructor de la clase Tramo con parada de inicio, parada de fin, tiempo y
     * tipo.
     *
     * @param inicio Parada de inicio del tramo.
     * @param fin    Parada de fin del tramo.
     * @param tiempo Tiempo que tarda en recorrer el tramo.
     * @param tipo   Tipo de tramo.
     */
    public Tramo(Parada inicio, Parada fin, int tiempo, int tipo) {
        this.inicio = inicio;
        this.fin = fin;
        this.tiempo = tiempo;
        this.tipo = tipo;
    }

    /**
     * Calcula el promedio de congestión entre las líneas asociadas a las paradas de
     * inicio y fin.
     *
     * @return Promedio de congestión.
     */
    public int getCongestion() {
        int promedioCongestion = 0;
        for (Linea linea : inicio.getLineas())
            promedioCongestion += linea.getFrecuencia();
        for (Linea linea : fin.getLineas())
            promedioCongestion += linea.getFrecuencia();
        return promedioCongestion / (inicio.getLineas().size() + fin.getLineas().size());
    }

    /**
     * Obtiene la parada de inicio del tramo.
     *
     * @return Parada de inicio del tramo.
     */
    public Parada getInicio() {
        return inicio;
    }

    /**
     * Obtiene la parada de fin del tramo.
     *
     * @return Parada de fin del tramo.
     */
    public Parada getFin() {
        return fin;
    }

    /**
     * Obtiene el tiempo que tarda en recorrer el tramo.
     *
     * @return Tiempo del tramo.
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * Obtiene el tipo de tramo.
     *
     * @return Tipo de tramo.
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Establece la parada de inicio del tramo.
     *
     * @param inicio Parada de inicio del tramo.
     */
    public void setInicio(Parada inicio) {
        this.inicio = inicio;
    }

    /**
     * Establece la parada de fin del tramo.
     *
     * @param fin Parada de fin del tramo.
     */
    public void setFin(Parada fin) {
        this.fin = fin;
    }

    /**
     * Establece el tiempo que tarda en recorrer el tramo.
     *
     * @param tiempo Tiempo del tramo.
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * Establece el tipo de tramo.
     *
     * @param tipo Tipo de tramo.
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Genera una representación en cadena del tramo.
     *
     * @return Representación en cadena del tramo.
     */
    @Override
    public String toString() {
        return "Tramo [inicio=" + inicio + ", fin=" + fin + ", tiempo=" + tiempo + ", tipo=" + tipo + "]";
    }

    /**
     * Compara el tramo con otro objeto para verificar si son iguales. Dos tramos
     * son iguales si tienen el mismo tipo y las mismas paradas de inicio y fin.
     *
     * @param o Objeto a comparar.
     * @return `true` si son iguales, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Tramo))
            return false;
        Tramo tramo = (Tramo) o;
        return tipo == tramo.tipo && Objects.equals(inicio, tramo.inicio) && Objects.equals(fin, tramo.fin);
    }
}
