package business;

import java.time.LocalDate;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import business.exceptions.AplicationException;

/**
 * Classe que permite criar e aceder �s provas
 * 
 * @author Grupo 2
 *
 */
@Stateless
public class ProvaCatalogo {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Cria uma nova prova e persistia e presiste na base de dados
	 * @param tipo - Class com o subtipo da prova
	 * @param designacao - Designa��o da prova
	 * @param numeroParticipantes - Numero de participantes
	 * @param numeroArbitrosPorEncontro - Numero de arbitros por encontro
	 * @param dataInicio - Data de inicio da prova, isto �, dos jogos da sua
	 * primeira fase
	 * @param periodicidade - Periodicidade das fases da prova
	 * @throws AplicationException - Se a prova n�o foi criada por um dos seus
	 * atributos n�o ser v�lido
	 */
	public Prova criarTaca(String designacao,
			int numeroParticipantes, int numeroArbitrosPorEncontro,
			LocalDate dataInicio, Periodicidade periodicidade)
					throws AplicationException {
		provaExiste(designacao);
		Prova prova = new Taca(designacao, numeroParticipantes,
				numeroArbitrosPorEncontro, dataInicio, periodicidade);
		em.persist(prova);
		return prova;
	}
	
	private void provaExiste(String designacao) throws AplicationException {
		boolean provaExiste = false;
		try {
			if (getProva(designacao) != null)
				provaExiste = true;
		} catch (AplicationException ae) {}
		if (provaExiste)
			throw new AplicationException("Prova com nome \"" + designacao + "\" já existe");
	}

	public Prova criarCompeticao(String designacao,
			int numeroParticipantes, int numeroArbitrosPorEncontro,
			LocalDate dataInicio, Periodicidade periodicidade)
					throws AplicationException {
		provaExiste(designacao);
		Prova prova = new Campeonato(designacao, numeroParticipantes,
				numeroArbitrosPorEncontro, dataInicio, periodicidade);
		em.persist(prova);
		return prova;
	}

	/**
	 * Seleciona uma prova da base de dados
	 * @param designacaoProva - Designa��o da prova
	 * @return A prova selecionada com a desinga��o indicada
	 * @throws AplicationException - Se n�o exite um prova com essa designa��o
	 */
	public Prova getProva(String designacaoProva) throws AplicationException {
		TypedQuery<Prova> query = em.createNamedQuery(Prova.FIND_BY_DESIGNACAO,
				Prova.class);
		query.setParameter(Prova.DESIGNACAO, designacaoProva);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new AplicationException(
					"Prova com designa��o " + designacaoProva + " n�o existe!");
		}
	}

	public Iterable<Prova> getTodasProvas() throws AplicationException {
		try {
			TypedQuery<Prova> query = em.createNamedQuery(Prova.FIND_ALL, Prova.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new AplicationException("Erro ao obter lista de provas");
		}
	}
}
