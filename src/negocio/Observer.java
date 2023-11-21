package negocio;

/**
 * Interfaz que define el comportamiento de un observador.
 */
public interface Observer {
	
	/**
	 * Método que se llama cuando hay un cambio en el estado del sujeto observado.
	 */
	void update();
}
