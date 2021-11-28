package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import business.exceptions.AplicationException;
import business.handlers.AssociarArbitroEncontroHandler;
import facade.dto.ArbitroDTO;
import facade.dto.EncontroDTO;
import facade.dto.ProvaDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IEncontroServiceRemote;

/**
 * Classe que representa o servi�o de associar um arbitro a um encontro
 * 
 * @author grupo 002
 *
 */
@Stateless
@WebService
public class EncontroService implements IEncontroServiceRemote {

	@EJB
	private AssociarArbitroEncontroHandler aaeh;

	/* (non-Javadoc)
	 * @see application.IEncontroServiceRemote#encontrosComFaltaDeArbitros(java.lang.String)
	 */
	@Override
	public Collection<EncontroDTO> encontrosComFaltaDeArbitros(String designacaoProva) throws ApplicationException {
		try {
			return aaeh.encontrosComFaltaDeArbitros(designacaoProva);
		} catch (AplicationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see application.IEncontroServiceRemote#associarArbitroAoEncontro(int, int)
	 */
	@Override
	public Collection<ArbitroDTO> associarArbitroAoEncontro(int numeroEnc, int numFederativoArbitro)
			throws ApplicationException {
		try {
			return aaeh.associarArbitroAoEncontro(numeroEnc, numFederativoArbitro);
		} catch (AplicationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public Collection<String> getProvasDesignacao() throws ApplicationException {
		List<String> result = new ArrayList<>();
		Iterable<ProvaDTO> provas;
		try {
			provas = aaeh.getTodasProvas();
		} catch (AplicationException e) {
			throw new ApplicationException(e.getMessage());
		}
		
		provas.forEach(p -> result.add(p.getDesignacao()));
		
		return result;
	}

	@Override
	public Collection<ArbitroDTO> getArbitros() throws ApplicationException {
		try {
			return aaeh.getTodosArbitros();
		} catch (AplicationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}
}
