package negocio.exceptions;

/**
 * Excepción lanzada cuando se hace referencia a un tramo que no existe en el
 * sistema.
 * Esta excepción indica que se está haciendo referencia a un tramo que no está
 * presente
 * en la base de datos o en el contexto actual.
 */
public class TramoReferenciaException extends RuntimeException {
}
