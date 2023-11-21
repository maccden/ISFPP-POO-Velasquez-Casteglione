package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Parada representa una parada de transporte público con un código
 * único, una dirección y la lista de líneas asociadas.
 */
public class Parada {

    private int codigo;
    private String direccion;
    private List<Linea> lineas;

    /**
     * Constructor por defecto de la clase Parada. Inicializa la lista de líneas.
     */
    public Parada() {
        lineas = new ArrayList<>();
    }

    /**
     * Constructor de la clase Parada con código y dirección.
     *
     * @param codigo    Código único de la parada.
     * @param direccion Dirección de la parada.
     */
    public Parada(int codigo, String direccion) {
        this.codigo = codigo;
        this.direccion = direccion;
    }

    /**
     * Obtiene el código de la parada.
     *
     * @return Código de la parada.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene la dirección de la parada.
     *
     * @return Dirección de la parada.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Obtiene una lista nueva que contiene las líneas asociadas a la parada.
     *
     * @return Lista de líneas asociadas a la parada.
     */
    public List<Linea> getLineas() {
        return new ArrayList<>(lineas);
    }

    /**
     * Verifica si la parada está asociada a una línea específica.
     *
     * @param linea Línea a verificar.
     * @return `true` si la parada está asociada a la línea, de lo contrario
     *         `false`.
     */
    public boolean isLinea(Linea linea) {
        for (Linea l : lineas)
            if (l.equals(linea))
                return true;
        return false;
    }

    /**
     * Establece la asociación de la parada con una línea. Si la parada ya está
     * asociada a la línea, no realiza ninguna acción.
     *
     * @param linea Línea a asociar.
     */
    public void setLinea(Linea linea) {
        if (!lineas.isEmpty())
            for (Linea l : lineas)
                if (l.equals(linea))
                    return;
        lineas.add(lineas.size(), linea);
    }

    /**
     * Elimina la asociación de la parada con una línea específica.
     *
     * @param linea Línea a desasociar.
     */
    public void removeLinea(Linea linea) {
        for (int i = 0; i < lineas.size(); i++)
            if (lineas.get(i).equals(linea)) {
                lineas.remove(i);
                return;
            }
    }

    /**
     * Establece el código de la parada.
     *
     * @param codigo Código de la parada.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece la dirección de la parada.
     *
     * @param direccion Dirección de la parada.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Genera una representación en cadena de la parada.
     *
     * @return Representación en cadena de la parada.
     */
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

    /**
     * Compara la parada con otro objeto para verificar si son iguales. Dos paradas
     * son iguales si tienen el mismo código.
     *
     * @param o Objeto a comparar.
     * @return `true` si son iguales, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Parada))
            return false;
        Parada parada = (Parada) o;
        return codigo == parada.codigo;
    }
}
