package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una línea de transporte público.
 */
public class Linea {

    private String codigo;
    private int comienza;
    private int finaliza;
    private int frecuencia;
    private List<Parada> paradas;

    /**
     * Constructor de la clase Linea.
     *
     * @param codigo     Código único de la línea.
     * @param comienza   Hora de inicio de la línea.
     * @param finaliza   Hora de finalización de la línea.
     * @param frecuencia Frecuencia de la línea.
     */
    public Linea(String codigo, int comienza, int finaliza, int frecuencia) {
        this.codigo = codigo;
        this.comienza = comienza;
        this.finaliza = finaliza;
        this.frecuencia = frecuencia;
        this.paradas = new ArrayList<>();
    }

    /**
     * Agrega una parada a la lista de paradas asociadas a la línea.
     *
     * @param parada Parada a agregar.
     */
    public void agregarParada(Parada parada) {
        paradas.add(parada);
        parada.setLinea(this);
    }

    /**
     * Establece la lista de paradas asociadas a la línea.
     *
     * @param paradas Lista de paradas.
     */
    public void setParadas(List<Parada> paradas) {
        this.paradas = paradas;
    }

    /**
     * Obtiene el código de la línea.
     *
     * @return Código de la línea.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código de la línea.
     *
     * @param codigo Código de la línea.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la hora de inicio de la línea.
     *
     * @return Hora de inicio de la línea.
     */
    public int getComienza() {
        return comienza;
    }

    /**
     * Establece la hora de inicio de la línea.
     *
     * @param comienza Hora de inicio de la línea.
     */
    public void setComienza(int comienza) {
        this.comienza = comienza;
    }

    /**
     * Obtiene la hora de fin de la línea.
     *
     * @return Hora de fin de la línea.
     */
    public int getFinaliza() {
        return finaliza;
    }

    /**
     * Establece la hora de fin de la línea.
     *
     * @param finaliza Hora de fin de la línea.
     */
    public void setFinaliza(int finaliza) {
        this.finaliza = finaliza;
    }

    /**
     * Obtiene la frecuencia de la línea.
     *
     * @return Frecuencia de la línea.
     */
    public int getFrecuencia() {
        return frecuencia;
    }

    /**
     * Establece la frecuencia de la línea.
     *
     * @param frecuencia Frecuencia de la línea.
     */
    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    /**
     * Obtiene la lista de paradas de la línea.
     *
     * @return Lista de paradas de la línea.
     */
    public List<Parada> getParadas() {
        List<Parada> paradaList = new ArrayList<>();
        paradaList.addAll(paradas);
        return paradaList;
    }

    /**
     * Verifica si la línea contiene una parada específica.
     *
     * @param parada Parada a verificar.
     * @return true si la línea contiene la parada, false en caso contrario.
     */
    public boolean contains(Parada parada) {
        return paradas.contains(parada);
    }

    /**
     * Sobrescribe el método equals para la clase Linea.
     *
     * @param obj Objeto a comparar.
     * @return true si las líneas son iguales, false en caso contrario.
     */
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

    /**
     * Sobrescribe el método toString para la clase Linea.
     *
     * @return Representación en cadena de la línea.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Linea{");
        sb.append("codigo='").append(codigo).append('\'');
        sb.append(", comienza=").append(comienza);
        sb.append(", finaliza=").append(finaliza);
        sb.append(", frecuencia=").append(frecuencia);
        sb.append(", paradas=");
        for (Parada parada : paradas)
            sb.append(parada.getCodigo());
        sb.append('}');
        return sb.toString();
    }
}
