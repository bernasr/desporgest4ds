package facade.handlers;

import java.util.Collection;

import javax.ejb.Remote;

import business.exceptions.AplicationException;
import facade.dto.ArbitroDTO;
import facade.dto.EncontroDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IEncontroServiceRemote {

	/**
	 * Retorna os encontros com falta de arbitros
	 * @return uma colecao iteravel com os encontros com falta de arbitros
	 * @throws AplicationException
	 */
	Collection<EncontroDTO> encontrosComFaltaDeArbitros(String designacaoProva)
			throws ApplicationException;

	/**
	 * Cria a associ��o entre o arbitro e o encontro
	 * @param numeroEnc - Numero do encontro a associar
	 * @param numFederativoArbitro - Numero federativo do arbitro a associar
	 * @throws AplicationException - Se n�o foi possivel realizar a associa��o
	 */
	Collection<ArbitroDTO> associarArbitroAoEncontro(int numeroEnc,
			int numFederativoArbitro) throws ApplicationException;

	/**
	 * Retorna o nome de todas as provas
	 * @return Uma colecao com o nome de todas as provas
	 * @throws ApplicationException - Se não foi possivel encontrar as provas
	 */
	Collection<String> getProvasDesignacao() throws ApplicationException;

	/**
	 * Retorna todos os arbitros
	 * @return Todos os arbitros
	 * @throws ApplicationException - Se não foi possivel encontrar os arbitros
	 */
	Collection<ArbitroDTO> getArbitros() throws ApplicationException;

}