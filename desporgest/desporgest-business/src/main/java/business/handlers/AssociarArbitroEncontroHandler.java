package business.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.Arbitro;
import business.ArbitroCatalogo;
import business.Encontro;
import business.EncontroCatalogo;
import business.Prova;
import business.ProvaCatalogo;
import business.exceptions.AplicationException;
import facade.dto.ArbitroDTO;
import facade.dto.EncontroDTO;
import facade.dto.ProvaDTO;

/**
 * Classe que representa o handler do caso de uso de associar um arbitro
 * a um encontro
 * 
 * @author Grupo 2
 *
 */
@Stateless
public class AssociarArbitroEncontroHandler {

	@EJB
	private ProvaCatalogo provaCatalogo;
	@EJB
	private ArbitroCatalogo arbitroCatalogo;
	@EJB
	private EncontroCatalogo encontroCatalogo;

	/**
	 * Retorna os encontros com falta de arbitros
	 * @return uma colecao iteravel com os encontros com falta de arbitros
	 * @throws AplicationException 
	 */
	public Collection<EncontroDTO> encontrosComFaltaDeArbitros(String designacaoProva) throws AplicationException {
		Prova prova = provaCatalogo.getProva(designacaoProva);
		Iterable<Encontro> encontros = prova.encontrosComFaltaDeArbitros();
		return getEncontrosDTOs(encontros);
	}

	/**
	 * Passa de Encontros a EncontrosDTO
	 * @param encontros - Encontros a passar a DTO
	 * @return Uma colecao de EncontrosDTO
	 */
	private Collection<EncontroDTO> getEncontrosDTOs(Iterable<Encontro> encontros) {
		List<EncontroDTO> result = new ArrayList<>();

		encontros.forEach(e -> {
			EncontroDTO dto = new EncontroDTO(e.getNumero(), e.getDataRealizacao(), e.participante1(), e.participante2(), e.getFase());
			result.add(dto);
		});

		return result;
	}

	/**
	 * Finaliza a associ��o entre o encontro e o arbitro escolhido,
	 * persistindo-a na base de dados
	 * @param numeroEnc - Numero do encontro a associar
	 * @param numFederativoArbitro - Numero federativo do arbitro a associar
	 * @throws AplicationException - Se n�o foi possivel realizar a associa��o
	 */
	public Collection<ArbitroDTO> associarArbitroAoEncontro(int numeroEnc, int numFederativoArbitro)
			throws AplicationException {

		Arbitro arb = arbitroCatalogo.getArbitro(numFederativoArbitro);
		Encontro enc = encontroCatalogo.getEncontro(numeroEnc);
		Prova prova = enc.getProva();

		if (arb.estaArbitrar(enc.getDataRealizacao()))
			throw new AplicationException(
					"Arbitro j� est� arbitrar nesse dia");
		if (prova.arbitraNestaFase(arb, enc))
			throw new AplicationException(
					"Arbitro j� est� arbitrar um encontro nessa fase");
		if (prova.arbitraOutraMao(arb, enc))
			throw new AplicationException(
					"Arbitro j� est� arbitrar a outra m�o do encontro");

		arb.adicionarEncontro(enc);
		enc.adicionarArbitro(arb);
		Iterable<Arbitro> arbitros = enc.getArbitros();
		return getArbitrosDTOs(arbitros);
	}

	/**
	 * Passa de Arbitros a ArbitrosDTO
	 * @param arbitros - Arbitros a passar a DTO
	 * @return Uma colecao de ArbitrosDTO
	 */
	private Collection<ArbitroDTO> getArbitrosDTOs(Iterable<Arbitro> arbitros) {
		List<ArbitroDTO> result = new ArrayList<>();

		arbitros.forEach(a -> result.add(new ArbitroDTO(a.getNumeroFederativo())));

		return result;
	}

	/**
	 * Retorna todas as provas
	 * @return todas as provas em DTO
	 * @throws ApplicationException - Se não foi possivel aceder às provas
	 */
	public Iterable<ProvaDTO> getTodasProvas() throws AplicationException {
		List<ProvaDTO> result = new ArrayList<>();
		Iterable<Prova> provas = provaCatalogo.getTodasProvas();
		
		for (Prova p : provas) {
			result.add(new ProvaDTO(p.getDesignacao(), p.getNumeroParticipantes(), p.numeroArbitrosPorEncontro(), getEncontros(p)));
		}
		
		return result;
	}

	/**
	 * Retorna todos os encontros de prova
	 * @param prova - A prova a retirar os encontros
	 * @return Todos os encontros da prova em formato DTO
	 */
	private List<EncontroDTO> getEncontros(Prova prova) {
		List<EncontroDTO> result = new ArrayList<>();
		prova.getFases().forEach(f -> f.getEncontros().forEach(e -> {
			EncontroDTO dto = new EncontroDTO(e.getNumero(), e.getDataRealizacao(), e.participante1(), e.participante2(), e.getFase());
			result.add(dto);
		}));

		return result;
	}

	/**
	 * Recolhe todos os arbitros do sistema
	 * @return Uma coleção com todos os arbitros do sistema, em formato DTO
	 * @throws ApplicationException - Se não foi possivel aceder aos arbitros
	 */
	public Collection<ArbitroDTO> getTodosArbitros() throws AplicationException {
		List<ArbitroDTO> result = new ArrayList<>();
		Iterable<Arbitro> arbitros =  arbitroCatalogo.getTodosArbitros();
		
		for (Arbitro a : arbitros) {
			ArbitroDTO dto = new ArbitroDTO(a.getNumeroFederativo());
			result.add(dto);
		}
		
		return result;
	}
}
