package application;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import business.Periodicidade;
import business.TipoProva;
import business.exceptions.AplicationException;
import business.handlers.CriarProvaDesportivaHandler;
import facade.dto.PeriodicidadeDTO;
import facade.dto.ProvaDTO;
import facade.dto.TipoProvaDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IProvaServiceRemote;

/**
 * Classe que representa o servi�o de criar uma prova
 * 
 * @author Grupo 2
 *
 */
@Stateless
@WebService
public class ProvaService implements IProvaServiceRemote {

	@EJB
	private CriarProvaDesportivaHandler cpdh;

	/**
	 * Cria uma nova prova do tipo taça
	 * 
	 * @param designacao - Designa��o da prova
	 * @param numPartic - Numero de participantes
	 * @param numArbitroPorEnc - Numero de arbitros por encontro
	 * @param dataInicio - Data de inicio da prova
	 * @param periodicidade - Periodicidade das fases da prova
	 * @throws AplicationException - Se n�o foi possivel criar a prova
	 */
	private ProvaDTO criarTaca(String designacao, int numPartic,
			int numArbitroPorEnc, String dataInicio, int periodicidade)
					throws ApplicationException {
		try {
			return cpdh.criarTaca(designacao, numPartic, numArbitroPorEnc,
					dataInicio, periodicidade);
		} catch (AplicationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	/**
	 * Cria uma nova prova do tipo campeonato
	 * 
	 * @param designacao - Designa��o da prova
	 * @param numPartic - Numero de participantes
	 * @param numArbitroPorEnc - Numero de arbitros por encontro
	 * @param dataInicio - Data de inicio da prova
	 * @param periodicidade - Periodicidade das fases da prova
	 * @throws AplicationException - Se n�o foi possivel criar a prova
	 */
	private ProvaDTO criarCompeticao(String designacao, int numPartic,
			int numArbitroPorEnc, String dataInicio, int periodicidade)
					throws ApplicationException {
		try {
			return cpdh.criarCompeticao(designacao, numPartic, numArbitroPorEnc,
					dataInicio, periodicidade);
		} catch (AplicationException e) {
			throw new ApplicationException(e.getMessage());
		}

	}
	
	@Override
	public ProvaDTO criarProva(int tipo, String designacao, int numPartic,
			int numArbitroPorEnc, String dataInicio, int periodicidade) throws ApplicationException {
		if (tipo == TipoProva.COMPETICAO.ordinal())
			return criarCompeticao(designacao, numPartic, numArbitroPorEnc, dataInicio, periodicidade);
		if (tipo == TipoProva.TACA.ordinal())
			return criarTaca(designacao, numPartic, numArbitroPorEnc, dataInicio, periodicidade);
		throw new ApplicationException("Prova desse tipo não existe");
	}

	@Override
	public List<PeriodicidadeDTO> getPeriodicidades() {
		List<PeriodicidadeDTO> result = new ArrayList<>();

		for (Periodicidade p : Periodicidade.values())
			result.add(new PeriodicidadeDTO(p.ordinal(), p.toString()));

		return result;
	}

	@Override
	public List<TipoProvaDTO> getTiposDeProvas() {
		List<TipoProvaDTO> result = new ArrayList<>();
		for (TipoProva t : TipoProva.values())
			result.add(new TipoProvaDTO(t.ordinal(), t.toString()));
		return result;
	}
}
