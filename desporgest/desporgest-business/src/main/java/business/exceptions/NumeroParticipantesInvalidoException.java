package business.exceptions;


/**
 * Representa a excepção da criação de uma prova com um numero de particpantes
 * inválido
 * 
 * @author Grupo 2
 *
 */
public class NumeroParticipantesInvalidoException extends AplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NumeroParticipantesInvalidoException(String s) {
		super(s);
	}

}
