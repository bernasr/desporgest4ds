package business.exceptions;

/**
 * Representa a excepção da criação de uma prova numa data invalida
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
