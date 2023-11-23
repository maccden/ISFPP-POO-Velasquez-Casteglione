package negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un sujeto observable.
 */
public class Subject {

	private List<Observer> observers = new ArrayList<>();

	/**
	 * Notifica a todos los observadores registrados que ha ocurrido un cambio.
	 */
	public void refresh() {
		notifyAllObservers();
	}

	/**
	 * Agrega un observador a la lista de observadores.
	 *
	 * @param observer Observador a agregar.
	 */
	public void attach(Observer observer) {
		observers.add(observer);
	}

	/**
	 * Elimina un observador de la lista de observadores.
	 *
	 * @param observer Observador a eliminar.
	 */
	public void detach(Observer observer) {
		observers.remove(observer);
	}

	/**
	 * Notifica a todos los observadores en la lista.
	 */
	private void notifyAllObservers() {
		for (Observer observer : observers)
			observer.update();
	}
}
