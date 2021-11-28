package business.exceptions;

/**
 * Representa a excep��o da cria��o de uma prova numa data invalida
 * 
 * @author Grupo 2
 *
 */
public class DataInicioInvalidaException extends AplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataInicioInvalidaException(String m) {
		super(m);
	}
}
