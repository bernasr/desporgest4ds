package presentation.web.model;

import java.util.ArrayList;
import java.util.Collection;

import facade.dto.ArbitroDTO;
import facade.dto.EncontroDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IEncontroServiceRemote;

public class AssociarArbitroModel extends Model {
	private String prova;
	private String numEncontro;
	private String numFederativo;
	private IEncontroServiceRemote encontroHandler;

	public String getProva() {
		return prova;
	}

	public String getNumEncontro() {
		return numEncontro;
	}

	public String getNumFederativo() {
		return numFederativo;
	}

	public void setProva(String prova) {
		this.prova = prova;
	}

	public void setNumEncontro(String numEncontro) {
		this.numEncontro = numEncontro;
	}

	public void setNumFederativo(String numFederativo) {
		this.numFederativo = numFederativo;
	}

	public void setEncontroHandler(IEncontroServiceRemote encontroHandler) {
		this.encontroHandler = encontroHandler;
	}

	public void clear() {
		prova = "";
		numEncontro = "";
		numFederativo = "";
	}

	public Iterable<String> getProvasDesignacao() {
		try {
			Collection<String> provas = encontroHandler.getProvasDesignacao();
			return provas;
		} catch (ApplicationException e) {
			return new ArrayList<>();
		}
	}

	public Iterable<EncontroDTO> getEncontros() {
		try {
			Collection<EncontroDTO> provas = encontroHandler.encontrosComFaltaDeArbitros(prova);
			return provas;
		} catch (ApplicationException e) {
			return new ArrayList<>();
		}
	}
	
	public Iterable<ArbitroDTO> getArbitros() {
		try {
			Collection<ArbitroDTO> arbitros = encontroHandler.getArbitros();
			return arbitros;
		} catch (ApplicationException e) {
			return new ArrayList<>();
		}
	}
}
