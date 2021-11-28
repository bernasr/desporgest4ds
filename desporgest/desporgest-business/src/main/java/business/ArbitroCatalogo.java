package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import business.exceptions.AplicationException;

/**
 * Classe que permite criar e aceder aos arbitros
 * 
 * @author Grupo 2
 *
 */
@Stateless
public class ArbitroCatalogo {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Cria um novo arbitro e presiste na base de dados
	 * @param numeroFederativo - Numero federativo do arbitro
	 */
	public void criarNovo(int numeroFederativo) {
		Arbitro arb = new Arbitro(numeroFederativo);
		em.persist(arb);
	}

	/**
	 * Seleciona um arbitro da base de dados
	 * @param numeroFederativo - Numero federativo do arbitro
	 * @return O Arbitro com o numero federativo correspondente
	 * @throws AplicationException - Se n�o existir arbitro com esse numero
	 * federativo
	 */
	public Arbitro getArbitro(int numeroFederativo) throws AplicationException {
		TypedQuery<Arbitro> query = em.createNamedQuery(Arbitro.FIND_BY_NUMERO,
				Arbitro.class);
		query.setParameter(Arbitro.NUMBER, numeroFederativo);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new AplicationException("�rbitro com n�mero federativo "
					+ numeroFederativo + " n�o existe!");
		}
	}

	public Iterable<Arbitro> getTodosArbitros() throws AplicationException {
		try {
			TypedQuery<Arbitro> query = em.createNamedQuery(Arbitro.FIND_ALL, Arbitro.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new AplicationException("Erro ao obter lista de árbitros");
		}
	}
}
