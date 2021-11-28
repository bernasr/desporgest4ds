package business.exceptions;

/**
 * Representa uma excepção geral da aplicação
 * 
 * @author Grupo 2
 *
 */
public class AplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AplicationException() {}

	public AplicationException(String message) {
		super(message);
	}

}
