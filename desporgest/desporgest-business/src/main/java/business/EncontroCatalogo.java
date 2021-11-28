package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import business.exceptions.AplicationException;

/**
 * 
 * @author grupo 002
 *
 */
@Stateless
public class EncontroCatalogo {
	@PersistenceContext
	private EntityManager em;
	
	public Encontro getEncontro(int numeroEncontro) throws AplicationException {
		TypedQuery<Encontro> query = em.createNamedQuery(Encontro.FIND_BY_NUMBER,
				Encontro.class);
		query.setParameter(Encontro.NUMERO, numeroEncontro);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new AplicationException("Encontro com o n�mero " + numeroEncontro
					+ " n�o existe ou tem informa��o completa!");
		}
	}
}
