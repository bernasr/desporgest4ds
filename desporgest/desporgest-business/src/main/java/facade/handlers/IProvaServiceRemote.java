package facade.handlers;

import java.util.List;

import javax.ejb.Remote;

import business.exceptions.AplicationException;
import facade.dto.PeriodicidadeDTO;
import facade.dto.ProvaDTO;
import facade.dto.TipoProvaDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IProvaServiceRemote {

	/**
	 * Cria uma nova prova
	 * 
	 * @param designacao - Designa��o da prova
	 * @param tipo - Tipo de prova (campeonato ou ta�a)
	 * @param numPartic - Numero de participantes
	 * @param numArbitroPorEnc - Numero de arbitros por encontro
	 * @param dataInicio - Data de inicio da prova
	 * @param periodicidade - Periodicidade das fases da prova
	 * @throws AplicationException - Se n�o foi possivel criar a prova
	 */
	public ProvaDTO criarProva(int tipo, String designacao, int numPartic,
			int numArbitroPorEnc, String dataInicio, int periodicidade)
					throws ApplicationException;

	/**
	 * Retorna todas as periodicidades
	 * @return Uma lista com todas as periodicidades
	 */
	public List<PeriodicidadeDTO> getPeriodicidades();
	
	/**
	 * Retorna todos os tipos de provas
	 * @return Uma lista com todos os tipos de provas
	 */
	public List<TipoProvaDTO> getTiposDeProvas();
}
