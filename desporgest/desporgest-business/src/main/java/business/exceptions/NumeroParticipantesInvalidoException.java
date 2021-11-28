package business.exceptions;


/**
 * Representa a excep��o da cria��o de uma prova com um numero de particpantes
 * inv�lido
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
