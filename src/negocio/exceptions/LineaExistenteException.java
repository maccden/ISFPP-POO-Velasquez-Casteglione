package negocio.exceptions;

/**
 * Excepción lanzada cuando se intenta crear una nueva línea con un código que
 * ya existe.
 * Esta excepción indica que ya hay una línea registrada con el mismo código y
 * no se puede crear una duplicada.
 */
public class LineaExistenteException extends RuntimeException {
}
