package negocio.exceptions;

/**
 * Excepción lanzada cuando se intenta agregar una parada que ya existe en el
 * sistema.
 * Esta excepción indica un intento de duplicación de paradas, lo cual no está
 * permitido.
 */
public class ParadaExistenteException extends RuntimeException {
}
