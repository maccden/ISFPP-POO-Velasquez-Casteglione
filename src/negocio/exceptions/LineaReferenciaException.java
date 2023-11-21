package negocio.exceptions;

/**
 * Excepción lanzada cuando se intenta realizar una operación que hace
 * referencia a una línea inexistente o no válida.
 * Por ejemplo, puede ser lanzada al intentar realizar una operación que
 * involucra una línea que no está registrada en el sistema.
 */
public class LineaReferenciaException extends RuntimeException {
}
