package negocio.exceptions;

/**
 * Excepción lanzada cuando se intenta agregar un tramo que ya existe en el
 * sistema.
 * Esta excepción indica un intento de duplicación de tramos, lo cual no está
 * permitido.
 */
public class TramoExistenteException extends RuntimeException {
}
