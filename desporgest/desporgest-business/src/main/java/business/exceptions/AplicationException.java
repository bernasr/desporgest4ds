package business.exceptions;

/**
 * Representa uma excep��o geral da aplica��o
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
