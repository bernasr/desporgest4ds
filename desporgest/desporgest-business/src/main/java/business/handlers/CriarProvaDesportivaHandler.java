package business.handlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.Periodicidade;
import business.Prova;
import business.ProvaCatalogo;
import business.exceptions.AplicationException;
import business.exceptions.NumeroParticipantesInvalidoException;
import facade.dto.EncontroDTO;
import facade.dto.ProvaDTO;

/**
 * Classe que representa o handler do caso de uso de criar uma prova
 * 
 * @author Grupo 2
 *
 */
@Stateless
public class CriarProvaDesportivaHandler {

	@EJB
	private ProvaCatalogo provaCatalogo;

	/**
	 * Cria uma nova prova e persistia na base de dados
	 * @param designacao - Designa��o da prova
	 * @param tipo - Tipo de prova (campeonato ou ta�a)
	 * @param numPartic - Numero de participantes
	 * @param numArbitroPorEnc - Numero de arbitros por encontro
	 * @param dataInicio - Data de inicio da prova
	 * @param periodicidade - Periodicidade das fases da prova
	 * @throws AplicationException - Se n�o foi possivel criar a prova
	 */
	public ProvaDTO criarTaca(String designacao, int numPartic,
			int numArbitroPorEnc, String dataInicio, int periodicidade)
					throws AplicationException {
		verificarParticipantesTaca(numPartic);
		LocalDate date = LocalDate.parse(dataInicio);
		Periodicidade period = Periodicidade.values()[periodicidade];

		Prova p = provaCatalogo.criarTaca(designacao, numPartic, numArbitroPorEnc,
				date, period);
		return new ProvaDTO(p.getDesignacao(), p.getNumeroParticipantes(), p.numeroArbitrosPorEncontro(), getEncontros(p));
	}

	private List<EncontroDTO> getEncontros(Prova prova) {
		List<EncontroDTO> result = new ArrayList<>();
		prova.getFases().forEach(f -> f.getEncontros().forEach(e -> {
			EncontroDTO dto = new EncontroDTO(e.getNumero(), e.getDataRealizacao(), e.participante1(), e.participante2(), e.getFase());
			result.add(dto);
		}));

		return result;
	}

	public ProvaDTO criarCompeticao(String designacao, int numPartic,
			int numArbitroPorEnc, String dataInicio, int periodicidade)
					throws AplicationException {
		verificarParticipantesCampeonato(numPartic);
		LocalDate date = LocalDate.parse(dataInicio);
		Periodicidade period = Periodicidade.values()[periodicidade];

		Prova p = provaCatalogo.criarCompeticao(designacao, numPartic, numArbitroPorEnc,
				date, period);
		return new ProvaDTO(p.getDesignacao(), p.getNumeroParticipantes(), p.numeroArbitrosPorEncontro(), getEncontros(p));
	}
	
	/**
	 * Verifica se o numero de participante de uma prova geral, isto �, tem
	 * de ser um numero positivo
	 * @throws NumeroParticipantesInvalidoException - Se o numero de
	 * n�o for positivo
	 */
	private void verificarParticipantesGeral(int numeroParticipantes)
			throws NumeroParticipantesInvalidoException {
		if (numeroParticipantes <= 0)
			throw new NumeroParticipantesInvalidoException(
					"O n�mero de participante tem de ser positivo");
	}
	
	/**
	 * Verifica se o numero de participante de uma prova do tipo taca, isto �, tem
	 * de ser uma potencia de 2
	 * @param numeroParticipantes
	 * @throws NumeroParticipantesInvalidoException - Se não for uma potencia
	 * de dois
	 */
	private void verificarParticipantesTaca(int numeroParticipantes)
			throws NumeroParticipantesInvalidoException {
		verificarParticipantesGeral(numeroParticipantes);
		int n = numeroParticipantes;
		if ((n & (n - 1)) != 0)
			throw new NumeroParticipantesInvalidoException(
					"N�mero de participantes numa ta�a tem de ser"
					+ " uma pot�ncia de 2");
	}
	
	/**
	 * Verifica se o numero de participante de uma prova do tipo campeonato, isto �, tem
	 * de ser um numero par
	 * @param numeroParticipantes
	 * @throws NumeroParticipantesInvalidoException - Se o numero nao for par
	 */
	private void verificarParticipantesCampeonato(int numeroParticipantes)
			throws NumeroParticipantesInvalidoException {
		verificarParticipantesGeral(numeroParticipantes);
		if (numeroParticipantes % 2 != 0)
			throw new NumeroParticipantesInvalidoException(
					"N�mero de participantes num campeonato tem de ser"
					+ " um n�mero par");
	}
}
